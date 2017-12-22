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

	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
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
		BGM3D.playBGM(BGM);//BGMを設定して流す

		// プレイヤーの配置
		player = new Player("data\\images\\CharaFront.gif");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);

		// 王様の配置
		king = new Sprite("data\\RPG\\king.png");
		king.setPosition(18.0, 24.0);
		king.setCollisionRadius(0.5);
		universe.place(king);

		// プレイヤーを画面の中央に
		setCenter(player);

		// シナリオの設定
		setScenario("data\\game\\Scenario\\scenario.xml");
	}

	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\item\\nitta.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);

		// 敵を画面の中央に
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
	
	// 戦闘用画面の作成
	public BaseScenarioGameContainer createSubRWTContainer() {
		subContainer = new FightContainer();
		return subContainer;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {		
		// 迷路ゲームステージを構成するオブジェクトの位置とプレイヤーの位置をもとに速度を0にするかどうかを調べる。
		boolean resetVelocity = map.checkGridPoint(player);

		// 誤差による位置修正を行うため、プレイヤーのx成分とy成分が0.0の時、位置の値を切り上げる
		if (player.getVelocity().getX() == 0.0
				&& player.getVelocity().getY() == 0.0) {
			player.setPosition(
					new BigDecimal(player.getPosition().getX()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue(),
					new BigDecimal(player.getPosition().getY()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		// 速度が0.0にするフラグが立っていれば、速度を0にする
		if (resetVelocity) {
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// キャラが移動していなければ、キー操作の処理を行える。
		if (!disableControl) {
			// キー操作の処理
			// 左
			if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
				player.setVelocity(-6.0, 0.0);
				player.setImage("data\\images\\CharaLeft.gif");
				muki = left;
				disableControl = true;
			}
			// 右
			else if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
				player.setVelocity(6.0, 0.0);
				player.setImage("data\\images\\CharaRight.gif");
				muki = right;
				disableControl = true;

			}
			// 上
			else if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 6.0);
				player.setImage("data\\images\\CharaBack.gif");
				muki = up;
				disableControl = true;
			}
			// 下
			else if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -6.0);
				player.setImage("data\\images\\CharaFront.gif");
				muki = down;
				disableControl = true;
			}
		}

		//キャラを初期位置に置きなおす処理(ハマったときのため)
		if(virtualController.isKeyDown(0, RWTVirtualController.LEFT)){
		}
		// キャラを初期位置に置きなおす処理(ハマったときのため)
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.setPosition(14.0, 14.0);
		}

		player.motion(interval, map);

		// 衝突判定
		
//		if (player.checkCollision(king)) {
		if(muki==right && player.getPosition().getX()==8 && player.getPosition().getY()==2 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)||(muki==left && player.getPosition().getX()==12 && player.getPosition().getY()==2 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C))||muki==down && player.getPosition().getX()==10 && player.getPosition().getY()==4 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// かばんを調べた場合
			scenario.fire("かばんを調べる");	// 「かばんを調べる」というイベントを発生する（シナリオが進む）
		}
		if(muki==up && player.getPosition().getX()==22 && player.getPosition().getY()==18 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// スマホを調べた場合
			scenario.fire("スマホを調べる"); // 「スマホを調べる」というイベントを発生する（シナリオが進む）
		}
		if(muki==left && player.getPosition().getX()==4 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 筆箱を調べた場合
			scenario.fire("筆箱を調べる");	//筆箱を調べる
		}
		if(muki==up && player.getPosition().getX()==26 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 扉を調べた場合
			scenario.fire("扉を調べる");	//扉を調べる
		}
		if(muki==up && player.getPosition().getX()==10 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// カレンダーを調べた場合
			scenario.fire("カレンダーを調べる");	//カレンダーを調べる
			password = 1;
		}
		if(muki==up && player.getPosition().getX()>=14 && player.getPosition().getX()<=18 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// タンスを調べた場合
			scenario.fire("タンスを調べる");	//タンスを調べる
		}
		if(muki==up && player.getPosition().getX()>=4 && player.getPosition().getX()<=8 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 本棚を調べた場合
			scenario.fire("本棚を調べる");	//本棚を調べる
		}
		if(muki==up && player.getPosition().getX()==20 && player.getPosition().getY()==26 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 写真を調べた場合
			scenario.fire("写真を調べる");	//写真を調べる
		}
		if(muki==up && player.getPosition().getX()==6 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 資料を調べた場合
			scenario.fire("資料を調べる");	//資料を調べる
		}
		if(muki==up && player.getPosition().getX()==8 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 机の右の引き出しを調べた場合
			scenario.fire("机の右の引き出しを調べる");	//机の引き出しを調べる
			kagi_tsukue = 1;
		}
		if(muki==up && player.getPosition().getX()==4 && player.getPosition().getY()==8 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// 机の左の引き出しを調べた場合
			if(kagi_tsukue == 0){
				scenario.fire("机の左の引き出しを調べる1");	//机の左の引き出しを調べる（鍵は持っていない）
			} else {
				scenario.fire("机の左の引き出しを調べる2");	//机の左の引き出しを調べる（鍵は持っている）
			}
		}
		if(muki==up && player.getPosition().getX()==22 && player.getPosition().getY()==18 && virtualController.isKeyDown(0, RWTVirtualController.BUTTON_C)){
			// スマホ調べた場合
			if(password == 0){
				scenario.fire("スマホを調べる1");	//スマホを調べる（鍵は持っていない）
			} else {
				scenario.fire("スマホを調べる2");	//スマホを調べる（鍵は持っている）
			}
		}
		//座標取得用(Wキーでプレイヤーの現在の位置を取得)
		if(virtualController.isKeyDown(0, RWTVirtualController.UP)){
			System.out.println(player.getPosition().getX());
			System.out.println(player.getPosition().getY());
		}
	}

	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// シナリオ進行による世界への作用をここに書く
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}

	/**
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
