package deu.hlju.dawn.studentattendance.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import deu.hlju.dawn.studentattendance.ui.console.ConsoleActivity;
import deu.hlju.dawn.studentattendance.ui.camera.CameraActivity;
import deu.hlju.dawn.studentattendance.ui.schedule.ScheduleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn1 = new Button(this);
        btn1.setText("考勤");
        ll.addView(btn1);

        Button btn2 = new Button(this);
        btn2.setText("查看课程表");
        ll.addView(btn2);


        Button btn3 = new Button(this);
        btn3.setText("跳转控制台");
        ll.addView(btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.start(MainActivity.this);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleActivity.start(MainActivity.this);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsoleActivity.start(MainActivity.this);
            }
        });

    }

}
