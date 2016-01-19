/*
 LungCapacity.cpp - Library for managing ShiftBrite RGB Led.
  Created by Kevin Sala & Oriol Torrillas.
 */

#ifndef LungCapacity_h
#define LungCapacity_h
#include "Arduino.h"

class LungCapacity
{
  private:
	int _reqCode;
    float mapFloat(float x, float in_min, float in_max, float out_min, float out_max);
    float singleMeasure();

 public:
    LungCapacity(int reqCode);
	float measure();
};

#endif
