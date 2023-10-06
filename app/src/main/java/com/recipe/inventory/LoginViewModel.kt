package com.recipe.inventory

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipe.inventory.data.ItemsRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithGoogleCredential(credential: AuthCredential, home:() -> Unit)
            = viewModelScope.launch{
        try{
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Log.d("MascotaFeliz", "Loqueado con Google Exitoso!!")
                        home()
                    }
                }
                .addOnFailureListener {
                    Log.d("MascotaFeliz", "Error ${it}")
                }
        }
        catch (ex:Exception){
            Log.d("MascotaFeliz", "Excepcion al loquearr con Google: " + "${ex.localizedMessage}")
        }

    }

    var Guess by mutableStateOf("")
        private set

    var userGuess by mutableStateOf("")
        private set

    val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun signInWithEmailAndPassword(email:String, password:String, home: ()-> Unit)
            = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){

                        Log.d("MascotaFeliz", "signInWithEmailAndPassword loquyeado!!")
                        home()
                    }else{
                        Log.d("MascotaFeliz", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }

                }

        }
        catch (ex:Exception){
            Log.d("MascotaFeliz", "signInWithEmailAndPassword: ${ex.message}")

        }

    }

    fun createUserWithEmailAndPassword(
        email:String,
        password:String,
        home: () -> Unit
    ){
        if (_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        home()
                    }else{
                        Log.d("MascotaFeliz", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false

                }
        }



    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance().collection("traveller")
            .add(user)
            .addOnSuccessListener{
                Log.d("MascotaFeliz", "Creado ${it.id}")
            }
            .addOnFailureListener{
                Log.d("MascotaFeliz", "Error ${it}")
            }
    }

    fun Diotey(UseGuess: String) {
        _uiState.update { currentState ->
            currentState.copy(userID =  UseGuess
            )
        }

    }

    fun autin(): String {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userGuess = userId
        }
        return userGuess
    }

    fun austin(): Boolean {
        var hulk :Boolean = false
        val userId = auth.currentUser?.uid
        if (userId != null) {
            hulk = true
        }

        return hulk
    }


    fun hold(): String? {

        val userId = auth.currentUser?.uid


        return userId
    }


}