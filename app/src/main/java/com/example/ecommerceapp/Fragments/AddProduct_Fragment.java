package com.example.ecommerceapp.Fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.ecommerceapp.Activitys.Splash_Activity.preferences;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Data_Model.DataModel_AddProduct;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProduct_Fragment extends Fragment
{
    TextView textViewAddProductTitle ;
    TextInputEditText editTextAddProductName , editTextAddProductPrice , editTextAddProductDescription;
    ImageView imageViewAddProductImage;
    AppCompatButton buttonAddProduct;
    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;

    View view;

    String productName, productPrice, productDescription;
    Uri imageUri;


////////////----->>>>>    Image Pickup From Gallery - Declaration    <<<<-------////////////////////////////////////////////

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    int ResultCode  = result.getResultCode();
                    Intent data = result.getData();

                    if (ResultCode == RESULT_OK)
                    {
                        if (data != null)
                        {
                            Uri imageUri = data.getData();
                            imageViewAddProductImage.setImageURI(imageUri);
                        }
                    }
                }
            });


///////////==-->>>   This Is TextWatcher  =  Only for The AutoComplite TextView    <<<<--====//////////

    TextWatcher textWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)  { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if (autoCompleteTextView.getText().toString().isEmpty())
            {
                autoCompleteTextView.setError("Must Have Value");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_add_product, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewAddProductTitle = view.findViewById(R.id.textViewAddProductTitle);
        editTextAddProductName = view.findViewById(R.id.editTextAddProductName);
        editTextAddProductPrice = view.findViewById(R.id.editTextAddProductPrice);
        editTextAddProductDescription = view.findViewById(R.id.editTextAddProductDescription);
        imageViewAddProductImage = view.findViewById(R.id.imageViewAddProductImage);
        buttonAddProduct = view.findViewById(R.id.buttonAddProduct);

        autoCompleteTextView = view.findViewById(R.id.AutoCompleteTextView);
        spinner = view.findViewById(R.id.Spinner);

        textViewAddProductTitle.setText("Add_New_Product");
        buttonAddProduct.setText("Add_Product");

/////////////////////==---->>>    AutoComplite Text View    <<<---======//////////////////////////////

        String data[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, data);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.addTextChangedListener(textWatcher);


///////////////////====--->>>   Concept Of Spinner    <<<--===//////////////////////////////////////

        String dataSpinner[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};

        ArrayAdapter arrayAdapterSpinner = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dataSpinner);

        spinner.setAdapter(arrayAdapterSpinner);


////////////////===--->>>>   Get Image From The Gallery    <<<<<---===/////////////////////////////////////////////

        imageViewAddProductImage.setOnClickListener(view1 -> {

            Intent Inext = new Intent(Intent.ACTION_PICK);
            Inext.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            intentActivityResultLauncher.launch(Inext);

        });

//////////////------------------    Click On AddProductButton    ------------------////////////////

        buttonAddProduct.setOnClickListener(view1 -> {

            productName = editTextAddProductName.getText().toString();
            productPrice = editTextAddProductPrice.getText().toString();
            productDescription = editTextAddProductDescription.getText().toString();
            String Id = preferences.getString("SignInId", "");
            Log.d("TTTT", "onViewCreated: " + Id);

//////////////------------------    encode image - uri not needed ?   ------------------////////////////

            String imageData = null;
            Bitmap bitmap = ((BitmapDrawable) imageViewAddProductImage.getDrawable()).getBitmap();
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, BAOS);
            byte[] imageInByte = BAOS.toByteArray();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                imageData = Base64.getEncoder().encodeToString(imageInByte);
            }

            Retro_Interface_Instance_Class.callAPI().addProduct(Id, productName, productPrice, productDescription, imageData)
                    .enqueue(new Callback<DataModel_AddProduct>() {
                        @Override
                        public void onResponse(Call<DataModel_AddProduct> call, Response<DataModel_AddProduct> response)
                        {
                            if (response.body().getConnection() == 1)
                            {
                                if (response.body().getProductaddd() == 1)
                                {
                                    Toast.makeText(getContext(), " Product Added", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Product Additing Fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<DataModel_AddProduct> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                            Log.d("TLOG", "onFailure: " + t.getLocalizedMessage());
                        }
                    });

        });

    }
}