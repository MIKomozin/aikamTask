import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.search.RootInputForSearch;
import model.search.RootOutputForSearch;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonParser {
    public RootInputForSearch parseFromJsonToJava(){
        Gson gson = new Gson();
        RootInputForSearch rootInput = null;
        try (FileReader reader = new FileReader("InputFileForSearch.json")) {
            rootInput = gson.fromJson(reader, RootInputForSearch.class);
        } catch (Exception e) {
            System.out.println("Parse error");
        }
        return rootInput;
    }

    public void parseFromJavaToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        RootOutputForSearch rootOut = new DataFromDB().takeDataFromDataBaseForSearch(parseFromJsonToJava());

        // Java objects to String
        String json = gson.toJson(rootOut);

        try (FileWriter writer = new FileWriter("OutputFileForSearch.json")) {
            gson.toJson(rootOut, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
