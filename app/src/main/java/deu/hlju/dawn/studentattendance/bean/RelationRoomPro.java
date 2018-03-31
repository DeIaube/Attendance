package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("RelationRoomPro")
public class RelationRoomPro extends AVObject{

    public void setRoomId(String id) {
        put("roomId", id);
    }

    public String getRoomtId() {
        return getString("roomId");
    }

    public void setProjectId(String id) {
        put("projectId", id);
    }

    public String getProjectId() {
        return getString("projectId");
    }

    public void setTime(String time) {
        put("time", time);
    }

    public String getTime() {
        return getString("time");
    }

    @Override
    public String toString() {
        return String.format("roomId:%s, projectId:%s, time:%s", getRoomtId(), getProjectId(), getTime());
    }
}
