package com.ssafy.apm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleTranslator {
    public static String translate(String sourceLang, String targetLang, String text) throws IOException {
        /* Encoding Text UTF-8 CharacterSet */
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String requestURL = String.format("https://translate.google.com/m?sl=%s&tl=%s&q=%s",
                sourceLang, targetLang, encodedText);

        /* Request Translate */
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        /* Response Translate */
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        /* Extract Translated Text */
        Pattern pattern = Pattern.compile("<div class=\"result-container\">(.*?)</div>");
        Matcher matcher = pattern.matcher(response.toString());
        return matcher.find() ? matcher.group(1) : "Not Found";
    }

}
