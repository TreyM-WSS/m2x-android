/**
 * 
 */
package com.att.m2x;

/**
 * @author leandrinux
 *
 */
public final class M2X {

	public static String VERSION = "1.0";
	private static String DEFAULT_BASE_URL = "http://api-m2x.att.citrusbyte.com/v1";
	private static M2X mInstance = null;
	private static M2XHttpClient client = new M2XHttpClient();
	
	private String baseUrl;

	private M2X() {
		baseUrl = DEFAULT_BASE_URL;
	}
	
	public String getMasterKey() {
		return client.getMasterKey();
	}

	public void setMasterKey(String masterKey) {
		client.setMasterKey(masterKey);
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public M2XHttpClient getClient() {
		return client;
	}

	public static M2X getInstance() {
		if (mInstance == null) {
			mInstance = new M2X();
		}
		return mInstance;
	}

	
}
