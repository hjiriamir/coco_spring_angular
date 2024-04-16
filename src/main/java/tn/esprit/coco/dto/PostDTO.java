package tn.esprit.coco.dto;

import java.time.Instant;

public class PostDTO {
    private String Description;
    private Instant updateDate;

    public PostDTO() {}

    public PostDTO(String Description) {
        this.Description = Description;
    }

    // Getters and setters

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
        setUpdateDate(Instant.now());
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
}
