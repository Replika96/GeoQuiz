package com.example.geoQuiz

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.geoQuiz.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GeoQuiz(
                        question = "Bern is the capital of Sweden",
                        right = true,
                        modifier = Modifier.padding(innerPadding),
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}

@Composable
fun GeoQuiz(
    question: String,
    right: Boolean,
    modifier: Modifier = Modifier,
    context: Context,
) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text( text = question, fontSize = 20.sp)
        Row {
            Button(onClick = { onTrueClick(context, right, true) }) {
                Text( text = "True")
            }
            Button(onClick = { onTrueClick(context, right, false) }) {
                Text( text = "False")
            }

        }
        Button(onClick = TODO()) {
            Text(text = "Next")
        }
    }
}

private fun onTrueClick(context: Context, right: Boolean, answer: Boolean) {
    when(answer==right) {
        true -> Toast.makeText(context, "Right", Toast.LENGTH_LONG).show()
        false -> Toast.makeText(context, "Alas", Toast.LENGTH_LONG).show()
    }
}
