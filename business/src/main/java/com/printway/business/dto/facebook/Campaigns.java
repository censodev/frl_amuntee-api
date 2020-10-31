package com.printway.business.dto.facebook;

import lombok.Data;

import java.util.List;

@Data
public class Campaigns {
    private List<Campaign> data;

    @Data
    public static class Campaign {
        private String id;
        private String name;
        private Insights insights;
    }
}
