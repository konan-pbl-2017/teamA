package game;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;

public class EndingContainer extends RWTContainer {
	private TemplateRPG2D game;

	public EndingContainer(TemplateRPG2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("おしまい");
		startLabel.setRelativePosition(0.1f, 0.5f);
		startLabel.setColor(Color.WHITE);
		RWTLabel startLabel2 = new RWTLabel();
		startLabel2.setString("bボタンでリスタート");
		startLabel2.setRelativePosition(0.10f, 0.8f);
		Font f = new Font("", Font.PLAIN, 30);
		startLabel.setFont(f);
		addWidget(startLabel);
		startLabel2.setFont(f);
		addWidget(startLabel2);
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			game.restart();
		}
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

}
