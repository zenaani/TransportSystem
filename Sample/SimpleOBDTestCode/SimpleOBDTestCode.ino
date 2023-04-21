//1D:A5:68988D
#include <SoftwareSerial.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

const int I2C_ADDR = 0x27;
const int ROWS = 20;
const int COLS = 4;

// init vars
const int LED = 3; // A2
const int EN = 2;  // A1
const int RW = 1;  // A0
const int RS = 0;  // ??
const int D4 = 4;  // P0
const int D5 = 5;  // P1
const int D6 = 6;  // P2
const int D7 = 7;  // P3
int n = 1;

LiquidCrystal_I2C lcd(I2C_ADDR,EN,RW,RS,D4,D5,D6,D7,LED,POSITIVE);

SoftwareSerial BTserial(8,9); // RX | TX




byte inData;
char inChar;
String BuildINString="";
String DisplayString="";
String WorkingString="";
String WorkingString21="";
String WorkingString22="";

byte inData2;
char inChar2;
String BuildINString2="";
String DisplayString2="";

String coolant="";
String CatB1S1="";

long DisplayValue;
long DisplayValue2;
long A;
long B;
long AA2;
long B2;
char c;
int stat;

boolean NL = true;


String pidno="";
String pidno2="";


 
void setup() 
{   
  
  
  lcd.begin(ROWS,COLS);
  lcd.backlight();
  Serial.begin(9600);
  lcd.setCursor(0, 0);
  lcd.print("2009 BMW 320D");
  lcd.setCursor(0, 1);
  lcd.print("Welcome, Brani ");
  delay(1000);
  lcd.clear();
 
  // My HC-05 is set at 38400
  BTserial.begin(38400);  //comm mode


 
void loop()
{  

//Send coolant PID to ELM327
BTserial.println("0105");

BuildINString=""; 

//Listen and collect ECU response string as a sum of characters
while (BTserial.available()>0)
 {      inData=0;
        inChar=0;
        inData = BTserial.read();
        inChar = char(inData);
        BuildINString = BuildINString + inChar;
    }
         delay(200);
         //Send ECU response to PC serial monitor
          Serial.print("<ECU response> :");
          Serial.print(BuildINString);
          Serial.println();
         
   
 WorkingString = BuildINString.substring(4,6);  //The ECU sends back the requested PID code and two hex characters,Make sure to  collect the two hexadecimal characters after the PID code 0105. For me this starts at position 4 in the string and ends at position 5  (therefore use substring 4,6)
 A = strtol(WorkingString.c_str(),NULL,16);  //convert hex to decimnal
 DisplayValue = A-40; //Calculate temp according to formula
 coolant = String(DisplayValue);  // Convert integer to string for display
                                 

//Print to LCD
       lcd.clear();
       
       lcd.setCursor(0, 0);
       lcd.print("Coolant:"); 
       lcd.setCursor(14,0);
       lcd.print(coolant); 
       lcd.print("\337C");
       lcd.print("C");


delay(200);

}
 

    
    

        
    
