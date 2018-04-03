package deu.hlju.dawn.studentattendance;

import android.app.Application;


import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import deu.hlju.dawn.studentattendance.bean.AttendanceRecode;
import deu.hlju.dawn.studentattendance.bean.Project;
import deu.hlju.dawn.studentattendance.bean.RelationRoomPro;
import deu.hlju.dawn.studentattendance.bean.RelationStuPro;
import deu.hlju.dawn.studentattendance.bean.Room;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.config.Constants;


public class TattendanceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        AVObject.registerSubclass(Student.class);
        AVObject.registerSubclass(Project.class);
        AVObject.registerSubclass(Room.class);
        AVObject.registerSubclass(RelationRoomPro.class);
        AVObject.registerSubclass(RelationStuPro.class);
        AVObject.registerSubclass(AttendanceRecode.class);
        AVOSCloud.initialize(this, Constants.CLOUD_APP_ID,Constants.CLOUD_APP_KEY);
    }
}
