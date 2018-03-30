package deu.hlju.dawn.studentattendance.bean;

public class TimeTableModel {
	private int startnum;
	private int endnum;
	private int week;
	private String id;

	public TimeTableModel(int startnum, int endnum, int week, String id, String name, String teacher) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.week = week;
		this.id = id;
		this.name = name;
		this.teacher = teacher;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name="";
	private String teacher="";

	public int getStartnum() {
		return startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public int getWeek() {
		return week;
	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public void setWeek(int week) {
		this.week = week;
	}


	public void setName(String name) {
		this.name = name;
	}

	public TimeTableModel(int startnum, int endnum, int week, String name, String teacher) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.week = week;
		this.name = name;
		this.teacher = teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

}
