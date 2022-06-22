
definition(
	name: "Energy Saver",
	namespace: "smartthings",
	author: "SmartThings",
	description: "Turn things off if you're using too much energy",
	category: "Green Living",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png"
)

preferences {
	section {
		input(name: "meter", type: "capability.powerMeter", title: "When This Power Meter...", required: true, multiple: false, description: null)
		input(name: "threshold", type: "number", title: "Reports Above...", required: true, description: "in either watts or kw.")
	}
	section {
		input(name: "smartplug1", type: "capability.smartPlug", title: "Turn Off smart plug", required: true, multiple: true, description: null)
	}
}

def installed()
{
	subscribe(meter, "power", meterHandler)
}

def updated() {
	unsubscribe()
	subscribe(meter, "power", meterHandler)
}

def meterHandler(evt) {
	if(evt.value > threshold)
		smartplug1.off()
}