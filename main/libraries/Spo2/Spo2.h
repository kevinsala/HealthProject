/*
  Spo2.h - Library for SPO2 sensor.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#define debugMode 1

#ifndef Spo2_h
#define Spo2_h

#include "Arduino.h"

class Spo2
{
  private:
	int _SensorPin; 
	int _RedLedPin;
	int _IRLedPin;
	
    
 public:
    Spo2(int fotoSensor, int redLed, int IRLed);
    void setup();
	int measure();
};

#endif
