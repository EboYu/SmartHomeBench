
definition(
        name: "Set volume When I'm Here or calling",
        namespace: "IoTInspector",
        author: "SmartThings",
        description: "Turn something on when you arrive and back off when you leave.",
        category: "Convenience",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_presence-outlet.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_presence-outlet@2x.png"
)

preferences {
    section("When I arrive and leave..."){
        input "person5", "capability.presenceSensor", title: "Who?", multiple: true
    }
    section( "Calling" ) {
        input "phone1", "capability.switch", title: "Is on calling?", required: false
    }
    section {
        input "sonos", "capability.musicPlayer", title: "Sonos player", required: true
        input "normalVolume", "number", title: "Set the volume", description: "0-100%", required: false
        input "lowVolume", "number", title: "Set the volume", description: "0-100%", required: false
    }
}

def installed()
{
    subscribe(person5, "presence", presenceHandler)
    subscribe(phone1, "switch.on", getPhoneCallHandler)
}

def updated()
{
    unsubscribe()
    subscribe(person5, "presence", presenceHandler)
    subscribe(phone1, "switch.on", getPhoneCallHandler)
}

def presenceHandler(evt)
{
    log.debug "getPhoneCallHandler $evt.name: $evt.value"
    if(evt.value=="present"){
        sonos.play()
        log.debug "Someone's home!"
        sonos.setLevel(normalVolume)
    }else{
        sonos.stop()
        log.debug "Stop music player."
    }
}

def getPhoneCallHandler()
{
    log.debug "presenceHandler $evt.name: $evt.value"
    if(person5.currentPresence=="present"){
        sonos.setLevel(lowVolume)
    }
}