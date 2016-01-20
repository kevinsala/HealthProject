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
	double refPressure;
    double mapDouble(double x, double in_min, double in_max, double out_min, double out_max);
    double singleMeasure();

 public:
    LungCapacity(int reqCode);
	double measure();
};

#endif
