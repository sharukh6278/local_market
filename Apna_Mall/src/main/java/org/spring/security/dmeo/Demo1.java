package org.spring.security.dmeo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;
import java.util.Random;

public class Demo1 {
    public static  void main(String arg[]) throws JsonProcessingException {
        System.out.println(alphaNumericString(6));

        String token="eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyUmVnaXN0cmF0aW9uUmVxdWVzdFN0YXR1cyI6IlJFR0lTVFJBVElPTl9BUFBST1ZFRCIsInN1YiI6Int1c2VyUmVnaXN0cmF0aW9uUmVxdWVzdFN0YXR1cz1SRUdJU1RSQVRJT05fQVBQUk9WRUQsIHJvbGVzPVtdJExhenlMb2FkaW5nUHJveHksIGVtYWlsPXNya0BnbWFpbC5jb219Iiwicm9sZXMiOltdLCJleHAiOjE3MzM1NDEwNjEsImlhdCI6MTczMzM2ODI2MSwiZW1haWwiOiJzcmtAZ21haWwuY29tIn0.6RRPasyR_YlOqMy2E9ychqVhTkDiKnRL_OLyGrObWLUQxUzK6y3m-qjR_QIdJyO3zI5mwjpacEcLz3psf8B9HA";
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = token.split("\\.");
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(header);
        Map map=new ObjectMapper().readValue(payload, Map.class);
        System.out.println(map.get("email"));
        System.out.println(map.get("userRegistrationRequestStatus"));
        System.out.println(map.get("roles"));
    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
