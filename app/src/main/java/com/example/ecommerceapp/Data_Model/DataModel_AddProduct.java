package com.example.ecommerceapp.Data_Model;

public class DataModel_AddProduct {


    private Integer connection;
    private Integer productaddd;


    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getProductaddd() {
        return productaddd;
    }

    public void setProductaddd(Integer productaddd) {
        this.productaddd = productaddd;
    }

    @Override
    public String toString() {
        return "DataModel_AddProduct{" +
                "connection=" + connection +
                ", productaddd=" + productaddd +
                '}';
    }
//    Integer connection;
//    Integer productaddd;

//    public Integer getConnection() {
//        return connection;
//    }
//
//    public void setConnection(Integer connection) {
//        this.connection = connection;
//    }
//
//    public Integer getProductaddd() {
//        return productaddd;
//    }
//
//    public void setProductaddd(Integer productaddd) {
//        this.productaddd = productaddd;
//    }
//
//    @Override
//    public String toString() {
//        return "DataModel_AddProduct{" +
//                "connection=" + connection +
//                ", productaddd=" + productaddd +
//                '}';
//    }


}

