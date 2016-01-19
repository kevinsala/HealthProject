/*
 RgbLed.cpp - Library for managing ShiftBrite RGB Led.
  Created by Kevin Sala & Oriol Torrillas.
 */

#include "RgbLed.h"
#include "Arduino.h"

RgbLed::RgbLed(int dp, int lp, int ep, int cp)
{

  _DataPin = dp;
  _LatchPin = lp;
  _EnablePin = ep;
  _ClockPin = cp;

}

void RgbLed::setup() {
  pinMode(_DataPin,OUTPUT);
  pinMode(_LatchPin,OUTPUT);
  pinMode(_EnablePin,OUTPUT);
  pinMode(_ClockPin,OUTPUT);

  digitalWrite(_LatchPin, LOW);
  digitalWrite(_EnablePin, LOW);
}

void RgbLed::sendColor(long r, long g, long b)
{
  long packet = 0;
  long c = 00;
  
  r = (r*4);
  g = (g*4);
  b = (b*4);
  if(r > 1023)
  {
    r = 1023;
  }
  if(g > 1023)
  {
    g = 1023;
  }
  if(b > 1023)
  {
    b = 1023;
  }
  packet = (packet << 2) | (c & 3); //3 =  binary 11
  //dhex(packet);
  packet = (packet << 10) | (b & 1023);
  //dhex(packet);  
  packet = (packet << 10) | (r & 1023);
  //dhex(packet);
  packet = (packet << 10) | (g & 1023);
  //dhex(packet);
  long  s1 = 0xFF000000;
  long  s2 = 0xFF0000;
  long  s3 = 0xFF00;
  long  s4 = 0xFF;

  shiftOut(_DataPin, _ClockPin, MSBFIRST, (s1 & packet) >> 24);
  shiftOut(_DataPin, _ClockPin, MSBFIRST, (s2 & packet) >> 16);
  shiftOut(_DataPin, _ClockPin, MSBFIRST, (s3 & packet) >> 8);
  shiftOut(_DataPin, _ClockPin, MSBFIRST, (s4 & packet)); 
  delayMicroseconds(5);
  latch();
}

void RgbLed::latch()
{
  digitalWrite(_LatchPin, HIGH);
  delayMicroseconds(5);
  digitalWrite(_LatchPin, LOW);
}

void RgbLed::off()
{
  sendColor(0, 0, 0);
  digitalWrite(_ClockPin, LOW);
  digitalWrite(_DataPin, LOW);
  digitalWrite(_LatchPin, LOW);
  digitalWrite(_EnablePin, LOW); 
}