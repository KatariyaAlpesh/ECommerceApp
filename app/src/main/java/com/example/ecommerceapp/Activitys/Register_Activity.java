package com.example.ecommerceapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.ecommerceapp.Data_Model.DataModel_Register;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity
{
    TextInputEditText editTextName , editTextEmail , editTextPassword;
    AppCompatButton buttonSignUp;

    String textName , textEmail , textPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextRegisterName);
        editTextEmail = findViewById(R.id.editTextRegisterEmail);
        editTextPassword = findViewById(R.id.editTextRegisterPassword);
        buttonSignUp = findViewById(R.id.buttonRegisterSignUp);

        textEmail = getIntent().getStringExtra("editTextSignInEmail");
        textPassword = getIntent().getStringExtra("editTextSignInPassword");

        editTextEmail.setText(textEmail);
        editTextPassword.setText(textPassword);

        buttonSignUp.setOnClickListener(view -> {

            textName = Objects.requireNonNull(editTextName.getText()).toString();
            textEmail = Objects.requireNonNull(editTextEmail.getText()).toString();
            textPassword = Objects.requireNonNull(editTextPassword.getText()).toString();


/////////////////------------->>>>>>>>>>>>>       API Call Method      <<<<<<<<<<<<----------------//////////////////


            Retro_Interface_Instance_Class.callAPI().registerUser(textName,textEmail,textPassword)
                    .enqueue(new Callback<DataModel_Register>()
                    {
                        @Override
                        public void onResponse(Call<DataModel_Register> call, Response<DataModel_Register> response)
                        {
                            if (response.body().getConnection() == 1)
                            {
                                if (response.body().getResult() == 1)
                                {
                                    Toast.makeText(Register_Activity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable()
                                    {
                                        @Override
                                        public void run() {

                                            Intent Inext;
                                            Inext = new Intent(Register_Activity.this, Login_Activity.class);
                                            startActivity(Inext);
                                            finish();
                                        }
                                    },5000);

                                }
                                else
                                {
                                    Toast.makeText(Register_Activity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Register_Activity.this, "Connection Error", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataModel_Register> call, Throwable t)
                        {
                            Toast.makeText(Register_Activity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            if ( editTextName.hasFocus() )
            {
                editTextName.clearFocus();
            }
            else if ( editTextEmail.hasFocus() )
            {
                editTextEmail.clearFocus();
            }
            else if ( editTextPassword.hasFocus() )
            {
                editTextPassword.clearFocus();
            }

            hideKeyBoard(view);

        });
    }

    private void hideKeyBoard(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}