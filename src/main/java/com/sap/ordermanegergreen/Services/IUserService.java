package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    User getUser();
}
