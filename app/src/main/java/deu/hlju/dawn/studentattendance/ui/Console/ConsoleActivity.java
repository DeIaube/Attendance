package deu.hlju.dawn.studentattendance.ui.Console;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.ui.MainActivity;

public class ConsoleActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_console;
    }

    @Override
    protected void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void click(View view) {
        if (view.getId() == R.id.btn1) {
            // 增加课程
            final EditText nameEt = new EditText(this);
            final EditText idEt = new EditText(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(idEt);
            layout.addView(nameEt);
            new AlertDialog.Builder(this)
                    .setView(layout)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = nameEt.getText().toString();
                            String id = idEt.getText().toString();
                            final Project project = new Project();
                            project.setName(name);
                            project.setId(id);
                            project.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e != null) {
                                        Log.i("test", e.toString());
                                    } else {
                                        Log.i("test", project.toString());
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        } else if (view.getId() == R.id.btn2) {
            // 查看课程
            AVQuery<Project> query = new AVQuery<>("project");
            query.findInBackground(new FindCallback<Project>() {
                @Override
                public void done(List<Project> list, AVException e) {
                    if (e != null) {
                        Log.i("test", e.toString());
                    } else {
                        Log.i("test", list.toString());
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ConsoleActivity.class));
    }
}
