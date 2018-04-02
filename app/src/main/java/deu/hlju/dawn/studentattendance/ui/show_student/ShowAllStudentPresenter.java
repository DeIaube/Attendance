package deu.hlju.dawn.studentattendance.ui.show_student;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.bean.Student;
import deu.hlju.dawn.studentattendance.util.LogUtil;

public class ShowStudentPresenter extends ShowStudentContract.Presenter {

    private List<Student> students;

    public ShowStudentPresenter(Context context, ShowStudentContract.View view) {
        super(context, view);
    }

    @Override
    protected void start() {
        loadStudentMessage();
    }

    @Override
    protected void loadStudentMessage() {
        view.showProgress();
        AVQuery<Student> query = new AVQuery<>("Student");
        query.findInBackground(new FindCallback<Student>() {
            @Override
            public void done(List<Student> list, AVException e) {
                view.hideProgress();
                if (e == null) {
                    LogUtil.i("ShowStudentPresenter", "loadStudentMessage() " + list.toString());
                    students = list;
                    view.loadStudentMessage(list);
                } else {
                    LogUtil.i("ShowStudentPresenter", "loadStudentMessage() " + e.toString());
                    view.showMsg(context.getString(R.string.network_error));
                }

            }
        });
    }

    @Override
    protected void deleteStudent(final int position) {
        if (students == null) {
            return;
        }
        view.showProgress();
        Student student = students.remove(position);
        student.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                view.hideProgress();
                if (e != null) {
                    LogUtil.i("ShowStudentPresenter", "deleteStudent() " + e.toString());
                    view.showMsg(context.getString(R.string.network_error));
                } else {
                    view.deleteStudent(position);
                }
            }
        });
    }
}
