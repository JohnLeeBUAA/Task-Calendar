package test;
import java.io.*;

public class Filetest {
	public void test()
	{
          try {
	  BufferedWriter f = new BufferedWriter(new FileWriter(
			     "E:/test.txt"));
	  f.write("Let's do this!\n");
	  f.write("as\n");
	  f.close();

   BufferedReader reader = new BufferedReader(new FileReader(
     "E:/test.txt"));// ¶Á³öÎÄ¼þ
   
    System.out.println(reader.readLine());
   

  } catch (Exception e) {

   e.printStackTrace();
  }
  }
	public static void main(String[] args)
	{
		Filetest a=new Filetest();
		a.test();
	}


 }