package model.search;

import com.google.gson.annotations.Expose;

public class Criteria {
    public String lastName;
    public String productName;
    public Integer minTimes;
    public Integer minExpenses;
    public Integer maxExpenses;
    public Integer badCustomers;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getMinTimes() {
        return minTimes;
    }

    public void setMinTimes(Integer minTimes) {
        this.minTimes = minTimes;
    }

    public Integer getMinExpenses() {
        return minExpenses;
    }

    public void setMinExpenses(Integer minExpenses) {
        this.minExpenses = minExpenses;
    }

    public Integer getMaxExpenses() {
        return maxExpenses;
    }

    public void setMaxExpenses(Integer maxExpenses) {
        this.maxExpenses = maxExpenses;
    }

    public Integer getBadCustomers() {
        return badCustomers;
    }

    public void setBadCustomers(Integer badCustomers) {
        this.badCustomers = badCustomers;
    }
}
