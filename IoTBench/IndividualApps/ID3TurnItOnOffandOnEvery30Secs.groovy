/**
 *  Author: Z. Berkay Celik
 *  When a SmartSense Multi (contact sensor) is opened, a switch will be turned on, and then turned on signal is sent every 30 seconds.",
 *  Email: zbc102@cse.psu.edu
 */
definition(
    name: "Soteria",
    namespace: "Soteria",
    author: "IoTBench",
    description: "When a SmartSense Multi is opened, a switch will be turned on, and then turned off after 5 minutes.",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
	section("When it opens..."){
		input "contact1", "capability.contactSensor"
	}
	section("Turn on a switch for 5 minutes..."){
		input "switch1", "capability.switch"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(contact1, "contact.open", contactOpenHandler)
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribe(contact1, "contact.open", contactOpenHandler)
	subscribe(contact1, "contact.closed", contactCloseHandler)
}


def contactCloseHandler(evt) {
	switch1.off()
}

def contactOpenHandler(evt) {
	switch1.on()
	def fiveMinDelay = 60 * 5 //updated
	runIn(fiveMinDelay, turnOnSwitch)
}

def turnOnSwitch() {
	switch1.on() // send multiple off commands to the device
	def thirtySecDelay = 60 * 0.5 //updated
	runIn(thirtySecDelay, turnOnSwitch)
}