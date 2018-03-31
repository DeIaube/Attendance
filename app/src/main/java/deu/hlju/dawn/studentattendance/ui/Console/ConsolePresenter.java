package deu.hlju.dawn.studentattendance.ui.Console;

import android.content.Context;

import com.avos.avoscloud.AVQuery;

import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.RelationStuPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;
import io.reactivex.Observable;

public class ConsolePresenter extends ConsoleContract.Presenter {

    public ConsolePresenter(Context context, ConsoleContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {
        initData();
    }

    @Override
    protected void initData() {
        AVQuery<Student> studentAVQuery = new AVQuery<>("Studnet");
        AVQuery<Project> projectAVQuery = new AVQuery<>("project");
        AVQuery<Room> roomAVQuery = new AVQuery<>("Room");
        AVQuery<RelationRoomPro> relationRoomProAVQuery = new AVQuery<>("RelationRoomPro");
        AVQuery<RelationStuPro> relationStuProAVQuery = new AVQuery<>("RelationStuPro");
    }
}
