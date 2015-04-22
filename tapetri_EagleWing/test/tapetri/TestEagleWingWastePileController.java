package tapetri;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import tests.ModelFactory;


public class TestEagleWingWastePileController extends KSTestCase {
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
		
		ModelFactory.init(eagleWing.wastePile, "AS KS");
		
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wastePileView, 0, 0);
		eagleWing.wastePileView.getMouseManager().handleMouseEvent(press);

		// wastepile should now only have AS
		assertEquals ("AS", eagleWing.wastePile.peek().toString()); 
		
		
	}
}