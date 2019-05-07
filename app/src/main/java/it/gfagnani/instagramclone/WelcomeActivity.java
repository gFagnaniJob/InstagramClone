package it.gfagnani.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogOut;
    private TextView txtWelcome;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnLogOut = findViewById(R.id.btnLogOut);
        intent = new Intent(WelcomeActivity.this, SignUpLoginActivity.class);

        txtWelcome.setText("Welcome " + ParseUser.getCurrentUser().getUsername());

        btnLogOut.setOnClickListener(WelcomeActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(WelcomeActivity.this,
                                    "Logged out",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS,
                                    false)
                                    .show();
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(WelcomeActivity.this,
                                    e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                                    .show();
                        }
                    }
                });
                break;
        }
    }
}
