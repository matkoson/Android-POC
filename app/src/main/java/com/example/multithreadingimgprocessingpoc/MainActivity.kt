package com.example.multithreadingimgprocessingpoc

import ImageProcessor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img: ImageView = findViewById(R.id.imageView)
        val imgDrawable: Drawable = img.getDrawable()
        val rawBitmap: Bitmap = (imgDrawable as BitmapDrawable).getBitmap()
        val imageProcessor = ImageProcessor(rawBitmap);

        val singleThreadButton: Button = findViewById(R.id.button_single_thread);
        val multiThreadButton: Button = findViewById(R.id.button_multi_thread);
        val revertButton: Button = findViewById(R.id.button_revert);
        /Users/matkoson/dev/repos/poc/MultiThreadingImgProcessingPOC/app/src/main/java/com/example/multithreadingimgprocessingpoc/MainActivity.kt
        singleThreadButton.setOnClickListener(View.OnClickListener() {
            Log.d("MainActivity", "Single thread button clicked")
            val processedBitmap =
                imageProcessor.processWithSingleThread({ pixel -> imageProcessor.lighter(pixel) });
            img.setImageBitmap(processedBitmap);
        });

        multiThreadButton.setOnClickListener(View.OnClickListener() {

            GlobalScope.launch {
                Log.d("MainActivity", "Single thread button clicked")
                val processedBitmap = imageProcessor.processWithMultiThread({ pixel -> imageProcessor.lighter(pixel) });
                runOnUiThread(Runnable() {
                    img.setImageBitmap(processedBitmap);
                })
            }
        });

        revertButton.setOnClickListener(View.OnClickListener() {
            img.setImageBitmap(rawBitmap);
        });
    }
}
