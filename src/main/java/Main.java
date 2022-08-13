import model.search.RootInputForSearch;
import model.stat.RootInputForStat;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Main {

    public static final String PATH = "testFile\\";//путь, где находятся входной и выходной файлы
    public static final String URL = "jdbc:postgresql://localhost:5432/aikam";
    public static final String USER = "Maksim";
    public static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {
        String typeOfOperation;
        String fileIn;//имя входного файла
        String fileOut;//имя выходного файла

        if (args.length != 3) {
            throw new Exception("Количество введенных параметров должно быть равно 3");
        } else {
            if (args[0].equals("search") || args[0].equals("stat")) {
                if (args[0].equals("search")) {
                    typeOfOperation = "search";
                } else {
                    typeOfOperation = "stat";
                }
                fileIn = args[1];
                fileOut = args[2];
            } else {
                throw new Exception("Первым параметром должен быть тип операции (search или stat)");
            }
        }

        //проверим существование входного файла. Если его нет, то предупредим об этом
        if (!Files.exists(Paths.get(PATH + fileIn))) {
            throw new Exception("Входного файла " + (PATH + fileIn) + " не существует. Проверьте правильность введенных данных");
        }

        //проверим существование выходного файла. Если его нет, то создадим его с таким же именем как указано в параметрах
        if (!Files.exists(Paths.get(PATH + fileOut))) {
            System.out.println("Выходного файла " + (PATH + fileOut) + " не существует. Создадим пустой файл с таким же именем.");
            Files.createFile(Paths.get(PATH + fileOut));
        }

        //подключаемся к БД и работаем
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Отсутствует соединение с БД");
        }

        if (connection != null) {
            if (typeOfOperation.equals("search")) {
                //парсим данные из файла в объект класса RootInputForSearch
                RootInputForSearch rootInputForSearch = new GsonParser().parseFromJsonToJavaForSearch(PATH + fileIn, PATH + fileOut);

                //пишем данные в выходной файл
                new FromDataBaseToFile().fromDBToJsonFileForSearch(rootInputForSearch, connection, PATH + fileOut);
            } else {
                //парсим данные из файла в объект класса RootInputForStat
                RootInputForStat rootInputForStat = new GsonParser().parseFromJsonToJavaForStat(PATH + fileIn, PATH + fileOut);

                //пишем данные в выходной файл
                new FromDataBaseToFile().fromDBToJsonFileForStat(rootInputForStat, connection, PATH + fileOut);
            }
        }
    }
}
