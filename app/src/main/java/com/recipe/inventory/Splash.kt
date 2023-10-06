package com.recipe.inventory

import android.provider.CalendarContract
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.recipe.inventory.ui.AppViewModelProvider
import com.recipe.inventory.ui.navigation.NavigationDestination

import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import com.recipe.inventory.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



object SplashDestination : NavigationDestination {
    override val route = "splash"
    override val titleRes = R.string.get
}



@Composable
fun SplashScreen(
    navigateToTaskEntry: () -> Unit,
    navigateToTaskLogin: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //loviewModel: TaskDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }
    var splash: Boolean by remember { mutableStateOf(false) }



    suspend fun loather() {
        splash = viewModel.austin()

    }

    LaunchedEffect(Unit) {
        //  loviewModel.reduceQuantityByA()
        loather()
        //loviewModel.reduceQuantityByB()
    }


    suspend fun frif(){
        // splash = viewModel.austin()
        if (splash){
            navigateToTaskEntry()
        }else{
            navigateToTaskLogin()
        }

    }


    // val context = LocalContext.current

    // val telStore = StoreUserTil(context)
    // get saved email
    // val savedTil = telStore.getTil.collectAsState(initial = "")

    val scope = rememberCoroutineScope()


    suspend fun loadWeather() {
        // splash = savedTil.value as Boolean
        delay(3750L)

        frif()
    }







    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)

                }
            ),

            )
        loadWeather()

    }





    // if (iScoreRunning) navigateToSplash else navigateToEntry
    val color = CalendarContract.Colors.COLOR
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(343.dp)
            .scale(scale.value),

        shape = CircleShape,
        color = Color.Green,
        border = BorderStroke(width = 2.dp, color = Color.Magenta)


    ) {

        Card(
            modifier = Modifier
                .width(343.dp)
                .height(343.dp)
                .padding(1.dp)
                .clickable { scope.launch {
                    // telStore.saveTil(splash)
                }  },
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {





                Image(painter = painterResource(id = R.drawable.cooking),
                    contentDescription = null,
                    modifier = Modifier
                        .size(199.dp)
                        .padding(16.dp)
                        .clip(CircleShape)
                )

                Text("Comida Casera y mas...!!",

                    color = Color.Black.copy(alpha = 0.5f))

            }
        }


    }
}



