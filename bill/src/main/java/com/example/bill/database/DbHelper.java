package com.example.bill.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
/**
 * @author LQ
 */
@SuppressLint("DefaultLocale")
abstract public class DbHelper extends SQLiteOpenHelper {
	protected static String TAG = "DbHelper";
	protected static String db_name = "bill.sqlite";
	protected Context mContext;
	protected int mVersion;
	protected SQLiteDatabase mReadDB;
	protected SQLiteDatabase mWriteDB;
	protected String mTableName;
	protected String mCreateSQL;
	protected String mSelectSQL;

	public DbHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
		mVersion = version;
		mWriteDB = this.getWritableDatabase();
		mReadDB = this.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		mCreateSQL = "CREATE TABLE IF NOT EXISTS bill_info ("
				+ "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
				+ "date VARCHAR NOT NULL," + "month INTEGER NOT NULL," + "type INTEGER NOT NULL,"
				+ "amount DOUBLE NOT NULL," + "desc VARCHAR NOT NULL,"
				+ "create_time VARCHAR NOT NULL," + "update_time VARCHAR NULL"
				+ ");";
		Log.d(TAG, "create_sql:" + mCreateSQL);
		db.execSQL(mCreateSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}


	abstract protected List<?> query(String sql);

	// 根据序号查询记录
	public List<?> queryById(int id) {
		String sql = " _id=" + id + ";";
		return query(sql);
	}

}
