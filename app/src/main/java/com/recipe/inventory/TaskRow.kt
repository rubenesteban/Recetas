package com.recipe.inventory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recipe.inventory.ui.AppViewModelProvider
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recipe.inventory.data.Item

import com.recipe.inventory.ui.navigation.NavigationDestination
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.launch




@Composable
fun TaskRow (sharedViewModel: SharedViewModel = viewModel(factory = AppViewModelProvider.Factory), modifier: Modifier = Modifier) {


    var tuID: String by remember { mutableStateOf("") }
    var lati: String by remember { mutableStateOf("") }
    var logt: String by remember { mutableStateOf("") }
    var phone: String by remember { mutableStateOf("") }
    var phoneInt: Long by remember { mutableStateOf(0) }
    var cuant: String by remember { mutableStateOf("") }




   // val item by sharedViewModel.mapUiState.collectAsState(emptyList())
   /// val mapUiState by sharedViewModel.mapUiState.collectAsState()


    val context = LocalContext.current

    val scope = rememberCoroutineScope()



    Scaffold(

    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),

            modifier = Modifier.padding(padding)
        ) {


           // item { TaskOata(sharedViewModel = viewModel())}
          //  item { NoteItem(note = mapUiState) }


        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: MapUiState){

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp)
    ){
        note.userList.forEach {
            item {
                Notelist(note = it)
            }
        }
    }

}


@Composable
fun Notelist(note: Item){

    Card(
        modifier = Modifier.padding(6.dp),
    ){
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
        ) {

        }
        Text(text = note.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text =  note.profession,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight =  15.sp
        )

    }

}


@Composable
fun TaskOata(sharedViewModel: SharedViewModel ) {

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))


        }
        Button(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            onClick = {scope.launch {


            }

            }
        ){
            Text(text = "Load  Data")
        }

    }
}