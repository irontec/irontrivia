package com.irontec.axier.irontrivia

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.core.content.edit
import com.irontec.axier.irontrivia.networking.RxTriviaService
import com.irontec.axier.rxweather.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        browser_categories.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoriesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun doGetTriviaQuestions() {
        PreferenceManager.getDefaultSharedPreferences(this).edit {

        }
        ServiceGenerator.createService(RxTriviaService::class.java)
                .getQuestions("", 10, "easy")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ triviaGame ->

                }, { throwable -> throwable.printStackTrace() }) { Log.d("MainActivity", "Completed") }
    }
}
