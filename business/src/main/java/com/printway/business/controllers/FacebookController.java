package com.printway.business.controllers;

import com.printway.business.dto.facebook.AdAccounts;
import com.printway.business.services.FacebookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/facebook")
@Slf4j
public class FacebookController {
    @Autowired
    private FacebookService facebookService;

    @GetMapping("adaccounts")
    public List<AdAccounts> listAccount() throws Exception {
        return facebookService.fetchAdAccounts();
    }

}
