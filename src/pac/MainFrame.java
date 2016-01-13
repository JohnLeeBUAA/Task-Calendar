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
 * ����������
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
	 * ��Ա��������
	 */
	private JFrame f;// ������
	private JButton open;
	private JButton save;
	private JButton checkcalendar;
	private JButton checkTaskList;
	private JButton checkTimetable;
	private JButton addTask;
	private JButton changepassword;
	public Vector<Task> lis;// ������
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
	private EncryptionDecryption des;// ��Կ����

	/*
	 * MainFrame() �����ڹ��캯��
	 */
	public MainFrame() {
		/*
		 * ������֤
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

		f = new JFrame("�˵�");
		f.setLayout(new GridLayout(7, 1));
		lis = new Vector<Task>(5, 5);
		lis.setSize(0);
		open = new JButton("��");
		save = new JButton("����");
		checkcalendar = new JButton("�鿴����");
		checkTaskList = new JButton("�鿴����");
		checkTimetable = new JButton("�鿴�γ̱�");
		addTask = new JButton("�������");
		changepassword = new JButton("�޸�����");
		addframe = new AddTaskFrame(this, 1);

		// ��ťʵ��
		/*
		 * �򿪰�ťʵ��
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
		 * ���水ťʵ��
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
		 * ���������ťʵ��
		 */
		checkcalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarFrame rili = new CalendarFrame(lis);
				rili.launch();
			}
		});

		/*
		 * ����б�ťʵ��
		 */
		checkTaskList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchTaskList();
			}
		});

		/*
		 * �γ̱�ťʵ��
		 */
		checkTimetable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TimeTable tt = new TimeTable();
						tt.lanch();
					}
				});

		/*
		 * �������ťʵ��
		 */
		addTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addframe.launch();

			}
		});

		/*
		 * �������밴ťʵ��
		 */
		changepassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchChangePassWordFrame();
			}
		});

	}

	// ���������
	public void addTask(String con, int year, int month, int day, int location) {
		Task temp = new Task(con, year, month, day);
		lis.insertElementAt(temp, location);
	}

	// ɾ��������
	public void deleteTask(int i) {
		lis.remove(i);
	}

	// ��ӡ����
	public void display() {
		for (int i = 0; i < lis.size(); i++) {
			lis.elementAt(i).display();
		}
	}

	/*
	 * launchChangePassWordFrame() �ı����봰�ڹ��캯��
	 */
	public void launchChangePassWordFrame() {
		fpassword = new JFrame("�޸�����");
		passwordlabel = new JLabel("������������");
		passwordlabel2 = new JLabel("��������һ��");
		passwordbutton = new JButton("ȷ��");
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
						JOptionPane.showMessageDialog(null, "����������벻��ͬ");
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
	 * ����������
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
	 * ����ȷ�ϴ���
	 */
	public void launchPassWordFrame() {

		fpassword = new JFrame("���뱣��");
		passwordlabel = new JLabel("���������루Ĭ��Ϊ�գ�");
		passwordbutton = new JButton("ȷ��");

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
						JOptionPane.showMessageDialog(null, "�����������������");
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
	 * �����б���
	 */
	public void launchTaskList() {
		fTaskList = new JFrame("�����б�");
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
			del[i] = new JButton("ɾ��");
			del[i].addActionListener(new ActionListener() {
				int TaskListCounter = zz;

				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ���¼� ��"
							+ lis.elementAt(zz).getContent() + "  ��",
							"Attention", JOptionPane.YES_NO_OPTION);
					if (option == 0) {
						deleteTask(TaskListCounter);
						fTaskList.dispose();
						launchTaskList();
					}
				}
			});
			mod[i] = new JButton("�޸�");
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

			Font f1 = new Font("΢���ź�", Font.BOLD, 12);
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
			JOptionPane.showMessageDialog(null, "��ǰû������");
	    }
		else
			fTaskList.setVisible(true);

		fTaskList.setSize(600, num * 50 + 10);
		fTaskList.setLocationRelativeTo(null);
		fTaskList.setResizable(false);

	}
}
