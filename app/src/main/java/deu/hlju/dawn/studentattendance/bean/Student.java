package deu.hlju.dawn.studentattendance.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Student")
public class Student extends AVObject {

    public void setName(String name) {
        put("name", name);
    }

    public String getName() {
        return getString("name");
    }

    public void setId(String id) {
        put("id", id);
    }

    public String getId() {
        return getString("id");
    }

    public void setPortrait(String portrait) {
        put("portrait", portrait);
    }

    public String getPortrait() {
        return getString("portrait");
    }

    public void setFaceToken(String faceToken) {
        put("face_token", faceToken);
    }

    public String getFaceToken() {
        return getString("face_token");
    }

    @Override
    public String toString() {
        return String.format("name:%s,id:%s,portrait:%s,face_token:%s", getName(), getId(), getPortrait(), getFaceToken());
    }
}
