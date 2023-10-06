package com.recipe.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.recipe.inventory.R
import com.recipe.inventory.ui.AppViewModelProvider
import com.recipe.inventory.ui.navigation.NavigationDestination


object AddDataDestination : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.app_name
}

@Composable
fun AddDataScreen(
    navigateBack: () -> Unit,
    navigateToTaskEntry: () -> Unit,
    sharedViewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    var userID: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var profession: String by remember { mutableStateOf("") }
    var age: String by remember { mutableStateOf("") }
    var ageInt: Int by remember { mutableStateOf(0) }



    var qua: String by remember { mutableStateOf("") }
    var key: String by remember { mutableStateOf("") }



    val context = LocalContext.current




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
                .padding(start = 15.dp, top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navigateBack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back_button")
            }
        }
        // add data Layout
        Column(
            modifier = Modifier
                .padding(start = 60.dp, end = 60.dp, bottom = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // userID
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userID,
                onValueChange = {
                    userID = it
                },
                label = {
                    Text(text = "Category")
                }
            )
            // Name
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = {
                    name = it
                },
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
                label = {
                    Text(text = "Price")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // save Button

            Button(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                onClick = {
                    val userData = UserData(
                        userID = key,
                        name = name,
                        profession = profession

                    )

                    sharedViewModel.saveData(userData = userData, context = context)
                    navigateToTaskEntry()
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}