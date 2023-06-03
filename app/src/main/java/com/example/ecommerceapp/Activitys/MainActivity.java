package com.example.ecommerceapp.Activitys;

import static com.example.ecommerceapp.Activitys.Splash_Activity.editor;
import static com.example.ecommerceapp.Activitys.Splash_Activity.preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.Fragments.AddProduct_Fragment;
import com.example.ecommerceapp.Fragments.UpdateProductFragment;
import com.example.ecommerceapp.Fragments.View_All_Product_Fragment;
import com.example.ecommerceapp.Fragments.View_User_Product_Fragment;
import com.example.ecommerceapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.DrawerLayoutMain);
        toolbar = findViewById(R.id.ToolBarMain);
        navigationView = findViewById(R.id.NavigationViewMai);


/////////////////////-----===----->>>>>     TOOLBAR     <<<<<-------==================/////////////////////

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.app_name , R.string.app_name);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);

        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundColor(Color.WHITE);
        getSupportActionBar().setTitle("User Product's");


////////////////////======------------>>>>>>    NavigationView    <<<<<<<<<------------====//////////////////////

        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0); ///    open the all DraewrLayout Intems ( For Suporting items )

        ImageView imageViewHeader = headerView.findViewById(R.id.ImageViewHeader);
        TextView textViewHeaderName = headerView.findViewById(R.id.TextViewHeaderName);
        TextView textViewHeaderEmail = headerView.findViewById(R.id.TextViewHeaderEmail);

            ///   Header Menu ni Andar Name and Email Set KArva mare   //////

        textViewHeaderName.setText(preferences.getString("SignInName" , null));   ///   come From Login Activity
        textViewHeaderEmail.setText(preferences.getString("SignInEmail" , null));   ///   come From Login Activity


/////////////==--->>>    Prosess For Fragmenet Home   <<<--=====/////////////////////////////////////////////


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.LinearLayoutMain , new View_User_Product_Fragment() , "View_User_Product_Fragment")
                .commit();


////////////------------>>>>>>>    NavigationView - Selection Prosess For the Navigation Items    <<<<<--------------/////////////


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if (item.getItemId() == R.id.Add_Product)
                {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.LinearLayoutMain , new AddProduct_Fragment() , "AddProduct_Fragment")
                            .commit();
                    getSupportActionBar().setTitle("Add Product");
                }
                else
                    if (item.getItemId() == R.id.Show_Product)
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.LinearLayoutMain , new View_User_Product_Fragment() , "View_User_Product_Fragment")
                                .commit();
                        getSupportActionBar().setTitle("User Product");
                    }
                    else
                        if (item.getItemId() == R.id.Show_All_Products)
                        {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.LinearLayoutMain , new View_All_Product_Fragment() , "View_All_Product_Fragment")
                                    .commit();
                            getSupportActionBar().setTitle("All Product");
                        }

                    else
                        if (item.getItemId() == R.id.LogOut)
                        {
                            editor.putBoolean("isLoggedIn",false);
                            editor.putString("signInEmail",null);
                            editor.putString("signInPassword",null);
                            editor.putString("signInName",null);
                            editor.putString("signInId",null);
                            editor.commit();

                            Intent Iback;
                            Iback = new Intent(MainActivity.this , Login_Activity.class);
                            startActivity(Iback);
                            finish();

                        }

                drawerLayout.closeDrawers();
                return false;
            }
        });

    }


    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();

        BackPress_Interface backPress_interface = new BackPress_Interface()
        {
            @Override
            void onBackPressMethod() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.LinearLayoutMain);

                if (!(currentFragment instanceof AddProduct_Fragment) && !(currentFragment instanceof UpdateProductFragment))
                {
                    MainActivity.super.onBackPressed();
                    return;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.LinearLayoutMain,new View_User_Product_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        };
        backPress_interface.onBackPressMethod();

    }
}