/*          
    PEC - Projecte d'Enginyeria de Computadors
   kevin.sala & otorrillas  

*/

#include "Spo2.h"
#include "RgbLed.h"
#include "LedMux.h"
#include "TempSensor.h"


/* RGB LED INTERFACE */
#define RgbClockPin 5
#define RgbEnablePin 6
#define RgbLatchPin 7
#define RgbDataPin 8



/* SPO2 INTERFACE */
#define Spo2SensorPin A2
#define Spo2RedLedPin 11
#define Spo2IRLedPin 10

/* LEDMUX */
#define LedMuxA 2
#define LedMuxB 3
#define LedMuxC 4


RgbLed rgb_led(RgbDataPin,RgbLatchPin,RgbEnablePin,RgbClockPin);
Spo2 spo2(Spo2SensorPin, Spo2RedLedPin, Spo2IRLedPin);
LedMux ledmux(LedMuxA, LedMuxB, LedMuxC);
TempSensor tempsensor = TempSensor();

void setup() {
  Serial.begin(9600);
  if(!tempsensor.begin(0x19)) {
    Serial.println("Couldn't find MCP9808!");
  }
}

void loop() {
  
  
  String input = "";
  while(Serial.available() > 0) {
    input += (char) Serial.read();
    delay(5);
  }

  if(input == "spo2") {
    Serial.println("SPO2");
    int bloodoxy = spo2.measure();
    Serial.println(bloodoxy);
  }
      
  else if(input == "rgb") {
    Serial.println("RGB LED on");
    rgb_led.sendColor(255, 0, 125);
    delay(5000);
    rgb_led.sendColor(0, 0, 0);
    Serial.println("RGB LED off");
  }
  else if(input == "mux") {
    Serial.println("LedMux");
    ledmux.power_on(7);
    delay(100);
    ledmux.power_on(6);
    delay(100);
    ledmux.power_on(5);
    delay(100);
    ledmux.power_on(4);
    delay(100);
    ledmux.power_on(3);
    delay(100);
    ledmux.power_on(2);
    delay(100);
    ledmux.power_on(0);
    delay(100);
  }
  else if(input == "temp") {
    tempsensor.shutdown_wake(0);
    float c = tempsensor.readTempC();
    Serial.print("Temp: "); Serial.print(c);
    tempsensor.shutdown_wake(1);
  }
  
  
}
