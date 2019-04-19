package agent;

import core.Sfmt;
import environment.Field1;

public class AgentB {
	Field1 field;
	private int pos[];
	String status;
	ActionB action_B;
	int broken_time;

	public AgentB(Field1 f, int p[]){
		field = f;
		pos = p;
		status = "active";
		action_B = new ActionB(field, pos);
		broken_time = Integer.MIN_VALUE;
	}
	
	public int action(int[] e_pos){
		return action_B.act(pos, e_pos);
	}

	public void move(int act){
		switch(act){
		case 0:
			if(field.getPosStatus(pos[0],pos[1],act) == 1) pos[1]++;
			break;
		case 1:
			if(field.getPosStatus(pos[0],pos[1],act) == 1) pos[0]++;
			break;
		case 2:
			if(field.getPosStatus(pos[0],pos[1],act) == 1) pos[1]--;
			break;
		case 3:
			if(field.getPosStatus(pos[0],pos[1],act) == 1) pos[0]--;
			break;
		default:
		}
	}
	
	public int attack(int act, int e_pos[]){
		int hit = 0;
		switch(act){
		case 4:
			if(pos[0] == e_pos[0] && pos[1] < e_pos[1]) hit = 1;
			break;
		case 5:
			if(pos[1] == e_pos[1] && pos[0] < e_pos[0]) hit = 1;
			break;
		case 6:
			if(pos[0] == e_pos[0] && pos[1] > e_pos[1]) hit = 1;
			break;
		case 7:
			if(pos[1] == e_pos[1] && pos[0] > e_pos[0]) hit = 1;
			break;
		default:
		}
		return hit;
	}
	
	public Boolean canAct(int time, Sfmt rnd){
		if(status != "broken")return true;
		if(time - broken_time < 6)return false;
		
		status = "active";
		pos[0] = (int)(rnd.NextUnif()*5);
		pos[1] = (int)(rnd.NextUnif()*5);
		return true;
	}
	
	public int[] getPos(){
		return pos;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void broken(int time){
		status = "broken";
		broken_time = time;
		pos[0] = -1;
		pos[1] = -1;
	}
}
