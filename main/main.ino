/*          
    PEC - Projecte d'Enginyeria de Computadors
   kevin.sala & otorrillas  

*/

/* SPO2 INTERFACE */
#define FotoSensorPin A2
#define FotoRedLedPin 11
#define FotoIRLedPin 10
int FotoSensorValue = 0;

void spo2_measure() {
  digitalWrite(FotoIRLedPin, LOW);
  digitalWrite(FotoRedLedPin, HIGH);
  delay(10);
  FotoSensorValue = analogRead(FotoSensorPin);
  Serial.print("RedLed: ");
  Serial.print(FotoSensorValue);

  digitalWrite(FotoIRLedPin, HIGH);
  digitalWrite(FotoRedLedPin, LOW);
  delay(10);
  FotoSensorValue = analogRead(FotoSensorPin);
  Serial.print(" || IRLed: ");
  Serial.println(FotoSensorValue);
}

void setup() {
  Serial.begin(9600);
  /* SPO2 interface */
  pinMode(FotoRedLedPin, OUTPUT);
  pinMode(FotoIRLedPin, OUTPUT);

  digitalWrite(FotoRedLedPin, LOW);
  digitalWrite(FotoIRLedPin, LOW);
}

void loop() {
  
  String input = "";
  while(Serial.available() > 0) {
    input += (char) Serial.read();
    delay(5);
  }

  if(input == "spo2")
      spo2_measure();
  if(input == "rgb")
    Serial.println("RGB LED!");
  
  

  
  delay(500);
}
