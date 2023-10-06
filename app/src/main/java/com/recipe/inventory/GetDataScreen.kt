package com.recipe.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recipe.inventory.ui.AppViewModelProvider
import com.recipe.inventory.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.recipe.inventory.R


object GetDataDestination : NavigationDestination {
    override val route = "get"
    override val titleRes = R.string.app_name
}
@Composable
fun GetDataScreen(
    navigateBack: () -> Unit,
   // navigateToTaskEntry: () -> Unit,
    sharedViewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }
    var key: String by remember { mutableStateOf("") }


    var qua: String by remember { mutableStateOf("") }

    var url: String by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    var costo: String by remember { mutableStateOf("") }
    suspend fun loadWeather() {

        key = viewModel.autin()

    }

    LaunchedEffect(Unit) {

        loadWeather()
    }

    // main Layout
    Column(modifier = Modifier.fillMaxSize()) {
        // back button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navigateBack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back_button")
            }
        }
        // get data Layout
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // userID
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    value = userID,
                    onValueChange = {
                        userID = it
                    },
                    label = {
                        Text(text = "UserID")
                    }
                )
                // get user data Button

                Button(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(100.dp),
                    onClick = {
                        sharedViewModel.retrieveData(
                            userID = key,
                            context = context
                        ) { data ->
                            name = data.name
                            profession = data.profession


                        }
                    }
                ) {
                    Text(text = "Get Data")
                }
            }
            // Name
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = {
                    name = it
                },
                enabled = false,
                label = {
                    Text(text = "Title")
                }
            )
            // Profession
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = profession,
                onValueChange = {
                    profession = it
                },
                enabled = false,
                label = {
                    Text(text = "Preparedness")
                }
            )
            // Age
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = age,
                onValueChange = {
                    age = it
                },
                enabled = false,
                label = {
                    Text(text = "Ingredient")
                }

            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = qua,
                onValueChange = {
                    qua = it
                },
                enabled = false,
                label = {
                    Text(text = "Quantity")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // save Button
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {scope.launch {
                    sharedViewModel.getAllUserDocuments(
                        context = context
                    ) { data ->
                        key = data.userID
                        name = data.name
                        profession = data.profession
                        url = data.url
                        age = data.age
                        qua = data.qua
                        costo = data.gol

                    }

                    }
                }
            ) {
                Text(text = "Save")
            }


        }
    }
}








