package deu.hlju.dawn.studentattendance.ui.show_recode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;

public class ShowRecodeActivity extends BaseActivity {

    public static final String RELATION_ROOM_PRO = "relation_room_pro";

    private RelationRoomPro mRelationRoomPro;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_recode;
    }

    @Override
    protected void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mRelationRoomPro = getIntent().getParcelableExtra(RELATION_ROOM_PRO);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AVQuery<AttendanceRecode> query = new AVQuery<>("AttendanceRecode");
                    query.whereEqualTo("relationRoomPro", mRelationRoomPro);
                    List<AttendanceRecode> attendanceRecodes = query.find();
                    for (AttendanceRecode attendanceRecode : attendanceRecodes) {
                        attendanceRecode.getRelationRoomPro().fetchIfNeeded();
                        attendanceRecode.getStudent().fetchIfNeeded();
                        Log.e("aaaa", attendanceRecode.toString());
                    }
                } catch (AVException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, RelationRoomPro relationRoomPro) {
        Intent intent = new Intent(context, ShowRecodeActivity.class);
        intent.putExtra(RELATION_ROOM_PRO, relationRoomPro);
        context.startActivity(intent);
    }
}
