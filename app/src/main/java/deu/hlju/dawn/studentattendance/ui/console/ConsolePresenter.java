package deu.hlju.dawn.studentattendance.ui.console;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
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
    private FingerprintManager mFingerprintManager;
    private KeyguardManager mKeyguardManager;


    ConsolePresenter(Context context, ConsoleContract.View view) {
        super(context, view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void start() {
        mFingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (isFingerEnable()) {
            view.showCheckPermission();
            view.showMsg(context.getString(R.string.console_please_checkout_finger));
            startListening(null);
        } else {
            view.finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            view.showMsg(context.getString(R.string.console_no_finger_permission));
            view.finish();
            return;
        }
        mFingerprintManager.authenticate(cryptoObject, new CancellationSignal(), 0, new FingerprintManager.AuthenticationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
                view.showMsg(errString.toString());
                view.finish();
            }
            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                view.showMsg(helpString.toString());
            }
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                view.showMsg(context.getString(R.string.console_finger_check_succeed));
                view.hideCheckPermission();
            }
            @Override
            public void onAuthenticationFailed() {
                view.showMsg(context.getString(R.string.console_finger_check_fail));
            }
        }, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isFingerEnable() {
        if (!mFingerprintManager.isHardwareDetected()) {
            view.showMsg(context.getString(R.string.console_no_finger_mode));
            return false;
        }
        if (!mKeyguardManager.isKeyguardSecure()) {
            view.showMsg(context.getString(R.string.console_no_open_keyguard));
            return false;
        }
        if (!mFingerprintManager.hasEnrolledFingerprints()) {
            view.showMsg(context.getString(R.string.console_no_load_finger));
            return false;
        }
        return true;
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
            List<String> data = new ArrayList<>();
            for (Project project : projects) {
                data.add(String.format("课程编号:%s,课程名称:%s", project.getId(), project.getName()));
            }
            view.showListDialog(data);
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
            List<String> data = new ArrayList<>();
            for (Project project : projects) {
                data.add(String.format("教室编号:%s,教室名称:%s", project.getId(), project.getName()));
            }
            view.showListDialog(data);
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
            List<String> data = new ArrayList<>();
            for (Student student : studentListMap.keySet()) {
                for (Project project : studentListMap.get(student)) {
                    data.add(String.format("%s选择课程编号%s的%s", student.getName(), project.getId(), project.getName()));
                }
            }
            view.showListDialog(data);
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
            List<String> data = new ArrayList<>();
            for (RelationRoomPro relationRoomPro : relationRoomPros) {
                data.add(String.format("%s课程在星期%s的第%s至%s节,地址%s", relationRoomPro.getProject().getName(),
                        relationRoomPro.getWeek(), relationRoomPro.getStartNum(), relationRoomPro.getEndNum(),
                        relationRoomPro.getRoom().getName()));
            }
            view.showListDialog(data);
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
