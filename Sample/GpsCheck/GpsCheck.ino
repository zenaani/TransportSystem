#include <SoftwareSerial.h>
#include <TinyGPS++.h>

// Define software serial pins
SoftwareSerial gpsSerial(D4, D5);  // GPS module connected to D4 (TX) and D5 (RX)

// Define GPS object
TinyGPSPlus gps;

void setup() {
  Serial.begin(9600);
  gpsSerial.begin(9600);
}

void loop() {
  // Read GPS data from the software serial port
  while (gpsSerial.available()) {
    //Serial.write(gpsSerial.read());
    if (gps.encode(gpsSerial.read())) {
      // Print latitude and longitude in decimal degrees
      Serial.print("Latitude: ");
      Serial.println(gps.location.lat(), 6);
      Serial.print("Longitude: ");
      Serial.println(gps.location.lng(), 6);

      if (gps.date.isValid())
  {
    Serial.print(gps.date.month());
    Serial.print(F("/"));
    Serial.print(gps.date.day());
    Serial.print(F("/"));
    Serial.print(gps.date.year());
  }
  else
  {
    Serial.print(F("INVALID"));
  }
    }
  }
}
