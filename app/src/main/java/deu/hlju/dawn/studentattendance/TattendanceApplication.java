package deu.hlju.dawn.studentattendance;

import android.app.Application;


import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

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
        AVOSCloud.initialize(this, Constants.CLOUD_APP_ID,Constants.CLOUD_APP_KEY);
    }
}
