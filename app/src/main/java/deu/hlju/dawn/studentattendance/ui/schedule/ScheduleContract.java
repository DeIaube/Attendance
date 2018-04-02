package deu.hlju.dawn.studentattendance.ui.schedule;

import android.content.Context;

import java.util.List;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;

public interface ScheduleContract {
    interface View extends MvpView {
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
        void loadSchedule(List<RelationRoomPro> timeTables);
    }

    abstract class Presenter extends MvpPresenter {
        protected ScheduleContract.View view;
        protected Context context;

        public Presenter(Context context, ScheduleContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void loadSchedule();
    }
}
