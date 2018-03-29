package deu.hlju.dawn.studentattendance.ui.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.avos.avoscloud.AVQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseException;
import deu.hlju.dawn.studentattendance.bean.FaceSearchResult;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.config.Constants;
import deu.hlju.dawn.studentattendance.exception.FaceSearchNotFindException;
import deu.hlju.dawn.studentattendance.network.Request;
import deu.hlju.dawn.studentattendance.util.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CameraPresenter extends CameraContract.Presenter {

    CameraPresenter(Context context, CameraContract.View view) {
        super(context, view);
    }

    @Override
    protected void startSearchFace(byte[] bytes) {
        // 翻转图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap = convert(bitmap, bitmap.getWidth(), bitmap.getHeight());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        Request.getSingle().getApi().searchFace(Request.getSingle().getSearchPatames(baos.toByteArray()))
                .subscribeOn(Schedulers.io())
                .map(new Function<FaceSearchResult, Student>() {
                    @Override
                    public Student apply(FaceSearchResult faceSearchResult) throws Exception {
                        LogUtil.i("CameraPresenter", "startFaceSearch():" + faceSearchResult.toString());
                        List<FaceSearchResult.ResultsBean> faceSearchResultResults = faceSearchResult.getResults();
                        if (faceSearchResultResults == null || faceSearchResultResults.isEmpty()) {
                            throw new FaceSearchNotFindException();
                        }
                        FaceSearchResult.ResultsBean resultsBean = faceSearchResultResults.get(0);
                        if (resultsBean.getConfidence() <= Constants.FACE_SEARCH_DEFAULT_CONFIDENCE) {
                            throw new FaceSearchNotFindException();
                        }
                        String face_token = resultsBean.getFace_token();
                        AVQuery<Student> query = new AVQuery<>("Student");
                        query.whereEqualTo("face_token", face_token);
                        Student student = query.getFirst();
                        if (student == null) {
                            throw new FaceSearchNotFindException();
                        }
                        return student;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) throws Exception {
                        LogUtil.i("CameraPresenter", "startFaceSearch():" + student.toString());
                        punchClock(student);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i("CameraPresenter", "accept():"+throwable.toString());
                        if (throwable instanceof BaseException) {
                            view.showMsg(throwable.toString());
                        } else {
                            view.showMsg(context.getString(R.string.network_error));
                        }
                    }
                });
    }

    private void punchClock(Student student) {

    }

    private Bitmap convert(Bitmap a, int width, int height) {
        int w = a.getWidth();
        int h = a.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postRotate(90);  //旋转-90度
        Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()),new Rect(0, 0, width, height), null);
        return newb;
    }

    @Override
    protected void start() {

    }
}
