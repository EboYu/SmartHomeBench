/**
 *  Violation description: Unintended behavior (i.e. the light will be switched of while there is motion)
 *	over luminance physical channel
 *	App1 switches the light when motion is detected, 
 *  which triggers the action of App2 that will switch off all light bulbs. 
 *  The condition at line 25 should be captured by the analyzer to identify the violation correctly
 *	
 *  Violation Path: App1[motionActiveHandler] >> App1[bulb.on()]
 *					>> App2[illuminanceHandler] >> App2[switch.off()]
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
	section("When there's movement...") {
		input "motion1", "capability.motionSensor", title: "Where?", multiple: false
	}
	section("Turn on a light...") {
		input "bulb1", "capability.bulb", multiple: true
	}
}

def installed()
{
	subscribe(motion1, "motion", motionActiveHandler)
}

def motionActiveHandler(evt) {
	if (evt.value == "active"){
		bulb1.on()
	}
}