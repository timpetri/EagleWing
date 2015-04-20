package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class AutoTrunkToWingMove extends Move {

	BuildablePile trunk;
	
	Pile wing;
	
	public AutoTrunkToWingMove(BuildablePile trunk, Pile wing) {
		this.trunk = trunk;
		this.wing = wing;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		Card c = trunk.get();
		c.setFaceUp(true);
		wing.add(c);
		if (trunk.count() == 1) {
			trunk.flipCard();
		}
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		
		if (trunk.count() == 1) {
			trunk.flipCard();
		}
		Card c = wing.get();
		c.setFaceUp(false);
		trunk.add(c);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (trunk.count() > 1);
	}

}
