package com.example.ict4gs.ui.training

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ict4gs.R

class AutismTestActivity : AppCompatActivity() {

    private lateinit var questions: List<Question>
    private var currentIndex = 0
    private var totalScore = 0

    private lateinit var questionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var nextButton: Button

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val savedLanguage = sharedPref.getString("language", "English")
        val context = com.example.ict4gs.utils.LocaleHelper.setLocale(newBase, savedLanguage!!)
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autism_test)


        // ✅ Build questions from string resources
        questions = listOf(
            Question(getString(R.string.question1), true),
            Question(getString(R.string.question2), true),
            Question(getString(R.string.question3), true),
            Question(getString(R.string.question4), true),
            Question(getString(R.string.question5), false),
            Question(getString(R.string.question6), false),
            Question(getString(R.string.question7), true),
            Question(getString(R.string.question8), false)
        )

        // ✅ Find your views from the XML
        questionText = findViewById(R.id.questionText)
        radioGroup = findViewById(R.id.radioGroup)
        nextButton = findViewById(R.id.nextButton)

        // ✅ Load the first question
        loadQuestion()

        // ✅ Set the button click
        nextButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, getString(R.string.please_answer_all), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedOption = when (selectedId) {
                R.id.option1 -> 0
                R.id.option2 -> 1
                R.id.option3 -> 2
                R.id.option4 -> 3
                R.id.option5 -> 4
                else -> 0
            }

            val isPositive = questions[currentIndex].isPositive
            val score = if (isPositive) selectedOption else 4 - selectedOption
            totalScore += score

            currentIndex++
            if (currentIndex < questions.size) {
                loadQuestion()
            } else {
                showResult(totalScore)
            }
        }
        // Enable the top ActionBar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.autism_test) // Optional title

    }

    private fun loadQuestion() {
        questionText.text = "${currentIndex + 1}. ${questions[currentIndex].text}"
        radioGroup.clearCheck()
    }

    private fun showResult(score: Int) {
        val interpretation = when {
            score < 12 -> getString(R.string.result_low)
            score in 12..20 -> getString(R.string.result_moderate)
            else -> getString(R.string.result_high)
        }

        val message = getString(R.string.your_score, score) + "\n\n" + interpretation

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.test_result))
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Go back when back button is pressed
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    data class Question(val text: String, val isPositive: Boolean)
}
