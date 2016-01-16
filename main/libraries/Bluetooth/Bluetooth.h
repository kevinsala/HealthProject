/*
  Bluetooth.h - Library for bluetooth device.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#ifndef Bluetooth_h
#define Bluetooth_h

#include "Arduino.h"
#include <string.h>
#include <SoftwareSerial.h>
#include <assert.h>

#define RX_PIN 12
#define TX_PIN 13

#define START_MSG 0
#define DATA_MSG 1
#define END_MSG 2
#define DATA_END_MSG 3

class Bluetooth
{
	private:
    	SoftwareSerial ss;

 	public:
    	Bluetooth();
    	void setup();
    	int getAction();
    	void sendData(int functionality, double data, bool finished);
    	void send2Data(int functionality, double data1, double data2, bool finished);
    	void print(char * msg);
    	void print(int msg);
};

#endif
