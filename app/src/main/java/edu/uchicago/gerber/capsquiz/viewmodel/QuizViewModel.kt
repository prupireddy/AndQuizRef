package edu.uchicago.gerber.capsquiz.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class QuizViewModel  : ViewModel() {

    //this value used on the HomeScreen
    private var _playerName = mutableStateOf("Adam")
    //setting the value of a mutable state is easy, but to get it, you need to use state
    val playerName: State<String> = _playerName


    //////////////////////////////////
    //methods for HomeScreen
    //////////////////////////////////
    fun setPlayerName(name: String) {
        _playerName.value = name

    }


}