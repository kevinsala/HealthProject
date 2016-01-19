/*
  RespiratoryRate.cpp - Library for calculating
  respiratory rate from temperature sensor.
  Created by Kevin Sala & Oriol Torrillas.
 */

#ifndef RespiratoryRate_h
#define RespiratoryRate_h
#include "Arduino.h"
#include "TempSensor.h"
#define RespIncrease 0.5

class RespiratoryRate
{
  private:
	TempSensor _tempSensor;
    float singleMeasure();

 public:
    RespiratoryRate();
    void setup();
	int measure();
};

#endif
