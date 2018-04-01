package deu.hlju.dawn.studentattendance.ui.Console;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.ui.MainActivity;
import deu.hlju.dawn.studentattendance.ui.add_student.AddStudnetActivity;
import deu.hlju.dawn.studentattendance.ui.show_student.ShowStudentActivity;

public class ConsoleActivity extends BaseActivity implements ConsoleContract.View {

    private ConsoleContract.Presenter mPresenter;
    private SwipeRefreshLayout mConsoleSfl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_console;
    }

    @Override
    protected void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mConsoleSfl = findViewById(R.id.console_srl);
        mConsoleSfl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mConsoleSfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mPresenter = new ConsolePresenter(this, this);
    }

    public void click(View view) {
        if (view.getId() == R.id.btn1) {
            // 增加课程
            final EditText nameEt = new EditText(this);
            nameEt.setHint("课程id");
            final EditText idEt = new EditText(this);
            idEt.setHint("课程名称");
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(idEt);
            layout.addView(nameEt);
            new AlertDialog.Builder(this)
                    .setView(layout)
                    .setTitle(R.string.alert)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = nameEt.getText().toString();
                            String id = idEt.getText().toString();
                            mPresenter.addProject(id, name);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        } else if (view.getId() == R.id.btn2) {
            // 查看课程
            mPresenter.showProject();
        } else if (view.getId() == R.id.btn3) {
            // 增加学生
            AddStudnetActivity.start(this);
        } else if (view.getId() == R.id.btn4) {
            // 查看学生
            ShowStudentActivity.start(this);
        } else if (view.getId() == R.id.btn5) {
            // 增加教室
            final EditText nameEt = new EditText(this);
            nameEt.setHint("教室id");
            final EditText idEt = new EditText(this);
            idEt.setHint("教室名称");
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(idEt);
            layout.addView(nameEt);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert)
                    .setView(layout)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = nameEt.getText().toString();
                            String id = idEt.getText().toString();
                            mPresenter.addRoom(id, name);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        } else if (view.getId() == R.id.btn6) {
            // 查看教室
            mPresenter.showRoom();
        } else if (view.getId() == R.id.btn7) {
            // 增加课程
            final EditText projectEt = new EditText(this);
            projectEt.setHint("课程id");
            final EditText studentEt = new EditText(this);
            studentEt.setHint("学生id");
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(projectEt);
            layout.addView(studentEt);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert)
                    .setView(layout)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String projectId = projectEt.getText().toString();
                            String studentId = studentEt.getText().toString();
                            mPresenter.addRelationStuPro(studentId, projectId);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        } else if (view.getId() == R.id.btn8) {
            // 查看课程
            mPresenter.showRelationStuPro();
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

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void showProgress() {
        mConsoleSfl.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mConsoleSfl.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProject(List<Project> projects) {
        Log.i("test", projects.toString());
    }

    @Override
    public void showRoom(List<Room> rooms) {
        Log.i("test", rooms.toString());
    }

    @Override
    public void shwoRelationStuPro(Map<Student, Set<Project>> data) {
        for (Student student : data.keySet()) {
            Log.i("test", student.getName() + " : " + data.get(student).toString());
        }
    }
}
