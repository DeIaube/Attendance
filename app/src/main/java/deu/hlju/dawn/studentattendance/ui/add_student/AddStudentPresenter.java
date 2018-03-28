package deu.hlju.dawn.studentattendance.ui.add_student;

import android.content.Context;
import android.text.TextUtils;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseException;
import deu.hlju.dawn.studentattendance.bean.FaceDetectResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetAddResult;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.exception.FaceDetectCountException;
import deu.hlju.dawn.studentattendance.exception.NetworkException;
import deu.hlju.dawn.studentattendance.exception.StudentExistException;
import deu.hlju.dawn.studentattendance.network.Request;
import deu.hlju.dawn.studentattendance.util.LogUtil;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dawn on 2018/3/26.
 */

public class AddStudentPresenter extends AddStudentContract.Presenter{


    public AddStudentPresenter(Context context, AddStudentContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {

    }

    @Override
    protected void submit(final String name, final String id, String portraitPath) {
        view.showProgress();
        if (TextUtils.isEmpty(name)) {
            view.showMsg(context.getString(R.string.add_student_name_empty));
            view.hideProgress();
            return;
        }
        if (TextUtils.isEmpty(id)) {
            view.showMsg(context.getString(R.string.add_student_id_empty));
            view.hideProgress();
            return;
        }
        if (TextUtils.isEmpty(portraitPath)) {
            view.showMsg(context.getString(R.string.add_student_portrait_empty));
            view.hideProgress();
            return;
        }
        if (name.length() < 2 || name.length() > 6) {
            view.showMsg(context.getString(R.string.add_student_name_error));
            view.hideProgress();
            return;
        }
        if (id.length() != 8) {
            view.showMsg(context.getString(R.string.add_student_id_error));
            view.hideProgress();
            return;
        }
        File imageFile = new File(portraitPath);
        if (!imageFile.exists()) {
            view.showMsg(context.getString(R.string.file_not_exists));
            view.hideProgress();
            return;
        }
        try {
            final AVFile file = AVFile.withFile(imageFile.getName(), imageFile);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e != null) {
                        LogUtil.i("AddStudentPresenter", "submit():" + e.toString());
                        view.showMsg(context.getString(R.string.network_error));
                        view.hideProgress();
                    }
                    uploadStudentMessage(name, id, file.getUrl());
                }
            });
        } catch (FileNotFoundException ignored) {
        }

    }

    private void uploadStudentMessage(final String name, final String id, final String url) {
        LogUtil.i("AddStudentPresenter", String.format("uploadStudentMessage():name=%s,id=%s,url=%s", name, id, url));

        Request.getSingle().getApi().getFaceDetect(Request.getSingle().getDetactPatames(url))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<FaceDetectResult, ObservableSource<FaceSetAddResult>>() {
                    @Override
                    public ObservableSource<FaceSetAddResult> apply(FaceDetectResult faceDetectResult) throws Exception {
                        LogUtil.i("AddStudentPresenter", "getFaceDetect():" + faceDetectResult.toString());
                        if (!TextUtils.isEmpty(faceDetectResult.getError_message())) {
                            throw new NetworkException();
                        }
                        if (faceDetectResult.getFaces().size() != 1) {
                            throw new FaceDetectCountException();
                        }
                        AVQuery<Student> query = new AVQuery<>("Student");
                        query.whereEqualTo("id", id);
                        List<Student> queryList = query.find();
                        if (!queryList.isEmpty()) {
                            throw new StudentExistException();
                        }
                        Student student = new Student();
                        student.setName(name);
                        student.setId(id);
                        student.setPortrait(url);
                        student.setFaceToken(faceDetectResult.getFaces().get(0).getFace_token());
                        student.save();
                        return Request.getSingle().getApi().addFaceset(Request.getSingle().getAddFacesetPatames(student.getFaceToken()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FaceSetAddResult>() {
                    @Override
                    public void accept(FaceSetAddResult faceSetAddResult) throws Exception {
                        view.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i("AddStudentPresenter", "getFaceDetect():" + throwable.toString());
                        view.hideProgress();
                        if (throwable instanceof BaseException) {
                            view.showMsg(throwable.getMessage());
                        } else  {
                            view.showMsg(context.getString(R.string.network_error));
                        }
                    }
                });


    }
}
