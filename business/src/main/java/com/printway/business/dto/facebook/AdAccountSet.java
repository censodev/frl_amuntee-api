package com.printway.business.dto.facebook;

import lombok.Data;

import java.util.List;

@Data
public class AdAccountSet {
    private AdAccounts adaccounts;

    @Data
    public static class AdAccounts {
        private List<AdAccount> data;
    }
}
