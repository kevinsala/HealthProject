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
  _tempSensor = TempSensor();

}

void RespiratoryRate::setup() {
  _tempSensor.setup();
}

int RespiratoryRate::measure() {
	int res, respCount = 0;
	float tmpAmbient = singleMeasure();
	unsigned long elapsed = 0, current, start = millis();
	current = start;
	while(respCount < 5 and elapsed > 30000) {
		float tmpCurr = singleMeasure();
		if(tmpCurr - tmpAmbient > RespIncrease) 
			++respCount;
		current=millis();
		elapsed = current-start;
	}
	return int((respCount/elapsed)*60);
}

float RespiratoryRate::singleMeasure() {
  _tempSensor.shutdown_wake(0);
  float c = _tempSensor.readTempC();
  Serial.print("Temp: "); Serial.print(c);
  _tempSensor.shutdown_wake(1);
  return c;
}