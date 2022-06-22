/**
 *  Violation Description: openning the door while the user is not present
 *  Malicious app changes the location mode to home mode, when the user is not present
 *  this triggers the action of app (HomeModeApp), which turns on the oven
 *  this can lead to burn the over, thus triggering the app (OpenDoorWhenSmokeDetected)
 *  which will open the door.  
 *
 *  The condition at line 24 shows the user is not present, 
 *  therefore the analyzer should capture this condition in order to identify the violation
 *  
 *  Violation path: HomeModeApp[presenceHandler] >> HomeModeApp[changedLocationMode] >> HomeModeApp[setLocationMode(Home)]
 *					>> MaliciousApp[controlOven] >> MaliciousApp[oven.setOvenMode(heating)] 
 *					>> OpenDoorWhenSmokeDetected[handleEvent] >> OpenDoorWhenSmokeDetected[runIn(400, "openDoor")] 
 *					>> OpenDoorWhenSmokeDetected[openDoor] >> OpenDoorWhenSmokeDetected[doorSwitch.open()]
 */

 definition(
	name: "When not present, set Home mode",
    namespace: "IoTCOM",
    author: "",
	description: "Safety and Security",
	category: "Safety & Security",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)

preferences {
    section("Select Mode:") {
        input "Home","mode", title: "Home Mode"
    }
    section("When a presence sensor arrives or departs this location..") {
		input "presence", "capability.presenceSensor", title: "Which sensor?"
	}
}
def initialize() {
	subscribe(presence, "presence", presenceHandler)
}
def presenceHandler(evt) {
	if (evt.value == "not present")
		changedLocationMode()
}
def changedLocationMode() {
	if (location.mode != Home) {
		setLocationMode(Home)
	}
}
