/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  It's Too Hot
 *
 *  Author: SmartThings
 */
definition(
    name: "It's Too Hot",
    namespace: "smartthings",
    author: "SmartThings",
    description: "Monitor the temperature and when it rises above your setting get a notification and/or turn on an A/C unit or fan.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/B1_App3.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/B1_App3@2x.png"
)

preferences {
	section("Monitor the temperature...") {
		input "temperatureSensor1", "capability.temperatureMeasurement"
	}
	section("Turn on which heater...") {
		input "illuminanceSensor1", "capability.illuminanceMeasurement", required: false
	}
	section("Turn on which heater...") {
		input "heater1", "capability.heater", required: false
	}
}

def installed() {
	subscribe(temperatureSensor1, "temperature", temperatureHandler)
}

def updated() {
	unsubscribe()
	subscribe(temperatureSensor1, "temperature", temperatureHandler)
}

def temperatureHandler(evt) {
	if (evt.doubleValue <=20) {
		if( illuminanceSensor1.illuminance>2)
			heater1?.on()
	}else if(evt.doubleValue>30){
		heater1?.off()
	}
}

