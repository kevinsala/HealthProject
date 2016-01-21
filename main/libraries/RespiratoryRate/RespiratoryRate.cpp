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

}

void RespiratoryRate::setup(TempSensor tempSensor) {
	_tempSensor = tempSensor;
}
/*
int RespiratoryRate::measure() {
	int res, respCount = 0;
	delay(5000);
	float tmpLst = singleMeasure();
	unsigned long elapsed = 0, current, start = millis();
	current = start;
	while(respCount < 5 and elapsed < 30000) {
		float tmpCurr = singleMeasure();
		if(tmpCurr > tmpLst) 
			++respCount;
		current=millis();
		elapsed = current-start;
		tmpLst = tmpCurr;
	}
	return int((respCount/elapsed)*60);
}*/

float RespiratoryRate::measure() {
	int respCount = 0;
	delay(5000);
	float lastRead = singleMeasure();
	bool goingUp = false;
	unsigned long elapsed = 0, current, start = millis();
	current = start;
	while(respCount < 10 and elapsed < 30000) {
		float tmpCurr = singleMeasure();
		if (tmpCurr > lastRead && !goingUp) {
			++respCount;
			goingUp = true;
			Serial.println("Respiracio!");
		}
		else if (tmpCurr < lastRead && goingUp) {
			goingUp = false;
			Serial.println("Baixant!");
		}

		lastRead = tmpCurr;

		current=millis();
		elapsed = current-start;
	}

	float totalTime = elapsed / (float) (1000.0 * 60.0);

	float res = ((float) respCount / totalTime);
	return res;
}

float RespiratoryRate::singleMeasure() {
  _tempSensor.shutdown_wake(0);
  float c = _tempSensor.readTempC();
  Serial.print("Temp: "); Serial.println(c);
  delay(250);
  _tempSensor.shutdown_wake(1);
  return c;
}