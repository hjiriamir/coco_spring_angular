package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacebookService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${facebook.page-id}")
    private String pageId;

    @Value("${facebook.access-token}")
    private String accessToken;



    public void publishOnPage(String message) {
        String url = "https://graph.facebook.com/" + pageId + "/feed";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.postForObject(url, requestEntity, String.class);
    }
}
