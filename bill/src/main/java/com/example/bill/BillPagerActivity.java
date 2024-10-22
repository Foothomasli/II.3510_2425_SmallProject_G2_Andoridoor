package com.example.bill;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.bill.adapter.BillPagerAdapter;
import com.example.bill.util.DateUtil;
import com.example.bill.R;

/**
 * @author ZZ
 */
import java.util.Calendar;

public class BillPagerActivity extends AppCompatActivity implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener, ViewPager.OnPageChangeListener {
    private TextView tv_month;
    private ViewPager vp_bill;
    private Calendar calendar = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_month = findViewById(R.id.tv_month);
        tv_title.setText("Bill List");
        tv_option.setText("Add Bill");
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_option.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        tv_month.setText(DateUtil.getMonth(calendar));
        vp_bill = findViewById(R.id.vp_bill);
        initViewPager(); // 初始化翻页视图
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_option) {
            Intent intent = new Intent(this, BillAddActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 设置启动标志
            startActivity(intent);
        } else if (v.getId() == R.id.tv_month) {
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_month.setText(DateUtil.getMonth(calendar));
        vp_bill.setCurrentItem(month); // 设置翻页视图显示第几页
    }

    // 初始化翻页视图
    private void initViewPager() {
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(), calendar.get(Calendar.YEAR));
        vp_bill.setAdapter(adapter);
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
        vp_bill.addOnPageChangeListener(this);
    }


    public void onPageScrollStateChanged(int state) {}

    public void onPageScrolled(int position, float ratio, int offset) {}

    public void onPageSelected(int position) {
        calendar.set(Calendar.MONTH, position);
        tv_month.setText(DateUtil.getMonth(calendar));
    }
}
