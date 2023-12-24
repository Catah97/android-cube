package com.example.cube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidcube.databinding.ActivityMainBinding
import com.example.cube.`object`.Cube
import com.example.cube.view.old.structs.Scene

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val objectView = binding.objectView
        objectView.apply {
            currentObject = Cube()
            if (savedInstanceState != null) {
                val savedScene = savedInstanceState.getSerializable(SCENE_KEY) as Scene
                scene.use(savedScene)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val scene = binding.objectView.scene
        outState.putSerializable(SCENE_KEY, scene)
    }

    override fun onResume() {
        super.onResume()
        binding.objectView.resume()
    }

    override fun onPause() {
        binding.objectView.pause()
        super.onPause()
    }

    companion object {
        private const val SCENE_KEY = "sceneSaveKey"
    }
}