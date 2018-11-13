package com.d1m.social.wechat.pandora.integration.layer.api.dao;

import org.springframework.stereotype.Repository;

import com.d1m.social.wechat.pandora.integration.layer.api.model.GuestNormalProfile;

@Repository("guestNormalProfileMapper")
public interface GuestNormalProfileMapper {
	
	GuestNormalProfile search(GuestNormalProfile guestNormalProfile);
	GuestNormalProfile searchToken(GuestNormalProfile guestNormalProfile);
}
