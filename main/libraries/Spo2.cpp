/*
  Spo2.cpp - Library for SPO2 sensor.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#include "Spo2.h"
#include "Arduino.h"


Spo2::Spo2(int fotoSensor, int redLed, int IRLed)
{

  _SensorPin = fotoSensor;
  _RedLedPin = redLed;
  _IRLedPin = IRLed;
  
  pinMode(_RedLedPin,OUTPUT);
  pinMode(_IRLedPin,OUTPUT);
  digitalWrite(_RedLedPin, LOW);
  digitalWrite(_IRLedPin, LOW);
  
}

int Spo2::measure()
{
  int RedValue, IRValue;
  digitalWrite(_IRLedPin, LOW);
  digitalWrite(_RedLedPin, HIGH);
  delay(10);
  RedValue = analogRead(_SensorPin);
  
  

  digitalWrite(_IRLedPin, HIGH);
  digitalWrite(_RedLedPin, LOW);
  delay(10);
  IRValue = analogRead(_SensorPin);
  

  if(debugMode) {
    Serial.print("RedLed: ");
    Serial.print(RedValue);
    Serial.print(" || IRLed: ");
    Serial.println(IRValue);
  }
  
  return 1;
}
