/*          
    PEC - Projecte d'Enginyeria de Computadors
   kevin.sala & otorrillas  

*/

#include "libs/Spo2.h"
#include "libs/RgbLed.h"

/* RGB LED INTERFACE */
#define RgbClockPin 5
#define RgbEnablePin 6
#define RgbLatchPin 7
#define RgbDataPin 8

RgbLed rgb_led(RgbDataPin,RgbLatchPin,RgbEnablePin,RgbClockPin);

/* SPO2 INTERFACE */
#define FotoSensorPin A2
#define FotoRedLedPin 11
#define FotoIRLedPin 10





void setup() {
  Serial.begin(9600);
  
}

void loop() {
  
  String input = "";
  while(Serial.available() > 0) {
    input += (char) Serial.read();
    delay(5);
  }

  if(input == "spo2") {
    Serial.println("SPO2");
    spo2_measure();
  }
      
  if(input == "rgb") {
    Serial.println("RGB LED on");
    int color[3] = {1023, 0, 0};
    rgb_on(color);
  }

  
  delay(500);
}
