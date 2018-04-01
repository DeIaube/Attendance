package deu.hlju.dawn.studentattendance.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.TimeTableModel;
import deu.hlju.dawn.studentattendance.ui.view.TimeTableView;

public class ScheduleActivity extends BaseActivity implements ScheduleContract.View {

    private TimeTableView mTimaTableView;
    private SwipeRefreshLayout mScheduleSrl;
    private ScheduleContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void init() {
        mTimaTableView = findViewById(R.id.timetable_ttv);
        mScheduleSrl = findViewById(R.id.schedule_srl);
        mScheduleSrl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mScheduleSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadSchedule();
            }
        });
        mPresenter = new SchedulePresenter(this, this);
    }

//    private void addList() {
//        mList.add(new TimeTableModel(1, 2,1,"财务报表分析", "3-105"));
//        mList.add(new TimeTableModel(3, 4,1,"审计实务", "3-444"));
//        mList.add(new TimeTableModel(5, 6,1,"财务管理实务", "3-231"));
//        mList.add(new TimeTableModel(2, 3,2,"财务管理实务", "3-543"));
//        mList.add(new TimeTableModel(5, 8,2,"审计实务", "3-333"));
//    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ScheduleActivity.class));
    }

    @Override
    public void showProgress() {
        mScheduleSrl.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mScheduleSrl.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSchedule(List<TimeTableModel> timeTables) {
        mTimaTableView.setTimeTable(timeTables);
    }
}
