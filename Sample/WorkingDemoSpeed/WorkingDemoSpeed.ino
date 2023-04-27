#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <SoftwareSerial.h>
#include <TinyGPS++.h>

SoftwareSerial gpsSerial(D4, D5);  // GPS module connected to D4 (TX) and D5 (RX)
TinyGPSPlus gps;

// Replace with your network credentials
const char* ssid = "Zen";
const char* password = "Password";

// Replace with your Firebase project's URL and secret key
#define FIREBASE_HOST "https://transport-system-ff1e1-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "5eJBjzCv9jb1oLEspwNxUfGGzPCvYpCk3I7FamiI"

FirebaseData data;

void setup() {
  Serial.begin(9600);
  delay(100);

  // Connect to Wi-Fi network
  WiFi.begin(ssid, password);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());

  // Initialize Firebase connection
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {

  float x = 10.0;
  float y = 20.0;

  while (gpsSerial.available()) {
    if (gps.encode(gpsSerial.read())) {
      // Print latitude and longitude in decimal degrees
      Serial.print("Latitude: ");
      Serial.println(gps.location.lat(), 6);
      x = gps.location.lat();
      Serial.print("Longitude: ");
      Serial.println(gps.location.lng(), 6);
      y = gps.location.lng();
    }}
  
   // create a FirebaseData object
  Firebase.setString(data, "latitude", x);
  Serial.println("Lat Passed");
  

  Firebase.setString(data, "longitude", y);
  Serial.println("Long Passed");

  
}
