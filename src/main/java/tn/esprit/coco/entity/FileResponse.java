package tn.esprit.coco.entity;

import java.time.LocalDateTime;

public class FileResponse {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(LocalDateTime uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    private String fileName;
    private LocalDateTime uploadDateTime;
    public FileResponse(Long id, String fileName, LocalDateTime uploadDateTime) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDateTime = uploadDateTime;
    }
}
