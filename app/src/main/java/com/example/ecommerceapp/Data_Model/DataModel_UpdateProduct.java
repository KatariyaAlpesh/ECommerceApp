package com.example.ecommerceapp.Data_Model;

import androidx.annotation.NonNull;

public class DataModel_UpdateProduct {

    Integer connection;
    Integer result;


    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @NonNull
    @Override
    public String toString() {
        return "DataModel_Register{" +
                "connection=" + connection +
                ", result=" + result +
                '}';
    }

}
