package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("RelationRoomPro")
public class RelationRoomPro extends AVObject{

    public Room getRoom() {
        return (Room) get("room");
    }

    public void setRoom(Room room) {
        put("room", room);
    }

    public Project getProject() {
        return (Project) get("project");
    }

    public void setProject(Project project) {
        put("project", project);
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
        return String.format("room:%s, project:%s, week:%s, startNum:%s, endNum:%s", getRoom(), getProject(), getWeek(), getStartNum(), getEndNum());
    }
}
