
package com.example.ecommerceapp.Data_Model.View;

import java.util.List;

public class DataModel_ViewProduct
{

    private Integer connection;
    private Integer result;
    private List<DataModel_ProductData> productdata;



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

    public List<DataModel_ProductData> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<DataModel_ProductData> productdata) {
        this.productdata = productdata;
    }

    @Override
    public String toString() {
        return "DataModelViewProduct{" +
                "connection=" + connection +
                ", result=" + result +
                ", productdata=" + productdata +
                '}';
    }

}
