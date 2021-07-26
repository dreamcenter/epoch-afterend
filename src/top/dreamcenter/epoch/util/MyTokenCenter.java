package top.dreamcenter.epoch.util;

import com.google.gson.Gson;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.exception.TokenParseFailException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MyTokenCenter {

    // i>奇数位互换，偶数位不变
    private static void codeByMy(byte[] bytes) {
        int len = bytes.length;
        for (int i=0 ; i < len/2; i++) {
            if (i%2 != 0) {
                byte temp = bytes[i];
                bytes[i] = bytes[len-i-1];
                bytes[len-i-1] = temp;
            }
        }
    }

    public static String getToken(TokenEntity entity){
        String entityJson = new Gson().toJson(entity);
        byte[] bytes = entityJson.getBytes(StandardCharsets.UTF_8);
        codeByMy(bytes);

        String to2 = Base64.getEncoder().encodeToString(bytes);
        bytes = to2.getBytes(StandardCharsets.UTF_8);
        codeByMy(bytes);

        byte temp = bytes[1];
        bytes[1] = bytes[bytes.length-2];
        bytes[bytes.length-2] = temp;

        String to3 =Base64.getEncoder().encodeToString(bytes);
        return to3;
    }

    //exception
    public static TokenEntity getTokenEntity(String token) throws TokenParseFailException {
        try{
            byte[] bytes = Base64.getDecoder().decode(token);
            System.out.println(new String(bytes));
            byte temp = bytes[1];
            bytes[1] = bytes[bytes.length-2];
            bytes[bytes.length-2] = temp;
            codeByMy(bytes);
            bytes = Base64.getDecoder().decode(bytes);
            codeByMy(bytes);
            String entityJson = new String(bytes,"utf8");
            return new Gson().fromJson(entityJson,TokenEntity.class);
        } catch (Exception e){
            throw new TokenParseFailException();
        }
    }
}
