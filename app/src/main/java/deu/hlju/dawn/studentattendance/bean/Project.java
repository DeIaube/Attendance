package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("project")
public class Project extends AVObject {

    public void setId(String id) {
        put("id", id);
    }

    public String getId() {
        return getString("id");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName() {
        return getString("name");
    }

    @Override
    public String toString() {
        return String.format("id:%s, name:%s", getId(), getName());
    }
}
