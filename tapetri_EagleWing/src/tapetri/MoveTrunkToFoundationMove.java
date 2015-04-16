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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

}
