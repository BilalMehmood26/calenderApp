package com.buzztech.calenderapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private var _binding :ActivityAuthBinding?=null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

    }
}