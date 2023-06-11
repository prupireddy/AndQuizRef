package edu.uchicago.gerber.capsquiz.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uchicago.gerber.capsquiz.CapsQuizApplication
import edu.uchicago.gerber.capsquiz.R
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



    init {
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
    private suspend fun getPipedCountryAndCapital() : List<String> {

        //arrayDeferred is the future value returned by .async
        val arrayDeferred = CoroutineScope(Dispatchers.IO).async {
            CapsQuizApplication.app.resources.getStringArray(R.array.countries_capitals)
        }
        //calling .await() forces execution to wait until the value gets returned
        val array = arrayDeferred.await()
        val index: Int = Random.nextInt(array.size)
        return array[index].split(PIPE)

    }

    fun getQuestion() {

        //in order to update the UI, we must be on the UI aka Main thread in Android
        viewModelScope.launch(Dispatchers.Main) {
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
    }

    //this method is called when the user clicks the submit button
    fun submitAnswer(question: Question) {
        //to update the mutable-state, we first get the value from the state, increment it,
        val nextNumber: Int = questionNumber.value + 1
        //and then set the intermediate variable to the mutable-state
        _questionNumber.value = nextNumber

        //if the user selected the correct answer
        if (question.capital == selectedOption.value) {
            Log.d("ANSWER" ,"correct")
            // incrementCorrect()
        } else {
            Log.d("ANSWER" ,"incorrect")
            //incrementIncorrect()
        }
        //queue up another valid question
        getQuestion()
        //clear out the selected value
        clearSelectedOption()
    }

    //this method is called when the user selects a radio button
    fun selectOption(option: String) {
        _selectedOption.value = option

    }

    //just reset the mutableState to empty string upon new Question
    private fun clearSelectedOption() {
        _selectedOption.value = ""
    }




}