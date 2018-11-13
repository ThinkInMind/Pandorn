package cn.d1m.pandora.entry.output;

public class AccrountsSearchRlt {
	private String UID;
	private Profile profile;
	private String isActive;
	private String socialProviders;
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getSocialProviders() {
		return socialProviders;
	}
	
	public void setSocialProviders(String socialProviders) {
		this.socialProviders = socialProviders;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
