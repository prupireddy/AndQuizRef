package edu.uchicago.gerber.capsquiz.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.uchicago.gerber.capsquiz.navigation.Screen
import edu.uchicago.gerber.capsquiz.ui.theme.RedColor
import edu.uchicago.gerber.capsquiz.viewmodel.QuizViewModel
import edu.uchicago.gerber.quiz4class.model.Question


@Composable
fun QuestionScreen(navController: NavController, viewModel: QuizViewModel) {


    val selectedOption: String = viewModel.selectedOption.value
    val question: Question = viewModel.question.value
    val questionNumber: Int = viewModel.questionNumber.value
    val answers: List<String> = viewModel.question.value.allAnswers



    Scaffold(
        //define the padding at the root
        Modifier.padding(paddingValues = PaddingValues(all = 10.dp)),
        topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Capitals Quiz",
                    textAlign = TextAlign.Start,
                )
            },
        )
    }) {
        paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7f)
                ) {
                    Text(
                        text = "Question $questionNumber :",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Divider()
                    Text(
                        text = question.questionText,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        style = MaterialTheme.typography.h6
                    )
                    answers.forEach { option ->
                        Box(modifier = Modifier
                            .padding(2.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { viewModel.selectOption(option = option) }
                            )) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)

                            ) {
                                RadioButton(selected = selectedOption == option, onClick = {
                                    viewModel.selectOption(option = option)
                                })
                                Text(
                                    text = option,
                                    modifier = Modifier
                                        .padding(top = 12.dp),

                                    style = MaterialTheme.typography.subtitle1
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(12.dp),
                ) {
                    Button(
                        onClick = {
                            viewModel.submitAnswer(question = question)
                        },
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight(),
                        enabled = selectedOption.isNotBlank()
                    ) {
                        Text(
                            text = "Submit", style = MaterialTheme.typography.button.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            navController.popBackStack(Screen.Question.route, true)
                            navController.navigate(Screen.Result.route)
                        },
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)

                    ) {
                        Text(
                            text = "Quit", style = MaterialTheme.typography.button.copy(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                    }
                }

            }

        }



}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview(quizViewModel: QuizViewModel = QuizViewModel()) {
    QuestionScreen(navController = rememberNavController(), viewModel= quizViewModel)
}
