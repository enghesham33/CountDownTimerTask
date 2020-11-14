package com.example.myapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTimerButton.setOnClickListener {
            if (enteredTimeEditText.text.toString().isDigitsOnly()) {
                cancelTimer()
                val remainingTimeInSeconds = enteredTimeEditText.text.toString().toLong() - (Calendar.getInstance().timeInMillis / 1000)
                countDownTimer =
                    object : CountDownTimer(remainingTimeInSeconds * 1000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timerTextView.text = getRemainingTime(millisUntilFinished / 1000)
                        }

                        override fun onFinish() {

                        }
                    }.start()
            } else {
                Toast.makeText(
                    MainActivity@ this,
                    "Please enter time in the future in seconds",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getRemainingTime(remainingSeconds: Long): String {
        val days = ((remainingSeconds / 60) / 60) / 24
        val hours = ((remainingSeconds / 60) / 60) % 24
        val minutes = (remainingSeconds / 60) % 60
        val seconds = (remainingSeconds % 60)
        return String.format(Locale.ENGLISH, "%02d", days) + ":" + String.format(
            Locale.ENGLISH,
            "%02d",
            hours
        ) + ":" + String.format(
            Locale.ENGLISH,
            "%02d",
            minutes
        ) + ":" + String.format(Locale.ENGLISH, "%02d", seconds)
    }

    private fun cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    override fun onPause() {
        super.onPause()
        cancelTimer()
    }

    override fun onStop() {
        super.onStop()
        cancelTimer()
    }


}