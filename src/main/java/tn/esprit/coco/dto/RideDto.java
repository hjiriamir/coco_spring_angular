package tn.esprit.coco.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RideDto {
    private long id;
    private String startLocation;
    private String arrivalAddress;
    private String departureDate;
    private String time;
    private int price;
    private long placeDisponible;
}
