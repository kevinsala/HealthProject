/*          
    PEC - Projecte d'Enginyeria de Computadors
   kevin.sala & otorrillas  

*/

#include "Spo2.h"
#include "RgbLed.h"
#include "LedMux.h"
#include "TempSensor.h"
#include "LungCapacity.h"
#include "Bluetooth.h"
#include "Ecg.h"

/* RGB LED INTERFACE */
#define RgbClockPin 5
#define RgbEnablePin 6
#define RgbLatchPin 7
#define RgbDataPin 8

/* SPO2 INTERFACE */
#define Spo2SensorPin A2
#define Spo2RedLedPin 11
#define Spo2IRLedPin 10

/* LEDMUX */
#define LedMuxA 2
#define LedMuxB 3
#define LedMuxC 4

/* ACTIONS */
#define CONTINUE_ACTION -1
#define STOP_ACTION -2

/* FUNCTIONALITY IDENTIFIERS */
#define RESPIRATORY_RATE_FUNC 0
#define BLOOD_PRESSURE_FUNC 1
#define ECG_FUNC 2
#define LUNG_CAPACITY_FUNC 4
#define TEMPERATURE_FUNC 5
#define OXYGEN_SATURATION_FUNC 6
#define NO_FUNC -1

/* FUNCTIONALITY METHODS DECLARATION */
void playRespiratoryRate();
void playBloodPressure();
void playEcg();
void playLungCapacity();
void playTemperature();
void playOxygenSaturation();

/* GLOBAL VARIABLES */
RgbLed rgb_led(RgbDataPin,RgbLatchPin,RgbEnablePin,RgbClockPin);
Spo2 spo2(Spo2SensorPin, Spo2RedLedPin, Spo2IRLedPin);
LedMux ledmux(LedMuxA, LedMuxB, LedMuxC);
TempSensor tempsensor = TempSensor();
Ecg ecg = Ecg();
Bluetooth bluetooth = Bluetooth();

int currentFunc;

void setup() {
  currentFunc = NO_FUNC;
  Serial.begin(9600);
  tempsensor.setup();
  ecg.setup();
  bluetooth.setup();
  ledmux.setup();
  spo2.setup();
  rgb_led.setup();

  ledmux.power_off();
}

void loop() {

  if (Serial.available() > 0) {
    currentFunc = Serial.read() - '0';
    delay(5);
  }

  /* GETTING THE NEXT ACTION AND FUNCTIONALITY */
  int actionReceived = bluetooth.getAction();
  if (currentFunc == NO_FUNC && actionReceived >= 0) {
    currentFunc = actionReceived;
  }

  /* Functionalities */
  if (currentFunc == RESPIRATORY_RATE_FUNC) playRespiratoryRate();
  else if (currentFunc == BLOOD_PRESSURE_FUNC) playBloodPressure();
  else if (currentFunc == ECG_FUNC) playEcg();
  else if (currentFunc == LUNG_CAPACITY_FUNC) playLungCapacity();
  else if (currentFunc == TEMPERATURE_FUNC) playTemperature();
  else if (currentFunc == OXYGEN_SATURATION_FUNC) playOxygenSaturation();

  delay(100);
}

void playRespiratoryRate()
{
  ledmux.power_on(2);
  delay(1000);
  ledmux.power_off();
  currentFunc = NO_FUNC;
}

void playBloodPressure()
{
  ledmux.power_on(3);
  delay(1000);
  ledmux.power_off();
  currentFunc = NO_FUNC;
}

void playEcg()
{
  ledmux.power_on(4);
  
  int beats = 0;
  long startTime = millis(), totalTime = 0, iterations = 0;
  bool cancelled = false, finished = false;

  /* Waits 15 seconds and counts the number of heart beats */
  while (!finished) {
    double valueRead = ecg.readValue();
    if (valueRead >= 1020) ++beats;
    if (iterations % 50 == 0) {
      if (bluetooth.getAction() == STOP_ACTION) {
        cancelled = true;
        break;
      }
    }

    delay(20);
    totalTime = millis() - startTime;
    finished = totalTime >= 15000;
    bluetooth.send2Data(ECG_FUNC, totalTime,valueRead, finished);
    ++iterations;
  }

  if (!cancelled) {
    double bpm = (double) beats / ((double) totalTime / (double) 60000);
    Serial.print("ECG: "); Serial.print(bpm); Serial.println(" BPM");
  }
  else Serial.println("ECG Cancelled");

  ledmux.power_off();
  currentFunc = NO_FUNC;
}

void playLungCapacity()
{
  ledmux.power_on(5);
  delay(1000);
  ledmux.power_off();
  currentFunc = NO_FUNC;
}

void playTemperature()
{
  ledmux.power_on(6);

  tempsensor.shutdown_wake(0);
  float c = tempsensor.readTempC();
  Serial.print("Temp: "); Serial.print(c);
  tempsensor.shutdown_wake(1);
  
  ledmux.power_off();
  currentFunc = NO_FUNC;
}

void playOxygenSaturation()
{
  ledmux.power_on(7);
  
  Serial.println("SPO2");
  int bloodoxy = spo2.measure();
  Serial.println(bloodoxy);
  
  ledmux.power_off();
  currentFunc = NO_FUNC;
}

