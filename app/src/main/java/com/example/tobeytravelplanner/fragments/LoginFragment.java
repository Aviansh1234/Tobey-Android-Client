package com.example.tobeytravelplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tobeytravelplanner.FirstScreen;
import com.example.tobeytravelplanner.HomeScreen;
import com.example.tobeytravelplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    AppCompatActivity context;

    public LoginFragment(AppCompatActivity context) {
        this.context = context;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    EditText email,password;
    Button login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.logInEmail);
        password = view.findViewById(R.id.logInPassword);
        login = view.findViewById(R.id.logInBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    login.setEnabled(false);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    context.startActivity(new Intent(context, FirstScreen.class));
                                    context.finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "The credentials are incorrect please try again", Toast.LENGTH_SHORT).show();
                                    login.setEnabled(true);
                                }
                            });
                }
            }
        });
        return view;
    }
    private boolean validate() {
        if (email.getText().toString().trim().isEmpty()){
            email.setError("Email can not be empty");
            return false;
        }
        if (password.getText().toString().trim().length()<6){
            password.setError("Password must be atleast 6 characters long");
            return false;
        }
        return true;
    }
}