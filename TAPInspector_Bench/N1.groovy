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
        input "userPresent", "capability.presenceSensor", multiple:false
        input "curlingiron", "capability.switch", title:"light"
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
    subscribe(userPresent, "presence", presenceHandler)
}

def presenceHandler(evt) {
    log.debug "evt.name: $evt.value"
    if(evt.value == "present"){
        runIn(3 * 60, turnOnCurlingIron)
    }else {
        curlingiron.off()
    }
}

def turnOnCurlingIron() {
    log.debug "no one in home, turn off light"
    curlingiron.on()
}
