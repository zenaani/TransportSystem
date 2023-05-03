void setup() {
  Serial.begin(9600);   // start serial communication at 9600bps
  pinMode(A0, INPUT);
}

void loop() {
  int x = analogRead(A0);
  Serial.println(x);  // send data to NodeMCU                      // wait for a second
  delay(10000);

}
