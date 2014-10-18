/**
 * 
 */
package cn.seddat.macaroni.content;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author gengmaozhang01
 * @since 2014-10-14 下午11:38:56
 */
public class PhoneStatusReceiver extends BroadcastReceiver {

	private static String tag = PhoneStatusReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		ToastService.toast(context, "call " + intent.getAction());
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		// 去电
		if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Log.i(tag, "call out " + phoneNumber);
			ToastService.toast(context, "call out " + phoneNumber);
		}
		// 来电
		else if (telephony.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
			String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.i(tag, "call in " + phoneNumber);
			ToastService.toast(context, "call in " + phoneNumber);
		}
		// 接通
		else if (telephony.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
			Log.i(tag, "call offhook");
			ToastService.toast(context, "call offhook");
		}
		// 挂断
		else if (telephony.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
			Log.i(tag, "call idle");
			ToastService.toast(context, "call idle");
		}
	}

}
