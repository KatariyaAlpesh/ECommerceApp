package com.example.ecommerceapp.Fragments;

import static com.example.ecommerceapp.Activitys.Splash_Activity.preferences;

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_User_Product_Fragment extends Fragment
{

    boolean isViewUserFragment = true;     ////    THIS IS FOR POPUP MENU SHOW - ONLY USE IN USER FRAGMENT


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


////////////--------->>>>>>>>>    Progress Bar visibility    <<<<<<<<<<<<<<<<--------------///////////////
/////////------------>>>>>>>>    Until recyclerView data loads    <<<<<<<<<<<<<-----------////////////


        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

    String id = preferences.getString("SignInId" , null); ///===--->>>   come From Login Activity

        Log.d("AAAAA", "onViewCreated: id =  " + id);


        Retro_Interface_Instance_Class.callAPI().viewProduct(id).enqueue(new Callback<DataModel_ViewProduct>()
        {
            @Override
            public void onResponse(Call<DataModel_ViewProduct> call, Response<DataModel_ViewProduct> response)
            {
                if (response.body().getConnection() == 1)
                {
                    if (response.body().getResult() == 1)
                    {
                        List<DataModel_ProductData> listProductData =response.body().getProductdata();

                        ViewUser_Product_Fragment_Adapter viewUserProductFragmentAdapter = new ViewUser_Product_Fragment_Adapter(
                                                                                                (FragmentActivity) getContext()
                                                                                                 , isViewUserFragment
                                                                                                 , listProductData );

                        recyclerView.setAdapter(viewUserProductFragmentAdapter);


 /////    Prosess For Search view   <<<--==//////////////////////////////////////////////////////

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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



                        LinearLayoutManager LM = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,
                                false);
                        recyclerView.setLayoutManager(LM);


//////////---------------------    Progress Bar visibility gone   ---------------------/////////////
///////////---------------------     recyclerView data is ready   ---------------------/////////////


                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "User has No Products",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DataModel_ViewProduct> call, Throwable t)
            {
                Toast.makeText(getContext(), "Error : " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                Log.d("viewUser", "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

}