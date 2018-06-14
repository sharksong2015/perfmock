package com.shuli.perfmock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shuli.perfmock.model.Latency;
import com.shuli.perfmock.model.MockItem;
import com.shuli.perfmock.service.LatencyService;
import com.shuli.perfmock.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;

@SpringBootApplication
public class PerfmockApplication {
    @Autowired
    MockService mockService;

    @Autowired
    LatencyService latencyService;

    public static void main(String[] args) {
        SpringApplication.run(PerfmockApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                for (String str : args) {
                    if (str.indexOf("--mockfile") != -1) {
                        File mockFile = new File(str.split("=")[1]);
                        System.out.println(mockFile.getAbsoluteFile());
                        String txt = FileCopyUtils.copyToString(new FileReader(mockFile));
                        System.out.println(txt);
                        JSONArray jsonArray = JSONArray.parseArray(txt);
                        int num = jsonArray.size();
                        for (int i = 0; i < num; i++) {
                            JSONObject rec = jsonArray.getJSONObject(i);

                            MockItem mockItem = new MockItem();
                            mockItem.setUri(rec.getJSONObject("request").getString("uri"));
                            mockItem.setDesc(rec.getString("description"));
                            mockItem.setLatency(
                                    rec.getJSONObject("response").containsKey("latency") ? rec.getJSONObject("response")
                                            .getInteger("latency") : 0);

                            mockItem.setMethod(
                                    rec.getJSONObject("request").containsKey("method") ? rec.getJSONObject("request")
                                            .getString("method") : "POST/GET");
                            mockItem.setResponse(rec.getJSONObject("response").getJSONObject("json"));

                            //query参数
                            if (rec.getJSONObject("request").containsKey("queries")) {
                                JSONObject q = rec.getJSONObject("request").getJSONObject("queries");
                                for (String key : q.keySet()) {
                                    mockItem.getQueries().put(key, q.getString(key));
                                }
                            }

                            //表单参数
                            if (rec.getJSONObject("request").containsKey("forms")) {
                                JSONObject q = rec.getJSONObject("request").getJSONObject("forms");
                                for (String key : q.keySet()) {
                                    mockItem.getQueries().put(key, q.getString(key));
                                }
                            }
                            mockService.addMockItem(mockItem);
                        }

                    }

                    if (str.indexOf("--latency") != -1) {
                        String latencyStr = str.split("=")[1];
                        int min = Integer.parseInt(latencyStr.split(",")[0]);
                        int max = Integer.parseInt(latencyStr.split(",")[1]);

                        Latency latency = new Latency(min, max);
                        latencyService.setLatency(latency);

                    }
                }

            }
        };
    }
}
