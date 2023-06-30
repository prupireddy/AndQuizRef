package edu.uchicago.gerber.capsquiz.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uchicago.gerber.capsquiz.CapsQuizApplication
import edu.uchicago.gerber.capsquiz.R
import edu.uchicago.gerber.capsquiz.model.Constants
import edu.uchicago.gerber.capsquiz.model.Constants.CAPITAL_INDEX
import edu.uchicago.gerber.capsquiz.model.Constants.COUNTRY_INDEX
import edu.uchicago.gerber.capsquiz.model.Constants.PIPE
import edu.uchicago.gerber.capsquiz.model.Constants.REGION_INDEX
import edu.uchicago.gerber.quiz4class.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuizViewModel  : ViewModel() {



    //these values used on the QuestionScreen
    //the following is used for preview-only. We can ignore this in production app and we don't need a corresponding State
    private var previewAnswers = mutableListOf("Paris", "Berlin", "London", "Dublin", "Lisbon")

    //set all to hard-coded default values initially
    private var _playerName = mutableStateOf("Adam")
    val playerName: State<String> = _playerName

    private var _question = mutableStateOf<Question>(Question("Germany", "Berlin", "EUR", previewAnswers))
    val question: State<Question> = _question

    private var _questionNumber = mutableStateOf<Int>(1)
    val questionNumber: State<Int> = _questionNumber

    private var _selectedOption = mutableStateOf<String>("Berlin")
    val selectedOption: State<String> = _selectedOption


    //these values used on the ResultScreen
    private var _correctSubmissions = mutableStateOf(92)
    val correctSubmissions: State<Int> = _correctSubmissions

    private var _incorrectSubmissions = mutableStateOf(8)
    val incorrectSubmissions: State<Int> = _incorrectSubmissions



    init {
        //clear out the default values above which are used in Preview mode
        reset()
        clearSelectedOption()
        getQuestion()
    }

    //////////////////////////////////
    //methods for HomeScreen
    //////////////////////////////////
    fun setPlayerName(name: String) {
        _playerName.value = name

    }

    //////////////////////////////////
    //methods for QuestionScreen
    //////////////////////////////////
    //this method will fetch a random item from resources array such as <item>Greece|Athens|EUR</item>
    //and then split and return it as List<String>
    private fun getPipedCountryAndCapital() : List<String> {

        val index: Int = Random.nextInt(Constants.COUNTRY_CAP_REGION_ARRAY.size)
        return Constants.COUNTRY_CAP_REGION_ARRAY[index].split(PIPE)

    }

    fun getQuestion() {

            //gets a random country capitals from the array in resources
            val correctAnswer: List<String> = getPipedCountryAndCapital()
            //convert it into a new question object
            val question =
                Question(
                    correctAnswer[COUNTRY_INDEX],
                    correctAnswer[CAPITAL_INDEX],
                    correctAnswer[REGION_INDEX]
                )

            while (question.allAnswers.size < 5) {
                var potentialWrongAnswer = getPipedCountryAndCapital()
                //if any of these conditions are met, go fetch another one
                while (
                //the capital of potentialWrongAnswer is the same (same as correctAnswer), skip
                    potentialWrongAnswer[CAPITAL_INDEX] == correctAnswer[CAPITAL_INDEX] ||
                    //to make questions more difficult, the wrong answers should be in the same region, if not skip
                    potentialWrongAnswer[REGION_INDEX] != correctAnswer[REGION_INDEX] ||
                    //the wrong answers already contain the potentialWrongAnswer, skip
                    question.allAnswers.contains(potentialWrongAnswer[CAPITAL_INDEX])
                ) {
                    //go fetch another one
                    potentialWrongAnswer = getPipedCountryAndCapital()
                }
                //add the capital of the validated potentialWrongAnswer to the wrong answers of the question
                question.addAnswer(potentialWrongAnswer[CAPITAL_INDEX])
            }
            //add the correct answer
            question.addAnswer(question.capital)
            _question.value = question

    }

    fun submitAnswer(question: Question) {
        //to update the mutable-state, we first get the value from the state, increment it,
        val nextNumber: Int = questionNumber.value + 1
        //and then set the intermediate variable to the mutable-state
        _questionNumber.value = nextNumber

        //if the user selected the correct answer
        if (question.capital == selectedOption.value) {
            incrementCorrect()
        } else {
            incrementIncorrect()
        }
        //queue up another valid question
        getQuestion()
        //clear out the selected value
        clearSelectedOption()
    }

    fun selectOption(option: String) {
        _selectedOption.value = option

    }

    private fun clearSelectedOption() {
        _selectedOption.value = ""
    }

    //////////////////////////////////
    //methods for ResultScreen
    //////////////////////////////////
    fun anotherQuiz() {
        _correctSubmissions.value = 0
        _incorrectSubmissions.value = 0
        _questionNumber.value = 1
    }

    fun reset() {
        anotherQuiz()
        _playerName.value = ""
    }

    private fun incrementCorrect() {
        val correctSubmitted = correctSubmissions.value + 1
        _correctSubmissions.value = correctSubmitted
    }

    private fun incrementIncorrect() {
        val incorrectSubmitted = incorrectSubmissions.value + 1
        _incorrectSubmissions.value = incorrectSubmitted
    }

}


