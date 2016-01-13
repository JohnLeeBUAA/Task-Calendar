package pac;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * 课程表窗口
 */
public class TimeTable extends JFrame {
	private JFrame ttFrame;
	private JPanel ttClass;
	private JLabel ttTitle;
	private JPanel titlePanel;
	private JLabel[][] classMatrix;
	private JPanel fixPanel;
	private String[] week = { "Monday", "Tuesday", "Thursday", "Wendesday",
			"Friday" };// 选择修改星期
	private String[] classNo = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10" };// 选择修改课程
	private JComboBox weekCbx;// 选星期几
	private JComboBox classCbx;// 选课序号
	private JTextField fixField;// 修改框
	private JButton confirm;
	private JButton save;
	private JButton select;
	private Object2WriteOrRead o2wr;

	public TimeTable() {
		ttFrame = new JFrame();
		ttFrame.setSize(640, 480);
		ttFrame.setLayout(new BorderLayout());
		ttFrame.setLocationRelativeTo(null);
		ttClass = new JPanel();
		ttClass.setLayout(new GridLayout(11, 6));// 课表主体
		ttTitle = new JLabel("课程表");
		titlePanel = new JPanel();
		classMatrix = new JLabel[11][];
		for (int i = 0; i < 11; i++) {
			classMatrix[i] = new JLabel[6];
			for (int j = 0; j < 6; j++) {
				classMatrix[i][j] = new JLabel("空缺");
			}
		}
		classMatrix[0][0].setText("");
		classMatrix[0][1].setText("MONDAY");
		classMatrix[0][2].setText("TUESDAY");
		classMatrix[0][3].setText("THURSDAY");
		classMatrix[0][4].setText("WENDESDAY");
		classMatrix[0][5].setText("FRIDAY");
		classMatrix[1][0].setText("1");
		classMatrix[2][0].setText("2");
		classMatrix[3][0].setText("3");
		classMatrix[4][0].setText("4");
		classMatrix[5][0].setText("5");
		classMatrix[6][0].setText("6");
		classMatrix[7][0].setText("7");
		classMatrix[8][0].setText("8");
		classMatrix[9][0].setText("9");
		classMatrix[10][0].setText("10");
		classMatrix[1][1].setText("口语");
		classMatrix[2][1].setText("口语");
		classMatrix[5][1].setText("政治");
		classMatrix[6][1].setText("政治");
		classMatrix[7][1].setText("政治");
		classMatrix[3][2].setText("物理");
		classMatrix[4][2].setText("物理");
		classMatrix[5][2].setText("上机");
		classMatrix[6][2].setText("上机");
		classMatrix[7][2].setText("计组");
		classMatrix[8][2].setText("计组");
		classMatrix[1][3].setText("概率");
		classMatrix[2][3].setText("概率");
		classMatrix[3][3].setText("算法");
		classMatrix[4][3].setText("算法");
		classMatrix[5][3].setText("网络");
		classMatrix[6][3].setText("网络");
		classMatrix[9][3].setText("上机");
		classMatrix[10][3].setText("上机");
		classMatrix[1][4].setText("物理");
		classMatrix[2][4].setText("物理");
		classMatrix[4][4].setText("体育");
		classMatrix[5][4].setText("JAVA");
		classMatrix[6][4].setText("JAVA");
		classMatrix[7][4].setText("计组");
		classMatrix[8][4].setText("计组");
		classMatrix[9][4].setText("上机");
		classMatrix[10][4].setText("上机");
		classMatrix[1][5].setText("概率");
		classMatrix[2][5].setText("概率");
		fixPanel = new JPanel();
		fixPanel.setLayout(new GridLayout(1, 6));
		weekCbx = new JComboBox();
		classCbx = new JComboBox();
		fixField = new JTextField("请填入修改的课程");

		fixField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fixField.setText("");
				

			}
		});// 点击输入框则把输入框置空
		for (int i = 0; i < 5; i++) {
			weekCbx.addItem(week[i]);
		}
		for (int i = 0; i < 10; i++) {
			classCbx.addItem(classNo[i]);
		}
		confirm = new JButton("确定");
		save = new JButton("保存");
		select = new JButton("载入课表");
		o2wr = new Object2WriteOrRead();
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classMatrix[classCbx.getSelectedIndex() + 1][weekCbx
						.getSelectedIndex() + 1].setText(fixField.getText());

			}
		});//点击确认将下拉框对应课程改为当前输入框内容
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ready2Write();
				writeObject2File();
			}
		});//点击保存则将o2wr写入根目录下文件
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readObjectFromFile();

			}
		});//点击载入则从根目录文件载入课表

	}
/*
 * 将当前课程表保存在o2wr中
 */
	public void ready2Write() {
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 6; j++) {
				o2wr.setClasses(classMatrix[i][j].getText(), i, j);
			}
		}
	}
/*
 * 修改屏幕显示为o2wr中的课程表
 */
	public void load2Screen() {
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 6; j++) {
				classMatrix[i][j].setText(o2wr.getClass(i, j));
			}
		}
	}

	public void copyObject(Object2WriteOrRead objectFromFile) {
																
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 6; j++) {
				o2wr.setClasses(objectFromFile.getClass(i, j), i, j);
			}
		}
	}
/*
 * 将o2wr写入文件
 */
	public void writeObject2File() {
		try {
			FileOutputStream fos = new FileOutputStream("TimeTable.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(o2wr);
		} catch (IOException e) {
			System.out.printf("操！");

		}

	}
	/*
	 * 将文件中保存的课程表录入o2wr
	 */
	public void readObjectFromFile() {
		try {

			FileInputStream fis = new FileInputStream("TimeTable.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			o2wr = (Object2WriteOrRead) ois.readObject();
			load2Screen();

		} catch (IOException e) {
			
			// do nothing if file does not exist
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/*
 * 启动课程表
 */
	public void lanch() {
		ttFrame.add(ttClass, BorderLayout.CENTER);
		ttFrame.add(titlePanel, BorderLayout.NORTH);
		titlePanel.add(ttTitle);
		ttFrame.add(fixPanel, BorderLayout.SOUTH);
		fixPanel.add(weekCbx);
		fixPanel.add(classCbx);

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 6; j++) {
				ttClass.add(classMatrix[i][j]);
				classMatrix[i][j].setVisible(true);
			}
		}
		fixPanel.add(fixField);
		fixPanel.add(confirm);
		fixPanel.add(save);
		fixPanel.add(select);
		ttTitle.setVisible(true);
		ttClass.setVisible(true);
		weekCbx.setVisible(true);
		classCbx.setVisible(true);
		fixField.setVisible(true);
		confirm.setVisible(true);
		save.setVisible(true);
		select.setVisible(true);
		fixPanel.setVisible(true);
		ttFrame.setVisible(true);
		ttFrame.setResizable(false);
		

	}
}
