package deu.hlju.dawn.studentattendance.exception;

import deu.hlju.dawn.studentattendance.base.BaseException;

public class ProjectNotExistException extends BaseException{
    public ProjectNotExistException() {
        super("此课程不存在,请重试");
    }
}
