package com.example.animalfinder_hw1_17050111016

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.level_design.*
import java.util.*


class Level5 : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var timer: Int = 8
    private var bonusPerSecond: Int = 16000 + timer * 800
    private var score: Int = 0
    private var playTimes: Int = 1
    private val animals: MutableList<Animal> = AnimalList().getRandomAnimal(8)
    private val correctChoice: Int = (0 until animals.size).random()
    private lateinit var tts:TextToSpeech
    private lateinit var mediaPlayer: MediaPlayer

    var countDownTimer = object : CountDownTimer((1000 * this.timer).toLong(), 1000) {
        override fun onFinish() {
            Log.d("Timer", "finished $timer")
        }

        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            timer--;
            twLevelTimer.text = "$timer"
            twLevelScore.text = "Score: $score (+${bonusPerSecond * timer} bonus)"
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.level_design)
        mediaPlayer = MediaPlayer.create(applicationContext, animals[correctChoice].sound)
        playTimes = intent.getIntExtra("PLAY_TIMES", 1)
        score = intent.getIntExtra("CURRENT_SCORE", 0)
        ib1.setImageResource(animals[0].image)
        ib2.setImageResource(animals[1].image)
        ib3.setImageResource(animals[2].image)
        ib4.setImageResource(animals[3].image)
        ib5.setImageResource(animals[4].image)
        ib6.setImageResource(animals[5].image)
        ib7.setImageResource(animals[6].image)
        ib8.setImageResource(animals[7].image)
        twLevelScore.text = "Score: $score"
        twLevelTimer.text = "$timer"
        twLevelName.text = "Level 5 (Stage $playTimes/3)"
        tts = TextToSpeech(this, this)
        ib1.setOnClickListener {
            checkAnswer(ib1)
        }
        ib2.setOnClickListener {
            checkAnswer(ib2)
        }
        ib3.setOnClickListener {
            checkAnswer(ib3)
        }
        ib4.setOnClickListener {
            checkAnswer(ib4)
        }
        ib5.setOnClickListener {
            checkAnswer(ib5)
        }
        ib6.setOnClickListener {
            checkAnswer(ib6)
        }
        ib7.setOnClickListener {
            checkAnswer(ib7)
        }
        ib8.setOnClickListener {
            checkAnswer(ib8)
        }
    }

    private fun checkAnswer(btn: ImageButton) {
        mediaPlayer.release()
        countDownTimer.cancel()
        if((correctChoice + 1).toString() == btn.tag) {
            tts.speak("That was correct!", TextToSpeech.QUEUE_ADD, null, "correct answer")
        } else {
            tts.speak("Oh no! That was ${animals[correctChoice].name}, why don't you try again?", TextToSpeech.QUEUE_ADD, null, "wrong answer")
        }
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR){
            tts.language = Locale.US // settings TTS language to English
            tts.speak("Which animal is this? Listen carefully.", TextToSpeech.QUEUE_ADD, null, "question")
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onDone(utteranceId: String) {
                    if(utteranceId == "question") { // if question finished
                        mediaPlayer.start() // play animal sound
                        countDownTimer.start()
                    } else if(utteranceId == "correct answer") {
                        score += timer * bonusPerSecond
                        if(playTimes == 3) {
                            FileOperations().writeHighScoreToTheFile(applicationContext, score)
                            tts.speak("Congratulations, you finished the game!", TextToSpeech.QUEUE_ADD, null, "game finished")
                        } else {
                            val intent = Intent(applicationContext, Level5::class.java)
                            intent.putExtra("PLAY_TIMES", playTimes + 1)
                            intent.putExtra("CURRENT_SCORE", score)
                            finish()
                            startActivity(intent)
                        }
                    } else if(utteranceId == "wrong answer") {
                        FileOperations().writeHighScoreToTheFile(applicationContext ,score)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    } else if(utteranceId == "game finished") {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                }

                override fun onError(utteranceId: String?) {
                    Log.d("TTS", "TTS Error...")
                }

                override fun onStart(utteranceId: String?) {
                    Log.e("TTS", "TTS started...")
                }
            })
        } else {
            Log.e("TTS", "TTS not working...")
        }
    }
}
