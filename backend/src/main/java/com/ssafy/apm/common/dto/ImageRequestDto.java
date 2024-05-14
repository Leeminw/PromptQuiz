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
public class ImageRequestDto {
    private Long id;
    private String url;
    private String uuid;
    private Long filesize;
    private String filename;
    private String filepath;
    private String contentType;

    public Image toEntity() {
        return Image.builder()
                .id(this.id)
                .url(this.url)
                .uuid(this.uuid)
                .filesize(this.filesize)
                .filename(this.filename)
                .filepath(this.filepath)
                .contentType(this.contentType)
                .build();
    }
}
