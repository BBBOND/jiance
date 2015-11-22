package com.kim.jiance.model.auxiliaryinfo;

public class ErrorInterface {

    private String id;
    private String eventId;
    private String errorPort;
    private String errorValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getErrorPort() {
        return errorPort;
    }

    public void setErrorPort(String errorPort) {
        this.errorPort = errorPort;
    }

    public String getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(String errorValue) {
        this.errorValue = errorValue;
    }


}
