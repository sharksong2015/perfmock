package com.shuli.perfmock.controller;

import com.alibaba.fastjson.JSONObject;
import com.shuli.perfmock.model.MockItem;
import com.shuli.perfmock.service.LatencyService;
import com.shuli.perfmock.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:songhongli
 * @Created: 2018/6/14
 */
@RestController
public class MockController {
    private static Logger logger = LoggerFactory.getLogger(MockController.class.getName());

    @Autowired
    LatencyService latencyService;

    @Autowired
    MockService mockService;

    private static final JSONObject JSON_OBJECT = new JSONObject();

    static {
        JSON_OBJECT.put("SUCCESS", "true");
        JSON_OBJECT.put("msg", "uri is not valid");
    }


    @RequestMapping("/**/*")
    public Object mock(HttpServletRequest req) {
        String rest = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        Map<String, String> params = new HashMap<>();

        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            params.put(key,req.getParameter(key));
        }

        MockItem mockItem = mockService.getMockItem(rest,params);

        if(mockItem == null){
            return JSON_OBJECT;
        }

        try{
            int latencyNum = latencyService.get();
            logger.info("访问:" + mockItem.getUri() + "; latency: " + latencyNum);

            if(latencyNum>0){
                Thread.sleep(latencyNum);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return mockItem.getResponse();
    }
}
