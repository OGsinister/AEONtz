package com.example.aeontz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aeontz.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}