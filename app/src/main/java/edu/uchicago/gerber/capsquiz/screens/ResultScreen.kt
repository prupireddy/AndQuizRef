package edu.uchicago.gerber.capsquiz.screens


import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uchicago.gerber.capsquiz.ui.theme.RedColor


@Composable
fun ResultScreen() {

    val correctSubmissions = 46
    val incorrectSubmissions = 5
    val scorePercent = 92.19898989898
    val playerName = "Adam"

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
        //pass the paddingValues from Scaffold into the children to be used
            paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
            ) {
                Text(
                    text = "$playerName, your results are:",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                    style = MaterialTheme.typography.h6
                )
                Divider()

                //insert scores here
                
            } //end outer column

            //insert row of buttons here

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen()
}
