/*
  Ecg.h - Library for ecg sesnor.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#ifndef Ecg_h
#define Ecg_h

#include "Arduino.h"

class Ecg
{
	private:
        int readPin;

 	public:
    	Ecg();
        void setup();
        double readValue();
};

#endif
