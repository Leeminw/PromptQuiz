package com.ssafy.apm.socket.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameAnswerCheck {

    private Integer type;
    private Boolean result;
    private HashMap<String, Double> similarity;

}
