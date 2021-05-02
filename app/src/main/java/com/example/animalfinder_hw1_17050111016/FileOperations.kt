package com.example.animalfinder_hw1_17050111016

import android.content.Context
import java.io.File

class FileOperations {
    private val fileName = "bestScore.txt"

    fun readHighScoreFromFile(context: Context): Int {
        val file = File(context.filesDir, fileName)

        if(file.exists()) { // If high score file exists, return high score value
            val contents = file.readText()
            return contents.toInt()
        } else { // If high score file is not exists, create high score file and return 0
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write("0".toByteArray())
            }
            return 0
        }
    }

    fun writeHighScoreToTheFile(context: Context, score: Int) {
        if(score > readHighScoreFromFile(context)) {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(score.toString().toByteArray())
            }
        }
    }
}