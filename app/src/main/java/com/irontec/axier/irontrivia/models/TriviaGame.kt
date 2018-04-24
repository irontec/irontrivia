package com.irontec.axier.irontrivia.models

import com.google.gson.annotations.SerializedName

class TriviaGame {

    @SerializedName("response_code")
    var responseCode: Int? = null
    @SerializedName("results")
    var questions: List<TriviaQuestion>? = null

}