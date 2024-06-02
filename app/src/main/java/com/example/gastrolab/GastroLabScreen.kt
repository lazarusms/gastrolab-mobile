package com.example.gastrolab

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GastroLabScreen(navController: NavController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // Requesters de foco independentes para cada campo
    val dateFocusRequester = FocusRequester()
    val timeFocusRequester = FocusRequester()
    val nameFocusRequester = FocusRequester()
    val peopleFocusRequester = FocusRequester()
    val contactFocusRequester = FocusRequester()

    // Estados dos valores dos campos
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var numberOfPeople by remember { mutableStateOf("") }
    var contactInfo by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            CustomApp(navController)
        }

    )
    {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Atenção !") },
                text = { Text(text = dialogMessage) },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(colorResource(id = R.color.azul))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 16.dp)
                    )
                    Text(
                        text = "GastroLab",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .offset(y = (30).dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFAD2D4E2)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 24.dp, horizontal = 32.dp)
                        ) {
                            Text(
                                text = "Faça sua reserva",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            TextInput(
                                InputType.Date(),
                                value = date,
                                onValueChange = { date = it },
                                focusRequester = dateFocusRequester,
                                keyboardActions = KeyboardActions(onNext = { timeFocusRequester.requestFocus() })
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            TextInput(
                                InputType.Time,
                                value = time,
                                onValueChange = { time = it },
                                focusRequester = timeFocusRequester,
                                keyboardActions = KeyboardActions(onNext = { nameFocusRequester.requestFocus() })
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            TextInput(
                                InputType.CustomerName,
                                value = customerName,
                                onValueChange = { customerName = it },
                                focusRequester = nameFocusRequester,
                                keyboardActions = KeyboardActions(onNext = { peopleFocusRequester.requestFocus() })
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            TextInput(
                                InputType.NumberOfPeople,
                                value = numberOfPeople,
                                onValueChange = { numberOfPeople = it },
                                focusRequester = peopleFocusRequester,
                                keyboardActions = KeyboardActions(onNext = { contactFocusRequester.requestFocus() })
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            TextInput(
                                InputType.ContactInfo,
                                value = contactInfo,
                                onValueChange = { contactInfo = it },
                                focusRequester = contactFocusRequester,
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus(force = true)
                                })
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    Log.d("API Response4", "Response: $numberOfPeople")
                                    Log.d("API Response1", "Response: $customerName")
                                    Log.d("API Response23", "Response: $date")
                                    Log.d("API Response3", "Response: $time")
                                    Log.d("API Response3", "Response: $contactInfo")
                                    val reservation = Reservation(
                                        customerName = customerName,
                                        numberOfPeople = numberOfPeople.toIntOrNull() ?: 0,
                                        date = date,
                                        time = time,
                                        contactInfo = contactInfo,
                                        status = "PENDENTE"
                                    )
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            val call = ApiServiceProvider.apiService.registerReservation(reservation)
                                            val response = call.execute()
                                            if (response.isSuccessful) {
                                                val responseBody = response.body()?.string()
                                                Log.d("API Response", "Response: $responseBody")
                                                withContext(Dispatchers.Main) {
                                                    focusManager.clearFocus(force = true)
                                                    date = ""
                                                    time = ""
                                                    customerName = ""
                                                    numberOfPeople = ""
                                                    contactInfo = ""
                                                    showDialog = true
                                                    dialogMessage = "Reserva criada com sucesso! Verifique o site!"
                                                }
                                            } else {
                                                withContext(Dispatchers.Main) {
                                                    dialogMessage = "Falha ao realizar a reserva. Por favor, tente novamente."
                                                    showDialog = true
                                                }
                                            }
                                        } catch (e: Exception) {
                                            Log.d("API Response", "Response: ${e.printStackTrace()}")
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors()
                            ) {
                                Text(
                                    text = "Agendar",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



