package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("AttendanceRecode")
public class AttendanceRecode extends AVObject{

    public RelationRoomPro getRelationRoomPro() {
        return (RelationRoomPro) get("relationRoomPro");
    }

    public void setRelationRoomPro(RelationRoomPro relationRoomPro) {
        put("relationRoomPro", relationRoomPro);
    }

    public void setStudent(Student student) {
        put("student", student);
    }

    public Student getStudent() {
        return (Student) get("student");
    }

    @Override
    public String toString() {
        return String.format("student:%s, relationRoomPro:%s", getStudent(), getRelationRoomPro());
    }
}
