package deu.hlju.dawn.studentattendance.ui.schedule;

import android.annotation.SuppressLint;
import android.content.Context;

import com.avos.avoscloud.AVQuery;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class SchedulePresenter extends ScheduleContract.Presenter {

    SchedulePresenter(Context context, ScheduleContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {
        loadSchedule();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadSchedule() {
        view.showProgress();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<RelationRoomPro>>() {
                    @Override
                    public List<RelationRoomPro> apply(String s) throws Exception {
                        AVQuery<RelationRoomPro> roomProAVQuery = new AVQuery<>("RelationRoomPro");
                        List<RelationRoomPro> relations = roomProAVQuery.find();
                        for (RelationRoomPro relation : relations) {
                            relation.getRoom().fetchIfNeeded();
                            relation.getProject().fetchIfNeeded();
                        }
                        return relations;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RelationRoomPro>>() {
                    @Override
                    public void accept(List<RelationRoomPro> timeTableModelList) {
                        view.hideProgress();
                        view.loadSchedule(timeTableModelList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.hideProgress();
                        view.showMsg(context.getString(R.string.network_error));
                    }
                });
    }
}
