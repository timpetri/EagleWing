package tapetri;

import ks.common.games.Solitaire;
import ks.common.model.BuildablePile;
import ks.common.model.Move;
import ks.common.model.Pile;

public class AutoTrunkToWingMove extends Move {

	BuildablePile trunk;
	
	Pile wing;
	
	public AutoTrunkToWingMove(BuildablePile trunk, Pile wing) {
		
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) { return false; }
		
		wing.add(trunk.get());
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		
		trunk.add(wing.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return (trunk.count() > 1);
	}

}
