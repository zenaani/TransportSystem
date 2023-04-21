#include <SoftwareSerial.h>
#include "ELMduino.h"


//GitHubPowerBroker2 ELMduino Test


SoftwareSerial mySerial(2, 3); // RX, TX
ELM327 myELM327;


uint32_t rpm = 0;


void setup()
{
  Serial.begin(115200);
  mySerial.begin(115200);
  myELM327.begin(mySerial, true, 2000);
}


void loop()
{
  float tempRPM = myELM327.rpm();

  if (myELM327.nb_rx_state == ELM_SUCCESS)
  {
    rpm = (uint32_t)tempRPM;
    Serial.print("RPM: "); Serial.println(rpm);
  }
  else if (myELM327.nb_rx_state != ELM_GETTING_MSG)
    myELM327.printError();
}
