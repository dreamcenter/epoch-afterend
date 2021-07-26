package top.dreamcenter.epoch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.dreamcenter.epoch.entity.APIData;
import top.dreamcenter.epoch.service.APIDataService;
import top.dreamcenter.epoch.service.ThirdPartyService;
import top.dreamcenter.epoch.util.ThirdPartyRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
@RequestMapping("/api/third")
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService tps;
    @Autowired
    private APIDataService ads;

    @RequestMapping("/date")
    public void getDateDetail(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getDateDetail());
    }

    @RequestMapping("/flatterer")
    public void getFlattererSentence(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getFlattererSentence());
    }

    @RequestMapping("/rainbow")
    public void getRainbowPiSentence(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getRainbowPiSentence());
    }

    @RequestMapping("/gold")
    public void getGoldSentence(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getGoldSentence());
    }

    @RequestMapping("/ip/number")
    public void getIP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        String remoteAddr = request.getRemoteAddr();
        response.getWriter().write(remoteAddr);
    }

    @RequestMapping("/ip/detail")
    public void getIPDetail(String ip, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getIPDetail(ip));
    }

    @RequestMapping("/weather")
    public void getWeather(String ip, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        response.getWriter().write(tps.getWeather(ip));
    }

    @RequestMapping("/z/{id}")
    public void getUserMode(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");

        APIData apiData = ads.innerGet(id);

        byte method = apiData.getMethod();
        String url = apiData.getUrl();
        String result = "-1";

        if (method == 0) { //GET
            String queryString = request.getQueryString();
            result = ThirdPartyRequest.getGETRequest(url,queryString);
        } else { //POST
            /*BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String temp;
            while((temp=reader.readLine())!=null) builder.append(temp);
            reader.close();
            System.out.println(builder);*/
            result = "\"暂未开放代理POST请求，敬请期待\"";
        }
        /*
         APIData{
            id=1, master='root', permission=1, name='老黄历', method=0,
            url='http://81.70.80.152/epoch/api/third/date',
            description='获取老黄历的各种描述信息',
            type='application/json;charset=utf8',
            param='none'
         }
         */

        response.getWriter().write("{\"z\":"+ result +"}");
    }
}
