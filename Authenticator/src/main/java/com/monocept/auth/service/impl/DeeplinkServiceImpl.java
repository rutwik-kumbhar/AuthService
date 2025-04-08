package com.monocept.auth.service.impl;

import com.monocept.auth.service.DeeplinkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class DeeplinkServiceImpl  implements DeeplinkService {


    @Value("${deeplink.base.url}")
    private String baseDeeplinkUrl;

    @Override
    public String createDeeplinkUrl(String token) {
        return  baseDeeplinkUrl + "token=" + token.substring(7);
    }
}
