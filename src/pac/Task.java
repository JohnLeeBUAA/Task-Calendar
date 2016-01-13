package pac;

/**
 * @author Administrator
 * 
 */
class Task {
	/*
	 * 任务基类
	 */
	private String content;
	private int year;
	private int month;
	private int day;

	public Task(String content, int year, int month, int day) {
		this.content = content;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public void display() {
		System.out.println(content);
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);
	}

	public String getContent() {
		return content;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
