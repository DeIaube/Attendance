package deu.hlju.dawn.studentattendance.ui.camera;

import android.content.Context;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;

public interface CameraContract {
    interface View extends MvpView{
        void showMsg(String msg);
    }

    abstract class Presenter extends MvpPresenter {
        protected CameraContract.View view;
        protected Context context;

        Presenter(Context context, CameraContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void startSearchFace(byte[] bytes);
    }
}
