/**
 *  Race condition due to rule delay
 */
definition(
        name: "Turn on or off light according to present",
        namespace: "IotInspector",
        author: "Eboyu",
        description: "Turn on light when present, turn off light when not present",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet@2x.png")

preferences {
    section("When you leave home....") {
        input "person2", "capability.presenceSensor", multiple:false
        input "light3", "capability.switch", title:"light"
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
    subscribe(person2, "presence", presenceHandler)
}

def presenceHandler(evt) {
    log.debug "evt.name: $evt.value"
    if(evt.value == "present"){
        light3.on()
    }else {
        runIn(0.1 * 60, turnofflight)
    }
}

def turnofflight() {
    log.debug "no one in home, turn off light"
    light3.off()
}
