package com.d1m.social.wechat.pandora.integration.layer.api.controller;

import com.d1m.social.wechat.pandora.integration.layer.api.model.GuestNormalProfile;
import com.d1m.social.wechat.pandora.integration.layer.api.service.GuestNormalProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GuestNormalProfileController {

    private static final Logger log = LoggerFactory.getLogger(GuestNormalProfileController.class);

    @Autowired
    public GuestNormalProfileService guestNormalProfileService;

    @RequestMapping("/GuestNormalProfile/search")
    @ResponseBody
    public GuestNormalProfile search(@RequestBody GuestNormalProfile guestNormalProfile) {
        log.info("/GuestNormalProfile/search>>" + guestNormalProfile.toString());
        return guestNormalProfileService.search(guestNormalProfile);
    }
}
