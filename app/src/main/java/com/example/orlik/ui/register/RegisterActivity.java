package com.example.orlik.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
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

import com.example.orlik.R;
import com.example.orlik.ui.login.LoginViewModel;
import com.example.orlik.ui.login.LoginViewModelFactory;

import retrofit2.http.Query;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG="RegisterActivityTag";
    private RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        final EditText usernameEditText = findViewById(R.id.emailText);
        final EditText passwordEditText = findViewById(R.id.passwordText);
        final EditText secondPasswordEditText = findViewById(R.id.secPasswordText);
        final EditText nameEditText = findViewById(R.id.nameText);
        final EditText surnameEditText = findViewById(R.id.surnameText);
        final Button registerButton = findViewById(R.id.registerButton);


        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                Log.v(TAG,"RegisterFormState changed");
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));

                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getSecPasswordError() != null) {
                    secondPasswordEditText.setError(getString(registerFormState.getSecPasswordError()));
                }
                if (registerFormState.getNameError() != null) {
                    nameEditText.setError(getString(registerFormState.getNameError()));

                }
                if (registerFormState.getSurnameError() != null) {
                    surnameEditText.setError(getString(registerFormState.getSurnameError()));
                }
            }
        });

        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult result) {
                Log.v(TAG,"RegisterResult changed");

                if(result!=null)
                {
                    if(result.getError()==null)
                    {
                        Toast.makeText(getApplicationContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                        //Complete and destroy login activity once successful
                        setResult(Activity.RESULT_OK);
                        finish();
                    }else if(result.getError().equals(getString(R.string.register_login_failed)))
                    {
                        usernameEditText.setError(result.getError());
                    }else
                    {
                        registerButton.setEnabled(true);
                        Toast.makeText(getApplicationContext(), R.string.register_failed, Toast.LENGTH_LONG).show();
                    }

                }

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
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        secondPasswordEditText.getText().toString(),
                        nameEditText.getText().toString(),
                        surnameEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        secondPasswordEditText.addTextChangedListener(afterTextChangedListener);
        nameEditText.addTextChangedListener(afterTextChangedListener);
        surnameEditText.addTextChangedListener(afterTextChangedListener);
        surnameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerButton.setEnabled(false);

                    registerViewModel.register(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            nameEditText.getText().toString(),
                            surnameEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setEnabled(false);
                registerViewModel.register(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        nameEditText.getText().toString(),
                        surnameEditText.getText().toString());
            }
        });
    }
}