/*
 LungCapacity.cpp - Library for measuring lung capacity
  from a pressure sensor.
  Created by Kevin Sala & Oriol Torrillas.
 */

#include "LungCapacity.h"
#include "Arduino.h"
#include <Wire.h>

LungCapacity::LungCapacity(int reqCode)
{
	_reqCode = reqCode;
}

void LungCapacity::setup() {
	refPressure = singleMeasure();
}

double LungCapacity::measure()
{
	
	double sMeasure = singleMeasure();

	// Wait user to start blowing
	while(sMeasure <= refPressure) 
		sMeasure = singleMeasure();

	// user has started the test
	double resCapacity = 0;
	// keep measuring until user has finished blowing
	while(sMeasure > refPressure) {
		// transform measure pressure to volume (L). Capacity = v * A * t * 1000
		double sCapacity = sqrt(sMeasure/1.225) * 1096.7 * 50.2655 * 0.5 * 1000;
		resCapacity += sCapacity;
		//measure another sample
		delay(1);
		sMeasure = singleMeasure();
	}

	return resCapacity;

}

double LungCapacity::singleMeasure() {
	Wire.requestFrom(_reqCode, 2);	//Request 2 bytes from slave device 0x28 (our Honeywell)
	double presValue = 0;
  	while (Wire.available()) {	//Slave may send less than requested
	    byte one = Wire.read();
	    byte two = Wire.read();
	    //The formula is (80/(30-0))*(Papplied-0)+10%
	    int presCount = (one << 8) + two;
	    double presPercent = mapDouble(presCount, 0, 16383, 0, 100);
	    presValue = ((presPercent-10)/2.666)*6894.75729;	//Converted to pascals
	    Serial.print(presCount);
	    Serial.print(", ");
	    Serial.print(presPercent-10);
	    Serial.print("%, Value: ");
	    Serial.print(presValue);
	    Serial.println("pa");
  	}

  	return presValue;
}

//Custom version of the map function that supports doubles
double LungCapacity::mapDouble(double x, double in_min, double in_max, double out_min, double out_max) {
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}
