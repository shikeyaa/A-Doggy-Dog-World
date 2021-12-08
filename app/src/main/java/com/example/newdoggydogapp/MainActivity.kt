package com.example.newdoggydogapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.newdoggydogapp.database.DogImageEntity
import com.example.newdoggydogapp.viewmodels.MainViewModel
import com.example.newdoggydogapp.viewmodels.MainViewModelFactory
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as DogApplication).database.dogImageDao())
    }

    private lateinit var imageBanner:ImageView
    private lateinit var dogBone:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeDogButton: Button = findViewById(R.id.button)
        val prevDogButton : Button = findViewById(R.id.prevButton)

        imageBanner = findViewById(R.id.bannerImage)
        dogBone = findViewById(R.id.dogBone)
        animateGlobe()

        viewModel.currentlyDisplayedImage.observe(this,
            {
                val mainImage : ImageView = findViewById(R.id.DogImageHolder)
                Picasso.with(this).load(it.imgSrcUrl).into(mainImage)
            })

        changeDogButton.setOnClickListener {
            val currentImgUrl = viewModel.currentlyDisplayedImage.value?.imgSrcUrl
            val newDogImage = currentImgUrl?.let { it1 -> DogImageEntity(imageUrl = it1) }

            viewModel.getNewDog()
            if (newDogImage != null) {
                viewModel.addDog(newDogImage)
            }
            viewModel.deleteMostRecentDog()
        }

        prevDogButton.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun animateGlobe() {
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)

        imageBanner.animation = rotate
        dogBone.animation = shake
    }


}
//   C:/Users/shike/AndroidStudioProjects/NewDoggyDogApp