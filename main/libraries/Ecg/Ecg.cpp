/*
  Ecg.cpp - Library for ecg sensor.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#include "Ecg.h"
#include "Arduino.h"

Ecg::Ecg()
{
	readPin = A1;
}

void Ecg::setup()
{
	pinMode(readPin, INPUT);
}

double Ecg::readValue()
{
	return (double) analogRead(readPin);
}