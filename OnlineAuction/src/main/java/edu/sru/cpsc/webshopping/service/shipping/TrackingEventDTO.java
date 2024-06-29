package edu.sru.cpsc.webshopping.service.shipping;

public class TrackingEventDTO {
    private String objectCreated;
    private String objectId;
    private String status;
    private String statusDetails;
    private String statusDate;
    private AddressDTO location;

    public String getObjectCreated() {
        return objectCreated;
    }

    public void setObjectCreated(String objectCreated) {
        this.objectCreated = objectCreated;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public AddressDTO getLocation() {
        return location;
    }

    public void setLocation(AddressDTO location) {
        this.location = location;
    }

    
}

