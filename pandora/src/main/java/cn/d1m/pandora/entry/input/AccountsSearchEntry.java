package cn.d1m.pandora.entry.input;

public class AccountsSearchEntry {
	
	private String ApiKey;
	private String userKey;
	private String secret;
	private String query;
	public String getApiKey() {
		return ApiKey;
	}
	public void setApiKey(String apiKey) {
		this.ApiKey = apiKey;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
