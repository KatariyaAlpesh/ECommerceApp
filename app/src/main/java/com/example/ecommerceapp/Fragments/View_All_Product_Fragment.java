package com.example.ecommerceapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommerceapp.Adapter.ViewUser_Product_Fragment_Adapter;
import com.example.ecommerceapp.Data_Model.View.DataModel_ProductData;
import com.example.ecommerceapp.Data_Model.View.DataModel_ViewProduct;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_All_Product_Fragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_user_product, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFragmentViewUserProduct);
        ProgressBar progressBar = view.findViewById(R.id.progressBarCircular);
        SearchView searchView = view.findViewById(R.id.searchView);


////////==-->>>    When Click on Search RechclerView VISIBILITY is GONE    <<<---===///////////////////////////////

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


//////////////////--------->>>>>>>>>>    API Method Calling    <<<<<<<<<----------------/////////////////////////////

        Retro_Interface_Instance_Class.callAPI().viewAllProduct().enqueue(new Callback<DataModel_ViewProduct>() {
                    @Override
                    public void onResponse(Call<DataModel_ViewProduct> call, Response<DataModel_ViewProduct> response)
                    {
                        if (response.body().getConnection() == 1)
                        {
                            if (response.body().getResult() == 1)
                            {
                                Toast.makeText(getActivity().getApplicationContext(), "Data Import Success", Toast.LENGTH_LONG).show();
                                Log.d("ViewUserData", "onResponse: " + response.body().getProductdata());

                                List<DataModel_ProductData> listProductData = response.body().getProductdata();

                                Collections.sort(listProductData, new Comparator<DataModel_ProductData>()
                                {
                                    @Override
                                    public int compare(DataModel_ProductData dataModel_productData, DataModel_ProductData t1)
                                    {
                                        return dataModel_productData.getPName().compareTo(t1.getPName());
                                    }
                                });

                                /////    Set Adapter

                                ViewUser_Product_Fragment_Adapter viewUserProductFragmentAdapter = new ViewUser_Product_Fragment_Adapter(
                                                                                                    (FragmentActivity) getContext()
                                                                                                    , listProductData);
                                recyclerView.setAdapter(viewUserProductFragmentAdapter);


                                /////    Prosess For Search view

                                searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query)
                                    {
                                        return false;
                                    }
                                    @Override
                                    public boolean onQueryTextChange(String newText)
                                    {
                                        if (!newText.isEmpty())
                                        {
                                            List<DataModel_ProductData> filterlist = new ArrayList<>();

                                            for (DataModel_ProductData singleProduct : listProductData)
                                            {
                                                if (singleProduct.getPName().toLowerCase().contains(newText.toLowerCase()) ||
                                                        singleProduct.getPPrice().toLowerCase().contains(newText.toLowerCase()))
                                                {
                                                    filterlist.add(singleProduct);
                                                }
                                                viewUserProductFragmentAdapter.filterData(filterlist);
                                            }
                                        }
                                        else
                                        {
                                            viewUserProductFragmentAdapter.filterData(listProductData);
                                        }
                                        return false;
                                    }
                                });



                                ////   Set LinearLayout Manager

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.
                                        VERTICAL,false);
                                recyclerView.setLayoutManager(linearLayoutManager);


////////==-->>>    When ProgresBar VISIBILITY is GONE then RecyclerView VISIBILITY is Back and Ready    <<<---===/////////////

                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                Toast.makeText(getContext(), "User Have No Product", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataModel_ViewProduct> call, Throwable t)
                    {
                        Toast.makeText(getContext(), "fail"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        Log.d("viewAll", "onFailure: " + t.getLocalizedMessage());
                    }
                });

    }
}