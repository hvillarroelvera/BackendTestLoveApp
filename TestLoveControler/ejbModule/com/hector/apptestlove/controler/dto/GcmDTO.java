package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class GcmDTO implements Serializable{
	
	private String Gcm_codGcm;
	private long expirationTime;
	private int appVersion;
	public String getGcm_codGcm() {
		return Gcm_codGcm;
	}
	public void setGcm_codGcm(String gcm_codGcm) {
		Gcm_codGcm = gcm_codGcm;
	}
	public long getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	public int getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(int appVersion) {
		this.appVersion = appVersion;
	}
	
	
}
