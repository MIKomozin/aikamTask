package model.search;

import java.util.ArrayList;

public class RootInputForSearch {
    public ArrayList<Criteria> criterias;

    public ArrayList<Criteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(ArrayList<Criteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public String toString() {
        return "model.search.RootInputForSearch{" +
                "criterias=" + criterias +
                '}';
    }
}
