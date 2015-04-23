package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;

/**
 * 
 * @author Tim Petri | tapetri@wpi.edu
 * Apr 23, 2015 2015
 * 
 * Move class for restocking the deck from wastepile
 */
public class RestockDeckMove extends Move {

	Pile wastePile;
	
	Deck deck;
	
	MutableInteger numRedeals;
	
	// used to make undo easier
	int cardsToMove;
	
	public RestockDeckMove(Pile wastePile, Deck deck, MutableInteger numRedeals) {
		this.wastePile = wastePile;
		this.deck = deck;
		this.numRedeals = numRedeals;
		this.cardsToMove = wastePile.count();
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		for (int i = 0; i < cardsToMove; i++) {
			deck.add(wastePile.get());
			game.getNumLeft().increment(+1);
		}
		
		numRedeals.increment(+1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		
		for (int i = 0; i < cardsToMove; i++) {
			wastePile.add(deck.get());
			game.getNumLeft().increment(-1);
		}
		
		numRedeals.increment(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (deck.empty() && !wastePile.empty() && (numRedeals.getValue() < 3));
	}

}
