package com.printway.business.services;

import com.printway.common.auth.User;
import org.springframework.data.domain.Page;

public interface AccountService {
    Page<User> listAccount();
}
