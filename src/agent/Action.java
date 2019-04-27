package agent;

import environment.Field1;
import core.*;

public abstract class Action {
	Field1 field;
	Sfmt rnd;
	
	//フィールドはここに書く
	

	public Action(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	public abstract int act(int pos[], int e_pos[]);
}
