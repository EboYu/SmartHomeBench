/**
 *  Race condition due to rule delay
 */
definition(
        name: "lock door according to present",
        namespace: "IotInspector",
        author: "Eboyu",
        description: "unlock when present, lock door when not present",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet@2x.png")

preferences {
    section("When you leave home....") {
        input "person1", "capability.presenceSensor", multiple:false
    }
    section("Make sure your door is locked when user is not present") {
        input "doorLock","capability.lock"
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
    subscribe(person1, "presence", presenceHandler)
}

def presenceHandler(evt) {
    log.debug "evt.name: $evt.value"
    if(evt.value == "present"){
        runIn(60, unlockDoor)
        runIn(30, lockDoor)
    }else {
        doorLock.lock()
    }
}

def unlockDoor() {
    log.debug "no one in home, turn off light"
    doorLock.unlock()
}

def lockDoor() {
    log.debug "no one in home, turn off light"
    if(doorLock.value="unlock"){
        doorLock.lock()
    }
}
