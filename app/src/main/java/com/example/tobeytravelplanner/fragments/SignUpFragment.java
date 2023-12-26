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

import com.example.tobeytravelplanner.HomeScreen;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpFragment extends Fragment {

    AppCompatActivity context;

    public SignUpFragment(AppCompatActivity context) {
        this.context = context;
    }

    public SignUpFragment() {

    }

    EditText name,email,password;
    Button signup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        name = view.findViewById(R.id.signUpName);
        email = view.findViewById(R.id.signUpEmail);
        password = view.findViewById(R.id.signUpPassword);
        signup = view.findViewById(R.id.signUpBtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    signup.setEnabled(false);
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User(name.getText().toString(),email.getText().toString());
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Account Create Successfully", Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, HomeScreen.class));
                                                    context.finish();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Something went wrong please try again later"+e.toString(), Toast.LENGTH_SHORT).show();
                                    signup.setEnabled(true);
                                }
                            });
                }
            }
        });
        return view;
    }

    private boolean validate() {
        if (name.getText().toString().trim().isEmpty()){
            name.setError("Name can not be empty");
            return false;
        }
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