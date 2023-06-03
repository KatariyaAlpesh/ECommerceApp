package com.example.ecommerceapp.Fragments;

import static android.app.Activity.RESULT_OK;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Data_Model.DataModel_UpdateProduct;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateProductFragment extends Fragment
{

    View view;

    TextView textViewAddProductTitle;
    TextInputEditText editTextProductName, editTextProductPrice, editTextProductDescription;
    ImageView imageViewProductImage;
    AppCompatButton buttonProductAdd, buttonProductUpdate;

    String productName, productPrice, productDescription;


////////------------------    Image Pick - From Gallery - Declaration   ------------------//////////

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {

                    int resultCode = activityResult.getResultCode();
                    Intent data = activityResult.getData();

                    if (resultCode == RESULT_OK) {

                        if (data != null) {

                            Uri imageUri = data.getData();
                            imageViewProductImage.setImageURI(imageUri);

                        }
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        textViewAddProductTitle = view.findViewById(R.id.textViewAddProductTitle);
        editTextProductName = view.findViewById(R.id.editTextAddProductName);
        editTextProductPrice = view.findViewById(R.id.editTextAddProductPrice);
        editTextProductDescription = view.findViewById(R.id.editTextAddProductDescription);
        imageViewProductImage = view.findViewById(R.id.imageViewAddProductImage);
        buttonProductAdd = view.findViewById(R.id.buttonAddProduct);
        buttonProductUpdate = view.findViewById(R.id.buttonUpdateProduct);


//////////        first set previous data in all fields


        buttonProductAdd.setVisibility(View.GONE);
        buttonProductUpdate.setVisibility(View.VISIBLE);
        textViewAddProductTitle.setText("Update Product");


//////////////////------->>>>>    Olad Data Show     <<<<<<<----------//////////////////////////////////////////

        Bundle bundle = getArguments();

        String pid = bundle.getString("id" , null);
        String pname = bundle.getString("name" , null);
        String pprice = bundle.getString("price" , null);
        String pdes = bundle.getString("des" , null);
        String pimage = bundle.getString("image" , null);

        Log.d("ProductID", "onViewCreated: " + pid);


        String imageFolderBaseUrl = "https://alpeshahirprojects.000webhostapp.com/ECommerceApp/";
//        String imagename = pimage;

        editTextProductName.setText(pname);
        editTextProductPrice.setText(pprice);
        editTextProductDescription.setText(pdes);


///////////////////////////////////  loading image with animation //////////////////////////////////

        Picasso.get()
                .load(imageFolderBaseUrl + pimage )
                .placeholder(R.drawable.img_loader)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imageViewProductImage);


//////////////------------------  new Image Pick - From Gallery    ------------------///////////////

        imageViewProductImage.setOnClickListener(view1 -> {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            intentActivityResultLauncher.launch(intent);    // image picker globally declared and defined

        });


//////////////------------------    Update Product Button - Click    ------------------////////////////

        buttonProductUpdate.setOnClickListener(view1 -> {

            productName = editTextProductName.getText().toString();
            productPrice = editTextProductPrice.getText().toString();
            productDescription = editTextProductDescription.getText().toString();


/////////------------------    new encode image - uri not needed ?   ------------------/////////////

            String imagedata = null;
            Bitmap bitmap = ((BitmapDrawable) imageViewProductImage.getDrawable()).getBitmap(); // bitmap direct from view ?
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);  //more qlty gives error timeout
            byte[] imageInByte = baos.toByteArray();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                imagedata = Base64.getEncoder().encodeToString(imageInByte);
            }


///////////------->>>>>>>>      API Calling Method    <<<<<---------/////////////////////////////////////////////


            Retro_Interface_Instance_Class.callAPI().updateProduct(pid,productName,productPrice,productDescription,imagedata,pimage)
                    .enqueue(new Callback<DataModel_UpdateProduct>() {

                        @Override
                        public void onResponse(Call<DataModel_UpdateProduct> call, Response<DataModel_UpdateProduct> response) {

                            Log.d("response", "onResponse: " + response.body());

                            if (response.body().getConnection() == 1)
                            {
                                if (response.body().getResult() == 1)
                                {
                                    Toast.makeText(getContext(), "Product Updated", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Product Update Failed",Toast.LENGTH_LONG).show();

                                    View_User_Product_Fragment fragment = new View_User_Product_Fragment();

                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.LinearLayoutMain,fragment)
                                            .commit();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataModel_UpdateProduct> call, Throwable t) {

                            Toast.makeText(getContext(), "" + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

        });

    }
}