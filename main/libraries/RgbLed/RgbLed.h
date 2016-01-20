/*
 RgbLed.cpp - Library for managing ShiftBrite RGB Led.
  Created by Kevin Sala & Oriol Torrillas.
 */

#ifndef RgbLed_h
#define RgbLed_h
#include "Arduino.h"

class RgbLed
{
  private:
	int _DataPin; 
	int _LatchPin;
	int _EnablePin;
	int _ClockPin;
    void latch();
 public:
    RgbLed(int dp, int lp, int ep, int cp);
    void setup();
	void sendColor(long r, long g, long b);
	void power_off();
};

#endif
