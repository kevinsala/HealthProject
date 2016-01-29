:pill:  Arduino sensor interface
====


## Interface Features
1. Bluetooh communication protocol.
3. Electrocardiogram generation :chart_with_upwards_trend:
4. Body temperature measurement.
5. Blood pressure measurement.
6. SPO2 measurement.
7. Respiratory rate measurement.
8. Lung capacity measurement.
9. ShiftBrite RGB LED control.
10. Function LEDs control via *multiplexer*. 

### Progress

- [x] Bluetooh communication protocol
- [x] ECG
  - [x] Formula to compute the BPM
  - [x] Render the electrocardiography
- [x] Body temperature sensor
- [ ] Blood pressure
- [x] SPO2
  - [x] Measurement of sensor analog output
  - [x] Formula transformation to *spo2* percentage
- [x] Respiratory rate - with a temperature sensor
- [x] Lung capacity
  - [x] Read of an instant value
  - [x] Calculate the overall capacity
- [x] ShiftBrite RGB Led Color interface
- [x] **MUX** for function LED control


