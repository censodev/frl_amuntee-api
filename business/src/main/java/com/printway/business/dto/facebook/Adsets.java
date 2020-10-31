package com.printway.business.dto.facebook;

import lombok.Data;

import java.util.List;

@Data
public class Adsets {
    private List<Adset> data;

    @Data
    public static class Adset {
        private String id;
    }
}
