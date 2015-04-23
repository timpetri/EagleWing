package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;


/**
 * 
 * @author Tim Petri | tapetri@wpi.edu
 * Apr 23, 2015 2015
 *
 * Move class for dealing a new card to the waste pile
 */
public class DealCardMove extends Move {
	
	Deck deck;
	Pile wastePile;
	
	public DealCardMove(Deck deck, Pile wastePile) {
		this.deck = deck;
		this.wastePile = wastePile;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		Card card = deck.get();
		wastePile.add(card);
		game.getNumLeft().increment(-1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		Card card = wastePile.get();
		deck.add(card);
		game.getNumLeft().increment(+1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}

}
