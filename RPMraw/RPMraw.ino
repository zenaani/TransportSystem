#include <SoftwareSerial.h> // Include the software serial library

//VCC - 5V
//GND - GND
//TX - 10
//RX - 11
//9 - EN

SoftwareSerial HC05(10,11); // RX, TX pins of HC05

void setup() {
  Serial.begin(9600); // Initialize serial communication
  HC05.begin(9600); // Initialize HC05 serial communication
  delay(1000); // Wait for the HC05 module to start
  Serial.print("Started");
}

void loop() {
  // Send "01 0C" command to retrieve RPM data
  HC05.write("01 0C\r");
  
  // Wait for the response from ELM327 module
  delay(100);
  
  // Read the response from ELM327 module
  while (HC05.available()) {
    char c = HC05.read();
    Serial.write(c);
  }
  
  // Wait for a brief moment before sending the next command
  delay(500);
}
