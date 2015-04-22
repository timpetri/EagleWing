package tapetri;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestMoves extends KSTestCase {
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
	
	
	public void testAutoMove() {
		
		// set wing to be empty
		ModelFactory.init(eagleWing.wings[0], "");
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals(13, eagleWing.trunk.count());
		assertEquals("AS", eagleWing.trunk.peek().getName());
		
		AutoTrunkToWingMove atwm = new AutoTrunkToWingMove(eagleWing.trunk, eagleWing.wings[0]);
		
		assertTrue(atwm.valid(eagleWing));
		
		atwm.doMove(eagleWing);
		
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals("AS", eagleWing.wings[0].peek().getName());
		assertEquals(12, eagleWing.trunk.count());		
		
		atwm.undo(eagleWing);
		
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals("AS", eagleWing.trunk.peek().getName());
		assertEquals(13, eagleWing.trunk.count());	
		
		
		
	}
	
	public void testWasteToFoundation() {
		// set deck to contain  6H and 5S
		ModelFactory.init(eagleWing.wastePile, "5S 6H");
		
		assertEquals(5, eagleWing.rankOfFoundation.getValue());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals(0, eagleWing.foundations[1].count());
		assertEquals("5H", eagleWing.foundations[0].peek().getName());
		
		Card cardInWaste = eagleWing.wastePile.get();
		
		MoveWasteToFoundationMove wtfm = new MoveWasteToFoundationMove(eagleWing.wastePile, eagleWing.foundations[0], cardInWaste, eagleWing.rankOfFoundation.getValue());
		
		assertTrue(wtfm.valid(eagleWing));
		
		wtfm.doMove(eagleWing);
		
		assertEquals(1, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[0].count());
		assertEquals("6H", eagleWing.foundations[0].peek().getName());
		
		Card secCardInWaste = eagleWing.wastePile.get();
		
		MoveWasteToFoundationMove wtfm2 = new MoveWasteToFoundationMove(eagleWing.wastePile, eagleWing.foundations[1], secCardInWaste, eagleWing.rankOfFoundation.getValue());
		
		assertTrue(wtfm2.valid(eagleWing));
		
		wtfm2.doMove(eagleWing);
		
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(1, eagleWing.foundations[1].count());
		assertEquals("5S", eagleWing.foundations[1].peek().getName());
		
	}
	
	public void testWasteToWingMove() {
		// set deck to contain AS
		ModelFactory.init(eagleWing.wastePile, "AS");
		
		// set wing and trunk to be empty
		ModelFactory.init(eagleWing.wings[0], "");
		ModelFactory.init(eagleWing.trunk, "");
		
		assertEquals(1, eagleWing.wastePile.count());
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.trunk.count());
		
		Card cardInWaste = eagleWing.wastePile.get();
		
		MoveWasteToWingMove wtwm = new MoveWasteToWingMove(eagleWing.wastePile, cardInWaste, eagleWing.wings[0]);
		assertTrue(wtwm.valid(eagleWing));
		
		// do move
		wtwm.doMove(eagleWing);
		
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.trunk.count());
		assertEquals("AS", eagleWing.wings[0].peek().toString());
		
		// undo move
		wtwm.undo(eagleWing);
		
		assertEquals(1, eagleWing.wastePile.count());
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.trunk.count());
		assertEquals("AS", eagleWing.wastePile.peek().toString());
	}
	
	
	public void testRestockDeckMove() {
		// set deck to be empty
		ModelFactory.init(eagleWing.deck, "");
		
		// set waste to contain cards
		ModelFactory.init(eagleWing.wastePile, "QS KS AS");
		
		assertEquals(0, eagleWing.deck.count());
		assertEquals(3, eagleWing.wastePile.count());
		
		int numRedeals = eagleWing.numRedeals.getValue();
		assertEquals(0, numRedeals);
		
		RestockDeckMove rdm = new RestockDeckMove(eagleWing.wastePile, eagleWing.deck, eagleWing.numRedeals);
		
		// do the move
		rdm.doMove(eagleWing);
		
		assertEquals(3, eagleWing.deck.count());
		assertEquals(0, eagleWing.wastePile.count());
		
		numRedeals = eagleWing.numRedeals.getValue();
		assertEquals(1, numRedeals);
		
		// undo move
		rdm.undo(eagleWing);
		
		assertEquals(0, eagleWing.deck.count());
		assertEquals(3, eagleWing.wastePile.count());
		
		numRedeals = eagleWing.numRedeals.getValue();
		assertEquals(0, numRedeals);
		
	}
	
	public void testDealCardMove() {
		Card topCard = eagleWing.deck.peek();
		DealCardMove dcm = new DealCardMove(eagleWing.deck, eagleWing.wastePile);
		
		assertEquals(30, eagleWing.deck.count());
		
		assertTrue(dcm.valid(eagleWing));
		
		dcm.doMove(eagleWing);
		
		assertEquals(29, eagleWing.deck.count());
		assertEquals(topCard, eagleWing.wastePile.peek());
		int value = eagleWing.getNumLeft().getValue();
		
		assertEquals(51, value);
		
		dcm.undo(eagleWing);
		
		assertEquals(30, eagleWing.deck.count());
	}
}
