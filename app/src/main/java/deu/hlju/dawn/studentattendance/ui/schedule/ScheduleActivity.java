package deu.hlju.dawn.studentattendance.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.TimeTableModel;
import deu.hlju.dawn.studentattendance.ui.view.TimeTableView;

public class ScheduleActivity extends AppCompatActivity {

    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mList = new ArrayList<>();
        mTimaTableView = findViewById(R.id.timetable_ttv);
        addList();
        mTimaTableView.setTimeTable(mList);
    }

    private void addList() {
        mList.add(new TimeTableModel(1, 2,1,"财务报表分析",
                "王老师"));
        mList.add(new TimeTableModel(3, 4,1,"审计实务",
                "李老师"));
        mList.add(new TimeTableModel(5, 6,1,"市场营销实务",
                "王"));
        mList.add(new TimeTableModel(2, 3,2,"财务管理实务",
                "老师1"));
        mList.add(new TimeTableModel(5, 8,2,"审计实务",
                "老师2"));

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ScheduleActivity.class));
    }
}