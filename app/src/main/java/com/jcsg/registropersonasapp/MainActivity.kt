package com.jcsg.registropersonasapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jcsg.registropersonasapp.ui.theme.RegistroPersonasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroPersonasAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Directorio()
                }
            }
        }
    }
}

@Composable
fun Directorio() {
    val userViewModel:UsersViewModel = viewModel()
    val userState = userViewModel.eventTracker.observeAsState()
    Column (modifier = Modifier.fillMaxHeight().fillMaxWidth()){
        Row {
            userState.value?.run {
                ListaUsuarios(this)
            }
        }
        Row {
            FormatoRegistro {
                    user -> userViewModel.newUser(user)
            }
        }

    }
}

@Composable
fun FormatoRegistro(createUser:  (User) -> Unit) {
    val (userName, setName) = remember { mutableStateOf("")}
    val (userLastName, setLastName) = remember { mutableStateOf("") }
    val (userGender, setGender) = remember { mutableStateOf(false) }
    Column {
        Row {
            OutlinedTextField(
                value = userName,
                onValueChange = {setName},
                modifier = Modifier.padding(5.dp).border(1.dp, Blue).weight(3f)
            )
            OutlinedTextField(
                value = userLastName,
                onValueChange = {setLastName},
                modifier = Modifier.padding(5.dp).border(1.dp, Blue).weight(3f)
            )
            Checkbox(
                checked = userGender,
                onCheckedChange ={setGender},
                modifier = Modifier.weight(1f)
            )
        }
        Button(onClick = {
                createUser(User(userName, userLastName, userGender))
        },Modifier.fillMaxWidth(1f)) {
            Text("Registrar Usuario")
        }
    }
}

@Composable
fun ListaUsuarios(userList: List<User>) {
    LazyColumn() {
        items(userList) {
            user -> CeldaUsuario(user)
        }
    }
}

@Composable 
fun CeldaUsuario(user: User) {
    Row (Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "User Image",
            modifier = Modifier.weight(1f)
        )
        Text(user.name, modifier = Modifier.weight(1f))
        Text(user.lastName, modifier = Modifier.weight(1f))
        Text(if(user.gender) "Masculino" else "Femenino", modifier = Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegistroPersonasAppTheme {
        FormatoRegistro {User -> print(User)}
    }
}