package com.example.animalfinder_hw1_17050111016

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var highScore by Delegates.notNull<Int>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

        // Check & set highscore from text file
        this.highScore = FileOperations().readHighScoreFromFile(this)
        twHighScore.text = "High Score: ${this.highScore}"

        btPlay.setOnClickListener {
            val intent = Intent(this, Level1::class.java)
            startActivity(intent)
            finish()
        }

        btExit.setOnClickListener {
            finish()
        }
    }
}