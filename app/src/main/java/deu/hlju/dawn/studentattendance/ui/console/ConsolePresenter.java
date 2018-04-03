package deu.hlju.dawn.studentattendance.ui.console;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseException;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.RelationStuPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.exception.ProjectNotExistException;
import deu.hlju.dawn.studentattendance.exception.RoomNotExistException;
import deu.hlju.dawn.studentattendance.exception.StudentNotExistException;
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

    ConsolePresenter(Context context, ConsoleContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {
    }

    @SuppressLint("CheckResult")
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
                        relationStuPros = relationStuProAVQuery.find();
                        for (RelationStuPro relationStuPro : relationStuPros) {
                            relationStuPro.getProject().fetchIfNeeded();
                            relationStuPro.getStudent().fetchIfNeeded();
                        }
                        relationRoomPros = relationRoomProAVQuery.find();
                        for (RelationRoomPro relationRoomPro : relationRoomPros) {
                            relationRoomPro.getProject().fetchIfNeeded();
                            relationRoomPro.getRoom().fetchIfNeeded();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        view.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
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

    @SuppressLint("CheckResult")
    @Override
    protected void addRelationStuPro(final String studentId, final String projectId) {
        if (TextUtils.isEmpty(studentId) || TextUtils.isEmpty(studentId)) {
            view.showMsg(context.getString(R.string.console_project_id_empty));
            return;
        }
        view.showProgress();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        AVQuery<Student> studentAVQuery = new AVQuery<>("Student");
                        studentAVQuery.whereEqualTo("id", studentId);
                        Student student = studentAVQuery.getFirst();
                        AVQuery<Project> projectAVQuery = new AVQuery<>("Project");
                        projectAVQuery.whereEqualTo("id", projectId);
                        Project project = projectAVQuery.getFirst();
                        if (student == null) {
                            throw new StudentNotExistException();
                        }
                        if (project == null) {
                            throw new ProjectNotExistException();
                        }
                        RelationStuPro relationStuPro = new RelationStuPro();
                        relationStuPro.setStudent(student);
                        relationStuPro.setProject(project);
                        relationStuPro.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        loadData();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.hideProgress();
                        if (throwable instanceof BaseException) {
                            view.showMsg(throwable.toString());
                        } else {
                            view.showMsg(context.getString(R.string.network_error));
                        }
                    }
                });
    }

    @Override
    protected void showRelationStuPro() {
        if (relationStuPros != null && students != null) {
            Map<Student, Set<Project>> studentListMap = new HashMap<>();
            for (RelationStuPro stuPro : relationStuPros) {
                Student student = stuPro.getStudent();
                Project project = stuPro.getProject();
                if (student != null && project != null) {
                    Set<Project> projects = studentListMap.get(student);
                    if (projects == null) {
                        projects = new HashSet<>();
                    }
                    projects.add(project);
                    studentListMap.put(student, projects);
                }
            }
            view.shwoRelationStuPro(studentListMap);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void addRelationRoomPro(final String roomId, final String projectId, final String week, final String startNum, final String endNum) {
        if (TextUtils.isEmpty(roomId) || TextUtils.isEmpty(projectId) || TextUtils.isEmpty(week) ||
                TextUtils.isEmpty(startNum) || TextUtils.isEmpty(endNum)) {
            view.showMsg(context.getString(R.string.console_project_id_empty));
            return;
        }
        int w = Integer.valueOf(week);
        int s = Integer.valueOf(startNum);
        int e = Integer.valueOf(endNum);
        if (w < 1 || w > 7 || s >= e || s < 1 || e > 10) {
            view.showMsg(context.getString(R.string.console_project_time_error));
            return;
        }
        view.showProgress();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        AVQuery<Room> roomAVQuery = new AVQuery<>("Room");
                        roomAVQuery.whereEqualTo("id", roomId);
                        Room room = roomAVQuery.getFirst();
                        AVQuery<Project> projectAVQuery = new AVQuery<>("Project");
                        projectAVQuery.whereEqualTo("id", projectId);
                        Project project = projectAVQuery.getFirst();
                        if (project == null) {
                            throw new ProjectNotExistException();
                        }
                        if (room == null) {
                            throw new RoomNotExistException();
                        }
                        RelationRoomPro roomPro = new RelationRoomPro();
                        roomPro.setRoom(room);
                        roomPro.setProject(project);
                        roomPro.setWeek(week);
                        roomPro.setStartNum(startNum);
                        roomPro.setEndNum(endNum);
                        roomPro.save();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        loadData();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.hideProgress();
                        if (throwable instanceof BaseException) {
                            view.showMsg(throwable.toString());
                        } else {
                            view.showMsg(context.getString(R.string.network_error));
                        }
                    }
                });
    }

    @Override
    protected void showRelationRoomPro() {
        if (relationRoomPros != null) {
            view.shwoRelationRoomPro(relationRoomPros);
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
