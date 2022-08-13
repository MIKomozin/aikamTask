import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.error.ErrorOut;
import model.search.RootInputForSearch;
import model.search.RootOutputForSearch;
import model.stat.RootInputForStat;
import model.stat.RootOutputForStat;

import java.io.FileReader;
import java.io.FileWriter;

public class GsonParser {
    //методы с типом операции Search
    //метод для парсинга данных из входного файла в формате json в объект класса Java RootInputForSearch
    public RootInputForSearch parseFromJsonToJavaForSearch(String pathInFile, String pathOutFile) throws Exception {
        Gson gson = new Gson();
        RootInputForSearch rootInput = null;
        try (FileReader reader = new FileReader(pathInFile)) {
            rootInput = gson.fromJson(reader, RootInputForSearch.class);
        } catch (Exception e) {
            ErrorOut error = new ErrorOut();
            error.setType("error");
            error.setMessage("Невозможно спарсить файл. Проверьте правильность введных данных");
            new GsonParser().parseFromJavaToJsonForError(error, pathOutFile);
            throw new Exception("Невозможно спарсить файл. Проверьте правильность введных данных");
        }
        return rootInput;
    }

    //метод для преобразования данных из объекта класса Java RootOutputForSearch в формата json для записи в выходной файл
    public void parseFromJavaToJsonForSearch(RootOutputForSearch rootOut, String pathOutFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(pathOutFile)) {
            //преобразуем объект класса RootOutputForSearch в JSON и записываем в файл
            gson.toJson(rootOut, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //методы с типом операции Stat
    public RootInputForStat parseFromJsonToJavaForStat(String pathInFile, String pathOutFile) throws Exception {
        Gson gson = new Gson();
        RootInputForStat rootInput = null;
        try (FileReader reader = new FileReader(pathInFile)) {
            rootInput = gson.fromJson(reader, RootInputForStat.class);
        } catch (Exception e) {
            ErrorOut error = new ErrorOut();
            error.setType("error");
            error.setMessage("Невозможно спарсить файл. Проверьте правильность введных данных");
            new GsonParser().parseFromJavaToJsonForError(error, pathOutFile);
            throw new Exception("Невозможно спарсить файл. Проверьте правильность введных данных");
        }
        return rootInput;
    }

    public void parseFromJavaToJsonForStat(RootOutputForStat rootOut, String pathOutFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(pathOutFile)) {
            //преобразуем объект класса RootOutputForSearch в JSON и записываем в файл
            gson.toJson(rootOut, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //запись ошибки в файл
    public void parseFromJavaToJsonForError(ErrorOut error, String pathOutFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(pathOutFile)) {
            //преобразуем объект класса ErrorOut в JSON и записываем в файл
            gson.toJson(error, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
