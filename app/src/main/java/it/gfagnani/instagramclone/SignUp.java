package it.gfagnani.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText name, punchSpeed, punchPower, kickSpeed, kickPower;
    private TextView textView, txtAllData;
    private Button btnGetAllData, btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.txtName);
        punchSpeed = findViewById(R.id.txtPunchSpeed);
        punchPower = findViewById(R.id.txtPunchPower);
        kickSpeed = findViewById(R.id.txtKickSpeed);
        kickPower = findViewById(R.id.txtKickPower);
        txtAllData = findViewById(R.id.txtAllData);
        btnTransition = findViewById(R.id.btnNextActivity);


        textView = findViewById(R.id.txtView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("vuBKWv9HJU", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null) {
                            if (object != null) {
                                FancyToast.makeText(SignUp.this,
                                        object.getString("name") + " found",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        true)
                                        .show();
                                textView.setText(object.getString("name") + "\n" +
                                        "punchSpeed: " + object.getNumber("punchSpeed") + "\n" +
                                        "punchPower: " + object.getNumber("punchPower") + "\n" +
                                        "kickSpeed: " + object.getNumber("kickSpeed") + "\n" +
                                        "kickPower: " + object.getNumber("kickPower"));
                            } else {
                                FancyToast.makeText(SignUp.this,
                                        "no object found",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR,
                                        true)
                                        .show();
                            }
                        } else {
                            FancyToast.makeText(SignUp.this,
                                    e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true)
                                    .show();
                        }
                    }
                });
            }
        });

        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnGetAllData.setOnClickListener(SignUp.this);
        btnTransition.setOnClickListener(SignUp.this);
    }

    public void helloWorldTapped(View view) {
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        try {
            kickBoxer.put("name", name.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(punchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(punchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(kickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(kickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this,
                                kickBoxer.get("name") + " is saved in server",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,
                                true)
                                .show();
                    } else {
                        FancyToast.makeText(SignUp.this,
                                e.getMessage(),
                                FancyToast.LENGTH_LONG,
                                FancyToast.ERROR,
                                true)
                                .show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this,
                    e.getMessage(),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,
                    true)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetAllData:
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                String temp = "";
                                for (ParseObject obj : objects) {
                                    temp = temp + "\t" + obj.get("name");
                                }
                                txtAllData.setText(temp);
                                FancyToast.makeText(SignUp.this,
                                        "success!",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        true)
                                        .show();
                            } else {
                                FancyToast.makeText(SignUp.this,
                                        "no objects found",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR,
                                        true)
                                        .show();
                            }
                        } else {
                            FancyToast.makeText(SignUp.this,
                                    e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true)
                                    .show();
                        }
                    }
                });
                break;
            case R.id.btnNextActivity:
                Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
