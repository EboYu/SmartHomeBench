/**
 *  Race condition due to rule concurrent
 */
definition(
        name: "open windows when temperature >27 ",
        namespace: "IotInspector",
        author: "Eboyu",
        description: "open windows when temperature is greater than 27",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_outlet@2x.png")

preferences {
    section("Monitor the temperature...") {
        input "temperatureSensor1", "capability.temperatureMeasurement"
    }
    section("Turn on which window...") {
        input "window1", "capability.doorControl", required: false
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
    subscribe(temperatureSensor1, "temperature", temperatureHandler)
}


def temperatureHandler() {
    if(evt.value >=27){
        window1.open()
    }else {
        window1.close()
    }
}

