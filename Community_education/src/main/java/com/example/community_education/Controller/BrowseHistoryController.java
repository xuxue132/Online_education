package com.example.community_education.Controller;

import com.example.community_education.Service.BrowseHistoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("public")
public class BrowseHistoryController {
    @Resource
    private BrowseHistoryService browseHistoryService;

    @PostMapping("AddBrowseHistory")
    public Object addBrowseHistory(@RequestBody Map<String, Object> map) {
        return browseHistoryService.addBrowseHistory(map);
    }

    @PostMapping("GetBrowseHistory")
    public Object getBrowseHistory(@RequestBody Map<String, Object> map) {
        return browseHistoryService.getBrowseHistory(map);
    }
}
