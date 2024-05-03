package com.ssafy.apm.common.dto;

import com.ssafy.apm.common.domain.S3File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3FileResponseDto {
    private Long id;
    private String url;
    private Long filesize;
    private String filename;
    private String filetype;
    private String originalFilename;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public S3FileResponseDto(S3File entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.filename = entity.getFilename();
        this.filesize = entity.getFilesize();
        this.filetype = entity.getFiletype();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
        this.originalFilename = entity.getOriginalFilename();
    }

}
