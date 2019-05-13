package it.gfagnani.instagramclone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession,
    edtProfileHobbies, edtProfileSport;
    private Button btnProfileUploadInfo, btnProfileLogout;
    private ParseUser parseUser;
    private TextView txtProfileTitle;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileSport = view.findViewById(R.id.edtProfileSport);

        btnProfileUploadInfo = view.findViewById(R.id.btnProfileUploadInfo);

        parseUser = ParseUser.getCurrentUser();

        txtProfileTitle = view.findViewById(R.id.txtProfileTitle);
        txtProfileTitle.setText(parseUser.getUsername() + " profile");

        initializeEditTexts();

        btnProfileUploadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileSport", edtProfileSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null) {
                            FancyToast.makeText(getContext(),
                                    "Info successfully saved",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.INFO,
                                    false).show();
                        } else {
                            FancyToast.makeText(getContext(),
                                    e.getMessage(),
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false).show();
                        }
                    }
                });
            }
        });

        btnProfileLogout = view.findViewById(R.id.btnProfileLogout);
        btnProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser != null) {
                    parseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null) {
                                FancyToast.makeText(getContext(),
                                        "logout successfully",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.INFO,
                                        false).show();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                FancyToast.makeText(getContext(),
                                        e.getMessage(),
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR,
                                        false).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    private void initializeEditTexts() {
        if (parseUser.get("profileName") != null) {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }
        if (parseUser.get("profileBio") != null) {
            edtProfileBio.setText(parseUser.get("profileBio").toString());
        }
        if (parseUser.get("profileProfession") != null) {
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());
        }
        if (parseUser.get("profileHobbies") != null) {
            edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());
        }
        if (parseUser.get("profileSport") != null) {
            edtProfileSport.setText(parseUser.get("profileSport").toString());
        }
    }

}
