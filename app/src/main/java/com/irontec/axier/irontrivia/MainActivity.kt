package com.irontec.axier.irontrivia

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import androidx.core.content.edit
import com.irontec.axier.irontrivia.networking.RxTriviaService
import com.irontec.axier.rxweather.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuInflater
import android.view.MenuItem
import com.irontec.roomexample.database.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.afollestad.materialdialogs.MaterialDialog
import android.R.array
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        browser_categories.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoriesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun doGetTriviaQuestions(category: Int) {
        ServiceGenerator.createService(RxTriviaService::class.java)
                .getQuestions("","multiple", 10, category, "easy")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ triviaGame ->
                }, { throwable -> throwable.printStackTrace() }) { Log.d("MainActivity", "Completed") }
    }

    private fun showCategories() {
        doAsync {

            val database = AppDatabase.getInstance(context = this@MainActivity)
            val categories = database.categoriesDao().all

            uiThread {
                MaterialDialog.Builder(this@MainActivity)
                        .title(R.string.categories)
                        .items(categories.map { it -> it.name })
                        .itemsCallback(MaterialDialog.ListCallback { dialog, view, which, text ->
                            doGetTriviaQuestions(categories[which].id!!)
                        })
                        .show()
            }
        }
    }

    private fun showDificulty() {

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
