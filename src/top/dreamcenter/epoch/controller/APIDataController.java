package top.dreamcenter.epoch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.dreamcenter.epoch.entity.APIData;
import top.dreamcenter.epoch.service.APIDataService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/apis")
public class APIDataController {
    @Autowired
    private APIDataService apiDataService;

    @RequestMapping("/list")
    public void showList(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(apiDataService.showList());
    }

    @RequestMapping("/add")
    public void addNew(@RequestBody APIData apiData, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        System.out.println(apiData);
        response.getWriter().write(apiDataService.addOne(apiData));
    }

    @RequestMapping("/own")
    public void own(@RequestHeader String authorization, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(apiDataService.getOwn(authorization));
    }

    @RequestMapping("/delete")
    public void delete(@RequestHeader String authorization, @RequestParam int id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(apiDataService.deleteOne(authorization, id));
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public void change(@RequestHeader String authorization, @RequestBody APIData apiData, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(apiDataService.getOne(authorization, apiData));
    }
}
