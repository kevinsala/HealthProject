/*          
    PEC - Projecte d'Enginyeria de Computadors
   kevin.sala & otorrillas  

*/

#include "Spo2.h"
#include "RgbLed.h"

/* RGB LED INTERFACE */
#define RgbClockPin 5
#define RgbEnablePin 6
#define RgbLatchPin 7
#define RgbDataPin 8



/* SPO2 INTERFACE */
#define Spo2SensorPin A2
#define Spo2RedLedPin 11
#define Spo2IRLedPin 10





void setup() {
  Serial.begin(9600);
}

void loop() {
  RgbLed rgb_led(RgbDataPin,RgbLatchPin,RgbEnablePin,RgbClockPin);
  Spo2 spo2(Spo2SensorPin, Spo2RedLedPin, Spo2IRLedPin);
  
  String input = "";
  while(Serial.available() > 0) {
    input += (char) Serial.read();
    delay(5);
  }

  if(input == "spo2") {
    Serial.println("SPO2");
    int bloodoxy = spo2.measure();
  }
      
  if(input == "rgb") {
    Serial.println("RGB LED on");
    rgb_led.sendColor(255, 0, 125);
  }

  
  delay(500);
  rgb_led.off();
}
