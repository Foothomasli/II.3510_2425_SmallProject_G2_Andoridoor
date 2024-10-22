package com.example.bill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.bill.adapter.BillListAdapter;
import com.example.bill.bean.BillInfo;
import com.example.bill.database.BillDBHelper;
import com.example.bill.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZ
 */
public class BillFragment extends Fragment {
    protected View mView;
    protected Context mContext;
    private int mMonth;
    private ListView lv_bill;
    private List<BillInfo> mBillList = new ArrayList<BillInfo>();

    public static BillFragment newInstance(int month) {
        BillFragment fragment = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    // 创建碎片视图
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        if (getArguments() != null) {
            mMonth = getArguments().getInt("month", 1);
        }
        mView = inflater.inflate(R.layout.fragment_bill, container, false);
        lv_bill = mView.findViewById(R.id.lv_bill);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        BillDBHelper helper = BillDBHelper.getInstance(mContext);
        mBillList = helper.queryByMonth(mMonth);
        if (mBillList!=null && mBillList.size()>0) {
            double income=0, expend=0;
            for (BillInfo bill : mBillList) {
                if (bill.type == 0) {
                    income += bill.amount;
                } else {
                    expend += bill.amount;
                }
            }
            BillInfo sum = new BillInfo();
            sum.date = "Total";
            sum.desc = String.format("Income%d euros \n Cost %d euros", (int) income, (int) expend);
            sum.remark = String.format("Balance %d euros", (int) (income-expend));
            mBillList.add(sum);
        }

        BillListAdapter listAdapter = new BillListAdapter(mContext, mBillList);
        lv_bill.setAdapter(listAdapter);
        lv_bill.setOnItemClickListener(listAdapter);
        lv_bill.setOnItemLongClickListener(listAdapter);
    }


}
