package tn.esprit.coco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SmsRequest {
    private final String phoneNumber;
    private final String message;


}
