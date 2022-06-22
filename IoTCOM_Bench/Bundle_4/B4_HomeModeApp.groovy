/**
 *  Benign app sets the oven mode 
 */
 
definition(
	name: "Set oven mode",
    namespace: "IoTCOM",
    author: "",
	description: "Safety and Security",
	category: "Safety & Security",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)

preferences {
    section("Controlling the oven mode..") {
		input "oven", "capability.ovenMode", title: "Which oven mode?"
	}
	section("Home mode..") {
		input "Home", "mode", title: "Mode?"
	}
}
def initialize() {
	subscribe(location, controlOven)
}
def controlOven(){
	if (location.mode == Home)
		oven.setOvenMode(heating)
}
