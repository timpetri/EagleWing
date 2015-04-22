package tapetri;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import tests.ModelFactory;

public class TestEagleWingFoundationController extends KSTestCase {
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

	public void testFromWasteLogic() {
		ModelFactory.init(eagleWing.wastePile, "AS");
		ModelFactory.init(eagleWing.foundations[0], "KS");
		
		assertEquals(1, eagleWing.foundations[0].count());
		
		// create mouse press at (0,0) within the wastepile view
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wastePileView, 0, 0);
		eagleWing.wastePileView.getMouseManager().handleMouseEvent(press);
		
		MouseEvent released = this.createReleased(eagleWing, eagleWing.foundationPileViews[0], 0, 0);
		eagleWing.foundationPileViews[0].getMouseManager().handleMouseEvent(released);
		
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[0].count());
	}
	
	public void testFromTrunkLogic() {
		ModelFactory.init(eagleWing.trunk, "AS");
		ModelFactory.init(eagleWing.foundations[0], "KS");
		
		assertEquals(1, eagleWing.foundations[0].count());
		
		// create mouse press at (0,0) within the trunk view
		MouseEvent press = this.createPressed(eagleWing, eagleWing.trunkView, 0, 0);
		eagleWing.trunkView.getMouseManager().handleMouseEvent(press);
		
		MouseEvent released = this.createReleased(eagleWing, eagleWing.foundationPileViews[0], 0, 0);
		eagleWing.foundationPileViews[0].getMouseManager().handleMouseEvent(released);
		
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[0].count());
	}
	
	public void testFromWingLogic() {
		ModelFactory.init(eagleWing.wings[0], "AS");
		ModelFactory.init(eagleWing.foundations[0], "KS");
		
		String topTrunk = eagleWing.trunk.peek().toString();
		
		assertEquals(1, eagleWing.foundations[0].count());
		
		// create mouse press at (0,0) within the wing view
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wingPileViews[0], 0, 0);
		eagleWing.wingPileViews[0].getMouseManager().handleMouseEvent(press);
		
		MouseEvent released = this.createReleased(eagleWing, eagleWing.foundationPileViews[0], 0, 0);
		eagleWing.foundationPileViews[0].getMouseManager().handleMouseEvent(released);
		
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[0].count());
		// a tiny fix for comparing face down with same card face up
		assertEquals(topTrunk, "["+eagleWing.wings[0].peek().toString()+"]");
	}
	
	
	
}
