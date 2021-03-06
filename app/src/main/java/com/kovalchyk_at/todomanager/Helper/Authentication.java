package com.kovalchyk_at.todomanager.Helper;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kovalchyk_at.todomanager.Model.User;

/**
 * Created by Kovalchyk_AT on 23.03.2018.
 */

public class Authentication extends AsyncTask<Void,Void,Void>{
    @Override
    protected Void doInBackground(Void... voids) {
        initialize();
        return null;
    }

    private static FirebaseAuth auth;
    private static FirebaseUser currentUser;


    public static String getCurrentUserId() {
        return currentUser.getUid();
    }
    public static void initialize() {
    /* in the onCreate() initialize FirebaseAuth*/
        auth = FirebaseAuth.getInstance();
    }

    public static boolean isSigned() {
        //check if user is signed in for update UI
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            return true; // вже ввійшов
        } else return false;// вхід не виконано

    }

    public static boolean createUser(@NonNull String email, @NonNull String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = auth.getCurrentUser();
                    currentUser.sendEmailVerification();
                    // update UI with signed in user information
                } else {
                    Log.w("MainActivity", "create user"+Authentication.isSigned(), task.getException());
                    // show messege
                }
            }
        });
        return isSigned();
    }

    public static boolean logInUser(@NonNull String email, @NonNull String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = auth.getCurrentUser();
                } else {
                    Log.w("MainActivity", "login user"+Authentication.isSigned(), task.getException());
                }
            }
        });
        return isSigned();
    }

    public static void getUserDate(User user) {
        if (currentUser != null) {
            user.setUserName(currentUser.getDisplayName());
            user.setUserId(currentUser.getUid());
        }
    }

    public static void logOut() {
        FirebaseAuth.getInstance().signOut();
        Log.w("MainActivity", "logout user"+Authentication.isSigned());
    }

    class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
