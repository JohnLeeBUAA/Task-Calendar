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
 * �γ̱���
 */
public class TimeTable extends JFrame {
	private JFrame ttFrame;
	private JPanel ttClass;
	private JLabel ttTitle;
	private JPanel titlePanel;
	private JLabel[][] classMatrix;
	private JPanel fixPanel;
	private String[] week = { "Monday", "Tuesday", "Thursday", "Wendesday",
			"Friday" };// ѡ���޸�����
	private String[] classNo = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10" };// ѡ���޸Ŀγ�
	private JComboBox weekCbx;// ѡ���ڼ�
	private JComboBox classCbx;// ѡ�����
	private JTextField fixField;// �޸Ŀ�
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
		ttClass.setLayout(new GridLayout(11, 6));// �α�����
		ttTitle = new JLabel("�γ̱�");
		titlePanel = new JPanel();
		classMatrix = new JLabel[11][];
		for (int i = 0; i < 11; i++) {
			classMatrix[i] = new JLabel[6];
			for (int j = 0; j < 6; j++) {
				classMatrix[i][j] = new JLabel("��ȱ");
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
		classMatrix[1][1].setText("����");
		classMatrix[2][1].setText("����");
		classMatrix[5][1].setText("����");
		classMatrix[6][1].setText("����");
		classMatrix[7][1].setText("����");
		classMatrix[3][2].setText("����");
		classMatrix[4][2].setText("����");
		classMatrix[5][2].setText("�ϻ�");
		classMatrix[6][2].setText("�ϻ�");
		classMatrix[7][2].setText("����");
		classMatrix[8][2].setText("����");
		classMatrix[1][3].setText("����");
		classMatrix[2][3].setText("����");
		classMatrix[3][3].setText("�㷨");
		classMatrix[4][3].setText("�㷨");
		classMatrix[5][3].setText("����");
		classMatrix[6][3].setText("����");
		classMatrix[9][3].setText("�ϻ�");
		classMatrix[10][3].setText("�ϻ�");
		classMatrix[1][4].setText("����");
		classMatrix[2][4].setText("����");
		classMatrix[4][4].setText("����");
		classMatrix[5][4].setText("JAVA");
		classMatrix[6][4].setText("JAVA");
		classMatrix[7][4].setText("����");
		classMatrix[8][4].setText("����");
		classMatrix[9][4].setText("�ϻ�");
		classMatrix[10][4].setText("�ϻ�");
		classMatrix[1][5].setText("����");
		classMatrix[2][5].setText("����");
		fixPanel = new JPanel();
		fixPanel.setLayout(new GridLayout(1, 6));
		weekCbx = new JComboBox();
		classCbx = new JComboBox();
		fixField = new JTextField("�������޸ĵĿγ�");

		fixField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fixField.setText("");
				

			}
		});// �����������������ÿ�
		for (int i = 0; i < 5; i++) {
			weekCbx.addItem(week[i]);
		}
		for (int i = 0; i < 10; i++) {
			classCbx.addItem(classNo[i]);
		}
		confirm = new JButton("ȷ��");
		save = new JButton("����");
		select = new JButton("����α�");
		o2wr = new Object2WriteOrRead();
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classMatrix[classCbx.getSelectedIndex() + 1][weekCbx
						.getSelectedIndex() + 1].setText(fixField.getText());

			}
		});//���ȷ�Ͻ��������Ӧ�γ̸�Ϊ��ǰ���������
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ready2Write();
				writeObject2File();
			}
		});//���������o2wrд���Ŀ¼���ļ�
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readObjectFromFile();

			}
		});//���������Ӹ�Ŀ¼�ļ�����α�

	}
/*
 * ����ǰ�γ̱�����o2wr��
 */
	public void ready2Write() {
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 6; j++) {
				o2wr.setClasses(classMatrix[i][j].getText(), i, j);
			}
		}
	}
/*
 * �޸���Ļ��ʾΪo2wr�еĿγ̱�
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
 * ��o2wrд���ļ�
 */
	public void writeObject2File() {
		try {
			FileOutputStream fos = new FileOutputStream("TimeTable.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(o2wr);
		} catch (IOException e) {
			System.out.printf("�٣�");

		}

	}
	/*
	 * ���ļ��б���Ŀγ̱�¼��o2wr
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
 * �����γ̱�
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
