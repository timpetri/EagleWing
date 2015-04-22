package tapetri;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import tests.ModelFactory;

public class TestEagleWingTrunkController extends KSTestCase {
	EagleWing eagleWing;
	GameWindow gw;

	@Override
	protected void setUp() {
		eagleWing = new EagleWing();
		gw = Main.generateWindow(eagleWing, Deck.OrderBySuit);
		// avoid AWT issues
		eagleWing.getContainer().refreshWidgets();
	}

	@Override
	protected void tearDown() {
		gw.dispose();
	}

	public void testPressLogic() {
		
		ModelFactory.init(eagleWing.trunk, "AS");
		// ModelFactory.init(eagleWing.foundations[0], "KS");
		
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wingPileViews[0], 0, 0);
		eagleWing.wingPileViews[0].getMouseManager().handleMouseEvent(press);

		// wastepile should now be empty
		assertEquals (0, eagleWing.wings[0].count()); 
		
	}
}
