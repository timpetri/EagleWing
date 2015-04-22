package tapetri;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import tests.ModelFactory;

public class TestEagleWingWingController extends KSTestCase {
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
		
		ModelFactory.init(eagleWing.wings[0], "AS");
		
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wingPileViews[0], 0, 0);
		eagleWing.wingPileViews[0].getMouseManager().handleMouseEvent(press);

		// wastepile should now be empty
		assertEquals (0, eagleWing.wings[0].count()); 
		
	}
	
	public void testReleasedLogic() {
		
		// make sure wing is empty
		ModelFactory.init(eagleWing.wings[0], "");
		assertEquals (0, eagleWing.wings[0].count()); 
		
		// ace of spades in wastepie
		ModelFactory.init(eagleWing.wastePile, "AS");
		
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wastePileView, 0, 0);
		eagleWing.wastePileView.getMouseManager().handleMouseEvent(press);
		
		MouseEvent release = this.createReleased (eagleWing, eagleWing.wingPileViews[0], 0, 0);
		eagleWing.wingPileViews[0].getMouseManager().handleMouseEvent(release);
		
		assertEquals("AS", eagleWing.wings[0].peek().toString());
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.wastePile.count());
		
	

		
	}
}
