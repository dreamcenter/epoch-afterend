package top.dreamcenter.epoch.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import top.dreamcenter.epoch.dao.APIDataDao;
import top.dreamcenter.epoch.entity.APIData;
import top.dreamcenter.epoch.exception.TokenParseFailException;
import top.dreamcenter.epoch.util.MyTokenCenter;

import java.util.List;

@Service
public class APIDataService {

    @Autowired
    private APIDataDao apiDataDao;

    public String showList(){
        List<APIData> list = apiDataDao.showList();
        return new Gson().toJson(list);
    }

    public String addOne(APIData data){
        int count = apiDataDao.addOne(data);
        return "{\"result\":"+count+"}";
    }

    public String getOwn(String authorization){
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();
            List<APIData> result = apiDataDao.showOwn(nickname);
            return "{\"code\":200, \"result\":" + new Gson().toJson(result) + "}";
        } catch (TokenParseFailException e) {
            e.printStackTrace();
            return "{\"code\":-1}";
        }
    }

    public String deleteOne(String authorization,int id){
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();
            int count = apiDataDao.deleteOne(nickname, id);
            if (count == 1) {
                return "{\"code\":200}";
            } else {
                return "{\"code\":-1}";
            }
        } catch (TokenParseFailException e) {
            e.printStackTrace();
            return "{\"code\":-1}";
        }
    }

    public String getOne(String authorization,APIData apiData){
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();
            if (!nickname.equals(apiData.getMaster())) return "{\"code\":-1}";
            int count = apiDataDao.changeOne(apiData);
            if (count == 1) {
                return "{\"code\":200}";
            } else {
                return "{\"code\":-1}";
            }
        } catch (TokenParseFailException e) {
            e.printStackTrace();
            return "{\"code\":-1}";
        }
    }

    public APIData innerGet(int id){
        return apiDataDao.getOne(id);
    }
}
