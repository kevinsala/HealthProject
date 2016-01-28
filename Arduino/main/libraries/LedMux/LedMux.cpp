/*
 LedMux.cpp - Library for multiplexor
  control for LEDS.
  Created by Kevin Sala & Oriol Torrillas.
 */

#include "LedMux.h"
#include "Arduino.h"

LedMux::LedMux(int muxA, int muxB, int muxC)
{
  _muxAPin = muxA;
  _muxBPin = muxB;
  _muxCPin = muxC;

}

void LedMux::setup() {
  pinMode(_muxAPin,OUTPUT);
  pinMode(_muxBPin,OUTPUT);
  pinMode(_muxCPin,OUTPUT);
}

void LedMux::power_on(int lednum)
{
  switch(lednum) {
    case 2:
      // Y5
      digitalWrite(_muxAPin, HIGH);
      digitalWrite(_muxBPin, LOW);
      digitalWrite(_muxCPin, HIGH);
      break;
    case 3:
      // Y4
      digitalWrite(_muxAPin, LOW);
      digitalWrite(_muxBPin, LOW);
      digitalWrite(_muxCPin, HIGH);
      break;
    case 4:
      // Y3
      digitalWrite(_muxAPin, LOW);
      digitalWrite(_muxBPin, HIGH);
      digitalWrite(_muxCPin, LOW);
      break;
    case 5:
      // Y2
      digitalWrite(_muxAPin, HIGH);
      digitalWrite(_muxBPin, HIGH);
      digitalWrite(_muxCPin, LOW);
      break;
    case 6:
      // Y1
      digitalWrite(_muxAPin, HIGH);
      digitalWrite(_muxBPin, LOW);
      digitalWrite(_muxCPin, LOW);
      break;
    case 7:
      // Y0
      digitalWrite(_muxAPin, LOW);
      digitalWrite(_muxBPin, LOW);
      digitalWrite(_muxCPin, LOW);
      break;
    default:
      digitalWrite(_muxAPin, LOW);
      digitalWrite(_muxBPin, HIGH);
      digitalWrite(_muxCPin, HIGH);
  }

}

void LedMux::power_off()
{
  digitalWrite(_muxAPin, LOW);
  digitalWrite(_muxBPin, HIGH);
  digitalWrite(_muxCPin, HIGH);
}