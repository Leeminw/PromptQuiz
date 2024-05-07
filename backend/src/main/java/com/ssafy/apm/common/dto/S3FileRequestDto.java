package com.ssafy.apm.common.dto;

import com.ssafy.apm.common.domain.S3File;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3FileRequestDto {

    private Long id;
    private String url;
    private Long filesize;
    private String filename;
    private String filetype;
    private String originalFilename;

    public S3File toEntity() {
        return S3File.builder()
                .id(this.id)
                .url(this.url)
                .filename(this.filename)
                .filesize(this.filesize)
                .filetype(this.filetype)
                .originalFilename(this.originalFilename)
                .build();
    }

}
