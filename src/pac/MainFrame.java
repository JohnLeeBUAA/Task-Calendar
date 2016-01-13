package pac;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Administrator
 *
 */

/*
 * 主函数窗口
 */
public class MainFrame extends JFrame {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame fun = new MainFrame();
		fun.launchPassWordFrame();

	}

	/*
	 * 成员变量定义
	 */
	private JFrame f;// 主窗口
	private JButton open;
	private JButton save;
	private JButton checkcalendar;
	private JButton checkTaskList;
	private JButton checkTimetable;
	private JButton addTask;
	private JButton changepassword;
	public Vector<Task> lis;// 任务类
	private AddTaskFrame addframe;
	private JFrame fTaskList;
	private JButton[] del;
	private JButton[] mod;
	private JLabel[] time;
	private JLabel[] content;
	private JFrame fpassword;
	private JLabel passwordlabel;
	private JLabel passwordlabel2;
	private JButton passwordbutton;
	private JPasswordField input;
	private JPasswordField input2;
	private String readpasswordinput;
	private String readpasswordinput2;
	private File passwordfile;
	private String path;
	private EncryptionDecryption des;// 密钥编码

	/*
	 * MainFrame() 主窗口构造函数
	 */
	public MainFrame() {
		/*
		 * 密码验证
		 */
		path = new String("./password.dat");
		passwordfile = new File(path);
		try {
			if (!passwordfile.exists()) {
				passwordfile.createNewFile();
			}
		} catch (Exception e) {
			System.err.println("Exception :" + e);
			e.printStackTrace();
		}

		f = new JFrame("菜单");
		f.setLayout(new GridLayout(7, 1));
		lis = new Vector<Task>(5, 5);
		lis.setSize(0);
		open = new JButton("打开");
		save = new JButton("保存");
		checkcalendar = new JButton("查看日历");
		checkTaskList = new JButton("查看任务");
		checkTimetable = new JButton("查看课程表");
		addTask = new JButton("添加任务");
		changepassword = new JButton("修改密码");
		addframe = new AddTaskFrame(this, 1);

		// 按钮实现
		/*
		 * 打开按钮实现
		 */
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Open File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						null, "txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					lis.clear();
					try {
						des = new EncryptionDecryption("morbidinfant");
						BufferedReader fin = new BufferedReader(new FileReader(
								path));
						int num = Integer.parseInt(des.decrypt(fin.readLine()));
						for (int i = 0; i < num; i++) {
							String con = String.valueOf(des.decrypt(fin
									.readLine()));
							int year = Integer.parseInt(des.decrypt(fin
									.readLine()));
							int month = Integer.parseInt(des.decrypt(fin
									.readLine()));
							int day = Integer.parseInt(des.decrypt(fin
									.readLine()));
							addTask(con, year, month, day, i);
						}
						fin.close();
					} catch (Exception readex) {
						System.err.println("Exception :" + readex);
						readex.printStackTrace();
					}
				}
			}
		});

		/*
		 * 保存按钮实现
		 */
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Save File");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						null, "txt");
				chooser.setFileFilter(filter);
				int option = chooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					try {
						des = new EncryptionDecryption("morbidinfant");
						BufferedWriter fout = new BufferedWriter(
								new FileWriter(path));
						fout.write(des.encrypt(String.valueOf(lis.size()))
								+ "\n");
						for (int i = 0; i < lis.size(); i++) {
							fout.write(des.encrypt(String.valueOf(lis
									.elementAt(i).getContent())) + "\n");
							fout.write(des.encrypt(String.valueOf(lis
									.elementAt(i).getYear())) + "\n");
							fout.write(des.encrypt(String.valueOf(lis
									.elementAt(i).getMonth())) + "\n");
							fout.write(des.encrypt(String.valueOf(lis
									.elementAt(i).getDay())) + "\n");
						}
						fout.close();
					} catch (Exception writeex) {
						System.err.println("Exception :" + writeex);
						writeex.printStackTrace();
					}
				}
			}
		});

		/*
		 * 检查日历按钮实现
		 */
		checkcalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarFrame rili = new CalendarFrame(lis);
				rili.launch();
			}
		});

		/*
		 * 检查列表按钮实现
		 */
		checkTaskList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchTaskList();
			}
		});

		/*
		 * 课程表按钮实现
		 */
		checkTimetable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TimeTable tt = new TimeTable();
						tt.lanch();
					}
				});

		/*
		 * 添加任务按钮实现
		 */
		addTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addframe.launch();

			}
		});

		/*
		 * 更改密码按钮实现
		 */
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchChangePassWordFrame();
			}
		});

	}

	// 添加任务函数
	public void addTask(String con, int year, int month, int day, int location) {
		Task temp = new Task(con, year, month, day);
		lis.insertElementAt(temp, location);
	}

	// 删除任务函数
	public void deleteTask(int i) {
		lis.remove(i);
	}

	// 打印函数
	public void display() {
		for (int i = 0; i < lis.size(); i++) {
			lis.elementAt(i).display();
		}
	}

	/*
	 * launchChangePassWordFrame() 改变密码窗口构造函数
	 */
	public void launchChangePassWordFrame() {
		fpassword = new JFrame("修改密码");
		passwordlabel = new JLabel("请输入新密码");
		passwordlabel2 = new JLabel("请再输入一遍");
		passwordbutton = new JButton("确定");
		input = new JPasswordField("", 20);
		input2 = new JPasswordField("", 20);

		passwordbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readpasswordinput = input.getText();
				readpasswordinput2 = input2.getText();

				try {
					if (readpasswordinput.equals(readpasswordinput2)) {
						des = new EncryptionDecryption("morbidinfant");
						BufferedWriter fout = new BufferedWriter(
								new FileWriter(path));
						fout.write(des.encrypt(readpasswordinput));
						fout.close();
						fpassword.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "您输入的密码不相同");
					}
				} catch (Exception readex) {
					System.err.println("Exception :" + readex);
					readex.printStackTrace();
				}
			}
		});
		fpassword.setLayout(new GridLayout(5, 1));
		fpassword.add(passwordlabel);
		fpassword.add(input);
		fpassword.add(passwordlabel2);
		fpassword.add(input2);
		fpassword.add(passwordbutton);
		fpassword.setSize(280, 170);
		fpassword.setLocationRelativeTo(null);
		fpassword.setResizable(false);
		fpassword.setVisible(true);
	}

	/*
	 * 主窗口设置
	 */
	public void launchMainFrame() {
		f.add(open);
		f.add(save);
		f.add(checkcalendar);
		f.add(checkTaskList);
		f.add(checkTimetable);
		f.add(addTask);
		f.add(changepassword);
		f.setSize(235, 400);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * 密码确认窗口
	 */
	public void launchPassWordFrame() {

		fpassword = new JFrame("密码保护");
		passwordlabel = new JLabel("请输入密码（默认为空）");
		passwordbutton = new JButton("确定");

		input = new JPasswordField("", 20);

		passwordbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readpasswordinput = input.getText();

				try {
					des = new EncryptionDecryption("morbidinfant");
					BufferedReader fin = new BufferedReader(
							new FileReader(path));
					String rightpassword = fin.readLine();
				    if (readpasswordinput.equals(des.decrypt(rightpassword))) {
						fpassword.dispose();
						launchMainFrame();
					} else {
						JOptionPane.showMessageDialog(null, "密码错误，请重新输入");
					}
					fin.close();
				} catch (Exception readex) {
					System.err.println("Exception :" + readex);
					readex.printStackTrace();
				}
			}
		});

		fpassword.setLayout(new GridLayout(3, 1));
		fpassword.add(passwordlabel);
		fpassword.add(input);
		fpassword.add(passwordbutton);

		fpassword.setSize(250, 110);
		fpassword.setLocationRelativeTo(null);
		fpassword.setVisible(true);
		fpassword.setResizable(false);
	}

	/*
	 * 任务列表窗口
	 */
	public void launchTaskList() {
		fTaskList = new JFrame("任务列表");
		int num = lis.size();
		fTaskList.setLayout(new GridLayout(num, 4));
		del = new JButton[num];
		mod = new JButton[num];
		time = new JLabel[num];
		content = new JLabel[num];
		for (int i = 0; i < num; i++) {
			final int zz = i;
			content[i] = new JLabel(lis.elementAt(i).getContent());
			time[i] = new JLabel(String.valueOf(lis.elementAt(i).getYear())
					+ "/  " + String.valueOf(lis.elementAt(i).getMonth())
					+ "/  " + String.valueOf(lis.elementAt(i).getDay()) + "/");
			del[i] = new JButton("删除");
			del[i].addActionListener(new ActionListener() {
				int TaskListCounter = zz;

				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, "确认删除事件 ："
							+ lis.elementAt(zz).getContent() + "  吗？",
							"Attention", JOptionPane.YES_NO_OPTION);
					if (option == 0) {
						deleteTask(TaskListCounter);
						fTaskList.dispose();
						launchTaskList();
					}
				}
			});
			mod[i] = new JButton("修改");
			mod[i].addActionListener(new ActionListener() {
				int TaskListCounter = zz;

				public void actionPerformed(ActionEvent e) {
					addframe.setCondtion(2);
					addframe.setDelnumber(TaskListCounter);
					addframe.launch();
					fTaskList.dispose();
				}
			});
			content[i].setVisible(true);
			time[i].setVisible(true);
			del[i].setVisible(true);
			mod[i].setVisible(true);

			Font f1 = new Font("微软雅黑", Font.BOLD, 12);
			Font f2 = new Font("Segoe Script", Font.BOLD, 12);

			content[i].setFont(f1);
			time[i].setFont(f2);
			del[i].setFont(f1);
			mod[i].setFont(f1);

			fTaskList.add(content[i]);
			fTaskList.add(time[i]);
			fTaskList.add(mod[i]);
			fTaskList.add(del[i]);
		}
		if (num == 0)
	    {
			fTaskList.setVisible(false);
			JOptionPane.showMessageDialog(null, "当前没有任务");
	    }
		else
			fTaskList.setVisible(true);

		fTaskList.setSize(600, num * 50 + 10);
		fTaskList.setLocationRelativeTo(null);
		fTaskList.setResizable(false);

	}
}
