package com.shuli.perfmock.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:songhongli
 * @Created: 2018/6/11
 */
@Data
public class MockItem {
    String desc;
    String uri;
    int latency;
    String method;
    JSONObject response;
    Map<String,String> queries = new HashMap<>();
}
