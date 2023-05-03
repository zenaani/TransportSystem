#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

const char* ssid = "Zen";
const char* password = "Password";

// Replace with your Firebase project's URL and secret key
#define FIREBASE_HOST "https://transport-system-ff1e1-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "5eJBjzCv9jb1oLEspwNxUfGGzPCvYpCk3I7FamiI"

FirebaseData data;

void setup() {
  Serial.begin(9600);  // start serial communication at 9600bps
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
  if (Serial.available()) {   // check if there is data available
    //Serial.write(Serial.read());   // read and echo back the data
    //int message = Serial.parseInt();
    float message = Serial.parseFloat();
    Serial.print("lattitude: ");
    Serial.println(message);
    float pasenger = Serial.parseFloat();
    Serial.print("Passenger: ");
    Serial.println(pasenger);

    Firebase.setInt(data, "latitude", message);  
    
    
  }  

delay(10000);
  
}
