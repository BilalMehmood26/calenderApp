package com.buzztech.calenderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.buzztech.calenderapp.ui.auth.AuthActivity
import com.buzztech.calenderapp.ui.main.MainActivity
import com.buzztech.calenderapp.utils.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private val mAuth:FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(mAuth.currentUser!=null){
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }else {
            postDelayed(2000) {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
            }
        }
    }
}