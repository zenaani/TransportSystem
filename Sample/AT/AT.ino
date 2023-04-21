#include <SoftwareSerial.h>


//VCC - 5V
//GND - GND
//TX - 10
//RX - 11
//9 - EN
//AT Commands Work Perfect

SoftwareSerial BTSerial(10, 11);   // RX | TX

void setup()
{
  pinMode(9,OUTPUT);
  digitalWrite(9,HIGH);
  Serial.begin(38400);
  Serial.println("Enter AT commands:");
  BTSerial.begin(38400);       // HC-05 default speed in AT command more
}

void loop()
{
  if (BTSerial.available())    // read from HC-05 and send to Arduino Serial Monitor
  Serial.write(BTSerial.read());

  if (Serial.available())     // Keep reading from Arduino Serial Monitor and send to HC-05
  BTSerial.write(Serial.read());
}
