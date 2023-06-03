
package com.example.ecommerceapp.Data_Model.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataModel_ProductData {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("UID")
    @Expose
    private String uid;
    @SerializedName("P_NAME")
    @Expose
    private String pName;
    @SerializedName("P_PRICE")
    @Expose
    private String pPrice;
    @SerializedName("P_DES")
    @Expose
    private String pDes;
    @SerializedName("P_IMAGE")
    @Expose
    private String pImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPPrice() {
        return pPrice;
    }

    public void setPPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getPDes() {
        return pDes;
    }

    public void setPDes(String pDes) {
        this.pDes = pDes;
    }

    public String getPImage() {
        return pImage;
    }

    public void setPImage(String pImage) {
        this.pImage = pImage;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", pName='" + pName + '\'' +
                ", pPrice='" + pPrice + '\'' +
                ", pDes='" + pDes + '\'' +
                ", pImage='" + pImage + '\'' +
                '}';
    }
//
//    private String ID;
//    private String UID;
//    private String P_NAME;
//    private String P_PRICE;
//    private String P_DES;
//    private String P_IMAGE;
//
//    public String getId() {
//        return ID;
//    }
//
//    public void setId(String ID) {
//        this.ID = ID;
//    }
//
//    public String getUid() {
//        return UID;
//    }
//
//    public void setUid(String UID) {
//        this.UID = UID;
//    }
//
//    public String getPName() {
//        return P_NAME;
//    }
//
//    public void setPName(String P_NAME) {
//        this.P_NAME = P_NAME;
//    }
//
//    public String getPPrice() {
//        return P_PRICE;
//    }
//
//    public void setPPrice(String P_PRICE) {
//        this.P_PRICE = P_PRICE;
//    }
//
//    public String getPDes() {
//        return P_DES;
//    }
//
//    public void setPDes(String P_DES) {
//        this.P_DES = P_DES;
//    }
//
//    public String getPImage() {
//        return P_IMAGE;
//    }
//
//    public void setPImage(String P_IMAGE) {
//        this.P_IMAGE = P_IMAGE;
//    }
//
//    @Override
//    public String toString() {
//        return "DataModel_ProductData{" +
//                "ID='" + ID + '\'' +
//                ", UID='" + UID + '\'' +
//                ", P_NAME='" + P_NAME + '\'' +
//                ", P_PRICE='" + P_PRICE + '\'' +
//                ", P_DES='" + P_DES + '\'' +
//                ", P_IMAGE='" + P_IMAGE + '\'' +
//                '}';
//    }
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */


}


