package cn.seddat.macaroni.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.seddat.macaroni.content.entity.Call;

public class DatabaseSupport extends SQLiteOpenHelper {

	private static final String DBNAME = "cn.seddat.macaroni.db";
	private static final int DBVERSION = 100;

	private Context context;

	public DatabaseSupport(Context context) {
		super(context, DBNAME, null, DBVERSION);
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// 通话记录表
		String sql = "create table if not exists " + Call.TABLE_NAME + "( " + Call.COL_ID + " integer primary key, "
				+ Call.COL_DEVICE_ID + " text, " + Call.COL_CALL_NUMBER + " text, " + Call.COL_TYPE + " integer, "
				+ Call.COL_DIAL_TIME + " integer, " + Call.COL_OFFHOOK_TIME + " integer, " + Call.COL_IDLE_TIME
				+ " integer, " + Call.COL_RING_TIMES + " integer, " + Call.COL_CALL_DURATION + " integer, "
				+ Call.COL_CONTACT_INFO + " text, " + Call.COL_UPDATE_TIME + " integer, " + Call.COL_STATUS
				+ " integer ); ";
		arg0.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// roughly
		arg0.execSQL("drop table if exists " + Call.TABLE_NAME);
		this.onCreate(arg0);
	}

}
