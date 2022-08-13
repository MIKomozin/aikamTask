import model.error.ErrorOut;
import model.search.*;
import model.stat.Customer;
import model.stat.Purchase;
import model.stat.RootInputForStat;
import model.stat.RootOutputForStat;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DataFromDB {

    public static final String URL = "jdbc:postgresql://localhost:5432/aikam";
    public static final String USER = "Maksim";
    public static final String PASSWORD = null;

    public void takeDataFromDataAndWriteToJsonFileForSearch(RootInputForSearch rootInputForSearch) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*
сопоставим данные полученные из файла json парсингом в объекты класса Сriteria с БД и получим необходимых нам данные.
На основании полученных данных создадим объекты класса Result, из которых состоят (ArrayList<Result> results) объекты класса Resultat,
из которых в свою очередь состоит (ArrayList<Resultat> resultats) объект класса RootOutputForSearch, который мы и будем преобразовывать в формат json
*/
        //объект класса, который будем преобразовывать в формат json
        RootOutputForSearch rootOut = new RootOutputForSearch();
        rootOut.setType("search");
        ArrayList<Resultat> resultats = new ArrayList<>();

//после того, как мы спарсили входной файл, то у нас получился объект класса RootInputForSearch с полем ArrayList<Criteria> criterias
//пройдемся циклом по полученным объектам класса Criteria, на основании которых получим данные из БД

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            for (Criteria criteria: rootInputForSearch.getCriterias()) {
                Resultat resultat = new Resultat();
                ArrayList<Result> results = new ArrayList<>();
                PreparedStatement preparedStatement;
                if (criteria.getLastName() != null && criteria.getProductName() == null && criteria.getMinTimes() == null
                && criteria.getMinExpenses() == null && criteria.getMaxExpenses() == null && criteria.getBadCustomers() == null) {
                    String sqlQuery = "SELECT first_name, last_name FROM buyers WHERE last_name = ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, criteria.getLastName());
                } else if (criteria.getProductName() != null && criteria.getMinTimes() != null && criteria.getLastName() == null
                        && criteria.getMinExpenses() == null && criteria.getMaxExpenses() == null && criteria.getBadCustomers() == null) {
                    String sqlQuery = "SELECT first_name, last_name, count(*) FROM buyers\n" +
                            "JOIN purchases ON buyers.id = buyers_id\n" +
                            "JOIN products ON products_id = products.id\n" +
                            "WHERE product_name = ?\n" +
                            "GROUP BY (first_name, last_name)\n" +
                            "HAVING count(*) >= ?;";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, criteria.getProductName());
                    preparedStatement.setInt(2, criteria.getMinTimes());
                } else if (criteria.getMinExpenses() != null && criteria.getMaxExpenses() != null && criteria.getLastName() == null
                        && criteria.getProductName() == null && criteria.getMinTimes() == null && criteria.getBadCustomers() == null) {
                    String sqlQuery = "SELECT first_name, last_name, SUM(price) FROM buyers\n" +
                            "JOIN purchases ON buyers.id = buyers_id\n" +
                            "JOIN products ON products_id = products.id\n" +
                            "GROUP BY (first_name, last_name)\n" +
                            "HAVING SUM(price) >= ? AND SUM(price) <= ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setInt(1, criteria.getMinExpenses());
                    preparedStatement.setInt(2, criteria.getMaxExpenses());
                } else if (criteria.getBadCustomers() != null && criteria.getLastName() == null && criteria.getProductName() == null
                        && criteria.getMinTimes() == null && criteria.getMinExpenses() == null && criteria.getMaxExpenses() == null) {
                    String sqlQuery = "SELECT first_name, last_name, SUM(price) AS summ FROM buyers\n" +
                            "JOIN purchases ON buyers.id = buyers_id\n" +
                            "JOIN products ON products_id = products.id\n" +
                            "GROUP BY (first_name, last_name)\n" +
                            "ORDER BY summ LIMIT ?\n";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setInt(1, criteria.getBadCustomers());
                } else {
                    continue;
                }
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Result result = new Result();
                        result.setFirstName(resultSet.getString("first_name"));
                        result.setLastName(resultSet.getString("last_name"));
                        results.add(result);
                    }
                    resultat.setCriteria(criteria);
                    resultat.setResults(results);
                    resultats.add(resultat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rootOut.setResultats(resultats);

        //пишем полученные данные в файл
        new GsonParser().parseFromJavaToJsonForSearch(rootOut);

    }

    public void takeDataFromDataAndWriteToJsonFileForStat(RootInputForStat rootInputForStat) throws Exception {
        RootOutputForStat rootOut = new RootOutputForStat();
        rootOut.setType("stat");

        //количество рабочих дней между указанными датами, и проверим правильно ли введена дата
        int workDays = numberOfWorkDays(rootInputForStat.getStartDate(), rootInputForStat.getEndDate());
        rootOut.setTotalDays(workDays);

        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sqlQuery = "SELECT textcat(textcat(last_name,' '), first_name) AS full_name, product_name, price \n" +
                    "FROM buyers JOIN purchases ON buyers.id = buyers_id JOIN products ON products_id = products.id\n" +
                    "--промежуток дат с учетом выходных дней\n" +
                    "WHERE date_of_buy >= DATE(?) AND date_of_buy <= DATE(?)\n" +
                    "AND (EXTRACT(dow FROM date_of_buy) IN (1, 2, 3, 4, 5))\n" +
                    "ORDER BY full_name";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, rootInputForStat.getStartDate());
            preparedStatement.setString(2, rootInputForStat.getEndDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            String previousCustomerName = null;
            Customer customer = null;
            ArrayList<Purchase> purchases = null;
            while (resultSet.next()) {
                //для первого элемента
                if (previousCustomerName == null) {
                    customer = new Customer();//создаем объект класса Сustomer
                    purchases = new ArrayList<>();//создаем пустой массив (поле объекта customer)
                    customer.setName(resultSet.getString("full_name"));//так как имя в итерации не меняется, то зададим его сразу для нашего объекта
                }
                //если предыдущее имя клиента не равно имени клиента в данной итерации, то создаем новый объект класса Customer
                else if (!previousCustomerName.equals(resultSet.getString("full_name"))) {
                    //перед тем как добавить массив purchases отсортируем его
                    ArrayList<Purchase> sortPurchases = (ArrayList<Purchase>) purchases.stream()
                            .sorted(Comparator.comparingInt(Purchase::getExpenses).reversed())
                                    .collect(Collectors.toList());
                    customer.setPurchases(sortPurchases);
                    customers.add(customer);//добавляем в массив ArrayList<Customer> customers сформированный объект класса Customer и создаем новый
                    customer = new Customer();
                    purchases = new ArrayList<>();
                    customer.setName(resultSet.getString("full_name"));
                }

                Purchase purchase = new Purchase();
                purchase.setProductName(resultSet.getString("product_name"));
                purchase.setExpenses(resultSet.getInt("price"));
                if (purchases.contains(purchase)) {
                    int index = purchases.indexOf(purchase);//получаем индекс данного объекта
                    Purchase purchaseToChange = purchases.get(index);//получим данный объект
                    purchaseToChange.setExpenses(purchaseToChange.getExpenses() + resultSet.getInt("price"));//поменяем в нем цену
                } else {
                    purchases.add(purchase);
                }

                //каждую итерацию сохраняем измененный массив покупок класса Purchase для объекта Customer
                customer.setTotalExpenses(customer.getTotalExpenses() + resultSet.getInt("price"));//каждую итерацию добавляем стоимость товара к итоговой сумме, потраченной клиентом
                previousCustomerName = resultSet.getString("full_name");
            }
            if (customer != null) {
                //обработаем последний элемент
                ArrayList<Purchase> sortPurchases = (ArrayList<Purchase>) purchases.stream()
                        .sorted(Comparator.comparingInt(Purchase::getExpenses).reversed())
                        .collect(Collectors.toList());
                customer.setPurchases(sortPurchases);
                customers.add(customer);//добавляем последний объект класса Customer в ArrayList<Customer> customers
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //отсортируем коллекцию по убыванию TotalExpenses
        ArrayList<Customer> sortedCustomers = (ArrayList<Customer>) customers.stream()
                .sorted(Comparator.comparingInt(Customer::getTotalExpenses).reversed())
                .collect(Collectors.toList());
        rootOut.setCustomers(sortedCustomers);//добавим итоговый массив в поле объекта RootOutputForStat

        //итоговая сумма всех покупок клиентов
        int totalExpenses = 0;
        for (Customer c: customers) {
           totalExpenses = totalExpenses + c.getTotalExpenses();
        }
        rootOut.setTotalExpenses(totalExpenses);

        //средний чек
        int numberOfCustomers = customers.size();
        double avgExpenses = totalExpenses/numberOfCustomers;
        rootOut.setAvgExpenses(avgExpenses);

        //пишем полученные данные в файл
        new GsonParser().parseFromJavaToJsonForStat(rootOut);
    }

    //метод для определения количества рабочих дней между датами
    public int numberOfWorkDays(String start, String end) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = LocalDate.parse(start, formatter);
            endDate = LocalDate.parse(end, formatter);
        } catch (DateTimeParseException e) {
            ErrorOut error = new ErrorOut();
            error.setType("error");
            error.setMessage("Неправильный формат даты");
            new GsonParser().parseFromJavaToJsonForError(error);
            throw new Exception("Неправильный формат даты");
        }

        int workdays = 0;
        if (startDate.isEqual(endDate)) {
            return workdays;
        }

        //проверим граничные условия
        if (DayOfWeek.SATURDAY.equals(startDate.getDayOfWeek())
                || DayOfWeek.SUNDAY.equals(startDate.getDayOfWeek())) {
        } else {
            workdays++;
        }

        if (DayOfWeek.SATURDAY.equals(endDate.getDayOfWeek())
                || DayOfWeek.SUNDAY.equals(endDate.getDayOfWeek())) {
        } else {
            workdays++;
        }

        while(startDate.isBefore(endDate)) {
            if (DayOfWeek.SATURDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.SUNDAY.equals(startDate.getDayOfWeek())) {

            } else {
                workdays++;
            }
            startDate = startDate.plusDays(1);
        }
        return workdays;

    }
}
