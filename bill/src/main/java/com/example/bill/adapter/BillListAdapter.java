package com.example.bill.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bill.BillAddActivity;
import com.example.bill.bean.BillInfo;
import com.example.bill.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZ
 */
public class BillListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{
    private static final String TAG = "BillListAdapter";
    private Context mContext;
    private List<BillInfo> mBillList = new ArrayList<BillInfo>();

    public BillListAdapter(Context context, List<BillInfo> billList) {
        mContext = context;
        mBillList = billList;
    }

    @Override
    public int getCount() {
        return mBillList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill, null);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BillInfo bill = mBillList.get(position);
        holder.tv_date.setText(bill.date);
        holder.tv_desc.setText(bill.desc);
        if (bill.date.equals("Total")) {
            holder.tv_amount.setText(bill.remark);
        } else {
            holder.tv_amount.setText(String.format("%s%d euros", bill.type==0?"Income":"Expense", (int) bill.amount));
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position >= mBillList.size()-1) {
            return;
        }
        Log.d(TAG, "onItemClick position=" + position);
        BillInfo bill = mBillList.get(position);
        // 以下跳转到账单填写页面
        Intent intent = new Intent(mContext, BillAddActivity.class);
        intent.putExtra("xuhao", bill.xuhao);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position >= mBillList.size()-1) {
            return true;
        }
        Log.d(TAG, "onItemLongClick position=" + position);
        BillInfo bill = mBillList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.create().show();
        return true;
    }



    public final class ViewHolder {
        public TextView tv_date;
        public TextView tv_desc;
        public TextView tv_amount;
    }

}
