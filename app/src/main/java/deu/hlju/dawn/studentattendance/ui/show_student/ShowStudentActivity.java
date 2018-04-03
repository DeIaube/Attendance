package deu.hlju.dawn.studentattendance.ui.show_student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.adapter.ShowStudentAdapter;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.Student;

public class ShowStudentActivity extends BaseActivity implements ShowStudentContract.View {

    private SwipeRefreshLayout mStudentSrl;
    private ShowStudentContract.Presenter mPresenter;
    private ShowStudentAdapter mStudentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_student;
    }

    @Override
    protected void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mStudentSrl = findViewById(R.id.student_srl);
        RecyclerView studentRv = findViewById(R.id.student_rv);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mStudentSrl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mStudentSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadStudentMessage();
            }
        });
        mStudentAdapter = new ShowStudentAdapter(this, new ArrayList<Student>());
        studentRv.setLayoutManager(new LinearLayoutManager(this));
        studentRv.setAdapter(mStudentAdapter);
        mPresenter = new ShowStudentPresenter(this, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0, swipeFlags = 0;
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(ShowStudentActivity.this)
                        .setTitle(getString(R.string.alert))
                        .setMessage(getString(R.string.show_student_remove))
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.deleteStudent(viewHolder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.loadStudentMessage();
                            }
                        })
                        .show();
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        });
        itemTouchHelper.attachToRecyclerView(studentRv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ShowStudentActivity.class));
    }

    @Override
    public void showProgress() {
        mStudentSrl.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mStudentSrl.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadStudentMessage(List<Student> students) {
        mStudentAdapter.refresh(students);
    }

    @Override
    public void deleteStudent(int position) {
        mStudentAdapter.notifyItemRemoved(position);
    }
}
