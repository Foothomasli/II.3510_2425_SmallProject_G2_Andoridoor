package com.example.bill;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bill.bean.BillInfo;
import com.example.bill.database.BillDBHelper;
import com.example.bill.util.DateUtil;
import com.example.bill.util.ViewUtil;
import com.example.bill.R;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author ZZ
 */
public class BillAddActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private final static String TAG = "BillAddActivity";
    private TextView tv_date;
    private RadioButton rb_income;
    private RadioButton rb_expand;
    private EditText et_desc;
    private EditText et_amount;
    private int mBillType = 1; // 账单类型。0 收入；1 支出
    private int xuhao; // 如果序号有值，说明已存在该账单
    private Calendar calendar = Calendar.getInstance();
    private BillDBHelper mBillHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_date = findViewById(R.id.tv_date);
        RadioGroup rg_type = findViewById(R.id.rg_type);
        rb_income = findViewById(R.id.rb_income);
        rb_expand = findViewById(R.id.rb_expand);
        et_desc = findViewById(R.id.et_desc);
        et_amount = findViewById(R.id.et_amount);
        tv_title.setText("Please fix the bill");
        tv_option.setText("Bill List");
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_option.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        rg_type.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        xuhao = getIntent().getIntExtra("xuhao", -1);
        mBillHelper = BillDBHelper.getInstance(this); // 获取账单数据库的帮助器对象
        if (xuhao != -1) { // 序号有值，就展示数据库里的账单详情
            List<BillInfo> bill_list = (List<BillInfo>) mBillHelper.queryById(xuhao);
            if (bill_list.size() > 0) { // 已存在该账单
                BillInfo bill = bill_list.get(0); // 获取账单信息
                Date date = DateUtil.formatString(bill.date);
                Log.d(TAG, "bill.date="+bill.date);
                Log.d(TAG, "year="+date.getYear()+",month="+date.getMonth()+",day="+date.getDate());
                calendar.set(Calendar.YEAR, date.getYear()+1900);
                calendar.set(Calendar.MONTH, date.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, date.getDate());
                if (bill.type == 0) {
                    rb_income.setChecked(true);
                } else {
                    rb_expand.setChecked(true);
                }
                et_desc.setText(bill.desc);
                et_amount.setText(""+bill.amount);
            }
        }
        tv_date.setText(DateUtil.getDate(calendar));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_option) {
            Intent intent = new Intent(this, BillPagerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_date) {
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (v.getId() == R.id.btn_save) {
            saveBill();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mBillType = (checkedId==R.id.rb_expand) ? 1 : 0;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(DateUtil.getDate(calendar));
    }

    // 保存账单
    private void saveBill() {
        ViewUtil.hideOneInputMethod(this, et_amount);
        BillInfo bill = new BillInfo();
        bill.xuhao = xuhao;
        bill.date = tv_date.getText().toString();
        bill.month = 100*calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH)+1);
        bill.type = mBillType;
        bill.desc = et_desc.getText().toString();
        bill.amount = Double.parseDouble(et_amount.getText().toString());
        mBillHelper.save(bill); // 把账单信息保存到数据库
        Toast.makeText(this, "Already add to bill list", Toast.LENGTH_SHORT).show();
        resetPage();
    }

    // 重置页面
    private void resetPage() {
        calendar = Calendar.getInstance();
        et_desc.setText("");
        et_amount.setText("");
        tv_date.setText(DateUtil.getDate(calendar));
    }
    
}
