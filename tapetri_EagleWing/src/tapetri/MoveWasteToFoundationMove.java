package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveWasteToFoundationMove extends Move {

	Pile wastePile;
	
	Pile foundation;
	
	int rankOfFoundation;
	
	Card cardBeingDragged;
	
	public MoveWasteToFoundationMove(Pile from, Pile to, Card cardBeingDragged, int rankOfFoundation) {
		this.wastePile = from;
		this.cardBeingDragged = cardBeingDragged;
		this.foundation = to;
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
		wastePile.add(foundation.get());
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
