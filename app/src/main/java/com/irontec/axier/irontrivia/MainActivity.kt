package com.irontec.axier.irontrivia

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.Menu
import com.irontec.axier.irontrivia.networking.RxTriviaService
import com.irontec.axier.rxweather.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import android.view.MenuItem
import com.irontec.roomexample.database.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.afollestad.materialdialogs.MaterialDialog
import com.irontec.axier.irontrivia.models.TriviaGame
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.os.Build
import android.text.Spanned




class MainActivity : AppCompatActivity() {

    var currentQuestionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doGetTriviaQuestions()
    }

    private fun doGetTriviaQuestions() {
        val dificulty = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                .getString("game_dificulty", "easy")
        val category = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                .getInt("game_category", 9)
        ServiceGenerator.createService(RxTriviaService::class.java)
                .getQuestions("","multiple", 10, category, dificulty)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ triviaGame ->
                    startGame(triviaGame)
                }, { throwable -> throwable.printStackTrace() }) { Log.d("MainActivity", "Completed") }
    }

    private fun startGame(triviaGame: TriviaGame) {
        val question = triviaGame.questions!![currentQuestionIndex]
        question_text.text = fromHtml(question.question!!)

        val responses = mutableListOf<String>()
        responses.addAll(question.incorrectAnswers!!)
        responses.add(question.correctAnswer!!)
        responses.shuffle()

        btn1.text = fromHtml(responses[0])
        btn2.text = fromHtml(responses[1])
        btn3.text = fromHtml(responses[2])
        btn4.text = fromHtml(responses[3])

        btn1.onClick {
            checkAnswer(btn1.text.toString(), question.correctAnswer!!)
            nextGame(triviaGame)
        }
        btn2.onClick {
            checkAnswer(btn2.text.toString(), question.correctAnswer!!)
            nextGame(triviaGame)
        }
        btn3.onClick {
            checkAnswer(btn3.text.toString(), question.correctAnswer!!)
            nextGame(triviaGame)
        }
        btn4.onClick {
            checkAnswer(btn4.text.toString(), question.correctAnswer!!)
            nextGame(triviaGame)
        }
    }

    private fun checkAnswer(answeredText: String, correctAnswer: String) {
        if (answeredText == correctAnswer) {
            //TODO increase points
        }
    }

    private fun nextGame(triviaGame: TriviaGame) {
        if (currentQuestionIndex < 10) {
            currentQuestionIndex++
            startGame(triviaGame)
        } else {
            currentQuestionIndex = 0
            doGetTriviaQuestions()
        }
    }

    private fun showCategories() {
        doAsync {

            val database = AppDatabase.getInstance(context = this@MainActivity)
            val categories = database.categoriesDao().all
            uiThread {
                MaterialDialog.Builder(this@MainActivity)
                        .title(R.string.categories)
                        .items(categories.map { it -> it.name })
                        .itemsCallback { dialog, view, which, text ->
                            PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                                    .edit()
                                    .putInt("game_category", categories[which].id)
                                    .commit()
                            doGetTriviaQuestions()
                        }
                        .show()
            }
        }
    }

    private fun showDificulty() {
        var dificulty = "easy"
        MaterialDialog.Builder(this@MainActivity)
                .title(R.string.categories)
                .items(listOf(
                        getString(R.string.easy),
                        getString(R.string.medium),
                        getString(R.string.hard)
                ))
                .itemsCallback { dialog, view, which, text ->
                    when(which) {
                        0 -> dificulty = "easy"
                        1 -> dificulty = "medium"
                        2 -> dificulty = "hard"
                    }
                }
                .show()

        PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                .edit()
                .putString("game_dificulty", dificulty)
                .commit()
        doGetTriviaQuestions()
    }

    private fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.dificulty -> {
                showDificulty()
                true
            }
            R.id.categories -> {
                showCategories()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
