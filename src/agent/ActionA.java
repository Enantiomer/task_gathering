package agent;

import environment.Field1;
import core.*;

//���Ɏg���������C�u����������΂����ɏ���. �� java.util.*;


public class ActionA {
	Field1 field;
	Sfmt rnd;
	
	//�t�B�[���h�͂����ɏ���
	

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//���̊֐����ɍl�����A���S���Y�����L�q����
	//�������g�������ꍇ��rnd.NextUnif()��0�ȏ�1�����̗����𓾂���(���͈̗̔͂���������Bcore/Sfmt.java�Q��)
	public int act(int pos[], int e_pos[]){

		
		
		//�Ԃ�l�ʂ̍s���ꗗ: 0(��ړ�), 1(�E�ړ�), 2(���ړ�), 3(���ړ�), 4(��U��), 5(�E�U��), 6(���U��), 7(���U��), ����ȊO(�������Ȃ�)
		return (int)(rnd.NextUnif()*9);
	}

}
