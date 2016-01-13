package test;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.*;
import pac.MainFrame;

public class Scroll {
	private JFrame f;
	private JScrollPane j;
	private JButton[] mod;
	private JButton[] del;
	private JLabel[] time;
	private JLabel[] content;
	private int num;
	
	public Scroll()
	{
		f=new JFrame("TaskList");
		
		num=20;
		f.setLayout(new FlowLayout());
		j=new JScrollPane();
		
		for(int i=0;i<num;i++)
		{
			content[i]=new JLabel("number"+i); 
		}
	}
	public void launch()
	{
		for(int i=0;i<num;i++)
			j.add(content[i]);
		f.add(j);
		f.setSize(500,500);
		f.setVisible(true);
		
	}
	public static void main(String[] args)
	{
		JTextArea ta=new JTextArea("1\n2\n3\n4\n5",3,10);
		JScrollPane sta=new JScrollPane(ta);
		JFrame f=new JFrame();
		sta.setVisible(true);
		f.add(sta);
		f.setSize(100,100);
		f.setVisible(true);
	}

}
