/**
 *  Race condition due to rule delay
 */
definition(
        name: "brewing coffee when user is present",
        namespace: "IotInspector",
        author: "Eboyu",
        description: "brewing coffee when user is present, close coffer maker when it is sleep time",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet@2x.png")

preferences {
    section("Start coffee when...") {
        input "myCoffeeMaker", "capability.extendedSwitch", title: "Coffee machine?"
    }
    section("When there's movement...") {
        input "person4", "capability.presenceSensor", title: "Where?", multiple: false
    }
    section("Turn it off at...") {
        input name: "sleepTime", title: "Turn On Time?", type: "time"
    }
}
/**
 *  Extended action, which will finish within a few of time, rather than instant
 */
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
    subscribe(person4,"presence", "presenceHandler")
    schedule(sleepTime,"sleepTimeCallback")
}

def presenceHandler(){
    if (evt.value == "present"){
        log.debug "Turning on switches"
        myCoffeeMaker.on()
    }

}

def sleepTimeCallback() {
    log.debug "Turning on switches"
    myCoffeeMaker.off()
}

