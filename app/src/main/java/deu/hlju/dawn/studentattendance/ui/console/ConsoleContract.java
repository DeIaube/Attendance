package deu.hlju.dawn.studentattendance.ui.console;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import deu.hlju.dawn.studentattendance.base.MvpPresenter;
import deu.hlju.dawn.studentattendance.base.MvpView;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;


public interface ConsoleContract {
    interface View extends MvpView {
        void showProgress();
        void hideProgress();
        void showMsg(String msg);
        void showProject(List<Project> projects);
        void showRoom(List<Room> rooms);
        void shwoRelationStuPro(Map<Student, Set<Project>> studentListMap);
        void shwoRelationRoomPro(List<RelationRoomPro> timeTablelList);
    }

    abstract class Presenter extends MvpPresenter {
        protected ConsoleContract.View view;
        protected Context context;

        public Presenter(Context context, ConsoleContract.View view) {
            this.view = view;
            this.context = context;
            start();
        }

        protected abstract void loadData();
        protected abstract void addProject(String id, String name);
        protected abstract void showProject();
        protected abstract void addRoom(String id, String name);
        protected abstract void showRoom();
        protected abstract void addRelationStuPro(String studentId, String projectId);
        protected abstract void showRelationStuPro();
        protected abstract void addRelationRoomPro(String roomId, String projectId, String week, String startNum, String endNum);
        protected abstract void showRelationRoomPro();
    }
}
