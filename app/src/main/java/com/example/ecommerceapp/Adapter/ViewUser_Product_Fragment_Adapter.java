package com.example.ecommerceapp.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Data_Model.DataModel_DeleteProduct;
import com.example.ecommerceapp.Data_Model.View.DataModel_ProductData;
import com.example.ecommerceapp.Fragments.UpdateProductFragment;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUser_Product_Fragment_Adapter extends RecyclerView.Adapter<ViewUser_Product_Fragment_Adapter.MyClass>
{

    FragmentActivity context;
    List<DataModel_ProductData> listProductData;
    boolean isViewUserFragment;


 ///////=====>>>>>>>>     This Fragment is for View_All_Product_Fragment    <<<<<<=======///////////////////////////////////////

    public ViewUser_Product_Fragment_Adapter(FragmentActivity context ,
                                             List<DataModel_ProductData> listProductData)
    {
        this.context = context;
        this.listProductData = listProductData;
    }


///////=====>>>>>>>>     This Fragment is for View_Product_Fragment    <<<<<<=======///////////////////////////////////////

    public ViewUser_Product_Fragment_Adapter(FragmentActivity context ,
                                             boolean isViewUserFragment ,
                                             List<DataModel_ProductData> listProductData)
    {
        this.context = context;
        this.isViewUserFragment = isViewUserFragment;
        this.listProductData = listProductData;

    }

    public class MyClass extends RecyclerView.ViewHolder
    {
        TextView productName , productPrize , productDescription;
        ImageView productPopupIcon , productImage;

        public MyClass(@NonNull View itemView)
        {
            super(itemView);

            productName = itemView.findViewById(R.id.ViewAllFragmentProductName);
            productPrize = itemView.findViewById(R.id.ViewAllFragmentProductPrice);
            productDescription = itemView.findViewById(R.id.ViewAllFragmentProductDescription);
            productPopupIcon = itemView.findViewById(R.id.ViewAllFragmentPopUpIcon);
            productImage = itemView.findViewById(R.id.ViewAllFragmentProductImage);

        }
    }


    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.fragmentview_user_product_imagepage, parent , false);

        MyClass M = new MyClass(view);

        return M;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUser_Product_Fragment_Adapter.MyClass holder, int position)
    {
        holder.productName.setText(listProductData.get(position).getPName());
        holder.productPrize.setText(listProductData.get(position).getPPrice());
        holder.productDescription.setText(listProductData.get(position).getPDes());


////////////=======>>>>>>>     This Intent is For Payment Activity       <<<<<<<=========///////////////////////////////


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent Inext;
//                Inext = new Intent(context , Payment_Activity.class);
//
//                Inext.putExtra("productOrderId" , listProductData.get(holder.getAdapterPosition()).getId());
//                Inext.putExtra("productName" , listProductData.get(holder.getAdapterPosition()).getPName());
//                Inext.putExtra("productPrice" , listProductData.get(holder.getAdapterPosition()).getPPrice());
//                Inext.putExtra("productImage" , listProductData.get(holder.getAdapterPosition()).getPImage());
//
//                context.startActivity(Inext);
//
//            }
//        });


//////////---->>>>    Loading Image with Animation     <<<<------/////////////////////////////////////

        String imageFolderBaseUrl = "https://alpeshahirprojects.000webhostapp.com/ECommerceApp/";

        Picasso.get()
                .load(imageFolderBaseUrl + listProductData.get(position).getPImage())
                .placeholder(R.drawable.img_loader)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.productImage);


        /////////////////------->>>>    PopUp Menu - Visibility    <<<<<<<<<<<<<-------------/////////////////////////////


        if (isViewUserFragment) {

            holder.productPopupIcon.setVisibility(View.VISIBLE);

        } else {

            holder.productPopupIcon.setVisibility(View.GONE);
        }


///////////---->>>    Work In PopUp Menu     <<<<---------/////////////////////////////////////////////////

        holder.productPopupIcon.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(context , holder.productPopupIcon);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu , popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {

                if (menuItem.getTitle().equals("Update"))
                {
                    //////////////////------------------    Update Product Fragment   ------------------////////////////

                    Bundle bundle = new Bundle();

                    bundle.putString("id",listProductData.get(holder.getAdapterPosition()).getId());
                    bundle.putString("name",listProductData.get(holder.getAdapterPosition()).getPName());
                    bundle.putString("price",listProductData.get(holder.getAdapterPosition()).getPPrice());
                    bundle.putString("des",listProductData.get(holder.getAdapterPosition()).getPDes());
                    bundle.putString("image",listProductData.get(holder.getAdapterPosition()).getPImage());

                    UpdateProductFragment fragment = new UpdateProductFragment();
                    fragment.setArguments(bundle);

                    context.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.LinearLayoutMain,fragment )
                            .commit();

                }

                //////////////////////------------------    Delete Product    ------------------////////////////////

                else if (menuItem.getTitle().equals("Delete"))
                {
                    String id = listProductData.get(holder.getAdapterPosition()).getId();

                    Retro_Interface_Instance_Class.callAPI().deleteProduct(id).enqueue(new Callback<DataModel_DeleteProduct>()
                    {
                        @Override
                                public void onResponse(Call<DataModel_DeleteProduct> call, Response<DataModel_DeleteProduct> response)
                                {
                                    if (response.body().getConnection() == 1)
                                    {
                                        if (response.body().getResult() == 1)
                                        {
                                            Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();

                                            listProductData.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());

                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Deleting Product Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<DataModel_DeleteProduct> call, Throwable t)
                                {
                                    Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                }
                return  true;

            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount()
    {
        return listProductData.size();
    }


////////////==-->>>>>>    FilterData Come From ViewAllProductFragment

    public void filterData(List<DataModel_ProductData> filterlist)
    {
        listProductData = filterlist;
        notifyDataSetChanged();
    }


}
