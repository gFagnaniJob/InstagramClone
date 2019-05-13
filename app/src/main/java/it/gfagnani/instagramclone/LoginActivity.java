package it.gfagnani.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent toSocial, toSignup;
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnSignup;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In!");

        toSocial = new Intent(LoginActivity.this, SocialMediaActivity.class);
        toSignup = new Intent(LoginActivity.this, SignUpActivity.class);

        edtEmail = findViewById(R.id.edtEnterEmailLI);
        edtPassword = findViewById(R.id.edtEnterPasswordLI);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLogin);
                }

                return false;
            }
        });

        btnLogin = findViewById(R.id.btnLoginLI);
        btnLogin.setOnClickListener(LoginActivity.this);

        btnSignup = findViewById(R.id.btnSignUpLI);
        btnSignup.setOnClickListener(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null) {
            startActivity(toSocial);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginLI:
                Log.d("cliccato", "login");
                try {
                    initializeString();
                    login(email, password);
                } catch (IllegalArgumentException e) {
                    makeFancyToast("Compile all fields!",
                            FancyToast.ERROR,
                            false);
                }
                break;
            case R.id.btnSignUpLI:
                startActivity(toSignup);
                finish();
                break;
        }
    }

    private void initializeString() {
        this.email = edtEmail.getText().toString();
        this.password = edtPassword.getText().toString();
    }

    private void login(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in " + email + " ...");
        progressDialog.show();
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    if (user != null) {
                        makeFancyToast("Logged in successfully",
                                FancyToast.SUCCESS,
                                false);
                        startActivity(toSocial);
                        finish();
                    } else {
                        makeFancyToast("User not found",
                                FancyToast.ERROR,
                                false);
                    }
                } else {
                    makeFancyToast(e.getMessage(),
                            FancyToast.ERROR,
                            false);
                }
                progressDialog.dismiss();
            }
        });
    }

    private void makeFancyToast (String text, int type, boolean icon) {
        FancyToast.makeText(LoginActivity.this,
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
