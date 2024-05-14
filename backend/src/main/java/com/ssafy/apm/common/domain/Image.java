package com.ssafy.apm.common.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String uuid;
    private Long filesize;
    private String filename;
    private String filepath;
    private String contentType;

    public Image update(MultipartFile file) {
        this.filesize = file.getSize();
        this.filename = file.getOriginalFilename();
        this.contentType = file.getContentType();
        return this;
    }
}
