/**
 *  Benign app opens the door when smook is detected
 */

definition(
	name: "Open door when smoke detected",
    namespace: "IoTCOM",
    author: "",
	description: "Safety and Security",
	category: "Safety & Security",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)


preferences {
    section("Alarm settings and actions") {
		input "smoke", "capability.smokeDetector", title: "Smoke Detected", required: false, multiple: true
    	input "doorSwitch", "capability.doorControl", title: "Which switch?"
    	input "doorSensor", "capability.contactSensor", title: "Which sensor?"
	}
}
def installed() {
	subscribe(smoke, "smoke.detected", handleEvent)
}

def handleEvent(evt) {
	if (doorSensor.currentContact == "closed") {
		runIn(400, "openDoor")
	}
}
private openDoor()
{
	if (doorSensor.currentContact == "closed") {
		log.debug "opening door"
		doorSwitch.open()
	}
}