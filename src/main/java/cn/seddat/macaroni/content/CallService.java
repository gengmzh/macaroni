/**
 * 
 */
package cn.seddat.macaroni.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import cn.seddat.macaroni.content.entity.Call;

/**
 * @author gengmaozhang01
 * @since 2014-10-18 下午5:06:33
 */
public class CallService {

	private ContentResolver contentResolver;

	public CallService(Context context) {
		super();
		this.contentResolver = context.getContentResolver();
	}

	public void save(Call call) {
		if (call == null) {
			throw new IllegalArgumentException("call is requied");
		}
		ContentValues values = new ContentValues();
		values.put(Call.COL_DEVICE_ID, call.getDeviceId());
		values.put(Call.COL_CALL_NUMBER, call.getCallNumber());
		values.put(Call.COL_TYPE, call.getType());
		values.put(Call.COL_DIAL_TIME, call.getDialTime() != null ? call.getDialTime().getTime() / 1000 : 0);
		values.put(Call.COL_OFFHOOK_TIME, call.getOffhookTime() != null ? call.getOffhookTime().getTime() / 1000 : 0);
		values.put(Call.COL_IDLE_TIME, call.getIdleTime() != null ? call.getIdleTime().getTime() / 1000 : 0);
		values.put(Call.COL_RING_TIMES, call.getRingTimes());
		values.put(Call.COL_CALL_DURATION, call.getCallDuration());
		values.put(Call.COL_CONTACT_INFO, call.getContactInfo());
		values.put(Call.COL_UPDATE_TIME, call.getUpdateTime() != null ? call.getUpdateTime().getTime() / 1000 : 0);
		values.put(Call.COL_STATUS, call.getStatus());
		if (call.getId() > 0) {
			this.contentResolver.update(ContentProviderSupport.URI_CALL, values, Call.COL_ID + "=?",
					new String[] { String.valueOf(call.getId()) });
		} else {
			this.contentResolver.insert(ContentProviderSupport.URI_CALL, values);
		}
	}

	public List<Call> query(String deviceId, String callNumber, Date startTime, Date endTime) {
		if (deviceId == null) {
			throw new IllegalArgumentException("deviceId is requied");
		}
		List<Call> calls = new ArrayList<Call>();
		// query
		String where = Call.COL_DEVICE_ID + "=?";
		List<String> args = new ArrayList<String>();
		args.add(deviceId);
		if (callNumber != null && callNumber.length() > 0) {
			where += " and " + Call.COL_CALL_NUMBER + "=?";
			args.add(callNumber);
		}
		if (startTime != null) {
			where += " and " + Call.COL_IDLE_TIME + ">=?";
			args.add(String.valueOf(startTime.getTime() / 1000));
		}
		if (endTime != null) {
			where += " and " + Call.COL_IDLE_TIME + "<=?";
			args.add(String.valueOf(endTime.getTime() / 1000));
		}
		Cursor cursor = this.contentResolver.query(ContentProviderSupport.URI_CALL, null, where,
				args.toArray(new String[args.size()]), "ORDER BY " + Call.COL_DIAL_TIME + " desc");
		// parse
		if (cursor.moveToFirst()) {
			do {
				Call call = new Call();
				call.setId(cursor.getLong(cursor.getColumnIndex(Call.COL_ID)));
				call.setDeviceId(cursor.getString(cursor.getColumnIndex(Call.COL_DEVICE_ID)));
				call.setCallNumber(cursor.getString(cursor.getColumnIndex(Call.COL_CALL_NUMBER)));
				call.setType(cursor.getInt(cursor.getColumnIndex(Call.COL_TYPE)));
				call.setDialTime(new Date(cursor.getLong(cursor.getColumnIndex(Call.COL_DIAL_TIME)) * 1000));
				call.setOffhookTime(new Date(cursor.getLong(cursor.getColumnIndex(Call.COL_OFFHOOK_TIME)) * 1000));
				call.setIdleTime(new Date(cursor.getLong(cursor.getColumnIndex(Call.COL_IDLE_TIME)) * 1000));
				call.setRingTimes(cursor.getInt(cursor.getColumnIndex(Call.COL_RING_TIMES)));
				call.setCallDuration(cursor.getInt(cursor.getColumnIndex(Call.COL_CALL_DURATION)));
				call.setContactInfo(cursor.getString(cursor.getColumnIndex(Call.COL_CONTACT_INFO)));
				call.setUpdateTime(new Date(cursor.getLong(cursor.getColumnIndex(Call.COL_UPDATE_TIME)) * 1000));
				call.setStatus(cursor.getInt(cursor.getColumnIndex(Call.COL_STATUS)));
				calls.add(call);
			} while (cursor.moveToNext());
		}
		return calls;
	}

}
