package com.example.ecommerceapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro_Interface_Instance_Class
{
//    public static Retro_Interface callAPI()
//    {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://alpeshahirprojects.000webhostapp.com/ECommerceApp/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
////        https://alpeshahirprojects.000webhostapp.com/ECommerceApp/
//        ///  =-->> URL Get From 000webhostapp.com , go to old panel , alpeshahirProjects , Tools , File Manager , public _html , EcommerceApp ,
//        ///  =-->>  Right Click on any PHP file and coppy URL
//
//        return retrofit.create(Retro_Interface.class);
//    }
    public static Retro_Interface callAPI()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://alpeshahirprojects.000webhostapp.com/ECommerceApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Retro_Interface.class);
    }

}
