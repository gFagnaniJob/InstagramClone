package it.gfagnani.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {

    private EditText name, punchSpeed, punchPower, kickSpeed, kickPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.txtName);
        punchSpeed = findViewById(R.id.txtPunchSpeed);
        punchPower = findViewById(R.id.txtPunchPower);
        kickSpeed = findViewById(R.id.txtKickSpeed);
        kickPower = findViewById(R.id.txtKickPower);
    }

    public void helloWorldTapped(View view) {
        /*ParseObject boxer = new ParseObject("Boxer");
        boxer.put("punch_speed", 200);
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this,
                                    "boxer object is saved successfully",
                                    Toast.LENGTH_LONG)
                    .show();
                }
            }
        });*/

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
}
