package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("RelationRoomPro")
public class RelationRoomPro extends AVObject{

    private String roomName;
    private String projectName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

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

    public void setWeek(String week) {
        put("week", week);
    }

    public String getWeek() {
        return getString("week");
    }

    public void setStartNum(String startNum) {
        put("startNum", startNum);
    }

    public String getStartNum() {
        return getString("startNum");
    }

    public void setEndNum(String endNum) {
        put("endNum", endNum);
    }

    public String getEndNum() {
        return getString("endNum");
    }

    @Override
    public String toString() {
        return String.format("roomId:%s, projectId:%s, week:%s, startNum:%s, endNum:%s", getRoomtId(), getProjectId(), getWeek(), getStartNum(), getEndNum());
    }
}
