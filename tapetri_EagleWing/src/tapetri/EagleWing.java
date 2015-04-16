package tapetri;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Deck;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class EagleWing extends Solitaire {

	MutableInteger rankOfFoundation;
	MutableInteger numRedeals;
	Deck deck;
	Pile wastePile;
	BuildablePile trunk;
	Pile foundations[] = new Pile[4];
	Pile wings[]  = new Pile[8];

	IntegerView scoreView;
	IntegerView numLeftView;
	IntegerView rankOfFoundView;
	IntegerView numRedealsView;
	DeckView deckView;
	PileView wastePileView;
	BuildablePileView trunkView;
	PileView foundationPileViews[] = new PileView[4];
	PileView wingPileViews[] = new PileView[8];


	@Override
	public String getName() {
		return "tapetri-EagleWing";
	}

	@Override
	public boolean hasWon() {
		return false;
	}

	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();

	}

	private void initializeControllers() {
		// Initialize Controllers for DeckView
		deckView.setMouseAdapter(new EagleWingDeckController (this, deck, wastePile));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));

		wastePileView.setMouseAdapter(new EagleWingWastePileController(this, wastePileView));
		wastePileView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		wastePileView.setUndoAdapter(new SolitaireUndoAdapter(this));

		for (int i = 0; i < 4; i++) {
			foundationPileViews[i].setMouseAdapter(new EagleWingFoundationPileController(this, foundationPileViews[i], trunkView));
			foundationPileViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			foundationPileViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));
		}

		for (int i = 0; i < 8; i++) {
			wingPileViews[i].setMouseAdapter(new EagleWingWingController(this, wingPileViews[i]));
			wingPileViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			wingPileViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));
		}	

		trunkView.setMouseAdapter(new EagleWingTrunkController(this, trunkView));
		trunkView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		trunkView.setUndoAdapter(new SolitaireUndoAdapter(this));



		// Ensure that any releases (and movement) are handled by the non-interactive widgets
		numLeftView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		numLeftView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		numLeftView.setUndoAdapter (new SolitaireUndoAdapter(this));

		// same for scoreView
		scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		scoreView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		scoreView.setUndoAdapter (new SolitaireUndoAdapter(this));

		// same for rankOfFoundView
		rankOfFoundView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		rankOfFoundView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		rankOfFoundView.setUndoAdapter (new SolitaireUndoAdapter(this));

		// same for numRedealsView
		numRedealsView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		numRedealsView.setMouseAdapter (new SolitaireReleasedAdapter(this));
		numRedealsView.setUndoAdapter (new SolitaireUndoAdapter(this));

		// Finally, cover the Container for any events not handled by a widget:
		getContainer().setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		getContainer().setMouseAdapter (new SolitaireReleasedAdapter(this));
		getContainer().setUndoAdapter (new SolitaireUndoAdapter(this));

	}

	private void initializeView() {
		CardImages ci = getCardImages();

		deckView = new DeckView (deck);
		deckView.setBounds (20,20, ci.getWidth(), ci.getHeight());
		container.addWidget (deckView);

		wastePileView = new PileView(wastePile);
		wastePileView.setBounds(40 + ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget (wastePileView);

		for (int i = 0; i < 4; i++) {
			foundationPileViews[i] = new PileView (foundations[i]);
			foundationPileViews[i].setBounds (100 + (i*20) + (4+i)*ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
			container.addWidget(foundationPileViews[i]);
		}

		for (int i = 0; i < 8; i++) {
			wingPileViews[i] = new PileView (wings[i]);
			wingPileViews[i].setBounds ((i+1)*20 + i*ci.getWidth(), 40+ci.getHeight(), ci.getWidth(), ci.getHeight());
			container.addWidget(wingPileViews[i]);
		}

		trunkView = new BuildablePileView(trunk);
		trunkView.setBounds(350, 60 + 2*ci.getHeight(), ci.getWidth(), 2*ci.getHeight());
		container.addWidget (trunkView);

		scoreView = new IntegerView (getScore());
		scoreView.setFontSize(16);
		scoreView.setBounds (20, 60+2*ci.getHeight(), 60, 20);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (16);
		numLeftView.setBounds (120, 60+2*ci.getHeight(), 60, 20);
		container.addWidget (numLeftView);

		rankOfFoundView = new IntegerView (rankOfFoundation);
		rankOfFoundView.setFontSize(18);
		rankOfFoundView.setBounds (60 + 2*ci.getWidth(), 20, 60, 20);
		container.addWidget (rankOfFoundView);

		numRedealsView = new IntegerView (numRedeals);
		numRedealsView.setFontSize(16);
		numRedealsView.setBounds(220, 60 + 2*ci.getHeight(), 60, 20);
		container.addWidget(numRedealsView);		

	}

	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement (deck);   // add to our model (as defined within our superclass).

		for (int i = 0; i < 4; i++) {
			foundations[i] = new Pile("fondation" + i);
			model.addElement(foundations[i]);
		}

		for (int i = 0; i < 8; i++) {
			wings[i] = new Pile("wing" + i);
			model.addElement(wings[i]);
		}

		wastePile = new Pile("waste");
		model.addElement(wastePile);

		trunk = new BuildablePile("trunk");
		model.addElement(trunk);

		updateNumberCardsLeft(52);
		//updateScore(0);

		for (int i = 0; i < 13; i++) {
			trunk.add(deck.get());
			trunk.flipCard();

		}

		for (int i = 0; i < 8; i++) {
			wings[i].add(deck.get());
		}

		foundations[0].add(deck.get());

		rankOfFoundation = new MutableInteger("foundationRank", foundations[0].rank());
		numRedeals = new MutableInteger("numRedeals", 0);

	}

	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new EagleWing(), Deck.OrderBySuit);
	}

	public boolean isFromWastePile(Pile fromPile) {
		return (fromPile == this.wastePile);
	}

}
