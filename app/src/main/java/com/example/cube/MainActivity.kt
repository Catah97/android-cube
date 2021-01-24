package com.example.cube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cube.`object`.Cube
import com.example.cube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val objectView = binding.objectView
        objectView.currentObject = Cube()
    }

    override fun onResume() {
        super.onResume()
        binding.objectView.resume()
    }

    override fun onPause() {
        binding.objectView.pause()
        super.onPause()
    }
}