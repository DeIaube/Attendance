package deu.hlju.dawn.studentattendance.ui.add_student;

import android.content.Context;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;

public interface AddStudentContract {

    interface View extends MvpView{
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
    }

    abstract class Presenter extends MvpPresenter {
        protected AddStudentContract.View view;
        protected Context context;

        public Presenter(Context context, AddStudentContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void submit(String name, String id, String portraitPath);
    }
}
