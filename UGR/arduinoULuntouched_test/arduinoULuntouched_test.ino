void setup() {
  Serial.begin(9600);   // start serial communication at 9600bps
  pinMode(A0, INPUT);
}

void loop() {
  int x = random(12,22);
  Serial.println(x);  // send data to NodeMCU                      // wait for a second
  delay(10000);
 int y = random(0,10);
 Serial.println(y);
delay(10000);
}
