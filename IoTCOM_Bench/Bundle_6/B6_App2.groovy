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
	section("Turn on the light and turn it of after few minutes...") {
		input "switch1", "capability.light", multiple: false
	}
	section("For how many minutes after if still off?"){
    	input "waitMinutes", "decimal", required: false, defaultValue: 5
    }
}

def installed()
{
	subscribe(switch1, "switch.off", switchOffHandler)
}

def switchOffHandler(evt) {
	switch1.on()
	def delay = 60 * waitMinutes
    runIn(delay, turnOffSwitch)	
}

def turnOffSwitch () {
	switch1.off()
}