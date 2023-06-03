package com.example.ecommerceapp.Data_Model.SignInData;

public class DataModel_SignIn
{
        public int connection;
        public int result;
        public DataModel_UserData userdata;


    public DataModel_SignIn(int connection, int result, DataModel_UserData userData) {
        this.connection = connection;
        this.result = result;
        this.userdata = userData;
    }

    public int getConnection() {
        return connection;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public DataModel_UserData getUserData() {
        return userdata;
    }

    public void setUserData(DataModel_UserData userData) {
        this.userdata = userData;
    }

    @Override
    public String toString() {
        return "DataModel_SignIn{" +
                "connection=" + connection +
                ", result=" + result +
                ", userData=" + userdata +
                '}';
    }


}
