package tapetri;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class EagleWingFoundationPileController extends SolitaireReleasedAdapter {

	/** The EagleWing game */
	protected EagleWing theGame;
	
	/** The specific Foundation pileView being controlled. */
	protected PileView src;
	
	/** The trunk buildablePileView being used to automatic moves */
	protected BuildablePileView trunkView;
	
	/**
	 * 
	 * EagleWingFoundationPileController constructor comment.
	 */
	public EagleWingFoundationPileController(EagleWing theGame, PileView foundation, BuildablePileView trunk) {
		super(theGame);
		
		this.theGame = theGame;
		this.src = foundation;
		this.trunkView = trunk;
	}

	
	public void mouseReleased(MouseEvent me) {

		Container c = theGame.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}


		// Determine the To Pile
		Pile foundation = (Pile) src.getModelElement();

		if (fromWidget instanceof BuildablePileView) {
			
			// TODO: card coming from trunk
			BuildablePile fromPile = (BuildablePile) fromWidget.getModelElement();

			/** Must be the ColumnView widget being dragged. */
			ColumnView columnView = (ColumnView) draggingWidget;
			Column col = (Column) columnView.getModelElement();
			if (col == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow ColumnView model element is null.");
				c.releaseDraggingObject();			
				return;
			}

			// must use peek() so we don't modify col prematurely. Here is a HACK! Presumably
			// we only want the Move object to know things about the move, but we have to put
			// in a check to verify that Column is of size one. NO good solution that I can
			// see right now.
			if (col.count() != 1) {
				fromWidget.returnWidget (draggingWidget);  // return home
			} else {
				Move m = new MoveTrunkToFoundationMove (fromPile, col.peek(), foundation, theGame.getFoundRankValue());

				if (m.doMove (theGame)) {
					// Success
					theGame.pushMove (m);
				} else {
					fromWidget.returnWidget (draggingWidget);
				}
			}
		} else {
			
			Pile fromPile = (Pile) fromWidget.getModelElement();
			
			/** Must be the CardView widget being dragged. */
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			if (theCard == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}
			
			
			if (theGame.isFromWastePile(fromPile)) {
				// card comes from waste pile
				Move m = new MoveWasteToFoundationMove (fromPile,  foundation, theCard, theGame.getFoundRankValue());
				if (m.doMove (theGame)) {
					// Success
					theGame.pushMove (m);
					
				} else {
					fromWidget.returnWidget (draggingWidget);
				}
				
			} else {
				
				// card comes from wing pile
				Move m = new MoveWingToFoundationMove (fromPile,  foundation, theCard, theGame.getFoundRankValue());
				if (m.doMove (theGame)) {
					// Success
					theGame.pushMove (m);
					
					// release the dragging object, (this will reset dragSource)
					//c.releaseDraggingObject();
					
					BuildablePile trunk = (BuildablePile) trunkView.getModelElement();
					
					Move m2 = new AutoTrunkToWingMove(trunk, fromPile);
					if (m2.doMove(theGame)) {
						theGame.pushMove(m2);
					}
					
					
				} else {
					fromWidget.returnWidget (draggingWidget);
				}
				
			}
		}

		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}
}
