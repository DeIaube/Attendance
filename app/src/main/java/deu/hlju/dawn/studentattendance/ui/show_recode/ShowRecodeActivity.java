package deu.hlju.dawn.studentattendance.ui.show_recode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.adapter.ShowRecodeAdapter;
import deu.hlju.dawn.studentattendance.base.BaseActivity;
import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;

public class ShowRecodeActivity extends BaseActivity implements ShowRecodeContract.View {

    public static final String RELATION_ROOM_PRO = "relation_room_pro";

    private RelationRoomPro mRelationRoomPro;
    private SwipeRefreshLayout mRecodeSwf;
    private ShowRecodeAdapter mAdapter;
    private ShowRecodeContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_recode;
    }

    @Override
    protected void init() {
        mRelationRoomPro = getIntent().getParcelableExtra(RELATION_ROOM_PRO);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(mRelationRoomPro.getProject().getName());
        }
        mRecodeSwf = findViewById(R.id.recode_srl);
        mRecodeSwf.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRecodeSwf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadRecode(mRelationRoomPro);
            }
        });
        RecyclerView recodeRv = findViewById(R.id.recode_rv);
        recodeRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShowRecodeAdapter(this, new ArrayList<AttendanceRecode>());
        recodeRv.setAdapter(mAdapter);
        mPresenter = new ShowRecodePresenter(this, this);
        mPresenter.loadRecode(mRelationRoomPro);
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

    @Override
    public void showProgress() {
        mRecodeSwf.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRecodeSwf.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadRecode(List<AttendanceRecode> attendanceRecodes) {
        mAdapter.refresh(attendanceRecodes);
    }
}
