/*
  RespiratoryRate.cpp - Library for calculating
  respiratory rate from temperature sensor.
  Created by Kevin Sala & Oriol Torrillas.
 */

#ifndef RespiratoryRate_h
#define RespiratoryRate_h
#include "Arduino.h"
#include "TempSensor.h"

class RespiratoryRate
{
  private:
	TempSensor _tempSensor;
    
 public:
    RespiratoryRate();
    void setup();
	void measure();
};

#endif
