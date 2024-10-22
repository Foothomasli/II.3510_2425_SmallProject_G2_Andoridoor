package com.example.bill.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.bill.bean.BillInfo;
import com.example.bill.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LQ
 */
public class BillDBHelper extends DbHelper {
    private static String table_name = "bill_info";

    public BillDBHelper(Context context) {
        super(context, db_name, null, 1);
        mTableName = table_name;
        mSelectSQL = String.format("select rowid,_id,date,month,type,amount,desc,create_time,update_time from %s where "
                , mTableName);
    }

    private static BillDBHelper mHelper = null;
    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }


    public long insert(BillInfo info) {
        List<BillInfo> infoList = new ArrayList<BillInfo>();
        infoList.add(info);
        return insert(infoList);
    }

    public long insert(List<BillInfo> infoList) {
        long result = -1;
        for (int i = 0; i < infoList.size(); i++) {
            BillInfo info = infoList.get(i);
            List<BillInfo> tempList = new ArrayList<BillInfo>();
            ContentValues cv = new ContentValues();
            cv.put("date", info.date);
            cv.put("month", info.month);
            cv.put("type", info.type);
            cv.put("amount", info.amount);
            cv.put("desc", info.desc);
            cv.put("create_time", info.create_time);
            cv.put("update_time", info.update_time);
            Log.d(TAG, "month="+info.month);
            result = mWriteDB.insert(mTableName, "", cv);
            if (result == -1) {
                return result;
            }
        }
        Log.d(TAG, "result="+result);
        return result;
    }


    public int update(BillInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("date", info.date);
        cv.put("month", info.month);
        cv.put("type", info.type);
        cv.put("amount", info.amount);
        cv.put("desc", info.desc);
        cv.put("create_time", info.create_time);
        cv.put("update_time", info.update_time);
        return mWriteDB.update(mTableName, cv, condition, null);
    }

    public int update(BillInfo info) {
        return update(info, "rowid=" + info.rowid);
    }

    public List<BillInfo> query(String condition) {
        String sql = mSelectSQL + condition;
        Log.d(TAG, "query sql: " + sql);
        List<BillInfo> infoList = new ArrayList<BillInfo>();
        Cursor cursor = mReadDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            BillInfo info = new BillInfo();
            info.rowid = cursor.getLong(0);
            info.xuhao = cursor.getInt(1);
            info.date = cursor.getString(2);
            info.month = cursor.getInt(3);
            info.type = cursor.getInt(4);
            info.amount = cursor.getDouble(5);
            info.desc = cursor.getString(6);
            info.create_time = cursor.getString(7);
            info.update_time = cursor.getString(8);
            infoList.add(info);
        }
        cursor.close();
        Log.d(TAG, "infoList.size="+infoList.size());
        return infoList;
    }

    public List<BillInfo> queryByMonth(int month) {
        return query("month="+month+" order by date asc");
    }

    public void save(BillInfo bill) {

        List<BillInfo> bill_list = (List<BillInfo>) queryById(bill.xuhao);
        BillInfo info = null;
        if (bill_list.size() > 0) {
            info = bill_list.get(0);
        }
        if (info != null) {
            bill.rowid = info.rowid;
            bill.create_time = info.create_time;
            bill.update_time = DateUtil.getNowDateTime("");
            update(bill); // 更新数据库记录
        } else {
            bill.create_time = DateUtil.getNowDateTime("");
            insert(bill);
        }
    }
}
