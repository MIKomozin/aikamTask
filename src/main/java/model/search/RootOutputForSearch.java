package model.search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootOutputForSearch {

    public String type;

    @SerializedName("results")
    public ArrayList<Resultat> resultats;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Resultat> getResultats() {
        return resultats;
    }

    public void setResultats(ArrayList<Resultat> resultats) {
        this.resultats = resultats;
    }
}
