package deu.hlju.dawn.studentattendance.ui.schedule;

import android.annotation.SuppressLint;
import android.content.Context;

import com.avos.avoscloud.AVQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class SchedulePresenter extends ScheduleContract.Presenter {

    public SchedulePresenter(Context context, ScheduleContract.View view) {
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
                        AVQuery<Room> roomAVQuery = new AVQuery<>("Room");
                        List<Room> rooms = roomAVQuery.find();
                        Map<String, Room> quickRoomMap = new HashMap<>();
                        for (Room room : rooms) {
                            quickRoomMap.put(room.getId(), room);
                        }
                        AVQuery<Project> projectAVQuery = new AVQuery<>("Project");
                        List<Project> projects = projectAVQuery.find();
                        Map<String, Project> quickProjectMap = new HashMap<>();
                        for (Project project : projects) {
                            quickProjectMap.put(project.getId(), project);
                        }
                        AVQuery<RelationRoomPro> roomProAVQuery = new AVQuery<>("RelationRoomPro");
                        List<RelationRoomPro> relations = roomProAVQuery.find();
                        for (RelationRoomPro relation : relations) {
                            Room room = quickRoomMap.get(relation.getRoomtId());
                            Project project = quickProjectMap.get(relation.getProjectId());
                            if (room != null && project != null) {
                                relation.setProjectName(project.getName());
                                relation.setRoomName(room.getName());
                            }
                        }
                        return relations;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RelationRoomPro>>() {
                    @Override
                    public void accept(List<RelationRoomPro> timeTableModelList) throws Exception {
                        view.hideProgress();
                        view.loadSchedule(timeTableModelList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.hideProgress();
                        view.showMsg(context.getString(R.string.network_error));
                    }
                });
    }
}
