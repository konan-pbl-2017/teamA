package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.gameMain.BaseScenarioGameContainer;
import framework.scenario.ScenarioManager;

/**
 * �V�i���I�Q�[���p���
 * @author Nitta
 *
 */
public class ScenarioGameContainer extends BaseScenarioGameContainer {
	
	public ScenarioGameContainer() {
		super();
	}

	@Override
	public void build(GraphicsConfiguration gc) {				
		super.build(gc);
		canvas.setRelativePosition(0.0f, 0.0f);		// 3D�\�����̍���[
		canvas.setRelativeSize(0.8f, 1.0f);		// 3D�\�����̃T�C�Y
		addCanvas(canvas);
		dialog.setRelativePosition(0.8f, 0.75f);	// �_�C�A���O�̍���[
		dialog.setFont(new Font("", Font.PLAIN, 12));	// �����̃t�H���g
		dialog.setColor(Color.WHITE);				// �����̐F
		addWidget(dialog);
		repaint();
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		if (key.getPlayer() == 0 && key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			scenario.fire("A");
		}
	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
	}
}