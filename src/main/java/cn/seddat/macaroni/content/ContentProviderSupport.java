package cn.seddat.macaroni.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;
import cn.seddat.macaroni.content.entity.Call;

/**
 * @author gengmaozhang01
 * @since 2014-10-18 下午4:40:38
 */
public class ContentProviderSupport extends ContentProvider {

	private static final String tag = ContentProviderSupport.class.getSimpleName();

	public static final String AUTHORITY = "cn.seddat.macaroni.provider";
	public static final String PATH_CALL = "calls";
	public static final int CODE_CALL = 1;
	public static final Uri URI_CALL = Uri.parse("content://" + AUTHORITY + "/" + PATH_CALL);

	private static final UriMatcher URI_MATCHER;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		// calls
		URI_MATCHER.addURI(AUTHORITY, PATH_CALL, CODE_CALL);
		URI_MATCHER.addURI(AUTHORITY, PATH_CALL + "/#", CODE_CALL);
	}

	private DatabaseSupport databaseSupport;

	@Override
	public boolean onCreate() {
		this.databaseSupport = new DatabaseSupport(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case CODE_CALL:
			return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_CALL;
		default:
			throw new IllegalArgumentException("unknown Uri " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = databaseSupport.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
		case CODE_CALL: // call
			long id = db.insert(Call.TABLE_NAME, null, values);
			if (id > -1) {
				return Uri.withAppendedPath(URI_CALL, String.valueOf(id));
			} else {
				return null;
			}
		default:
			throw new IllegalArgumentException("unknown Uri " + uri);
		}
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
		int rows = 0;
		final SQLiteDatabase db = databaseSupport.getWritableDatabase();
		db.beginTransaction();
		try {
			switch (URI_MATCHER.match(uri)) {
			case CODE_CALL: // call
				String sql = "insert into " + Call.TABLE_NAME + "(" + Call.COL_DEVICE_ID + "," + Call.COL_CALL_NUMBER
						+ "," + Call.COL_TYPE + "," + Call.COL_DIAL_TIME + "," + Call.COL_OFFHOOK_TIME + ","
						+ Call.COL_IDLE_TIME + "," + Call.COL_RING_TIMES + "," + Call.COL_CALL_DURATION + ","
						+ Call.COL_CONTACT_INFO + "," + Call.COL_UPDATE_TIME + "," + Call.COL_STATUS
						+ ") values(?,?,?,?,?,?,?,?,?,?,?) ";
				final SQLiteStatement stat = db.compileStatement(sql);
				for (ContentValues values : valuesArray) {
					stat.bindString(1, values.getAsString(Call.COL_DEVICE_ID));
					stat.bindString(2, values.getAsString(Call.COL_CALL_NUMBER));
					stat.bindLong(3, values.getAsLong(Call.COL_TYPE));
					stat.bindLong(4, values.getAsLong(Call.COL_DIAL_TIME));
					stat.bindLong(5, values.getAsLong(Call.COL_OFFHOOK_TIME));
					stat.bindLong(6, values.getAsLong(Call.COL_IDLE_TIME));
					stat.bindLong(7, values.getAsLong(Call.COL_RING_TIMES));
					stat.bindLong(8, values.getAsLong(Call.COL_CALL_DURATION));
					stat.bindString(9, values.getAsString(Call.COL_CONTACT_INFO));
					stat.bindLong(10, values.getAsLong(Call.COL_UPDATE_TIME));
					stat.bindLong(11, values.getAsLong(Call.COL_STATUS));
					stat.executeInsert();
					rows++;
				}
				break;
			default:
				throw new IllegalArgumentException("unknown Uri " + uri);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Log.e(tag, "builk insert failed", ex);
		} finally {
			db.endTransaction();
		}
		return rows;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = databaseSupport.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
		case CODE_CALL: // call
			return db.delete(Call.TABLE_NAME, selection, selectionArgs);
		default:
			throw new IllegalArgumentException("unknown Uri " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = databaseSupport.getWritableDatabase();
		switch (URI_MATCHER.match(uri)) {
		case CODE_CALL: // call
			return db.update(Call.TABLE_NAME, values, selection, selectionArgs);
		default:
			throw new IllegalArgumentException("unknown Uri " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = databaseSupport.getReadableDatabase();
		final int type = URI_MATCHER.match(uri);
		switch (type) {
		case CODE_CALL: // call
			return db.query(Call.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("unknown Uri " + uri);
		}
	}

}
