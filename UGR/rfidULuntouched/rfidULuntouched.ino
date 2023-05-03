#include <SoftwareSerial.h>

#define RFID_RX 2
#define RFID_TX 3

SoftwareSerial RFID(RFID_RX, RFID_TX);

int count = 0;
int x =0;
String prevTagID = "000000000000";

void setup() {
  Serial.begin(9600);
  RFID.begin(9600);
}

void loop() {
  
  if (RFID.available()) {
    String tagID = "";
    while (RFID.available()) 
      {
    String tagID = "";
    while (RFID.available()) {
      tagID += (char)RFID.read();
    }
    delay(50);
    }
    
    Serial.println("Tag ID: " + tagID);
    if (tagID != prevTagID) { // Check for duplicate tag readings
      count++;
      Serial.print("Count: ");
      Serial.println(count);
      prevTagID = tagID;
    }
    else if(tagID == prevTagID)
    {
      count--;
      Serial.print("Count: ");
      Serial.println(count);
      prevTagID = "0000000000000";
    }
    //Serial.println(count);
  }
}

