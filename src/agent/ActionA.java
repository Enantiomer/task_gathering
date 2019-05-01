package agent;

import environment.Field1;
import core.*;

//���Ɏg���������C�u����������΂����ɏ���. �� java.util.*;
import static java.lang.Math.abs;
import java.util.Arrays;


public class ActionA {
	Field1 field;
	Sfmt rnd;

	//�t�B�[���h�͂����ɏ���
	private static final int REWARD_THRESHOLD = 7; // ��V����臒l
	private static final int STEP_THRESHOLD = 60000; // �X�e�b�v����臒l
	private static int score1 = 0; // �X�R�A
	private static int score2 = 0;
	private static int numOfsteps = 0; // �X�e�b�v��

	public ActionA(Field1 f, int p[]){
		field = f;
		rnd = new Sfmt(7);
	}
	
	//���̊֐����ɍl�����A���S���Y�����L�q����
	//�������g�������ꍇ��rnd.NextUnif()��0�ȏ�1�����̗����𓾂���(���͈̗̔͂���������Bcore/Sfmt.java�Q��) �g�p��: if(rnd.NextUnif() < 0.3){30%�̊m���Ŕ���������������}
	public int act(int pos[], int e_pos[]){
		//�ʒu(x,y)��z����(0(��),1(�E),2(��),3(��))�ɐi�߂邩�ǂ���: field.getPosStatus(x,y,z)  �Ԃ�l:{1: �i�߂�, 1�ȊO: �i�߂Ȃ�}
		//�ʒu(x,y)�̊e�����ւ̐i�s�ۂ̔z��([0](��),[1](�E),[2](��),[3](��)) field.getPosStatus(x,y)  �Ԃ�l:{�e�����Ɉړ��ł��邩�̔z��}  ��: {1,0,1,0}

		// score�̊T�Z�l���v�Z����
		calcScore(pos, e_pos);
		// �X�e�b�v��
		numOfsteps++;
//		System.out.println("score1: " + score1 + ", score2: " + score2);
//		System.out.println("numofSteps: " + numOfsteps);
		if (numOfsteps > STEP_THRESHOLD) {
			// ������STEP_THRESHOLD�X�e�b�v�i�񂾂Ƃ��A������葊�肪��葽���̕�V�𓾂Ă����ꍇ�ɂ͍U����D�悷��B
			if (score1 > score2 && isAttackable(pos, e_pos) && rnd.NextUnif() < 0.8) {
				return determineAttackDirection(pos, e_pos);
			}
			return determineMoveDirection(pos);
		}
		if (field.getRewardCount() > REWARD_THRESHOLD) { // ��V�̐���臒l��葽��������
			// �ړ���D��
			if (rnd.NextUnif() < 0.6) return determineMoveDirection(pos);
			if (isAttackable(pos, e_pos)) return determineAttackDirection(pos, e_pos);
			return (int)(rnd.NextUnif()*4);
		} else {
			// �U����D��
			if (rnd.NextUnif() < 0.6 && isAttackable(pos, e_pos)) return determineAttackDirection(pos, e_pos);
			return determineMoveDirection(pos);
		}
		
		//�Ԃ�l�ʂ̍s���ꗗ: 0(��ړ�), 1(�E�ړ�), 2(���ړ�), 3(���ړ�), 4(��U��), 5(�E�U��), 6(���U��), 7(���U��), ����ȊO(�������Ȃ�)
	}

	private boolean isAttackable(int pos[], int e_pos[]) {
		// ����̈ʒu�����̈ړ����ɍU���͈͂ɂ���ꍇ��true��Ԃ����\�b�h
		if (abs(pos[0] - e_pos[0]) >= 2 && abs(pos[1] - e_pos[1]) >= 2) return false;
		return true;
	}

	private void calcScore(int pos[], int e_pos[]) {
		if (pos[0] == -1 || pos[1] == -1 || e_pos[0] == -1 || e_pos[1] == -1) return;
		score1 += field.getEvent(pos[0], pos[1]);
		score2 += field.getEvent(e_pos[0], e_pos[1]);
	}

	private int determineMoveDirection(int pos[]) {
		for (int y = pos[1] - 1; y <= pos[1] + 1; y++) {
			for (int x = pos[0] - 1; x <= pos[0] + 1; x++) {
				if (x > -1 && x < 5 && y > -1 && y < 5 && field.getEvent(x, y) == 1) {
					if (x == pos[0] && y == pos[1] + 1 && field.getPosStatus(pos[0], pos[1], 0) == 1) return 0; //��
					if (x == pos[0] + 1 && y == pos[1] && field.getPosStatus(pos[0], pos[1], 1) == 1) return 1; //�E
					if (x == pos[0] && y == pos[1] - 1 && field.getPosStatus(pos[0], pos[1], 2) == 1) return 2; //��
					if (x == pos[0] - 1 && y == pos[1] && field.getPosStatus(pos[0], pos[1], 3) == 1) return 3; //��
				}
			}
		}
		return randomDirection(pos);
	}

	private int randomDirection(int pos[]) {
		if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,1,1,0})) {
			// ��ƍ��ɍs���Ȃ�
			int[] movableDirections = {1,2};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,0,1,1})) {
			// ��ƉE�ɍs���Ȃ�
			int[] movableDirections = {2,3};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,0,0,1})) {
			// �E�Ɖ��ɍs���Ȃ�
			int[] movableDirections = {0,3};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,0,0})) {
			// ���ƍ��ɍs���Ȃ�
			int[] movableDirections = {0,1};
			return movableDirections[(int)(rnd.NextUnif() * 2)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,1,0})) {
			int[] movableDirections = {0,1,2};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,1,0,1})) {
			int[] movableDirections = {0,1,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{1,0,1,1})) {
			int[] movableDirections = {0,2,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else if (Arrays.equals(field.getPosStatus(pos[0], pos[1]), new int[]{0,1,1,1})) {
			int[] movableDirections = {1,2,3};
			return movableDirections[(int)(rnd.NextUnif() * 3)];
		} else {
			return (int)(rnd.NextUnif() * 4);
		}
	}

	private int determineAttackDirection(int pos[], int e_pos[]) {
		// ���肪�E��
		if (e_pos[0] == pos[0] + 1) {
			if (e_pos[1] > pos[1] + 1) return 4; // ��U��
			if (e_pos[1] == pos[1] + 1) {
				int[] attackDirections = {4, 5};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; //�E����ɍU��
			}
			if (e_pos[1] == pos[1]) return 5; // �E�U��
			if (e_pos[1] == pos[1] - 1) {
				int[] attackDirections = {5, 6};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // �E�����ɍU��
			}
			return 6; //���U��
		}
		// ���肪����
		if (e_pos[0] == pos[0] - 1) {
			if (e_pos[1] > pos[1] + 1) return 4; // ��U��
			if (e_pos[1] == pos[1] + 1) {
				int[] attackDirections = {4, 7};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // ������ɍU��
			}
			if (e_pos[1] == pos[1]) return 7; // ���U��
			if (e_pos[1] == pos[1] - 1) {
				int[] attackDirections = {6, 7};
				return attackDirections[(int)(rnd.NextUnif() * 2)]; // �������ɍU��
			}
			return 6; // ���U��
		}
		// ���肪�^�ォ�^��
		if (e_pos[1] > pos[1]) return 4;
		if (e_pos[1] == pos[1]) {
			return 4 + (int)(rnd.NextUnif()*4);
		}
		return 6;
	}
}
