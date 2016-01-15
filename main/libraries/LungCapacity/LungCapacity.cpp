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

float LungCapacity::measure()
{

	Wire.requestFrom(_reqCode, 2);	//Request 2 bytes from slave device 0x28 (our Honeywell)
	float presValue= 0;
  	while (Wire.available()) {	//Slave may send less than requested
	    byte one = Wire.read();
	    byte two = Wire.read();
	    //The formula is (80/(30-0))*(Papplied-0)+10%
	    int presCount = (one << 8) + two;
	    float presPercent = mapFloat(presCount, 0, 16383, 0, 100);
	    presValue = ((presPercent-10)/2.666f)*6894.75729f;	//Converted to pascals
	    Serial.print(presCount);
	    Serial.print(", ");
	    Serial.print(presPercent-10);
	    Serial.print("%, Value: ");
	    Serial.print(presValue);
	    Serial.println("pa");
  	}

  	return presValue;

}

//Custom version of the map function that supports floats
float LungCapacity::mapFloat(float x, float in_min, float in_max, float out_min, float out_max) {
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}
