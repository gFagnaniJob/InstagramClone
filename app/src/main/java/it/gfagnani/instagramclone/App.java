package it.gfagnani.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JzH8X5k31Ld60KuIeDdjYJV3IipiP8aO0SjK1w9j")
                // if defined
                .clientKey("npg9bsw4wQ2vHO2xx1T2rELbKKfmkBibVTB3mQs5")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
