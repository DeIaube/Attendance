package deu.hlju.dawn.studentattendance.ui.show_recode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVQuery;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShowRecodePresenter extends ShowRecodeContract.Presenter {

    ShowRecodePresenter(Context context, ShowRecodeContract.View view) {
        super(context, view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadRecode(RelationRoomPro relationRoomPro) {
        view.showProgress();
        Observable.just(relationRoomPro)
                .subscribeOn(Schedulers.io())
                .map(new Function<RelationRoomPro, List<AttendanceRecode>>() {
                    @Override
                    public List<AttendanceRecode> apply(RelationRoomPro relationRoomPro) throws Exception {
                        AVQuery<AttendanceRecode> query = new AVQuery<>("AttendanceRecode");
                        query.whereEqualTo("relationRoomPro", relationRoomPro);
                        List<AttendanceRecode> attendanceRecodes = query.find();
                        for (AttendanceRecode attendanceRecode : attendanceRecodes) {
                            attendanceRecode.getRelationRoomPro().fetchIfNeeded();
                            attendanceRecode.getStudent().fetchIfNeeded();
                        }
                        return attendanceRecodes;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AttendanceRecode>>() {
                    @Override
                    public void accept(List<AttendanceRecode> attendanceRecodes) throws Exception {
                        view.hideProgress();
                        view.loadRecode(attendanceRecodes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.hideProgress();
                        view.showMsg(context.getString(R.string.network_error));
                    }
                });
    }

    @Override
    protected void start() {
    }
}
