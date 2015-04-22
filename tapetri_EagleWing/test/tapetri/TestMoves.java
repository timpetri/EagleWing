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

	
	public void testWingToFoundation() {
		
		// set wing to contain 6H
		ModelFactory.init(eagleWing.wings[0], "6H");
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals("5H", eagleWing.foundations[0].peek().getName());
		
		// try to move 6H from wing to 5H in foundation
		Card firstCard =  eagleWing.wings[0].get();
		MoveWingToFoundationMove wtfm = new MoveWingToFoundationMove(eagleWing.wings[0], eagleWing.foundations[0], firstCard, eagleWing.getFoundRankValue());
		assertTrue(wtfm.valid(eagleWing));
		wtfm.doMove(eagleWing);
		
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals(2, eagleWing.foundations[0].count());
		assertEquals("6H", eagleWing.foundations[0].peek().getName());
		assertEquals(2, eagleWing.getScoreValue());
		
		// try to undo above move
		wtfm.undo(eagleWing);
		
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals("5H", eagleWing.foundations[0].peek().getName());
		assertEquals(1, eagleWing.getScoreValue());
		
		// set wing to contain 5S
		ModelFactory.init(eagleWing.wings[0], "5S");
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.foundations[1].count());
		
		// try to move 5S to empty foundation pile
		Card secondCard =  eagleWing.wings[0].get();
		MoveWingToFoundationMove wtfm2 = new MoveWingToFoundationMove(eagleWing.wings[0], eagleWing.foundations[1], secondCard, eagleWing.getFoundRankValue());
		assertTrue(wtfm2.valid(eagleWing));
		wtfm2.doMove(eagleWing);
		
		assertEquals(0, eagleWing.wings[0].count());
		assertEquals(1, eagleWing.foundations[1].count());
		assertEquals("5S", eagleWing.foundations[1].peek().getName());
		assertEquals(2, eagleWing.getScoreValue());
		
		// try to undo above move
		wtfm2.undo(eagleWing);
		
		assertEquals(1, eagleWing.wings[0].count());
		assertEquals(0, eagleWing.foundations[1].count());
		assertEquals(1, eagleWing.getScoreValue());
		
	}

	public void testTrunkToFoundation() {

		// set trunk to contain 6H
		ModelFactory.init(eagleWing.trunk, "6H");
		assertEquals(1, eagleWing.trunk.count());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals("5H", eagleWing.foundations[0].peek().getName());

		// try to move 6H from trunk to 5H in foundation
		Card firstCard = eagleWing.trunk.get();
		MoveTrunkToFoundationMove ttfm = new MoveTrunkToFoundationMove(eagleWing.trunk, firstCard, eagleWing.foundations[0], eagleWing.getFoundRankValue());
		assertTrue(ttfm.valid(eagleWing));
		ttfm.doMove(eagleWing);

		assertEquals(0, eagleWing.trunk.count());
		assertEquals(2, eagleWing.foundations[0].count());
		assertEquals("6H", eagleWing.foundations[0].peek().getName());

		// try to undo previous move
		ttfm.undo(eagleWing);

		assertEquals(1, eagleWing.trunk.count());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals("5H", eagleWing.foundations[0].peek().getName());

		// set trunk to contain 5S
		ModelFactory.init(eagleWing.trunk, "5S");
		assertEquals(1, eagleWing.trunk.count());
		assertEquals(0, eagleWing.foundations[1].count());

		// try to move 5S to empty slot
		Card secondCard = eagleWing.trunk.get();
		MoveTrunkToFoundationMove ttfm2 = new MoveTrunkToFoundationMove(eagleWing.trunk, secondCard, eagleWing.foundations[1], eagleWing.getFoundRankValue());
		assertTrue(ttfm2.valid(eagleWing));
		ttfm2.doMove(eagleWing);
		
		assertEquals(0, eagleWing.trunk.count());
		assertEquals(1, eagleWing.foundations[1].count());
		assertEquals("5S", eagleWing.foundations[1].peek().getName());

		// try to undo previous move
		ttfm2.undo(eagleWing);

		assertEquals(1, eagleWing.trunk.count());
		assertEquals(0, eagleWing.foundations[1].count());
		assertEquals("5S", eagleWing.trunk.peek().getName());
		
		// check that 2 can be placed on top of ace?

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

		// set deck to contain  6H, 5S and 2D
		ModelFactory.init(eagleWing.wastePile, "2D 5S 6H");
		ModelFactory.init(eagleWing.foundations[2], "AD");

		assertEquals(5, eagleWing.rankOfFoundation.getValue());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals(0, eagleWing.foundations[1].count());
		assertEquals(1, eagleWing.foundations[2].count()); 
		assertEquals("5H", eagleWing.foundations[0].peek().getName());
		assertEquals("AD", eagleWing.foundations[2].peek().getName());

		// test that you can place 6H on top of first foundation card
		Card cardInWaste = eagleWing.wastePile.get();
		MoveWasteToFoundationMove wtfm = new MoveWasteToFoundationMove(eagleWing.wastePile, eagleWing.foundations[0], cardInWaste, eagleWing.getFoundRankValue());
		assertTrue(wtfm.valid(eagleWing));
		wtfm.doMove(eagleWing);

		assertEquals(2, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[0].count());
		assertEquals("6H", eagleWing.foundations[0].peek().getName());

		// test that a card can be placed on an empty foundation pile
		Card secCardInWaste = eagleWing.wastePile.get();
		MoveWasteToFoundationMove wtfm2 = new MoveWasteToFoundationMove(eagleWing.wastePile, eagleWing.foundations[1], secCardInWaste, eagleWing.getFoundRankValue());
		assertTrue(wtfm2.valid(eagleWing));
		wtfm2.doMove(eagleWing);

		assertEquals(1, eagleWing.wastePile.count());
		assertEquals(1, eagleWing.foundations[1].count());
		assertEquals("5S", eagleWing.foundations[1].peek().getName());

		// test that the cards can be placed round-the-corner
		Card thirdCardInWaste = eagleWing.wastePile.get();
		MoveWasteToFoundationMove wtfm3 = new MoveWasteToFoundationMove(eagleWing.wastePile, eagleWing.foundations[2], thirdCardInWaste, eagleWing.getFoundRankValue());
		assertTrue(wtfm3.valid(eagleWing));
		wtfm3.doMove(eagleWing);

		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(2, eagleWing.foundations[2].count());
		assertEquals("2D", eagleWing.foundations[2].peek().getName());

		// reverse back state
		wtfm3.undo(eagleWing);
		wtfm2.undo(eagleWing);
		wtfm.undo(eagleWing);

		assertEquals(5, eagleWing.rankOfFoundation.getValue());
		assertEquals(1, eagleWing.foundations[0].count());
		assertEquals(0, eagleWing.foundations[1].count());
		assertEquals(1, eagleWing.foundations[2].count()); 
		assertEquals("5H", eagleWing.foundations[0].peek().getName());
		assertEquals("AD", eagleWing.foundations[2].peek().getName());


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

		assertEquals(29, value);

		dcm.undo(eagleWing);

		assertEquals(30, eagleWing.deck.count());
	}
}
