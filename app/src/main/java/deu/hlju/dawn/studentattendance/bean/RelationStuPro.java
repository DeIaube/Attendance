package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("RelationStuPro")
public class RelationStuPro extends AVObject{

    public void setStudentId(String id) {
        put("studentId", id);
    }

    public String getStudentId() {
        return getString("studentId");
    }

    public void setProjectId(String id) {
        put("projectId", id);
    }

    public String getProjectId() {
        return getString("projectId");
    }

    @Override
    public String toString() {
        return String.format("studentId:%s, projectId:%s", getStudentId(), getProjectId());
    }
}
