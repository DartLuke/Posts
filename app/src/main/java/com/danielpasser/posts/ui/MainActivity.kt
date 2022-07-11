package com.danielpasser.posts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danielpasser.posts.R


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}