package top.dreamcenter.epoch.test;

import org.junit.Test;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.util.MyTokenCenter;

import java.util.Calendar;

public class UsefulTest {
    @Test
    public void getOneToken(){
        TokenEntity entity = new TokenEntity("root",
                Calendar.getInstance().getTime(), 60*30);
        String token = MyTokenCenter.getToken(entity);
        System.out.println(token);
    }
}
