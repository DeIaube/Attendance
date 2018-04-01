package deu.hlju.dawn.studentattendance.bean;

public class TimeTableModel {
	private int startnum;
	private int endnum;
	private int week;
	private String name;

	public TimeTableModel(int startnum, int endnum, int week, String name, String room) {
		this.startnum = startnum;
		this.endnum = endnum;
		this.week = week;
		this.name = name;
		this.room = room;
	}

	public TimeTableModel(String startnum, String endnum, String week, String name, String room) {
		this.startnum = Integer.valueOf(startnum);
		this.endnum = Integer.valueOf(endnum);
		this.week = Integer.valueOf(week);
		this.name = name;
		this.room = room;
	}

	private String room;

	public int getStartnum() {
		return startnum;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TimeTableModel{" +
				"startnum=" + startnum +
				", endnum=" + endnum +
				", week=" + week +
				", name='" + name + '\'' +
				", room='" + room + '\'' +
				'}';
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
