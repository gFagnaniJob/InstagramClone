package it.gfagnani.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent toSignUp, toLogin;
    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogin;
    private String email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up!");

        toSignUp = new Intent(SignUpActivity.this, SecondActivity.class);
        toLogin = new Intent(SignUpActivity.this, LoginActivity.class);

        edtEmail = findViewById(R.id.edtEnterEmailSU);
        edtUsername = findViewById(R.id.edtEnterUsernameSU);
        edtPassword = findViewById(R.id.edtEnterPasswordSU);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUpSU);
        btnSignUp.setOnClickListener(SignUpActivity.this);

        btnLogin = findViewById(R.id.btnLoginSU);
        btnLogin.setOnClickListener(SignUpActivity.this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    makeFancyToast("Logged out successfully",
                            FancyToast.INFO,
                            false);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUpSU:
                try {
                    initializeStrings();
                    signUpUser(email, username, password);
                } catch (IllegalArgumentException e) {
                    makeFancyToast("Compile all fields!",
                            FancyToast.ERROR,
                            false);
                }
                break;
            case R.id.btnLoginSU:
                startActivity(toLogin);
                break;
        }
    }

    private void signUpUser(String email, String username, String password) {
        try {
            if (email.equals("") || username.equals("") || password.equals("")) {
                makeFancyToast("All fields are required",
                        FancyToast.INFO,
                        false);
            } else {
                final ParseUser parseUser = new ParseUser();
                parseUser.setEmail(email);
                parseUser.setUsername(username);
                parseUser.setPassword(password);

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing up " + username + " ...");
                progressDialog.show();

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            makeFancyToast("Signed Up successfully!",
                                    FancyToast.SUCCESS,
                                    false);
                            startActivity(toSignUp);
                        } else {
                            makeFancyToast(e.getMessage(),
                                    FancyToast.ERROR,
                                    false);
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        } catch (IllegalArgumentException e) {
            makeFancyToast("Tutti i campi sono obbligatori",
                    FancyToast.ERROR,
                    false);
        }
    }

    private void initializeStrings () {
        String emailText = edtEmail.getText().toString();
        String usernameText = edtUsername.getText().toString();
        String passwordText = edtPassword.getText().toString();
        this.email = emailText;
        this.username = usernameText;
        this.password = passwordText;
    }

    private void makeFancyToast (String text, int type, boolean icon) {
        FancyToast.makeText(SignUpActivity.this,
                text,
                FancyToast.LENGTH_LONG,
                type,
                icon)
                .show();
    }

    public void rootLayoutTapped (View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}