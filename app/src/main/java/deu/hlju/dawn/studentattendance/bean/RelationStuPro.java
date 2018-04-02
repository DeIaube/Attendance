package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("RelationStuPro")
public class RelationStuPro extends AVObject{

    public void setStudent(Student student) {
        put("student", student);
    }

    public Student getStudent() {
        return (Student) get("student");
    }

    public void setProject(Project project) {
        put("project", project);
    }

    public Project getProject() {
        return (Project) get("project");
    }

    @Override
    public String toString() {
        return String.format("student:%s, project:%s", getStudent(), getProject());
    }
}
