package edu.sru.cpsc.webshopping.service.shipping;

import java.util.Date;
import java.util.List;

public class TrackDTO {
    private String carrier;
    private String trackingNumber;
    private AddressDTO addressFrom;
    private AddressDTO addressTo;
    private String transaction;
    private Date eta;
    private String originalEta;
    private TrackingStatusDTO trackingStatus;
    private List<TrackingEventDTO> trackingHistory;
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public String getTrackingNumber() {
        return this.trackingNumber;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    public String getCarrier() {
        return this.carrier;
    }
    public void setAddressFrom(AddressDTO addressFrom) {
        this.addressFrom = addressFrom;
    }
    public AddressDTO getAddressFrom() {
        return this.addressFrom;
    }
    public void setAddressTo(AddressDTO addressTo) {
        this.addressTo = addressTo;
    }
    public AddressDTO getAddressTo() {
        return this.addressTo;
    }
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    public String getTransaction() {
        return this.transaction;
    }
    public void setEta(Date eta) {
        this.eta = eta;
    }
    public Date getEta() {
        return this.eta;
    }
    public void setOriginalEta(String originalEta) {
        this.originalEta = originalEta;
    }
    public String getOriginalEta() {
        return this.originalEta;
    }
    public void setTrackingStatus(TrackingStatusDTO trackingStatus) {
        this.trackingStatus = trackingStatus;
    }
    public TrackingStatusDTO getTrackingStatus() {
        return this.trackingStatus;
    }
    public void setTrackingHistory(List<TrackingEventDTO> trackingHistory) {
        this.trackingHistory = trackingHistory;
    }
    public List<TrackingEventDTO> getTrackingHistory() {
        return this.trackingHistory;
    }
}
