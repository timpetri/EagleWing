package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * 
 * @author Tim Petri | tapetri@wpi.edu
 * Apr 23, 2015 2015
 * 
 * Move class for moving a card from waste pile to wing
 */
public class MoveWasteToWingMove extends Move {
	
	Pile wastePile;
	Pile wing;
	Card cardBeingDragged;
	
	
	public MoveWasteToWingMove (Pile fromPile, Card c, Pile wing) {
		this.wastePile = fromPile;
		this.wing = wing;
		this.cardBeingDragged = c;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		wing.add(cardBeingDragged);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		wastePile.add(wing.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (wing.empty());
	}

}
