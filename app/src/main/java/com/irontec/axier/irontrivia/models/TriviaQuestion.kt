package com.irontec.axier.irontrivia.models

import com.google.gson.annotations.SerializedName

class TriviaQuestion {

    @SerializedName("category")
    var category: String? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("difficulty")
    var difficulty: String? = null
    @SerializedName("question")
    var question: String? = null
    @SerializedName("correct_answer")
    var correctAnswer: String? = null
    @SerializedName("incorrect_answers")
    var incorrectAnswers: List<String>? = null

}