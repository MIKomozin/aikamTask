import model.search.*;

import java.sql.*;
import java.util.ArrayList;

public class DataFromDB {

    public static final String URL = "jdbc:postgresql://localhost:5432/aikam";
    public static final String USER = "Maksim";
    public static final String PASSWORD = null;

    public RootOutputForSearch takeDataFromDataBaseForSearch(RootInputForSearch rootInputForSearch) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*
сопоставим данные полученные из файла json парсингом в объекты класса Сriteria с БД и получим необходимых нам данные.
На основании полученных данных создадим объекты класса model.search.Result, из которых состоят (ArrayList<model.search.Result> results) объекты класса model.search.Resultat,
из которых в свою очередь состоит (ArrayList<model.search.Resultat> resultats) объект класса model.search.RootOutputForSearch, который мы и будем парсить в формат json
*/
        //обект класса, который будем парсить в формат json
        RootOutputForSearch rootOut = new RootOutputForSearch();
        rootOut.setType("search");
        ArrayList<Resultat> resultats = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            for (Criteria criteria: rootInputForSearch.getCriterias()) {
                Resultat resultat = new Resultat();//объект класса model.search.Resultat, который необходим для парсинга model.search.RootOutputForSearch
                ArrayList<Result> results = new ArrayList<>();
                PreparedStatement preparedStatement = null;
                if (criteria.getLastName() != null) {
                    String sqlQuery = "SELECT first_name, last_name FROM buyers WHERE last_name = ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, criteria.getLastName());
                } else if (criteria.getProductName() != null && criteria.getMinTimes() != null) {
                    String sqlQuery = "SELECT first_name, last_name, count(*) FROM buyers\n" +
                            "JOIN purchases ON buyers.id = buyers_id\n" +
                            "JOIN products ON products_id = products.id\n" +
                            "WHERE product_name = ?\n" +
                            "GROUP BY (first_name, last_name)\n" +
                            "HAVING count(*) >= ?;";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, criteria.getProductName());
                    preparedStatement.setInt(2, criteria.getMinTimes());
                } else if (criteria.getMinExpenses() != null && criteria.getMaxExpenses() != null) {
                    String sqlQuery = "SELECT first_name, last_name, SUM(price) FROM buyers\n" +
                            "JOIN purchases ON buyers.id = buyers_id\n" +
                            "JOIN products ON products_id = products.id\n" +
                            "GROUP BY (first_name, last_name)\n" +
                            "HAVING SUM(price) >= ? AND SUM(price) <= ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setInt(1, criteria.getMinExpenses());
                    preparedStatement.setInt(2, criteria.getMaxExpenses());
                } else if (criteria.getBadCustomers() != null) {
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
                        Result result = new Result();//объект класса model.search.Result, который необходим для парсинга model.search.Resultat
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
        return rootOut;

    }
}
