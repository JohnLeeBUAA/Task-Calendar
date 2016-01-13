package pac;

//class Object2WriteOrRead is used to pack timetable up as an Object to be written 
//to file or receive Object from file
import java.io.Serializable;

import javax.swing.JLabel;
/*
 * øŒ≥Ã¿‡∂¡–¥¿‡
 */
public class Object2WriteOrRead implements Serializable {
	private String[][] classes;

	public Object2WriteOrRead() {
		classes = new String[11][];
		for (int i = 0; i < 11; i++) {
			classes[i] = new String[6];
			for (int j = 0; j < 6; j++) {
				classes[i][j] = new String("");
			}
		}
	}

	public void setClasses(String str, int i, int j) {
		classes[i][j] = str;
	}

	public String getClass(int i, int j) {
		return classes[i][j];
	}

}
