package com.example.ecommerceapp;

import com.example.ecommerceapp.Data_Model.DataModel_AddProduct;
import com.example.ecommerceapp.Data_Model.DataModel_DeleteProduct;
import com.example.ecommerceapp.Data_Model.DataModel_Register;
import com.example.ecommerceapp.Data_Model.DataModel_UpdateProduct;
import com.example.ecommerceapp.Data_Model.SignInData.DataModel_SignIn;
import com.example.ecommerceapp.Data_Model.View.DataModel_ViewProduct;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Retro_Interface
{

    @FormUrlEncoded
    @POST("Register.php")
    Call<DataModel_Register> registerUser(@Field("name")String name,
                                          @Field("email")String email,
                                          @Field("password")String password );

    @FormUrlEncoded
    @POST("login.php")
    Call<DataModel_SignIn> loginUser(@Field("email")String email,
                                     @Field("password")String password );


    @FormUrlEncoded
    @POST("deleteproduct.php")
    Call<DataModel_DeleteProduct> deleteProduct(@Field("id")String id);


    @FormUrlEncoded
    @POST("addProduct.php")
    Call<DataModel_AddProduct> addProduct(@Field("userid")String userid,
                                          @Field("pname")String pname,
                                          @Field("pprize")String pprize,
                                          @Field("pdes")String pdes,
                                          @Field("productimage")String productimage);


    @FormUrlEncoded
    @POST("updateproduct.php")
    Call<DataModel_UpdateProduct> updateProduct(@Field("id")String id,
                                                @Field("name")String name,
                                                @Field("price")String price,
                                                @Field("description")String description,
                                                @Field("imagedata")String imagedata,
                                                @Field("imagename")String imagename);


    @FormUrlEncoded
    @POST("viewProduct.php")
    Call<DataModel_ViewProduct> viewProduct(@Field("userid")String id);


    @GET("viewAllProduct.php")
    Call<DataModel_ViewProduct> viewAllProduct();

}
