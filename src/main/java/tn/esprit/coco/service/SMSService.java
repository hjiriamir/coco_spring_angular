package tn.esprit.coco.service;

import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import tn.esprit.coco.configuration.TwilioConfiguration;
import tn.esprit.coco.dto.request.SmsRequest;


@Service

public class SMSService {
    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public SMSService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(SmsRequest smsRequest) {
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNumber());
        String message = smsRequest.getMessage();
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
        System.out.println("Send sms {}" + smsRequest);
    }
}
