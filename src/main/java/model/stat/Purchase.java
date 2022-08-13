package model.stat;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Purchase {
    @SerializedName("name")
    public String productName;

    public int expenses;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    //переопределим equals для, метод contains правильно сравнивал объекты Purchase (в насшем случае только по имени)
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Purchase purchase = (Purchase) obj;
        return (Objects.equals(productName, purchase.productName));
    }
}
