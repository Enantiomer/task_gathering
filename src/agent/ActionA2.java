package agent;

import environment.Field2;
import core.*;

//���Ɏg���������C�u����������΂����ɏ���. �� java.util.*;


public class ActionA2 {
	Field2 field;
	Sfmt rnd;
	
	//�t�B�[���h�͂����ɏ���
	

	public ActionA2(Field2 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//���̊֐����ɍl�����A���S���Y�����L�q����
	//�������g�������ꍇ��rnd.NextUnif()��0�ȏ�1�����̗����𓾂���(���͈̗̔͂���������Bcore/Sfmt.java�Q��) �g�p��: if(rnd.NextUnif() < 0.3){30%�̊m���Ŕ���������������}
	public int act(int pos[], int e_pos[]){
		//�ʒu(x,y)��z����(0(��),1(�E),2(��),3(��))�ɐi�߂邩�ǂ���: field.getPosStatus(x,y,z)  �Ԃ�l:{1: �i�߂�, 1�ȊO: �i�߂Ȃ�}
		//�ʒu(x,y)�̊e�����ւ̐i�s�ۂ̔z��([0](��),[1](�E),[2](��),[3](��)) field.getPosStatus(x,y)  �Ԃ�l:{�e�����Ɉړ��ł��邩�̔z��}  ��: {1,0,1,0}

		
		
		//�Ԃ�l�ʂ̍s���ꗗ: 0(��ړ�), 1(�E�ړ�), 2(���ړ�), 3(���ړ�), 4(��U��), 5(�E�U��), 6(���U��), 7(���U��), ����ȊO(�������Ȃ�)
		return (int)(rnd.NextUnif()*9);
	}

}
