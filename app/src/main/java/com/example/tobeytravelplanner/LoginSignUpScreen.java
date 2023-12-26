package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.tobeytravelplanner.adapters.LoginSignupPagerAdapter;
import com.example.tobeytravelplanner.fragments.LoginFragment;
import com.example.tobeytravelplanner.fragments.SignUpFragment;
import com.google.android.material.tabs.TabLayout;

public class LoginSignUpScreen extends AppCompatActivity {

    LoginSignupPagerAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_screen);

        viewPager = findViewById(R.id.loginSignupPager);
        tabLayout = findViewById(R.id.loginSignupTabLayout);
        adapter = new LoginSignupPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        LoginFragment loginFragment = new LoginFragment(this);
        SignUpFragment signUpFragment = new SignUpFragment(this);
        adapter.addFragment(signUpFragment, "Sign Up");
        adapter.addFragment(loginFragment, "Log In");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}