package tapetri;

import java.awt.event.MouseEvent;

import tests.ModelFactory;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestEagleWingDeckController extends KSTestCase {
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
		
		ModelFactory.init(eagleWing.deck, "AS");
		
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(eagleWing, eagleWing.deckView, 0, 0);
		eagleWing.deckView.getMouseManager().handleMouseEvent(press);

		// what do we know about the game after press on deck? Card dealt!
		assertEquals ("AS", eagleWing.wastePile.peek().toString()); 
		
		// create mouse press at (0,0) within the deckview; should reset deck
		eagleWing.deckView.getMouseManager().handleMouseEvent(press);
		
		assertEquals ("AS", eagleWing.deck.peek().toString()); 
		
	}
}
