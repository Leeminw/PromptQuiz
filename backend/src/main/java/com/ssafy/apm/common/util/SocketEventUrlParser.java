package com.ssafy.apm.common.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketEventUrlParser {

    private static final Integer GAME = 1, CHANNEL = 2;
    private final Matcher matcher;

    public SocketEventUrlParser(String url) {
        Pattern pattern = Pattern.compile("/ws/sub/(\\w+)\\?uuid=([\\w-]+)");
        this.matcher = pattern.matcher(Objects.requireNonNull(url));
    }

    public Integer getType() {
        return matcher.group(1).equals("game") ? GAME : CHANNEL;
    }

    public String getUuid() {
        return matcher.group(2);
    }

    public boolean isOk() {
        return matcher.find();
    }

}
