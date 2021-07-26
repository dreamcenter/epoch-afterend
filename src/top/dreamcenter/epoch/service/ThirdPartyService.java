package top.dreamcenter.epoch.service;

import org.springframework.stereotype.Service;
import top.dreamcenter.epoch.util.JsonDataRegex;
import top.dreamcenter.epoch.util.ThirdPartyRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class ThirdPartyService {

    @Resource(name = "apikey")
    private String apikey;

    @Resource(name = "chinaZKey")
    private String chinaZKey;

    @Resource(name = "qWeatherKey")
    private String qWeatherKey;

    public String getDateDetail() throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "http://api.tianapi.com/txapi/lunar/index",
                "key=" + apikey);
    }

    public String getFlattererSentence() throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "http://api.tianapi.com/txapi/tiangou/index",
                "key=" + apikey);
    }

    public String getRainbowPiSentence() throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "http://api.tianapi.com/txapi/caihongpi/index",
                "key=" + apikey);
    }

    public String getGoldSentence() throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "http://api.tianapi.com/txapi/one/index",
                "key=" + apikey);
    }

    @Deprecated
    public String getIP() throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "http://api.ipify.org/");
    }

    public String getIPDetail(String ip) throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "https://apidatav2.chinaz.com/single/ip",
                "key=" + chinaZKey,"ip=" + ip);
    }

    private String getDetailLocation(String detail) {
        String detailLocation = JsonDataRegex.getResult(detail,"District");
        if (!detailLocation.equals("")) return detailLocation;
        detailLocation = JsonDataRegex.getResult(detail,"City");
        if (!detailLocation.equals("")) return detailLocation;
        detailLocation = JsonDataRegex.getResult(detail,"Province");
        if (!detailLocation.equals("")) return detailLocation;
        return "";
    }
    private String getDistrictInf(String district) throws IOException {
        return ThirdPartyRequest.getGETRequest(
                "https://geoapi.qweather.com/v2/city/lookup",
                "key=" + qWeatherKey,"location=" + district);
    }
    public String getWeather(String ip) throws Exception {
        if (ip == null || ip == "") return "{\"tip\":\"请设置ip\"}";

        String detailLocation = getDetailLocation(getIPDetail(ip));
        String districtInf = getDistrictInf(detailLocation);
        String districtCode = JsonDataRegex.getResult(districtInf, "id");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateFormatted = sdf.format(calendar.getTime());


        return ThirdPartyRequest.getGETRequest(
                "https://devapi.qweather.com/v7/weather/3d",
                "key=" + qWeatherKey,
                        "location=" + districtCode,
                        "date=" + dateFormatted);
    }
}
