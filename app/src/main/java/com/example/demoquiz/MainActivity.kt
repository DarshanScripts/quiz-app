package com.example.demoquiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var questionsList: ArrayList<QuestionModel>
    private var index: Int = 0
    lateinit var questionModel: QuestionModel

    private var correctAnswerCount: Int = 0
    private var wrongAnswerCount: Int = 0

    lateinit var countDown: TextView
    lateinit var questions: TextView
    lateinit var option1: Button
    lateinit var option2: Button
    lateinit var option3: Button
    lateinit var option4: Button

    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countDown = findViewById(R.id.countdown)
        questions = findViewById(R.id.questions)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        //question list
        questionsList = ArrayList()
        questionsList.add(
            QuestionModel(
                "What is 2 + 2?",
                "4",
                "5",
                "6",
                "7",
                "4"
            )
        )
        questionsList.add(
            QuestionModel(
                "What is the capital of India?",
                "Kerala",
                "Gujarat",
                "Delhi",
                "Madhya Pradesh",
                "Delhi"
            )
        )
        questionsList.add(
            QuestionModel(
                "Which planet in our solar system is known as the Red Planet?",
                "Venus",
                "Mars",
                "Jupiter",
                "Saturn",
                "Mars"
            )
        )
        questionsList.add(
            QuestionModel(
                "Which country is the birthplace of cricket game?",
                "France",
                "New Zealand",
                "Australia",
                "England",
                "England"
            )
        )
        questionsList.add(
            QuestionModel(
                "WHO stands for?",
                "World Humanitarian Organization",
                "World Health Organization",
                "World Heart Organization",
                "World Help Organization",
                "World Health Organization"
            )
        )

        //questionsList.shuffle()
        questionModel = questionsList[index]
        setAllQuestions()
        countdown()
    }

    fun countdown() {
        var duration: Long = TimeUnit.SECONDS.toMillis(15)
        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var sDuration: String = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
                countDown.text = sDuration
            }

            override fun onFinish() {
                index++
                if (index < questionsList.size) {
                    questionModel = questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    countdown()
                } else {
                    gameResult()
                }
            }
        }.start()
    }

    private fun correctAns(option: Button) {
        option.background = getDrawable(R.drawable.right_bg)
        correctAnswerCount++
    }

    private fun wrongAns(option: Button) {
        option.background = resources.getDrawable(R.drawable.wrong_bg)
        wrongAnswerCount++
    }

    private fun gameResult() {
        var intent = Intent(this, ResultActivity::class.java)

        intent.putExtra("correct", correctAnswerCount.toString())
        intent.putExtra("total", questionsList.size.toString())
        startActivity(intent)
    }

    private fun setAllQuestions() {
        questions.text = questionModel.question
        option1.text = questionModel.option1
        option2.text = questionModel.option2
        option3.text = questionModel.option3
        option4.text = questionModel.option4
    }

    private fun enableButton() {
        option1.isClickable = true
        option2.isClickable = true
        option3.isClickable = true
        option4.isClickable = true
    }

    private fun disableButton() {
        option1.isClickable = false
        option2.isClickable = false
        option3.isClickable = false
        option4.isClickable = false
    }

    private fun resetBackground() {
        option1.background = resources.getDrawable(R.drawable.option_bg)
        option2.background = resources.getDrawable(R.drawable.option_bg)
        option3.background = resources.getDrawable(R.drawable.option_bg)
        option4.background = resources.getDrawable(R.drawable.option_bg)
    }

    fun option1Clicked(view: View) {
        disableButton()
        if (questionModel.option1 == questionModel.answer) {
            option1.background = resources.getDrawable(R.drawable.right_bg)
            correctAns(option1)
        } else {
            wrongAns(option1)
        }
    }

    fun option2Clicked(view: View) {
        disableButton()
        if (questionModel.option2 == questionModel.answer) {
            option2.background = resources.getDrawable(R.drawable.right_bg)
            correctAns(option2)
        } else {
            wrongAns(option2)
        }
    }

    fun option3Clicked(view: View) {
        disableButton()
        if (questionModel.option3 == questionModel.answer) {
            option3.background = resources.getDrawable(R.drawable.right_bg)
            correctAns(option3)
        } else {
            wrongAns(option3)
        }
    }

    fun option4Clicked(view: View) {
        disableButton()
        if (questionModel.option4 == questionModel.answer) {
            option4.background = resources.getDrawable(R.drawable.right_bg)
            correctAns(option4)
        } else {
            wrongAns(option4)
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        } else {
            backToast = Toast.makeText(baseContext, "DOUBLE PRESS TO QUIT GAME", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}