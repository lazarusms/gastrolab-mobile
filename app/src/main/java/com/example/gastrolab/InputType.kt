package com.example.gastrolab

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
    val initialValue: String = ""
) {
    class Date() : InputType(
        label = "Data",
        icon = Icons.Default.DateRange,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None,
    )
    object Time : InputType(
        label = "Horario",
        icon = Icons.Default.CheckCircle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        visualTransformation = TimeVisualTransformation()
    )
    object CustomerName : InputType(
        label = "Seu nome",
        icon = Icons.Default.AccountCircle,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )
    object NumberOfPeople : InputType(
        label = "Total de pessoas",
        icon = Icons.Default.Face,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )
    object ContactInfo : InputType(
        label = "Email para contato",
        icon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )
    object SpecialRequests : InputType(
        label = "Pedidos especiais",
        icon = Icons.Default.Create,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        visualTransformation = VisualTransformation.None
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TextInput(inputType: InputType, value: String, onValueChange: (String) -> Unit, focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .onFocusEvent {
                if (it.isFocused) {
                    when (inputType) {
                        is InputType.Date -> {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                        is InputType.Time -> {
                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    selectedTime = LocalTime.of(hourOfDay, minute)
                                    val formattedTime = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                    onValueChange(formattedTime)
                                },
                                selectedTime.hour,
                                selectedTime.minute,
                                true
                            ).show()
                            focusManager.clearFocus(force = true)
                        }
                        else -> {}
                    }
                }
            },
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions,
        readOnly = inputType is InputType.Date || inputType is InputType.Time
    )

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = millis.toBrazilianDateFormat()
                            onValueChange(selectedDate)
                        }
                        showDatePickerDialog = false
                    }) {
                    Text(text = "Escolher data")
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
}


fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale("pt-br")).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}

fun TimeVisualTransformation() = VisualTransformation { text ->
    if (text.length >= 5) {
        val trimmed = text.substring(0..4)
        val transformed = trimmed.replaceRange(2, 3, ":")
        TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
    } else {
        TransformedText(text, OffsetMapping.Identity)
    }
}
