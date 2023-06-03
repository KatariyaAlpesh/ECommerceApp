package com.example.ecommerceapp.Activitys;

import static com.example.ecommerceapp.Activitys.Splash_Activity.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.ecommerceapp.Data_Model.SignInData.DataModel_SignIn;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Interface_Instance_Class;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity
{

    TextInputEditText editTextEmail, editTextPassword;
    AppCompatButton buttonSignIn, buttonSignUp;

    String textEmail, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);


        buttonSignIn.setOnClickListener(view -> {

            textEmail = Objects.requireNonNull(editTextEmail.getText()).toString();
            textPassword = Objects.requireNonNull(editTextPassword.getText()).toString();

            Retro_Interface_Instance_Class.callAPI().loginUser(textEmail , textPassword).enqueue(new Callback<DataModel_SignIn>()
            {
                @Override
                public void onResponse(Call<DataModel_SignIn> call, Response<DataModel_SignIn> response)
                {
                    if (response.body().getConnection() == 1)
                    {
                        if (response.body().getResult() == 1)
                        {
                            Toast.makeText(Login_Activity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                            editor.putBoolean("IsLoggedIn" , true);

                            editor.putString("SignInEmail" , response.body().getUserData().getEMAIL());
                            editor.putString("SignInPassword" , response.body().getUserData().getPASSWORD());
                            editor.putString("SignInName" , response.body().getUserData().getNAME());
                            editor.putString("SignInId" , response.body().getUserData().getID().toString());
                            editor.commit();

                            Intent Inext;
                            Inext = new Intent(Login_Activity.this , MainActivity.class);
                            startActivity(Inext);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "SignIn Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Login_Activity.this, "Connection Error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DataModel_SignIn> call, Throwable t)
                {
                    Toast.makeText(Login_Activity.this, "" + t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            });

            if (editTextEmail.hasFocus())
            {
                editTextEmail.clearFocus();
            }
            else if (editTextPassword.hasFocus())
            {
                editTextPassword.clearFocus();
            }

            hideKeyBoard(view);

        });


///////////////==-->>>    This Is for SignUp Activity    <<<<--====///////////////////////////////////////////

        buttonSignUp.setOnClickListener(view -> {

            textEmail = Objects.requireNonNull(editTextEmail.getText()).toString();
            textPassword = Objects.requireNonNull(editTextPassword.getText()).toString();

            Intent Inext;
            Inext = new Intent(Login_Activity.this , Register_Activity.class);
            Inext.putExtra("editTextSignInEmail",textEmail);
            Inext.putExtra("editTextSignInPassword",textPassword);
            startActivity(Inext);
            finish();

            if (editTextEmail.hasFocus())
            {
                editTextEmail.clearFocus();
            }
            else if (editTextPassword.hasFocus())
            {
                editTextPassword.clearFocus();
            }

            hideKeyBoard(view);

        });

    }

    private void hideKeyBoard(View view)
    {
        InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        IMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}