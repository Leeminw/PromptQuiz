package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SimilarityResponseDto implements Comparable<SimilarityResponseDto> {
    String value;
    Double rate;

    @Override
    public int compareTo(SimilarityResponseDto o) {
        return this.rate.compareTo(o.rate);
    }
}
