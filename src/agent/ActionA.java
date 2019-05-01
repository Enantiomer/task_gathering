package agent;

import environment.Field1;
import core.*;

//���Ɏg���������C�u����������΂����ɏ���. �� java.util.*;
import static java.lang.Math.abs;


public class ActionA {
	Field1 field;
	Sfmt rnd;

	//�t�B�[���h�͂����ɏ���
	private static final int REWARD_THRESHOLD = 5;
	

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//���̊֐����ɍl�����A���S���Y�����L�q����
	//�������g�������ꍇ��rnd.NextUnif()��0�ȏ�1�����̗����𓾂���(���͈̗̔͂���������Bcore/Sfmt.java�Q��) �g�p��: if(rnd.NextUnif() < 0.3){30%�̊m���Ŕ���������������}
	public int act(int pos[], int e_pos[]){
		//�ʒu(x,y)��z����(0(��),1(�E),2(��),3(��))�ɐi�߂邩�ǂ���: field.getPosStatus(x,y,z)  �Ԃ�l:{1: �i�߂�, 1�ȊO: �i�߂Ȃ�}
		//�ʒu(x,y)�̊e�����ւ̐i�s�ۂ̔z��([0](��),[1](�E),[2](��),[3](��)) field.getPosStatus(x,y)  �Ԃ�l:{�e�����Ɉړ��ł��邩�̔z��}  ��: {1,0,1,0}
		if (field.getRewardCount() > REWARD_THRESHOLD) {
			if (rnd.NextUnif() < 0.6) {
				// �Ƃ肠�����ړ�����
				// TODO: ��V�̈ʒu���擾���Ă������Ɉړ�����̂��ۂ�

				return (int)(rnd.NextUnif()*4);
			}
			// �Ƃ肠�����U������
			return 4 + (int)(rnd.NextUnif()*4);
		} else {
			if (rnd.NextUnif() < 0.6) {
				// �Ƃ肠�����U������
				return 4 + (int) (rnd.NextUnif() * 4);
			}
			// �Ƃ肠�����ړ�����
			return (int) (rnd.NextUnif() * 4);
		}
		
		//�Ԃ�l�ʂ̍s���ꗗ: 0(��ړ�), 1(�E�ړ�), 2(���ړ�), 3(���ړ�), 4(��U��), 5(�E�U��), 6(���U��), 7(���U��), ����ȊO(�������Ȃ�)
//		return (int)(rnd.NextUnif()*9);
	}

	private boolean isAttackable(int pos[], int e_pos[]) {
		// ����̈ʒu�����̈ړ����ɍU���͈͂ɂ���ꍇ��true��Ԃ����\�b�h
		if (abs(pos[0] - e_pos[0]) >= 2 && abs(pos[1] - e_pos[1]) >= 2) return false;
		return true;
	}
}
