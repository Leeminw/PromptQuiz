package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SimilarityResponseDto implements Comparable<SimilarityResponseDto> {
    // 품사 항목 (명사, 동사 등등)
    private String value;
    // 유사도
    private Double rate;

    @Override
    public int compareTo(SimilarityResponseDto o) {
        return this.rate.compareTo(o.rate);
    }
}
