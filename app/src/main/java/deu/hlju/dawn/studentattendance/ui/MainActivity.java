package deu.hlju.dawn.studentattendance.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import deu.hlju.dawn.studentattendance.ui.console.ConsoleActivity;
import deu.hlju.dawn.studentattendance.ui.camera.CameraActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn8 = new Button(this);
        btn8.setText("摄像头预览");
        ll.addView(btn8);

        Button btn10 = new Button(this);
        btn10.setText("跳转控制台");
        ll.addView(btn10);

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.start(MainActivity.this);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsoleActivity.start(MainActivity.this);
            }
        });

    }

}
