package simulation;

import environment.*;
import agent.*;
import core.*;


public class Simulation1 {
	public static void start(int seed_num, int sim_count, String f_clock){
		int sim_max = 100000;
		Sfmt rnd = new Sfmt(seed_num);
		int action1 = 8, action2 = 8;
		int hit1, hit2;
		int score1, score2;

		Field1 f1 = new Field1(rnd);

		int pos1[] = {0, 0};
		int pos2[] = {4, 4};
		AgentA player1 = new AgentA(f1, pos1);
		AgentB player2 = new AgentB(f1, pos2);
		score1 = 0;
		score2 = 0;
		
		//�X�e�b�v�P�ʂł̃��[�v
		for(int time=0;time<sim_max;time++){
			if(time%(sim_max/10) == 0){
				System.out.println("now "+ "_" + f_clock + "[" + sim_count + "]: " + time + "step------------------------------------------------------------------------");
			}
						
			//�C�x���g����
			f1.happen_event(rnd);
			
			//�G�[�W�F���g���s��(action = 0(��ړ�), 1(�E�ړ�), 2(���ړ�), 3(���ړ�), 4(��U��), 5(�E�U��), 6(���U��), 7(���U��), 8(�������Ȃ�))
			action1 = 8;
			action2 = 8;
			if(player1.canAct(time, rnd)) action1 = player1.action(player2.getPos());
			if(player2.canAct(time, rnd)) action2 = player2.action(player1.getPos());

			if(action1 < 4) player1.move(action1);
			if(action2 < 4) player2.move(action2);

			hit1 = 0;
			hit2 = 0;
			if(action1 >= 4) hit1 = player1.attack(action1, player2.getPos());
			if(action2 >= 4) hit2 = player2.attack(action2, player1.getPos());
			
			if(hit1 != 0) player2.broken(time);
			if(hit2 != 0) player1.broken(time);
			
			pos1 = player1.getPos();
			pos2 = player2.getPos();

			//�����}�X�ɂ���ꍇ�͂ǂ������V�͊l���ł�����V�����ł���
			if(pos1[0] == pos2[0] && pos1[1] == pos2[1] && pos1[0] != -1){
				f1.acquire_event(pos1[0], pos1[1]);
			}else{
				if(player1.getStatus() != "broken"){
					score1 += f1.acquire_event(pos1[0], pos1[1]);
				}
				if(player2.getStatus() != "broken"){
					score2 += f1.acquire_event(pos2[0], pos2[1]);
				}

			}

			//log��������
			//�}�b�v���ɔ������Ă���C�x���g�̈ʒu��\��
/*			for(int i=0;i<5;i++){
				for(int j=0;j<5;j++){
					System.out.print(f1.getEvent(j, i) + ", ");
				}
				System.out.println();
			}
*/
			//���ꂼ��̃G�[�W�F���g�����̃^�[���ɂƂ����s�����o��
			System.out.println("action player1: " + action1 + ", player2: " + action2);
			//���ꂼ��̃G�[�W�F���g�̂��̃^�[���̍��W���o��
			System.out.println("position player1: (" + player1.getPos()[0] + "," + player1.getPos()[1] + "), player2: (" + player2.getPos()[0] + "," + player2.getPos()[1] + ")");
			//���ꂼ��̃G�[�W�F���g������܂łɊl�����Ă��鑍�X�R�A���o��
			System.out.println("score player1: " + score1 + ", player2: " + score2);
			System.out.println();
		}
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				System.out.print(f1.getP(j, i) + ", ");
			}
			System.out.println();
		}
	}
}
