package com.example.timetracker.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.timetracker.MainActivity;
import com.example.timetracker.R;
import com.example.timetracker.data.model.AuthResponseData;
import com.example.timetracker.data.requests.LoginRequest;
import com.example.timetracker.databinding.ActivityLoginBinding;
import com.example.timetracker.ui.DashboardActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.timetracker.databinding.ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            //todo: validate the  username and password.
            String username = "moverr@gmail.com";
                    //usernameEditText.getText().toString();
            String password = "P@ssword?123";
                    //passwordEditText.getText().toString();




                // Create the logging interceptor
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Set logging level

            Toast.makeText(this, "Mailer", Toast.LENGTH_SHORT).show();

            LoginRequest request = new LoginRequest("moverr@gmail.com","P@ssword?123");

                authenticateUser(request);



        });
    }




//    private static final String BASE_URL = "https://hr.dmghana.com/public/hrsystem/";
    private static final String BASE_URL = "https://hr.dmghana.com/public/hrsystem/";


    private static final String USERNAME = "moverr@gmail.com";
    private static final String PASSWORD = "P@ssword?123";

    private void authenticateUser(LoginRequest loginRequest) {

        // Create an ExecutorService to run the network task
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Create a logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build the OkHttpClient with the logging interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


            Gson gson = new Gson();
            String  payload = gson.toJson(loginRequest);



            // Define the media type and request body
            MediaType mediaType = MediaType.Companion.parse("application/json");
            RequestBody body = RequestBody.Companion.create(mediaType, "{\"email\":\""+loginRequest.getUsername()+"\",\"password\":\""+loginRequest.getPassword()+"\",\"mode\":\"token\"}");

            // Build the request
            Request request = new Request.Builder()
                    .url("http://10.0.2.2/dictus/public/hrsystem/auth/authenticate")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();


            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);
                assert response.body() != null;
                String result = response.body().string();
                Log.i("API_CALL_RES", result);

                AuthResponseData user = gson.fromJson(result, AuthResponseData.class);
                Log.i("API_CALL_USER", user.toString());

                SharedPreferences sharedPreferences = getSharedPreferences("curlyapp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("AUTH", result);

                runOnUiThread(() -> {

                   // showSnackbar(null,"Login Succesful",10);

                    Intent mainIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("API_CALL", "Authentication failed");
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        showSnackbar(null,"Login Not succesful",10);

                    }


                });
            }
        });
    }



    private void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the undo action
                    }
                })
                .show();
    }


    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}