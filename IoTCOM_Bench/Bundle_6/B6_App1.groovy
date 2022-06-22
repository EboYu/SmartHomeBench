/**
 *  Violation description: Unintended behavior (i.e. loop triggering)
 *  App1 that will switch off all light bulbs, 
 *  This triggers the action of App2 to switch on one of the light bulbs
 *	and switch it off after few minutes. 
 *  This affects App1 over the luminance physical channel.
 *  This constitutes a loop between App1 and App2
 *  Violation Path: App1[illuminanceHandler] >> App1[switches.off()]
 *					>> App2[switchOffHandler] >> App2[runIn(delay, turnOffSwitch)]
 * 					>> App2[turnOffSwitch] >> switch1.off()
 */

definition(
	name: "IoTCOM",
    namespace: "IoTCOM",
    author: "",
	description: "Convenience",
	category: "Convenience",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)

preferences {
	section("light sensor identifies luminance level"){
		input "lightSensor", "capability.illuminanceMeasurement", required: true
	}
	section("Turn off all lights...") {
		input "switch1", "capability.light", multiple: true
	}
}

def installed()
{
	subscribe(lightSensor, "illuminance", illuminanceHandler)
}

def illuminanceHandler(evt) {
	if(evt.level>2)
		switch1.off()
}