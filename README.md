# Projecte d'Enginyeria de Computadors

:pill: Health project build with Arduino. 

## Features
1. Anrdoid custom app.
2. Communication with Android phone via Bluetooh.
3. Electrocardiogram recording :chart_with_upwards_trend:
4. Body temperature measurement.
5. Blood pressure measurement.
6. SPO2 measurement.
7. Lung capacity measurement.
8. RGB LED.

### To Do List

- [x] Serial communication integration
- [ ] SPO2
  - [x] Measurement of sensor analog output
  - [ ] Formula transformation to *spo2* percentage
- [x] RGB Led Color picker
- [x] LED control (**MUX**) for determining which feature is being used
- [x] Bluetooh communication with Android
- [x] Body temperature sensor
- [ ] Respiratory rate
- [ ] Lung capacity.
  - [x] Read of the value
  - [ ] Calculate the overall capacity
- [ ] ECG
  - [x] Formula to compute the BPM
  - [ ] Render the electrocardiography
- [ ] Blood pressure
