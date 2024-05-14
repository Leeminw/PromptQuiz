package com.ssafy.apm.common.dto;

import com.ssafy.apm.common.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    private Long id;
    private String url;
    private String uuid;
    private Long filesize;
    private String filename;
    private String filepath;
    private String contentType;

    public ImageResponseDto(Image entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.uuid = entity.getUuid();
        this.filesize = entity.getFilesize();
        this.filename = entity.getFilename();
        this.filepath = entity.getFilepath();
        this.contentType = entity.getContentType();
    }
}
