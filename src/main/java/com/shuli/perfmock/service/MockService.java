package com.shuli.perfmock.service;

import com.shuli.perfmock.model.Latency;
import com.shuli.perfmock.model.MockItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:songhongli
 * @Created: 2018/6/11
 */
@Service
public class MockService {
    public static HashMap<String, List<MockItem>> mock_map = new HashMap<>();

    public void addMockItem(MockItem mockItem) {
        List<MockItem> mockItems = mock_map.get(mockItem.getUri());
        if (mockItems == null) mockItems = new ArrayList<>();
        mockItems.add(mockItem);
        mock_map.put(mockItem.getUri(), mockItems);
    }

    public MockItem getMockItem(String uri) {
        List<MockItem> mockItems = mock_map.get(uri);
        if (mockItems != null && mockItems.size() > 0) {
            return mockItems.get(0);
        }
        return null;
    }

    public MockItem getMockItem(String uri, Map<String, String> queries) {
        List<MockItem> mockItems = mock_map.get(uri);
        if (mockItems != null && mockItems.size() > 0) {
            for (MockItem mockItem : mockItems) {
                boolean match = true;

                if(mockItem.getQueries().size() != queries.size()) {
                    match=false;
                }

                if(match){
                    return mockItem;
                }
            }
        }
        return null;
    }
}
