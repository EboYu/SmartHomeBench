
# Smart Home Bench

SmartHomeBench contains various malicious IoT apps collected from [IoTBench](https://github.com/IoTBench/IoTBench-test-suite), [IoTCOM](https://dl.acm.org/doi/10.1145/3395363.3397347) and our TAPInspector.

## Introduction

| Function name |File | Description |
| ------------- || ------------------------------ |
| `N1`     | N1.groovy| If the user present, turn curling iron on after 10 minutes; If not present, turn curling iron off.       |
| `N2`  |  N2.groovy| Unlock and then lock the door if user present; Lock the door if not present.    |
| `N3`   | N3.groovy| Brewing coffee if user is present; Close coffer maker if it is sleep time.   |
| `Group N4`  | Bundle_1 | If temperature drops below 20°C, turn heater on;<br> If temperature rises above 30°C, turn heater off;<br> If electric power rises above 3000W for 10 minutes, turn outlet off.   |
| `Group N5`  |  Bundle_2| When temperature rises above 26°C, turn heater off after 20 minutes if user is present and window is closed;<br> If user present and temperature is below 18°C, turn heater on and close window; <br>If temperature rises above 28°C, open window; <br>If temperature drops below 15°C, close window.    |
