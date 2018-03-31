package deu.hlju.dawn.studentattendance.ui.Console;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.RelationStuPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ConsolePresenter extends ConsoleContract.Presenter {

    private List<Student> students;
    private List<Project> projects;
    private List<Room> rooms;
    private List<RelationRoomPro> relationRoomPros;
    private List<RelationStuPro> relationStuPros;

    public ConsolePresenter(Context context, ConsoleContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {
    }

    @Override
    protected void loadData() {
        final AVQuery<Student> studentAVQuery = new AVQuery<>("Student");
        final AVQuery<Project> projectAVQuery = new AVQuery<>("Project");
        final AVQuery<Room> roomAVQuery = new AVQuery<>("Room");
        final AVQuery<RelationRoomPro> relationRoomProAVQuery = new AVQuery<>("RelationRoomPro");
        final AVQuery<RelationStuPro> relationStuProAVQuery = new AVQuery<>("RelationStuPro");
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        students = studentAVQuery.find();
                        rooms = roomAVQuery.find();
                        projects = projectAVQuery.find();
//                        relationRoomPros = relationRoomProAVQuery.find();
//                        relationStuPros = relationStuProAVQuery.find();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showMsg(throwable.toString());
                        view.hideProgress();
                    }
                });
    }

    @Override
    protected void showProject() {
        if (projects != null) {
            view.showProject(projects);
        }
    }

    @Override
    protected void addRoom(String id, String name) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)) {
            view.showMsg(context.getString(R.string.console_project_name_id_empty));
            return;
        }
        view.showProgress();
        final Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    view.showMsg(context.getString(R.string.seccess));
                } else {
                    view.showMsg(context.getString(R.string.network_error));
                }
                loadData();
            }
        });
    }

    @Override
    protected void showRoom() {
        if (rooms != null) {
            view.showRoom(rooms);
        }
    }

    @Override
    protected void addProject(String id, String name) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)) {
            view.showMsg(context.getString(R.string.console_project_name_id_empty));
            return;
        }
        view.showProgress();
        final Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    view.showMsg(context.getString(R.string.seccess));
                } else {
                    view.showMsg(context.getString(R.string.network_error));
                }
                loadData();
            }
        });
    }

}
