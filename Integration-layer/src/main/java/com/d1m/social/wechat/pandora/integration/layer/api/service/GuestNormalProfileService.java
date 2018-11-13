package com.d1m.social.wechat.pandora.integration.layer.api.service;

import com.d1m.social.wechat.pandora.integration.layer.api.model.GuestNormalProfile;

public interface GuestNormalProfileService {
	public GuestNormalProfile search(GuestNormalProfile guestNormalProfile);
	public GuestNormalProfile searchToken(GuestNormalProfile guestNormalProfile);
}
