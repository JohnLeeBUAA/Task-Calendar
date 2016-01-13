package pac;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Administrator
 * 
 */

/*
 * 添加任务窗口
 * 
 */
class AddTaskFrame extends JFrame{
	private JFrame f;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JTextField text4;
	private JButton confirm;
	private JButton cancel;
	private MainFrame mf;
	private int condition; // 1为添加，2 为修改
	private int delnumber;
	//添加任务窗口
	public AddTaskFrame(MainFrame mainframe, int con) {
		condition = con;
		this.mf = mainframe;
		f = new JFrame("添加任务");
		f.setLayout(new GridLayout(9, 1));
		label1 = new JLabel("事件：");
		label2 = new JLabel("年：");
		label3 = new JLabel("月：");
		label4 = new JLabel("日：");
		text1 = new JTextField("", 40);
		text2 = new JTextField("", 5);
		text3 = new JTextField("", 5);
		text4 = new JTextField("", 5);
		confirm = new JButton("确认");
		cancel = new JButton("取消");

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (text1.getText().equals("") || text2.getText().equals("")
						|| text3.getText().equals("")
						|| text4.getText().equals("")) {
					JLabel aa = new JLabel("无效输入");
					JFrame ff = new JFrame("错误!");
					ff.add(aa);
					ff.setLayout(new FlowLayout());
					ff.setSize(250, 100);
					ff.setResizable(false);
					ff.setLocationRelativeTo(null);
					ff.setVisible(true);
				} else {
					String tempcontent = String.valueOf(text1.getText());
					int tempyear = Integer.parseInt(text2.getText());
					int tempmonth = Integer.parseInt(text3.getText());
					int tempday = Integer.parseInt(text4.getText());

					if (invalid(tempyear, tempmonth, tempday)) {
						JLabel aa = new JLabel("无效日期");
						JFrame ff = new JFrame("错误!");
						ff.add(aa);
						ff.setLayout(new FlowLayout());
						ff.setSize(250, 100);
						ff.setResizable(false);
						ff.setLocationRelativeTo(null);
						ff.setVisible(true);
					} else {
						if (condition == 2)
							mf.deleteTask(delnumber);
						int location = 0;
						for (int i = 0; i < mf.lis.size(); i++) {
							if (isEarlyerThan(tempyear, tempmonth, tempday,
									mf.lis.elementAt(i).getYear(), mf.lis
											.elementAt(i).getMonth(), mf.lis
											.elementAt(i).getDay())) {
								break;
							}
							location++;
						}
						mf.addTask(tempcontent, tempyear, tempmonth, tempday,
								location);
						f.dispose();
						if (condition == 2) {
							mf.launchTaskList();
							condition = 1;
						}
					}
				}

			}
		});
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				f.setVisible(false);
			}
		});
	}
	//判断日期是否合法，合法返回1
	public boolean invalid(int year, int month, int day) {
		if (month < 1 || month > 12) {
			return true;
		} else {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (day < 1 || day > 31)
					return true;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if (day < 1 || day > 30)
					return true;
				break;
			default: {
				if (isleapyear(year))
					if (day < 1 || day > 29)
						return true;
					else if (day < 1 || day > 28)
						return true;
			}
			}
		}
		return false;
	}
	//判断是否为之前日期，是则返回1
	public boolean isEarlyerThan(int year1, int month1, int day1, int year2,
			int month2, int day2) {
		if (year1 < year2)
			return true;
		else if (year1 > year2)
			return false;
		else {
			if (month1 < month2)
				return true;
			else if (month1 > month2)
				return false;
			else {
				if (day1 < day2)
					return true;
				else if (day1 >= day2)
					return false;
			}
		}
		return true;
	}
	//判断是否为闰年，闰年返回为1
	public boolean isleapyear(int year) {
		if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
			return true;
		else
			return false;
	}
	//添加任务窗口初始化	
	public void launch() {
		f.setLayout(new GridLayout(5, 3));
		f.add(label1);
		f.add(text1);
		f.add(label2);
		f.add(text2);
		f.add(label3);
		f.add(text3);
		f.add(label4);
		f.add(text4);
		f.add(confirm);
		f.add(cancel);

		f.setSize(450, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setResizable(false);
	}
	//添加任务状态
	public void setCondtion(int x) {
		condition = x;
	}
	//删数任务位置
	public void setDelnumber(int x) {
		delnumber=x;
	}
}
