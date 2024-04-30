package tn.esprit.coco.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dpjjpjy1j";
    private final String API_KEY = "194889293825468";
    private final String API_SECRET = "KX05LX3wNjLkyVCGvrx_-QceRJk";
    @Bean(name = "cloudinaryConfig1")
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);

        return new Cloudinary(config);
    }
}
