package com.artexceptionals.youreuro.helpers;

import com.artexceptionals.youreuro.YourEuroApp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SignInHelper {

    public static boolean isLoggedIn() {
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(YourEuroApp.getAppContext());
        return alreadyloggedAccount != null;
    }
}
