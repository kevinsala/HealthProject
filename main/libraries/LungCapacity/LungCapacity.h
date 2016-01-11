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
    
 public:
    LungCapacity(int reqCode);
	int measure();
};

#endif
