package edu.uchicago.gerber.capsquiz.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uchicago.gerber.capsquiz.ui.theme.RedColor


@Composable
fun HomeScreen() {

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
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to Capitals Quiz",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Your Name",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.body1
                )
                OutlinedTextField(
                    value = "Adam",
                    onValueChange = {},
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    ),
                    maxLines = 1
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}