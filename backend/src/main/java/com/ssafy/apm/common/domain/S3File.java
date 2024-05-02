package com.ssafy.apm.common.domain;

import com.ssafy.apm.common.dto.request.S3FileRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Long filesize;
    private String filename;
    private String filetype;
    private String originalFilename;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public S3File update(S3FileRequestDto requestDto) {
        if (requestDto.getUrl() != null) this.url = requestDto.getUrl();
        if (requestDto.getFilesize() != null) this.filesize = requestDto.getFilesize();
        if (requestDto.getFilename() != null) this.filename = requestDto.getFilename();
        if (requestDto.getFiletype() != null) this.filetype = requestDto.getFiletype();
        if (requestDto.getOriginalFilename() != null) this.originalFilename = requestDto.getOriginalFilename();
        return this;
    }
}
