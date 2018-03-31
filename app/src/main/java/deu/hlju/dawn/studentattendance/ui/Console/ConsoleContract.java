package deu.hlju.dawn.studentattendance.ui.Console;

import android.content.Context;

import java.util.List;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;
import deu.hlju.dawn.studentattendance.bean.Student;



public interface ConsoleContract {
    interface View extends MvpView {
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
    }

    abstract class Presenter extends MvpPresenter {
        protected ConsoleContract.View view;
        protected Context context;

        public Presenter(Context context, ConsoleContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void initData();
    }
}
