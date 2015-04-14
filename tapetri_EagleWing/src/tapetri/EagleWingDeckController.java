package tapetri;

import heineman.klondike.DealCardMove;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class EagleWingDeckController extends SolitaireReleasedAdapter {
	/** The game. */
	protected EagleWing theGame;

	/** The WastePile of interest. */
	protected Pile wastePile;

	/** The Deck of interest. */
	protected Deck deck;

	/**
	 * EagleWingController constructor comment.
	 */
	public EagleWingDeckController(EagleWing theGame, Deck d, Pile wastePile) {
		super(theGame);

		this.theGame = theGame;
		this.wastePile = wastePile;
		this.deck = d;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempting a DealFourCardMove
		Move m = new DealCardMove (deck, wastePile);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
		// deck is empty
		else {
			m = new RestockDeckMove(wastePile, deck, theGame.numRedeals);
			if (m.doMove(theGame)) {
				theGame.pushMove(m);
				theGame.refreshWidgets();
			}
		}
	}

}
