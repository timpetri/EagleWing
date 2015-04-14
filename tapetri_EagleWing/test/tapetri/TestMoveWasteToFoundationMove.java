package tapetri;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestMoveWasteToFoundationMove extends TestCase {
	
	EagleWing eagleWing;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		eagleWing = new EagleWing();
		gw = Main.generateWindow(eagleWing, Deck.OrderBySuit);
	}
	
	@Override
	protected void tearDown() {
		gw.dispose();
	}
	
	public void testSimple () {
		
		Card topCard = eagleWing.deck.peek();
		DealCardMove dcm = new DealCardMove(eagleWing.deck, eagleWing.wastePile);

		
	}
	
}
