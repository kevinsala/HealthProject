/*
 LedMux.cpp - Library for managing ShiftBrite RGB Led.
  Created by Kevin Sala & Oriol Torrillas.
 */

#ifndef LedMux_h
#define LedMux_h
#include "Arduino.h"

class LedMux
{
  private:
	int _muxAPin; 
	int _muxBPin;
	int _muxCPin;
    
 public:
    LedMux(int muxA, int muxB, int muxC);
	void power_on(int lednum);
	void power_off();
};

#endif
