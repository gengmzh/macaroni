/**
 * 
 */
package cn.seddat.macaroni.content.entity;

import java.util.Date;

import android.provider.BaseColumns;

/**
 * @author gengmaozhang01
 * @since 2014-10-18 上午11:00:43
 */
public final class Call implements BaseColumns {

	public static final String TABLE_NAME = "calls";

	public static final String COL_ID = BaseColumns._ID;
	public static final String COL_DEVICE_ID = "did";
	public static final String COL_CALL_NUMBER = "call_number";
	public static final String COL_TYPE = "type";
	public static final String COL_DIAL_TIME = "dial_time";
	public static final String COL_OFFHOOK_TIME = "offhook_time";
	public static final String COL_IDLE_TIME = "idle_time";
	public static final String COL_RING_TIMES = "ring_times";
	public static final String COL_CALL_DURATION = "call_duration";
	public static final String COL_CONTACT_INFO = "contact_info";
	public static final String COL_UPDATE_TIME = "update_time";
	public static final String COL_STATUS = "status";

	public static final int TYPE_CALL_IN = 1, TYPE_CALL_OUT = 2;
	public static final int STATUS_ANSWERED = 1, STATUS_NOT_ANSWERED = 2;

	private long id;
	private String deviceId;
	private String callNumber;
	private int type;
	private Date dialTime;
	private Date offhookTime;
	private Date idleTime;
	private int ringTimes;
	private int callDuration;
	private String contactInfo;
	private Date updateTime;
	private int status;

	public Call() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public Call setId(long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public Call setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getCallNumber() {
		return callNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public Call setCallNumber(String callNumber) {
		this.callNumber = callNumber;
		return this;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public Call setType(int type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the dialTime
	 */
	public Date getDialTime() {
		return dialTime;
	}

	/**
	 * @param dialTime
	 *            the dialTime to set
	 */
	public Call setDialTime(Date dialTime) {
		this.dialTime = dialTime;
		return this;
	}

	/**
	 * @return the offhookTime
	 */
	public Date getOffhookTime() {
		return offhookTime;
	}

	/**
	 * @param offhookTime
	 *            the offhookTime to set
	 */
	public Call setOffhookTime(Date offhookTime) {
		this.offhookTime = offhookTime;
		return this;
	}

	/**
	 * @return the idleTime
	 */
	public Date getIdleTime() {
		return idleTime;
	}

	/**
	 * @param idleTime
	 *            the idleTime to set
	 */
	public Call setIdleTime(Date idleTime) {
		this.idleTime = idleTime;
		return this;
	}

	/**
	 * @return the ringTimes
	 */
	public int getRingTimes() {
		return ringTimes;
	}

	/**
	 * @param ringTimes
	 *            the ringTimes to set
	 */
	public Call setRingTimes(int ringTimes) {
		this.ringTimes = ringTimes;
		return this;
	}

	/**
	 * @return the callDuration
	 */
	public int getCallDuration() {
		return callDuration;
	}

	/**
	 * @param callDuration
	 *            the callDuration to set
	 */
	public Call setCallDuration(int callDuration) {
		this.callDuration = callDuration;
		return this;
	}

	/**
	 * @return the contactInfo
	 */
	public String getContactInfo() {
		return contactInfo;
	}

	/**
	 * @param contactInfo
	 *            the contactInfo to set
	 */
	public Call setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
		return this;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public Call setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public Call setStatus(int status) {
		this.status = status;
		return this;
	}

}
