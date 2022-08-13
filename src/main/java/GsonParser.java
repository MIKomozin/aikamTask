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
    public RootInputForSearch parseFromJsonToJavaForSearch(){
        Gson gson = new Gson();
        RootInputForSearch rootInput = null;
        try (FileReader reader = new FileReader("InputFileForSearch.json")) {
            rootInput = gson.fromJson(reader, RootInputForSearch.class);
        } catch (Exception e) {
            System.out.println("Parse error");
        }
        return rootInput;
    }

    //метод для преобразования данных из объекта класса Java RootOutputForSearch в формата json для записи в выходной файл
    public void parseFromJavaToJsonForSearch(RootOutputForSearch rootOut) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("OutputFileForSearch.json")) {
            //преобразуем объект класса RootOutputForSearch в JSON и записываем в файл
            gson.toJson(rootOut, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public void parseFromJavaToJsonForError(ErrorOut error) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("OutputFileForSearch.json")) {
            //преобразуем объект класса ErrorOut в JSON и записываем в файл
            gson.toJson(error, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //методы для парсинга с типом операции stat
    public RootInputForStat parseFromJsonToJavaForStat(){
        Gson gson = new Gson();
        RootInputForStat rootInput = null;
        try (FileReader reader = new FileReader("InputFileForSearch.json")) {
            rootInput = gson.fromJson(reader, RootInputForStat.class);
        } catch (Exception e) {
            System.out.println("Parse error");
        }
        return rootInput;
    }

    public void parseFromJavaToJsonForStat(RootOutputForStat rootOut) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("OutputFileForSearch.json")) {
            //преобразуем объект класса RootOutputForSearch в JSON и записываем в файл
            gson.toJson(rootOut, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
