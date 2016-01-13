package pac;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Administrator
 * 
 */

/*
 * 日历窗口
 * 
 */
class CalendarFrame extends JFrame{
	private JFrame f;
	private JPanel pCenter;
	private JPanel pNorth;
	private JPanel pSouth;
	private JButton pre;
	private JButton next;
	private JLabel info;
	private JButton[] date;
	private int year;
	private int month ;
	private int firstdayweek ;
	private Vector<Task> list = null;
	private int num;
	//查看日历窗口
	public CalendarFrame(Vector<Task> lis) {
		list = lis;
		f = new JFrame("任务日历");
		f.setLayout(new BorderLayout());

		pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(7, 7));
		pCenter.setVisible(true);
		pSouth = new JPanel();
		pSouth.setLayout(new FlowLayout());
		pSouth.setVisible(true);
		pNorth = new JPanel();
		pNorth.setLayout(new FlowLayout());
		pNorth.setVisible(true);
		pre = new JButton("Previous Month");

		next = new JButton("Next Month");

		pre.setSize(100, 50);
		next.setSize(100, 50);
		Calendar calx = Calendar.getInstance();//设初始月为系统当前时间
			year= calx.get(Calendar.YEAR);
			month=calx.get(Calendar.MONTH) + 1;
			calx.set(Calendar.DAY_OF_MONTH,1);
		int tmp= calx.get(Calendar.DAY_OF_WEEK)-1;
			if (tmp==0)tmp=7;
				firstdayweek=tmp;

		info = new JLabel("");
		info.setText(String.valueOf(year) + "年    " + String.valueOf(month)
				+ "月");
		info.setVisible(true);
		date = new JButton[49];
		for (int i = 0; i < 49; i++) {
			date[i] = new JButton("");
			date[i].setVisible(true);
		}

		date[0].setText("MON");
		date[1].setText("TUE");
		date[2].setText("WED");
		date[3].setText("THU");
		date[4].setText("FRI");
		date[5].setText("SAT");
		date[6].setText("SUN");

		setDateText();

		pre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setpredate();
				setDateText();
				info.setText(String.valueOf(year) + "年    "
						+ String.valueOf(month) + "月");
			}
		});

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnextdate();
				setDateText();
				info.setText(String.valueOf(year) + "年    "
						+ String.valueOf(month) + "月");
			}
		});
	}
	//月份日期函数，返回该月天数
	public int dayofmonth() {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;

		case 4:
		case 6:
		case 9:
		case 11:
			return 30;

		default: {
			if (isleapyear())
				return 29;
			else
				return 28;
		}

		}
	}
	//是否为闰年，闰年返回1
	public boolean isleapyear() {
		if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
			return true;
		else
			return false;
	}
	//窗口初始化
	public void launch() {
		// ImageIcon ic = new
		// ImageIcon(" /project/bgsuite/9107753_122128429156_2.jpg");
		f.setBackground(Color.black);
		Font f1 = new Font("微软雅黑", Font.BOLD, 15);
		Font f2 = new Font("Segoe Script", Font.BOLD, 13);

		pre.setFont(f2);
		next.setFont(f2);
		pNorth.add(pre);
		pNorth.add(next);
		for (int i = 0; i < 49; i++) {
			date[i].setFont(f2);
			pCenter.add(date[i]);
		}

		info.setFont(f1);

		pSouth.add(info);
		f.add(pNorth, BorderLayout.SOUTH);
		f.add(pCenter, BorderLayout.CENTER);
		f.add(pSouth, BorderLayout.NORTH);
		f.setSize(500, 420);
		f.setVisible(true);
		f.setResizable(true);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	//日历初始化
	public void launchframe(int printyear, int printmonth, int printday) {
		JFrame f = new JFrame("当日任务");
		int num = list.size();
		int counter = 0, start = 0;
		boolean flag = false;
		for (int j = 0; j < num; j++) {
			if ((list.elementAt(j).getYear() == printyear)
					&& (list.elementAt(j).getMonth() == printmonth)
					&& (list.elementAt(j).getDay() == printday)) {
				if (!flag) {
					start = j;
					flag = true;
				}
				counter++;
			}
		}

		if (flag) {
			f.setLayout(new GridLayout(counter, 2));
			JLabel[] time = new JLabel[counter];
			JLabel[] content = new JLabel[counter];
			for (int s = start; s < start + counter; s++) {
				content[s - start] = new JLabel(list.elementAt(s).getContent());
				time[s - start] = new JLabel(String.valueOf(list.elementAt(s)
						.getYear())
						+ "/  "
						+ String.valueOf(list.elementAt(s).getMonth())
						+ "/  "
						+ String.valueOf(list.elementAt(s).getDay()));

				Font f1 = new Font("微软雅黑", Font.BOLD, 13);
				Font f2 = new Font("Segoe Script", Font.BOLD, 13);
				content[s - start].setFont(f1);
				time[s - start].setFont(f2);

				content[s - start].setVisible(true);
				time[s - start].setVisible(true);
				f.add(content[s - start]);
				f.add(time[s - start]);
			}

			f.setSize(500, counter * 50 + 50);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			f.setResizable(false);
		}

	}
	
	/*
	 * 卖萌开始.....
	 */
	//设置日历button数值
	public void setDateText() {
		for (int i = 7; i < 6 + firstdayweek; i++) {
			date[i].setText("");
			date[i].setBackground(null);
		}
		for (int i = 6 + firstdayweek; i <= 5 + firstdayweek + dayofmonth(); i++) {
			final int zz = i;
			date[i].setText(String.valueOf(i - firstdayweek - 5));
			date[i].setBackground(null);
			num = list.size();
			for (int j = 0; j < num; j++) {
				if ((list.elementAt(j).getYear() == year)
						&& (list.elementAt(j).getMonth() == month)
						&& (list.elementAt(j).getDay() == (i - firstdayweek - 5))) {

					date[i].setBackground(Color.red);
					date[i].addActionListener(new ActionListener() {
						int printyear = year;
						int printmonth = month;
						int printday = (zz - firstdayweek - 5);

						public void actionPerformed(ActionEvent e) {
							launchframe(printyear, printmonth, printday);
						}
					});
					break;
				}
			}
		}
		for (int i = 6 + firstdayweek + dayofmonth(); i < 49; i++) {
			date[i].setText("");
			date[i].setBackground(null);
		}
	}
	//添加之前的日期
	public void setnextdate() {
		firstdayweek = (firstdayweek + (dayofmonth() % 7)) % 7;
		if (firstdayweek == 0)
			firstdayweek = 7;
		if (month == 12) {
			year++;
			month = 1;
		} else
			month++;

	}
	//添加之后的日期
	public void setpredate() {
		if (month == 1) {
			year--;
			month = 12;
		} else
			month--;
		firstdayweek = (firstdayweek + 7 - (dayofmonth() % 7)) % 7;
		if (firstdayweek == 0)
			firstdayweek = 7;
	}

}
