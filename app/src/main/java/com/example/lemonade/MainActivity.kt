package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

// Enum représentant les étapes de l'application
enum class LemonadeStep {
    TREE, LEMON, DRINK, EMPTY
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    // State pour l'étape actuelle
    var step by remember { mutableStateOf(LemonadeStep.TREE) }
    // State pour compter les pressions sur le citron
    var squeezeCount by remember { mutableStateOf(0) }
    // Nombre aléatoire de pressions nécessaires (2 à 4)
    var requiredSqueezes by remember { mutableStateOf(Random.nextInt(2, 5)) }

    // Définir l'image et le texte en fonction de l'étape
    val (imageRes, label) = when (step) {
        LemonadeStep.TREE -> R.drawable.lemon_tree to "Tap the lemon tree to select a lemon"
        LemonadeStep.LEMON -> R.drawable.lemon_squeeze to "Keep tapping the lemon to squeeze it"
        LemonadeStep.DRINK -> R.drawable.lemon_drink to "Tap the lemonade to drink it"
        LemonadeStep.EMPTY -> R.drawable.lemon_restart to "Tap the empty glass to start again"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier
                .clickable {
                    when (step) {
                        LemonadeStep.TREE -> {
                            step = LemonadeStep.LEMON
                            requiredSqueezes = Random.nextInt(2, 5)
                            squeezeCount = 0
                        }
                        LemonadeStep.LEMON -> {
                            squeezeCount++
                            if (squeezeCount >= requiredSqueezes) {
                                step = LemonadeStep.DRINK
                            }
                        }
                        LemonadeStep.DRINK -> step = LemonadeStep.EMPTY
                        LemonadeStep.EMPTY -> step = LemonadeStep.TREE
                    }
                }
                .padding(bottom = 16.dp)
        )

        Text(
            text = label,
            fontSize = 18.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lemon_tree),
                contentDescription = "Tap the lemon tree to select a lemon",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Tap the lemon tree to select a lemon",
                fontSize = 18.sp
            )
        }
    }
}
