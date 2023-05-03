package com.example.transportsystem

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen1(navController: NavController) {

    //Login Credentials
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.homescreen),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = name,
            shape = RoundedCornerShape(10.dp),
            onValueChange = { newName ->
                name = newName },
            label = {Text("Name")}
        )

        Spacer(modifier = Modifier.padding(7.dp) )

        OutlinedTextField(
            value = password,
            shape = RoundedCornerShape(10.dp),
            onValueChange = { newPassword ->
                password = newPassword },
            label = {Text("Password")},
            visualTransformation =  PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(10.dp) )

        Text(text = "Forgot Password?", fontSize = 12.sp)
        Spacer(modifier = Modifier
            .padding(7.dp)
            .clickable { } )

        //add credentials here
        Button(
            onClick = {
                if (name == "" && password == "") {
                    navController.navigate(route = Screen.First.route)
                    //Toast.makeText(, "Logged in Successfully!", Toast.LENGTH_SHORT).show()

                } else {
                    //Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(130.dp, 50.dp)) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.padding(90.dp))

    }
}

