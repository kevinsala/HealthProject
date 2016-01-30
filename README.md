# Health Project :pill:

This project aims to design and implement a portable HealthCare equipment. For the sensor interface, this project uses an **Arduino** Uno microcontroller. For connecting different sensors to the Arduino board, this project has also designed a **custom shield**. For controlling the interface, as well as showing the measurements results, this project uses an **Android application**.

:warning: **_Disclaimer: This Project is for educational and experimental purposes only. It should not be used for diagnostic or for medical operations of any sort._**:warning: 

## Available Measurements
* Electrocardiography and heart rate.
* Body temperature.
* Blood pressure.
* Oxygen saturation (SpO2).
* Respiratory rate.
* Lung capacity.

## Technical Features
* Android application to wirelessly control the hardware equipment.
* Bluetooth communication protocol between the hardware equipment and the application.
* ShiftBrite RGB LED control.
* Function LEDs control via _multiplexer_.
* Needs an external input voltatge (10V, -5V and GND input)

## Android Application
The next pictures shows the Android application and its friendly user interface. It has a page and instructions for each health functionality and a Bluetooth communication protocol, totally transparent to user. The application Project is located in the ['Android' folder](https://github.com/kevinsala/HealthProject/tree/master/Android)

![Main page](https://cloud.githubusercontent.com/assets/7224381/12695507/c0be4e9c-c750-11e5-9e37-5c61616f7aed.jpg)

_Menu of the Android application_

![Electrocardiography feature](https://cloud.githubusercontent.com/assets/7224381/12695511/e9be8000-c750-11e5-81bb-d897610ca936.jpg)

_Electrocardiography page of the Android application_

## Shield Design
The custom shield has been designed with [CadSoft EAGLE](http://www.cadsoftusa.com/) software. This design is completely functional and it has been tested. All related files are included in the ['Shield' folder](https://github.com/kevinsala/HealthProject/tree/master/Shield)

### Functional Block Diagram
The next figure represents the functional block diagram of the designed equipment.

![Functional block diagram](https://cloud.githubusercontent.com/assets/7224381/12680336/92172ce6-c6a9-11e5-8a45-736ef8f81a39.png)
_Functional block diagram_

### Electronic schematic
![Electronic schematic](https://cloud.githubusercontent.com/assets/7224381/12681147/6b867c54-c6ad-11e5-9417-b50e3f6e7696.jpg)

_Electronic schematic of the shield_
### Board Design
![Board design 1](https://cloud.githubusercontent.com/assets/7224381/12681143/66925826-c6ad-11e5-9c03-abb2e25c0591.jpg)


_Board design without conductive tracks_

![Board design 2](https://cloud.githubusercontent.com/assets/7224381/12681141/65e5ccb4-c6ad-11e5-9320-287f403a467a.jpg)

_Board design with conductive tracks (Complete)_

## Arduino Software
The Arduino code is located in the ['Arduino' folder](https://github.com/kevinsala/HealthProject/tree/master/Arduino). It contains the implementation of all the features except the blood pressure measurement.
