package deu.hlju.dawn.studentattendance.ui.show_recode;

import android.content.Context;

import java.util.List;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;
import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.Student;

public interface ShowRecodeContract {
    interface View extends MvpView {
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
        void loadRecode(List<AttendanceRecode> attendanceRecodes);
    }

    abstract class Presenter extends MvpPresenter {
        protected ShowRecodeContract.View view;
        protected Context context;

        public Presenter(Context context, ShowRecodeContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void loadRecode(RelationRoomPro relationRoomPro);
    }
}
