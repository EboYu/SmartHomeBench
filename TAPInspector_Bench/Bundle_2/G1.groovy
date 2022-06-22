
/**
 *  Race condition due to rule concurrent
 */
definition(
        name: "heater on according to present",
        namespace: "IotInspector",
        author: "Eboyu",
        description: "heater on for a few of minutes when present",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet@2x.png")

preferences {
    section("When you leave home....") {
        input "person", "capability.presenceSensor", multiple:false
    }
    section("Turn on which window...") {
        input "window1", "capability.doorControl", required: false
    }
    section("Turn on which heater...") {
        input "heater2", "capability.heater", required: false
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    subscribe(heater2, "switch", heaterOnHandler)
    subscribe(person, "presence", presenceHandler)
}

def heaterOnHandler(evt) {
    log.debug "evt.name: $evt.value"
    if(evt.value == "on"){
        runIn(10, closeHeater)
    }
}
def presenceHandler(evt) {
    log.debug "evt.name: $evt.value"
    if(evt.value == "present"){
        heater2.on()
    }
}

def closeHeater() {
    log.debug "no one in home, turn off light"
    if(window1.currentDoor="closed"){
        heater2.off()
    }
}

