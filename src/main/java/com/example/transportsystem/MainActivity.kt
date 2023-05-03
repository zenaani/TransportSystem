package com.example.transportsystem

import android.Manifest
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transportsystem.ui.theme.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TransportSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    //Location
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

                    // Check for location permission
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 100)
                        return@Surface
                    }

                    //Navigation
                    val navController = rememberNavController()

                    //Realtime Database
                    val database = Firebase.database
                    val myRef = database.getReference("update")
                    val lat = database.getReference("latitude")
                    val long = database.getReference("longitude")
                    val rpm = database.getReference("rpm")
                    val speed = database.getReference("speed")
                    val coolant = database.getReference("coolant")
                    val fuel = database.getReference("fuel")
                    val passengers = database.getReference("passengers")

                    myRef.setValue("Hello World")

/***
                    var latitude = 0.00
                    var longitude = 0.00
                    // Get the last known location
                    fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {

                                for (i in 1..50)
                                latitude = location.latitude
                                longitude = location.longitude
                                // Print the latitude and longitude
                                lat.setValue(latitude.toString())
                                long.setValue(longitude.toString())
                                println("Latitude: $latitude, Longitude: $longitude")
                                Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()
                            } else {
                            }
                        }
                        .addOnFailureListener {
                        }
***/




                    //Database Updates
                    val (latVal, newLatVal) = remember { mutableStateOf("") }
                    val (longVal, newLongVal) = remember { mutableStateOf("") }
                    val (rpmVal, newRpmVal) = remember { mutableStateOf(0) }
                    val (speedVal, newSpeedVal) = remember { mutableStateOf(0) }
                    val (coolantVal, newCoolantVal) = remember { mutableStateOf(0) }
                    val (fuelVal, newFuelVal) = remember { mutableStateOf(0) }
                    val (passengersVal, newPassengersVal) = remember { mutableStateOf(0) }



                    //Login Credentials
                    var name by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }


                    lat.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<String>()
                            Log.d(TAG, "Value is: " + value)
                            if (value != null) {
                                newLatVal(value)
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    long.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<String>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newLongVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    rpm.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<Int>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newRpmVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    speed.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<Int>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newSpeedVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    coolant.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<Int>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newCoolantVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    fuel.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<Int>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newFuelVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })

                    passengers.addValueEventListener(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = snapshot.getValue<Int>()
                            Log.d(TAG, "Value is: $value")
                            if (value != null) {
                                newPassengersVal(value)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(TAG, "Failed to read value.", error.toException())
                        }

                    })



                    @Composable
                    fun LoginScreen() {
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
                                    Toast.makeText(applicationContext, "Logged in Successfully!", Toast.LENGTH_SHORT).show()

                                } else {
                                    Toast.makeText(applicationContext, "Wrong username or password", Toast.LENGTH_SHORT).show()
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

                    @Composable
                    fun HomeScreen() {

                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        verticalArrangement = Arrangement.SpaceAround) {

                            Spacer(modifier = Modifier.padding(20.dp,20.dp))

                            Text(
                                text = "Transport System",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp
                            )

                            Spacer(modifier = Modifier.padding(20.dp,20.dp))

                            Text(
                                text = "Coordinates",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            Text(text = latVal)
                            Text(text = longVal)

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                Button(onClick = {
                                    navController.navigate(route = Screen.Map.route)
                                }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C1", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C2", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C3", color = OnPrimary)
                                }

                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C4", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C5", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C6", color = OnPrimary)
                                }

                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C7", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C8", color = OnPrimary)
                                }

                                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(20.dp), modifier = Modifier
                                    .size(0.dp, 130.dp)
                                    .padding(10.dp, 10.dp)
                                    .weight(1f)) {
                                    Text(text = "C9", color = OnPrimary)
                                }

                            }

                            Button(onClick = {
                                //Getting intent and PendingIntent instance
                                //Getting intent and PendingIntent instance
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                val pi = PendingIntent.getActivity(applicationContext, 0, intent, 0)

                                //Get the SmsManager instance and call the sendTextMessage method to send message

                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                val sms: SmsManager = SmsManager.getDefault()
                                sms.sendTextMessage("7034774669", null, "Vehicle encountered with fire. \nCoordinates\nLatitude : $latVal\nLongitude : $longVal ", pi, null)

                            }) {
                                Text(text = "Send SMS")
                            }

                            Button(onClick = {
                                navController.navigate(route = Screen.Detail.route)
                            }) {
                                Text(text = "Go To")

                            }

                            Row(modifier = Modifier.fillMaxWidth(), ) {

                                Box(modifier = Modifier
                                    .background(
                                        color = Primary,
                                    )
                                    .size(0.dp, 60.dp)
                                    .weight(1f)) {

                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_home_24), contentDescription = "home")
                                    Text(text = "Home", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }

                                Box(modifier = Modifier
                                    .background(
                                        color = Primary,
                                    )
                                    .size(0.dp, 60.dp)
                                    .weight(1f)) {

                                    Text(text = "Map", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }

                                Box(modifier = Modifier
                                    .background(
                                        color = Primary,
                                    )
                                    .size(0.dp, 60.dp)
                                    .weight(1f)) {

                                    Text(text = "Details", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }



                            }

                        }

                    }


                    @Composable
                    fun Map(lat: String, long: String, rpm: Int, speed: Int, coolant: Int, fuel: Int, passengers: Int) {
                        val singapore = LatLng(lat.toDouble(), long.toDouble())
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(singapore, 10f)
                        }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Background)) {

                            GoogleMap(
                                modifier = Modifier.fillMaxSize(1f),
                                cameraPositionState = cameraPositionState
                            ) {
                                Marker(
                                    state = MarkerState(position = singapore),
                                    title = "C10",
                                    snippet = "Marker in Singapore"
                                )
                            }

                            Column() {
                                Box(modifier = Modifier
                                    .padding(7.dp, 7.dp)
                                    .fillMaxWidth()
                                    .background(color = Primary, shape = RoundedCornerShape(20.dp))
                                    .size(0.dp, 60.dp)) {

                                    Text(text = "Engine Condition : OK", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround) {

                                    Box(modifier = Modifier
                                        .padding(7.dp, 10.dp)
                                        .background(
                                            color = Primary,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .size(0.dp, 110.dp)
                                        .weight(1f)) {

                                        Text(text = "Speed\n" + speed +" Km/h", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                    }

                                    Box(modifier = Modifier
                                        .padding(7.dp, 10.dp)
                                        .background(
                                            color = Primary,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .size(0.dp, 110.dp)
                                        .weight(1f)) {

                                        Text(text = "Revs\n" + rpm + "000 rpm", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                    }

                                    Box(modifier = Modifier
                                        .padding(7.dp, 10.dp)
                                        .background(
                                            color = Primary,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .size(0.dp, 110.dp)
                                        .weight(1f)) {

                                        Text(text = "Coolant\n" + coolant + " degree", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                    }

                                }
                            }



                            Column(modifier = Modifier.align(Alignment.BottomEnd)) {


                                Box(modifier = Modifier
                                    .size(40.dp, 40.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(50.dp)
                                    )) {
                                    Text("i", color = Primary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
                                }

                                Box(modifier = Modifier
                                    .padding(7.dp, 7.dp)
                                    .fillMaxWidth()
                                    .background(color = Primary, shape = RoundedCornerShape(20.dp))
                                    .size(0.dp, 60.dp)) {

                                    Text(text = "No of Passengers : " + passengers, modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }


                                Box(modifier = Modifier
                                    .padding(7.dp, 7.dp)
                                    .fillMaxWidth()
                                    .background(color = Primary, shape = RoundedCornerShape(20.dp))
                                    .size(0.dp, 60.dp)) {
                                    
                                    Box(modifier = Modifier
                                        .fillMaxWidth(0.3f)
                                        .fillMaxHeight()
                                        .background(
                                            color = FuelColor,
                                            shape = RoundedCornerShape(20.dp)
                                        )) {

                                    }

                                    Text(text = "Fuel Level : " + fuel, modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                }

                                Row(horizontalArrangement = Arrangement.SpaceAround) {
                                    Box(modifier = Modifier
                                        .padding(7.dp, 7.dp)
                                        .background(
                                            color = Primary,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .size(0.dp, 60.dp)
                                        .weight(1f)) {

                                        Text(text = "Longitude\n" + lat, modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                    }

                                    Box(modifier = Modifier
                                        .padding(7.dp, 7.dp)
                                        .background(
                                            color = Primary,
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .size(0.dp, 60.dp)
                                        .weight(1f)) {

                                        Text(text = "Latitude\n" + long, modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, color = OnPrimary)
                                    }
                                }


                            }







                            Spacer(modifier = Modifier.padding(15.dp))



                        }

                    }



                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(route = Screen.Home.route) {

                            LoginScreen()
                            //LoginScreen1(navController = navController)
                        }

                        composable(route = Screen.First.route) {
                            HomeScreen()
                        }

                        composable(route = Screen.Detail.route) {
                            DetailScreen()
                        }

                        composable(route = Screen.Map.route) {
                            Map(latVal,longVal, rpmVal, speedVal, coolantVal, fuelVal, passengersVal)
                        }
                    }





                }
            }
        }
    }

}
