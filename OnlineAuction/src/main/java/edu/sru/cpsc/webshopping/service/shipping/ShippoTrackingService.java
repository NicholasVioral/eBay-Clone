package edu.sru.cpsc.webshopping.service.shipping;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shippo.Shippo;
import com.shippo.exception.ShippoException;
import com.shippo.model.Track;


@Service
public class ShippoTrackingService {

    private final String shippoApiKey = "shippo_test_3dde1f5f7f457ed0646d4eb9ce4c19f3e584b1ae";

    public TrackDTO getTrackingInformation(String carrier, String trackingNumber) {
        Shippo.setApiKey(shippoApiKey);

        //test values: carrier = "shippo", trackingNumber = "SHIPPO_TRANSIT"
        try {
            Track track = Track.getTrackingInfo(carrier, trackingNumber, shippoApiKey);
            //print tracking 
            System.out.println(track);
            return mapTrackToTrackDTO(track);
        } catch (ShippoException e) {
            throw new RuntimeException("Failed to get tracking information", e);
        }
    }

    private TrackDTO mapTrackToTrackDTO(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTrackingNumber(track.getTrackingNumber());
        trackDTO.setCarrier(track.getCarrier());
        trackDTO.setAddressFrom(mapAddressToAddressDTO(track.getAddressFrom()));
        trackDTO.setAddressTo(mapAddressToAddressDTO(track.getAddressTo()));
        trackDTO.setEta(track.getETA());
        trackDTO.setTrackingStatus(mapTrackingStatusToTrackingStatusDTO(track.getTrackingStatus()));
        trackDTO.setTrackingHistory(mapTrackingEventToTrackingEventDTO(track.getTrackingHistory()));
        return trackDTO;
    }

    private AddressDTO mapAddressToAddressDTO(Track.Address address) {
    	if (address == null) {
            return null; 
        }
        AddressDTO addressDTO = new AddressDTO();
        try {
            Field cityField = Track.Address.class.getDeclaredField("city");
            cityField.setAccessible(true);
            String city = (String) cityField.get(address);
            addressDTO.setCity(city);
    
            Field stateField = Track.Address.class.getDeclaredField("state");
            stateField.setAccessible(true);
            String state = (String) stateField.get(address);
            addressDTO.setState(state);
    
            Field zipField = Track.Address.class.getDeclaredField("zip");
            zipField.setAccessible(true);
            String zip = (String) zipField.get(address);
            addressDTO.setZip(zip);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access address fields", e);
        }
        return addressDTO;
    }

    private TrackingStatusDTO mapTrackingStatusToTrackingStatusDTO(Track.TrackingEvent trackingStatus) {
        if(trackingStatus == null) {
            return null;
        }
        TrackingStatusDTO trackingStatusDTO = new TrackingStatusDTO();
        try {
            Field statusField = Track.TrackingEvent.class.getDeclaredField("status");
            statusField.setAccessible(true);
            Enum<?> statusEnum = (Enum<?>) statusField.get(trackingStatus);
            String status = statusEnum.name();
            trackingStatusDTO.setStatus(status);
    
            Field statusDetailsField = Track.TrackingEvent.class.getDeclaredField("statusDetails");
            statusDetailsField.setAccessible(true);
            String statusDetails = (String) statusDetailsField.get(trackingStatus);
            trackingStatusDTO.setStatusDetails(statusDetails);
    
            Field statusDateField = Track.TrackingEvent.class.getDeclaredField("statusDate");
            statusDateField.setAccessible(true);
            Date statusDate = (Date) statusDateField.get(trackingStatus);  // Assume statusDate is of type Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String statusDateString = sdf.format(statusDate);
            trackingStatusDTO.setStatusDate(statusDateString);

            trackingStatusDTO.setLocation(mapAddressToAddressDTO(trackingStatus.getLocation()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access tracking status fields", e);
        }
        return trackingStatusDTO;
    }

    private List<TrackingEventDTO> mapTrackingEventToTrackingEventDTO(Track.TrackingEvent[] trackingEvents) {
        if(trackingEvents == null) {
            return null;
        }
        List<TrackingEventDTO> trackingEventDTOs = new ArrayList<>();
        for (Track.TrackingEvent trackingEvent : trackingEvents) {
            TrackingEventDTO trackingEventDTO = new TrackingEventDTO();
            try {
                Field statusField = Track.TrackingEvent.class.getDeclaredField("status");
                statusField.setAccessible(true);
                Enum<?> statusEnum = (Enum<?>) statusField.get(trackingEvent);
                String status = statusEnum.name();
                trackingEventDTO.setStatus(status);
        
                Field statusDetailsField = Track.TrackingEvent.class.getDeclaredField("statusDetails");
                statusDetailsField.setAccessible(true);
                String statusDetails = (String) statusDetailsField.get(trackingEvent);
                trackingEventDTO.setStatusDetails(statusDetails);
        
                Field statusDateField = Track.TrackingEvent.class.getDeclaredField("statusDate");
                statusDateField.setAccessible(true);
                Date statusDate = (Date) statusDateField.get(trackingEvent);  // Assume statusDate is of type Date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String statusDateString = sdf.format(statusDate);
                trackingEventDTO.setStatusDate(statusDateString);
        
                // Assuming Track.TrackingEvent has a getLocation method or you have other means to access the location
                trackingEventDTO.setLocation(mapAddressToAddressDTO(trackingEvent.getLocation()));

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to access tracking event fields", e);
            }
            trackingEventDTOs.add(trackingEventDTO);
        }
        return trackingEventDTOs;
    }

}



