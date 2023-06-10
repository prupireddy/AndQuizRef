package edu.uchicago.gerber.quiz4class.model

import java.util.*


class Question(
    val country: String,
    val capital: String,
    val region: String,
    //has a default value of empty mutable list of string
    val allAnswers: MutableList<String> = mutableListOf()
) {
    //this is static context
    companion object {
        private val random = Random()
    }

    //public method that allows client to insert values randomly
    fun addAnswer(answer: String) {
        if (allAnswers.size == 0){
            allAnswers.add(answer)
        } else {
            val insertAt = random.nextInt(allAnswers.size + 1)
            allAnswers.add(insertAt, answer )
        }
    }

    val questionText: String
        get() = "What is the capital of $country?"

}
