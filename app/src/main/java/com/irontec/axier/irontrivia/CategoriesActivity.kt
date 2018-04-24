package com.irontec.axier.irontrivia

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.irontec.axier.irontrivia.adapters.CategoryAdapter
import com.irontec.roomexample.database.AppDatabase
import kotlinx.android.synthetic.main.activity_categories.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CategoriesActivity : AppCompatActivity() {

    private var mAdapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        mAdapter = CategoryAdapter(this@CategoriesActivity, mutableListOf())
        category_list.adapter = mAdapter

        doAsync {

            val database = AppDatabase.getInstance(context = this@CategoriesActivity)
            val categories = database.categoriesDao().all

            uiThread {
                mAdapter!!.addAll(categories)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.empty, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
