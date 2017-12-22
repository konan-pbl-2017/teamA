package game;

import java.awt.Color;
import java.math.BigDecimal;

import game.EndingContainer;
import game.StartContainer;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.audio.BGM3D;
import framework.audio.Sound3D;
import framework.game2D.Sprite;
import framework.gameMain.BaseScenarioGameContainer;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleRolePlayingGame;
import framework.model3D.ModelFactory;
import framework.model3D.Object3D;
import framework.model3D.Position3D;
import framework.model3D.Quaternion3D;
import framework.model3D.Universe;
import framework.physics.PhysicsUtility;
import framework.scenario.Event;
import framework.scenario.ScenarioManager;
import framework.scenario.ScenarioState;

public class TemplateRPG2D extends SimpleRolePlayingGame {
	private MapStage map;
	private Player player;
	private Sprite king;
	private Sprite enemy;
	int up = 0, down = 1, left = 2, right = 3;
	
	private IGameState startGameState = null;
	private IGameState endingGameState = null;

	int muki = 0;
	int kagi_tsukue = 0;

	int password = 0;

	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

	
	public TemplateRPG2D() {
		super();
		startGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateRPG2D.this.frame = frame;
				RWTContainer container = new StartContainer(TemplateRPG2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		endingGameState = new IGameState() {
			@Override
			public void init(RWTFrame3D frame) {
				TemplateRPG2D.this.frame = frame;
				RWTContainer container = new EndingContainer(TemplateRPG2D.this);
				changeContainer(container);
			}
			@Override
			public boolean useTimer() {
				return false;
			}
			@Override
			public void update(RWTVirtualController virtualController, long interval) {
			}
		};
		setCurrentGameState(startGameState);
	}
	
	public void init(Universe universe) {
		map = new MapStage();
		universe.place(map);
		setViewRange(40, 40);
		camera.addTarget(map);

		Sound3D BGM = BGM3D.registerBGM("data\\sound1\\honpen1.wav");
		BGM3D.playBGM(BGM);//BGM��ݒ肵�ė���

		// �v���C���[�̔z�u
		player = new Player("data\\images\\CharaFront.gif");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);

		// ���l�̔z�u
		king = new Sprite("data\\RPG\\king.png");
		king.setPosition(18.0, 24.0);
		king.setCollisionRadius(0.5);
		universe.place(king);

		// �v���C���[����ʂ̒�����
		setCenter(player);

		// �V�i���I�̐ݒ�
		setScenario("data\\game\\Scenario\\scenario.xml");
	}

	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\item\\nitta.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);

		// �G����ʂ̒�����
		setSubCenter(enemy);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		frame = new RWTFrame3D();
		frame.setSize(1000, 800);
		frame.setTitle("Template for 2D Role Playing Game");
		frame.setBackground(Color.BLACK);
		return frame;
	}
	
	public void restart() {
		stop();
		setCurrentGameState(startGameState);
		start();
	}

	public void play() {
		stop();
		setCurrentGameState(this);
		start();
	}

	public void ending() {
		stop();
		setCurrentGameState(endingGameState);
		start();
	}

	@Override
	protected RWTContainer createRWTContainer() {
		container = new ScenarioGameContainer();
		return container;
	}
	
	// �퓬�p��ʂ̍쐬
	public BaseScenarioGameContainer createSubRWTContainer() {
		subContainer = new FightContainer();
		return subContainer;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {		
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		boolean resetVelocity = map.checkGridPoint(player);

		// �덷�ɂ��ʒu�C�����s�����߁A�v���C���[��x������y������0.0�̎��A�ʒu�̒l��؂�グ��
		if (player.getVelocity().getX() == 0.0
				&& player.getVelocity().getY() == 0.0) {
			player.setPosition(
					new BigDecimal(player.getPosition().getX()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue(),
					new BigDecimal(player.getPosition().getY()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		// ���x��0.0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (resetVelocity) {
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if (!disableControl) {
			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
				player.setVelocity(-6.0, 0.0);
				player.setImage("data\\images\\CharaLeft.gif");
				muki = left;
				disableControl = true;
			}
			// �E
			else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
				player.setVelocity(6.0, 0.0);
				player.setImage("data\\images\\CharaRight.gif");
				muki = right;
				disableControl = true;

			}
			// ��
			else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 6.0);
				player.setImage("data\\images\\CharaBack.gif");
				muki = up;
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -6.0);
				player.setImage("data\\images\\CharaFront.gif");
				muki = down;
				disableControl = true;
			}
		}

		//�L�����������ʒu�ɒu���Ȃ�������(�n�}�����Ƃ��̂���)
		if(virtualController.isKeyDown(0, RWTVirtualController.LEFT)){
		}
		// �L�����������ʒu�ɒu���Ȃ�������(�n�}�����Ƃ��̂���)
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.setPosition(14.0, 14.0);
		}

		player.motion(interval, map);

		// �Փ˔���
		
//		if (player.checkCollision(king)) {
		if(muki==right && player.getPosition().getX()==8 && player.getPosition().getY()==2 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)||(muki==left && player.getPosition().getX()==12 && player.getPosition().getY()==2 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C))||muki==down && player.getPosition().getX()==10 && player.getPosition().getY()==4 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// ���΂�𒲂ׂ��ꍇ
			scenario.fire("���΂�𒲂ׂ�");	// �u���΂�𒲂ׂ�v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
		if(muki==up && player.getPosition().getX()==22 && player.getPosition().getY()==18 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �X�}�z�𒲂ׂ��ꍇ
			scenario.fire("�X�}�z�𒲂ׂ�"); // �u�X�}�z�𒲂ׂ�v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
		if(muki==left && player.getPosition().getX()==4 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �M���𒲂ׂ��ꍇ
			scenario.fire("�M���𒲂ׂ�");	//�M���𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()==26 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// ���𒲂ׂ��ꍇ
			scenario.fire("���𒲂ׂ�");	//���𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()==10 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �J�����_�[�𒲂ׂ��ꍇ
			scenario.fire("�J�����_�[�𒲂ׂ�");	//�J�����_�[�𒲂ׂ�
			password = 1;
		}
		if(muki==up && player.getPosition().getX()>=14 && player.getPosition().getX()<=18 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �^���X�𒲂ׂ��ꍇ
			scenario.fire("�^���X�𒲂ׂ�");	//�^���X�𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()>=4 && player.getPosition().getX()<=8 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �{�I�𒲂ׂ��ꍇ
			scenario.fire("�{�I�𒲂ׂ�");	//�{�I�𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()==20 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �ʐ^�𒲂ׂ��ꍇ
			scenario.fire("�ʐ^�𒲂ׂ�");	//�ʐ^�𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()==6 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �����𒲂ׂ��ꍇ
			scenario.fire("�����𒲂ׂ�");	//�����𒲂ׂ�
		}
		if(muki==up && player.getPosition().getX()==8 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// ���̉E�̈����o���𒲂ׂ��ꍇ
			scenario.fire("���̉E�̈����o���𒲂ׂ�");	//���̈����o���𒲂ׂ�
			kagi_tsukue = 1;
		}
		if(muki==up && player.getPosition().getX()==4 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// ���̍��̈����o���𒲂ׂ��ꍇ
			if(kagi_tsukue == 0){
				scenario.fire("���̍��̈����o���𒲂ׂ�1");	//���̍��̈����o���𒲂ׂ�i���͎����Ă��Ȃ��j
			} else {
				scenario.fire("���̍��̈����o���𒲂ׂ�2");	//���̍��̈����o���𒲂ׂ�i���͎����Ă���j
			}
		}
		if(muki==up && player.getPosition().getX()==22 && player.getPosition().getY()==18 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// �X�}�z���ׂ��ꍇ
			if(password == 0){
				scenario.fire("�X�}�z�𒲂ׂ�1");	//�X�}�z�𒲂ׂ�i���͎����Ă��Ȃ��j
			} else {
				scenario.fire("�X�}�z�𒲂ׂ�2");	//�X�}�z�𒲂ׂ�i���͎����Ă���j
			}
		}
		//���W�擾�p(W�L�[�Ńv���C���[�̌��݂̈ʒu���擾)
		if(virtualController.isKeyDown(0, RWTVirtualController.UP)){
			System.out.println(player.getPosition().getX());
			System.out.println(player.getPosition().getY());
		}
	}

	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// �V�i���I�i�s�ɂ�鐢�E�ւ̍�p�������ɏ���
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}

	/**
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
