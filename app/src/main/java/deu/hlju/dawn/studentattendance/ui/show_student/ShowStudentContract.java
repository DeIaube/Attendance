package deu.hlju.dawn.studentattendance.ui.show_student;

import android.content.Context;

import java.util.List;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;
import deu.hlju.dawn.studentattendance.bean.Student;


/**
 * Created by Dawn on 2018/3/26.
 */

public interface ShowStudentContract {
    interface View extends MvpView {
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
        void loadStudentMessage(List<Student> students);
        void deleteStudent(int position);
    }

    abstract class Presenter extends MvpPresenter {
        protected ShowStudentContract.View view;
        protected Context context;

        public Presenter(Context context, ShowStudentContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void loadStudentMessage();
        protected abstract void deleteStudent(int position);
    }
}
