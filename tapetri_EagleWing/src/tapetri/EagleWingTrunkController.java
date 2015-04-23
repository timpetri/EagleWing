package tapetri;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Column;
import ks.common.view.BuildablePileView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;


/**
 * 
 * @author Tim Petri | tapetri@wpi.edu
 * Apr 23, 2015 2015
 *
 * Controller for the trunk view
 */
public class EagleWingTrunkController extends SolitaireReleasedAdapter {

	/** The EagleWing game */
	protected EagleWing theGame;

	/** The specific trunk BuildablePileView being controlled. */
	protected BuildablePileView src;

	/**
	 * 
	 * EagleWingFoundationPileController constructor comment.
	 */
	public EagleWingTrunkController(EagleWing theGame, BuildablePileView trunk) {
		super(theGame);

		this.theGame = theGame;
		this.src = trunk;
	}

	public void mousePressed(MouseEvent me) {

		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container c = theGame.getContainer();

		/** Return if there is no card to be chosen. */
		BuildablePile trunk = (BuildablePile) src.getModelElement();
		if (trunk.count() != 1) {
			c.releaseDraggingObject();
			return;
		}

		// Get a column of cards to move from the BuildablePileView
		// Note that this method will alter the model for BuildablePileView if the condition is met.
		ColumnView colView = src.getColumnView (me);

		// an invalid selection (either all facedown, or not in faceup region)
		if (colView == null) {
			return;
		}

		// Check conditions
		Column col = (Column) colView.getModelElement();
		if (col == null) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a ColumnView with no Column.");
			return; // sanity check, but should never happen.
		}

		// verify that Column has desired properties
		if (!(col.count() == 1)) {
			trunk.push (col);
			java.awt.Toolkit.getDefaultToolkit().beep();
			return; // announce our displeasure
		}

		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (colView, me);

		// Tell container which BuildablePileView is the source for this drag event.
		c.setDragSource (src);

		// we simply redraw our source pile to avoid flicker,
		// rather than refreshing all widgets...
		src.redraw();
	}


}
