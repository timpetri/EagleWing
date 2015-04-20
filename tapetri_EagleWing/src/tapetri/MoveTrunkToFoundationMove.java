package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class MoveTrunkToFoundationMove extends Move {

	BuildablePile trunk;
	Pile foundation;
	Card cardBeingDragged;
	int rankOfFoundation;

	public MoveTrunkToFoundationMove(BuildablePile trunk, Card c, Pile foundation, int rankOfFound) {
		this.trunk = trunk;
		this.cardBeingDragged = c;
		this.foundation = foundation;
		rankOfFoundation = rankOfFound;
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
		trunk.add(foundation.get());
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