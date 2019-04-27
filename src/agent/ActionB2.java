package agent;

import core.Sfmt;
import environment.Field2;

public class ActionB2 {
	Field2 field;
	Sfmt rnd;
	
	public ActionB2(Field2 f, int p[]){
		field = f;
		rnd = new Sfmt(11);
	}
	
	public int act(int pos[], int e_pos[]){

		return (int)(rnd.NextUnif()*9);
	}


}
