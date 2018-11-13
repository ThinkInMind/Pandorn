package cn.d1m.pandora.entry.output;

import java.util.List;

public class AccountsSearchResponse {
	private List<AccrountsSearchRlt> results;
	private int objectsCount;
	private int totalCount;
	private String statusCode;
	public List<AccrountsSearchRlt> getResults() {
		return results;
	}
	public void setResults(List<AccrountsSearchRlt> results) {
		this.results = results;
	}
	public int getObjectsCount() {
		return objectsCount;
	}
	public void setObjectsCount(int objectsCount) {
		this.objectsCount = objectsCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
