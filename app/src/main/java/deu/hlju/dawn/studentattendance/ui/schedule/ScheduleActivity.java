package deu.hlju.dawn.studentattendance.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.ui.show_recode.ShowRecodeActivity;
import deu.hlju.dawn.studentattendance.ui.view.TimeTableView;

public class ScheduleActivity extends BaseActivity implements ScheduleContract.View, TimeTableView.OnTimeTableClickListener {

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
        mTimaTableView.setOnTimeTableClickListener(this);
    }

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
    public void loadSchedule(List<RelationRoomPro> timeTables) {
        mTimaTableView.setTimeTable(timeTables);
    }

    @Override
    public void onClick(View view, RelationRoomPro relationRoomPro) {
        ShowRecodeActivity.start(this, relationRoomPro);
    }
}
