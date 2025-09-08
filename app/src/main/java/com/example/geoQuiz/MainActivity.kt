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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoQuiz.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val questions = listOf(
                        Question("Bern is the capital of Sweden", true),
                        Question("The Earth revolves around the Sun", true),
                        Question("Sydney is the capital of Australia", false),
                        Question("Water boils at 100°C at sea level", true),
                        Question("The Great Wall of China is visible from space with the naked eye", false),
                        Question("Mount Everest is the highest mountain on Earth", true),
                        Question("The Amazon River is the longest river in the world", false),
                        Question("Paris is the capital of France", true),
                        Question("The Sahara is the largest desert on Earth", true),
                        Question("Penguins can fly", false)
                    )
                    GeoQuiz(questions, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GeoQuiz(questions: List<Question>, modifier: Modifier) {
    var selectedAnswer by remember { mutableStateOf<Boolean?>(null) }
    val geoRegular = FontFamily(Font(R.font.georegular))
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFinished by remember { mutableStateOf(false) } // 👈 добавляем флаг окончания

    val context = LocalContext.current

    if (isFinished) {

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 Congratulations!\nYou passed Geo Quiz!",
                fontSize = 30.sp,
                fontFamily = geoRegular,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Button(onClick = {

                currentIndex = 0
                selectedAnswer = null
                isFinished = false
            }) {
                Text("Again", fontFamily = geoRegular)
            }
        }
    } else {
        // Questions
        val question = questions[currentIndex]

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Geo Quiz",
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = geoRegular,
                textAlign = TextAlign.Center
            )
            Spacer(modifier.padding(20.dp))
            // Question
            Text(
                text = question.text,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                fontFamily = geoRegular,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
            Row {
                Button(
                    onClick = {
                        onAnswerClick(context, question.answer, true)
                        selectedAnswer = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            selectedAnswer == null -> Color.Gray
                            selectedAnswer == true && !question.answer -> Color.Red
                            selectedAnswer == true && question.answer -> Color.Green
                            else -> Color.Gray
                        }
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "True", fontFamily = geoRegular)
                }
                Button(
                    onClick = {
                        onAnswerClick(context, question.answer, false)
                        selectedAnswer = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            selectedAnswer == null -> Color.Gray
                            selectedAnswer == false && question.answer -> Color.Red
                            selectedAnswer == false && !question.answer -> Color.Green
                            else -> Color.Gray
                        }
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "False", fontFamily = geoRegular)
                }
            }
            // Counter
            Text(
                text = "${currentIndex + 1}/${questions.size}",
                fontSize = 24.sp,
                modifier = modifier.fillMaxWidth(),
                fontFamily = geoRegular,
                textAlign = TextAlign.Center
            )
            selectedAnswer?.let {
                Button(onClick = {
                    if (currentIndex < questions.lastIndex) {
                        currentIndex++
                        selectedAnswer = null
                    } else {
                        isFinished = true
                    }
                }) {
                    Text(text = "Next", fontFamily = geoRegular)
                }
            }
        }
    }
}


private fun onAnswerClick(context: Context, right: Boolean, answer: Boolean) {
    when(answer==right) {
        true -> Toast.makeText(context, "Right", Toast.LENGTH_LONG).show()
        false -> Toast.makeText(context, "Alas", Toast.LENGTH_LONG).show()
    }
}
