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
 * Move class for moving a card from wing pile to foundation
 */
public class MoveWingToFoundationMove extends Move {

	Pile wing;
	
	Pile foundation;
	
	int rankOfFoundation;
	
	Card cardBeingDragged;
	
	public MoveWingToFoundationMove(Pile wing, Pile foundation, Card c, int rankOfFoundation) {
		this.wing = wing;
		this.foundation = foundation;
		this.cardBeingDragged = c;
		this.rankOfFoundation = rankOfFoundation;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		foundation.add(cardBeingDragged);
		game.updateScore(+1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		wing.add(foundation.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if (foundation.empty()) {
			return cardBeingDragged.getRank() == rankOfFoundation;
		}
		else {
			boolean sameSuit = cardBeingDragged.getSuit() == foundation.suit();
			boolean oneRankAbove = ((cardBeingDragged.getRank() - foundation.rank()) % 13 == 1);
		
			// deals with round-the-corner foundations
			boolean aceOverKing = (cardBeingDragged.getRank() == Card.ACE && foundation.rank() == Card.KING);
			
			return sameSuit && (oneRankAbove || aceOverKing);
		}
	}
	
}
