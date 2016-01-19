/*
  RespiratoryRate.cpp - Library for calculating
  respiratory rate from temperature sensor.
  Created by Kevin Sala & Oriol Torrillas.
 */

#include "RespiratoryRate.h"
#include "TempSensor.h"
#include "Arduino.h"

RespiratoryRate::RespiratoryRate()
{
  _tempSensor = TempSensor(0x29);

}

void RespiratoryRate::setup() {
  _tempSensor.setup();
}

void RespiratoryRate::measure() {

}

void RespiratoryRate::singleMeasure() {
  _tempSensor.shutdown_wake(0);
  float c = _tempSensor.readTempC();
  Serial.print("Temp: "); Serial.print(c);
  _tempSensor.shutdown_wake(1);
}