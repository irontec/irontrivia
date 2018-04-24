package com.irontec.axier.irontrivia

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.irontec.axier.irontrivia.models.TriviaCategory
import com.irontec.axier.irontrivia.networking.RxTriviaService
import com.irontec.axier.rxweather.ServiceGenerator
import com.irontec.roomexample.database.AppDatabase
import com.irontec.roomexample.database.entities.DBTriviaCategories
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.doAsync
import org.json.JSONObject


class TriviaApplication : Application() {

    override fun onCreate() {
        super.onCreate()


        ServiceGenerator.createService(RxTriviaService::class.java)
                .getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseBody ->
                    val body = JSONObject(responseBody.body()!!.string())
                    val categories: List<TriviaCategory> =
                            Gson().fromJson(
                                    body.get("trivia_categories").toString(),
                                    object : TypeToken<List<TriviaCategory>>() {}.type)
                    Log.d("TriviaApplication", categories.size.toString())
                    doAsync {
                        val dbCategories = mutableListOf<DBTriviaCategories>()
                        for (category in categories) dbCategories.add(DBTriviaCategories(category.id!!, category.name!!))
                        val database = AppDatabase.getInstance(context = this@TriviaApplication)
                        database.categoriesDao().insertAll(dbCategories)
                    }
                }, { throwable -> throwable.printStackTrace() }) { Log.d("TriviaApplication", "Completed") }
    }
}