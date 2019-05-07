package it.gfagnani.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsernameSignUp, edtUsernameLogin, edtPasswordSignup, edtPasswordLogin;
    private Button btnSignup, btnLogin;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);

        edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        edtPasswordSignup = findViewById(R.id.edtPasswordSignUp);

        btnSignup = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(SignUpLoginActivity.this);
        btnSignup.setOnClickListener(SignUpLoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                saveUser(edtUsernameSignUp.getText().toString(), edtPasswordSignup.getText().toString());
                break;
            case R.id.btnLogin:
                checkUser(edtUsernameLogin.getText().toString(), edtPasswordLogin.getText().toString());
                break;
        }
    }

    private void checkUser(String username, String password) {
        try {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null) {
                        FancyToast.makeText(SignUpLoginActivity.this,
                                user.getUsername() + " logged in!",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,
                                false)
                                .show();
                        startActivity(intent);
                    } else {
                        FancyToast.makeText(SignUpLoginActivity.this,
                                "Error",
                                FancyToast.LENGTH_LONG,
                                FancyToast.ERROR,
                                false)
                                .show();
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    private void saveUser(String username, String password) {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(username);
        parseUser.setPassword(password);
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(SignUpLoginActivity.this,
                            "Success",
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false)
                            .show();
                    startActivity(intent);
                } else {
                    FancyToast.makeText(SignUpLoginActivity.this,
                            e.getMessage(),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false)
                            .show();
                }
            }
        });
    }
}
