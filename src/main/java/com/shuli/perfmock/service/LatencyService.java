package com.shuli.perfmock.service;

import com.shuli.perfmock.model.Latency;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author:songhongli
 * @Created: 2018/6/11
 */
@Service
public class LatencyService {
    //public static Map<Integer,Latency> latencyMap = new HashMap<>();
    private  Latency latency;
    public void setLatency(Latency latency){
        this.latency = latency;
    }

    public int get(){
        //int size = latencyMap.keySet().size();
        //int key = new Random().nextInt(size);
        //Latency latency = latencyMap.get(key);


        if(latency == null){
            return 0;
        }
        return new Random().nextInt(latency.getMax())%(latency.getMax() - latency.getMin() + 1) + latency.getMin();
    }
}
