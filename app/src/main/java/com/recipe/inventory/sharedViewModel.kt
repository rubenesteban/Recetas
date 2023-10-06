package com.recipe.inventory

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.recipe.inventory.data.Item
import com.recipe.inventory.data.ItemsRepository
import com.recipe.inventory.data.UserImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SharedViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {


    val _uiState = MutableStateFlow(UserData())
    val uiState: StateFlow<UserData> = _uiState.asStateFlow()


    private val firestore = FirebaseFirestore.getInstance()


    val mapUiState: StateFlow<MapUiState> =
        itemsRepository.getAllItemsStream().map { MapUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MapUiState()
            )




    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        var oclose = UserData()
    }



    private var userUiState by mutableStateOf(UserUiState())
        private set



    suspend fun deleteNote(noteId: String){
        val noteRef = firestore.collection("note").document(noteId)
        noteRef.delete().await()
    }
    fun getNotesFlow(userId: String): Flow<List<Note>> = callbackFlow{
        val notesRef = firestore.collection("notes")
            .whereEqualTo("userId", userId).orderBy("title")

        val subscription = notesRef.addSnapshotListener{ snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val notes = mutableListOf<Note>()
                for (document in querySnapshot.documents){
                    val note = document.toObject(Note::class.java)
                    note?.id = document.id
                    note?.let {notes.add(it)}
                }
                trySend(notes).isSuccess
            }
        }
        awaitClose{ subscription.remove()}


    }


    fun saveData(
        userData: UserData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        Log.d("MascotaFeliz", "saveData")
        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userData.userID)
        Log.d("MascotaFeliz", "saveData 1")

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun saveLike(
        userData: UserData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        Log.d("MascotaFeliz", "saveData")
        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userData.userID)
            .collection("likes")
            .document(userData.name)
        Log.d("MascotaFeliz", "saveData 1")

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun retrieveData(
        userID: String,
        context: Context,
        data: (UserData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val userData = it.toObject<UserData>()!!
                        data(userData)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun retrieveImage(
        userID: String,
        context: Context,
        data: (UserImage) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("images")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val userImage = it.toObject<UserImage>()!!
                        data(userImage)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(
        userID: String,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully deleted data", Toast.LENGTH_SHORT)
                        .show()

                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun datosbd(): Flow<List<UserData>> = callbackFlow{
        val db = Firebase.firestore
            .collection("user")


        db.get().addOnSuccessListener { result ->
            val notes = mutableListOf<UserData>()
            for (document in result) {
                Log.d("MascotaFeliz", "${document.id} => ${document.data}")
                Log.d(TAG, "${document.id} => ${document.data}")
                val note = document.toObject(UserData::class.java)
                note?.userID = document.id
                note?.let {notes.add(it)}
              //  reduceQuantityChenking1(notes )

                Log.d("MascotaFeliz", "Loqueando con en dataosbd ")

            }


        }
            .addOnFailureListener { exception ->
                Log.d("MascotaFeliz", "Error getting documents: ", exception)
                Log.d(TAG, "Error getting documents: ", exception)
            }



    }




    suspend fun deleteTask() {
        itemsRepository.deleteAllT()
    }


    fun getAllUserDocuments(
        context: Context,
        data: (UserData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")

        try {
            fireStoreRef.get()
                .addOnSuccessListener { result ->
                    for(document in result){
                        // for getting single or particular document
                        if (document.exists()) {
                            val userData = document.toObject<UserData>()!!
                            data(userData)
                            reduceQuantityChenking( userData.userID, userData.name, userData.profession, userData.url, userData.age, userData.qua, userData.gol)
                            Log.d("MascotaFeliz", "Loqueando con ")

                        } else {
                            Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }




    fun reduceQuantityChenking( p: String, h:String, s:String, w:String, z:String, q:String, t:String){
        viewModelScope.launch {
            val currentTask = userUiState.userDetails.toItem()

            deleteTask()
            itemsRepository.insertItem(currentTask.copy(userID = p, name = h, profession = s, url = w, age = z, qua = q, costo = t))
        }
    }



    fun reduceQuantityChenking1(notes : MutableList<UserData> ){
        viewModelScope.launch {
            val currentTask = notes.size
            val current = notes

            for (i in 0..currentTask-1){
                val current = notes[i]
                itemsRepository.insertItem(notes[i].toItem().copy(userID = current.userID, name = current.name, profession = current.profession))
            }



        }
    }


}




data class MapUiState(val userList: List<Item> = listOf())

data class NoteUiState(val noteList: List<UserData> = listOf())


data class Note(
    var id: String? =null,
    var userId: String = "",
    val title : String = "",
    val content : String = ""
)

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = true
)


data class UserData(
    val id: Int = 0,
    var userID: String = "",
    var name: String = "",
    var profession: String = "",
    var url: String = "",
    var age: String = "",
    var qua: String ="",
    var gol: String =""


)

data class UserDetails(
    val id: Int = 0,
    var userID: String = "",
    var name: String = "",
    var profession: String = "",
    var url: String = "",
    var age: String = "",
    var qua: String = "",
    var gol: String =""


)

/**
 * Extension function to convert [TaskUiState] to [Task]. If the value of [TaskDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [TaskUiState] is not a valid [Int], then the quantity will be set to 0
 */
fun UserDetails.toItem(): Item = Item(
    id = id,
    userID = userID,
    name = name,
    profession = profession,
    url = url,
    age = age,
    qua = qua,
    costo = gol


)

fun UserData.toItem(): Item = Item(
    id = id,
    userID = userID,
    name = name,
    profession = profession,
    url = url,
    age = age,
    qua = qua,
    costo = gol



)




/**
 * Extension function to convert [Task] to [TaskUiState]
 */
fun Item.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetails = this.toUserDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Task] to [TaskDetails]
 */
fun Item.toUserDetails(): UserDetails = UserDetails(
    id = id,
    userID = userID,
    name = name,
    profession = profession,
    url = url,
    age = age,
    qua = qua,
    gol = costo

)

data class UserImage(

    var userID: String = "",
    var url:String = ""
)