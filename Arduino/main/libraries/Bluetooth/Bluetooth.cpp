/*
  Bluetooth.cpp - Library for Bluetooth device.
  Created by Kevin Sala & Oriol Torrillas.
  
*/

#include "Bluetooth.h"
#include "Arduino.h"
#include <string.h>
#include <assert.h>


Bluetooth::Bluetooth() : ss(RX_PIN, TX_PIN) {}

void Bluetooth::setup() {
	ss.begin(9600);
}

int Bluetooth::getAction()
{
	if (ss.available() >= 4 && ss.read() == 'M') {
		int functionality = ss.read() - '0';
		int msgType = ss.read() - '0';
		assert(ss.read() == 'X');

		Serial.print("MSG: Func: ");
		Serial.print(functionality);
		Serial.print(", Type: ");
		Serial.println(msgType);

		if (msgType == END_MSG) return STOP_ACTION;
		else return functionality;
	}

	return CONTINUE_ACTION;
}

void Bluetooth::sendData(int functionality, double data, bool finished)
{
	/* The message starts with an 'M' */
	String msg = String('M');

	/* Converts the data to string */
	String strData = String(data, 3);

	/* Adds the size of the message (2 digits, max size: 99) */
	int size = strData.length() + 4;
	if (size < 10) msg.concat("0");
	msg.concat(size);

	/* Adds the functionality */
	msg.concat(functionality);

	/* Adds the type of the message */
	if (finished) msg.concat(DATA_END_MSG);
	else msg.concat(DATA_MSG);

	/* Adds the data field */
	msg.concat(strData);

	/* Adds a separator */
	msg.concat('!');

	/* Adds the final mark of the message */
	msg.concat('X');

	/* Sends the message */
	char msgCharArray[msg.length() + 1];
	msg.toCharArray(msgCharArray, msg.length() + 1);
	Serial.println(msgCharArray);

	if (ss.write(msgCharArray) != msg.length())
		Serial.println("Message sent not correctly");
}

void Bluetooth::send2Data(int functionality, double data1, double data2, bool finished)
{
	/* The message starts with an 'M' */
	String msg = String('M');

	/* Converts the data to string */
	String strData1 = String(data1, 3);
	String strData2 = String(data2, 3);

	/* Adds the size of the message (2 digits, max size: 99) */
	int size = strData1.length() + strData2.length() + 4;
	if (size < 10) msg.concat("0");
	msg.concat(size);

	/* Adds the functionality */
	msg.concat(functionality);

	/* Adds the type of the message */
	if (finished) msg.concat(DATA_END_MSG);
	else msg.concat(DATA_MSG);

	/* Adds the data field */
	msg.concat(strData1);

	/* Adds a separator */
	msg.concat('!');

	/* Adds the data field */
	msg.concat(strData2);

	/* Adds the final mark of the message */
	msg.concat('X');

	/* Sends the message */
	char msgCharArray[msg.length() + 1];
	msg.toCharArray(msgCharArray, msg.length() + 1);
	Serial.println(msgCharArray);

	if (ss.write(msgCharArray) != msg.length())
		Serial.println("Message sent not correctly");
}

void Bluetooth::print(char * msg)
{
	ss.println(msg);
}

void Bluetooth::print(int msg)
{
	ss.println(msg);
}