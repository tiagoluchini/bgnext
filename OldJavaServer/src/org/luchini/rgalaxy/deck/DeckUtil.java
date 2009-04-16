package org.luchini.rgalaxy.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.luchini.rgalaxy.deck.enums.SetType;
import org.luchini.rgalaxy.deck.cards.*;

public class DeckUtil {

	public static List<Card> createNewShuffledDeck(SetType setType) {
		List<Card> out = new ArrayList<Card>();
		out.add(new AlienRobotScoutShip());
		out.add(new AlienRobotSentry());
		out.add(new AlphaCentauri());
		out.add(new AquaticUpliftRace());
		out.add(new AsteroidBelt());
		out.add(new AvianUpliftRace());
		out.add(new BlasterGemMines());
		out.add(new ColonyShip());
		out.add(new ColonyShip());
		out.add(new ContactSpecialist());
		out.add(new ContactSpecialist());
		out.add(new DesertedAlienColony());
		out.add(new DesertedAlienLibrary());
		out.add(new DesertedAlienOutpost());
		out.add(new DestroyedWorld());
		out.add(new DropShips());
		out.add(new DropShips());
		out.add(new EmpathWorld());
		out.add(new ExpeditionForce());
		out.add(new ExpeditionForce());
		out.add(new FormerPenalColony());
		out.add(new InterstellarBank());
		out.add(new InterstellarBank());
		out.add(new InvestmentCredits());
		out.add(new InvestmentCredits());
		out.add(new LostAlienWarship());
		out.add(new NewMilitaryTactics());
		out.add(new NewMilitaryTactics());
		out.add(new NewSparta());
		out.add(new PreSentientRace());
		out.add(new RadioactiveWorld());
		out.add(new RebelBase());
		out.add(new RebelFuelCache());
		out.add(new RebelHomeworld());
		out.add(new RebelOutpost());
		out.add(new RebelWarriorRace());
		out.add(new RefugeeWorld());
		out.add(new ReplicantRobots());
		out.add(new ReplicantRobots());
		out.add(new ReptilianUpliftRace());
		out.add(new SpaceMarines());
		out.add(new SpaceMarines());
		out.add(new TheLastoftheUpliftGnarssh());
		shuffleDeck(out);
		return out;
	}
	
	public static List<Card> extractStartingCardsAndReshuffle(int qty, List<Card> cards) {
		List<Card> out = null;
		if (cards != null) {
			out = new ArrayList<Card>(qty);
			int c=0;
			int[] indexesToRemove = new int[qty];
			for (Card card : cards) {
				if (card.isStartingWorld()) {
					out.add(card);
					indexesToRemove[c] = cards.indexOf(card);
					c++;
					if (c==qty)
						break;
				}
			}
			for (int i=0; i<qty; i++) {
				cards.remove(i);
			}
			shuffleDeck(cards);
		}
		return out;
	}
	
	public static void shuffleDeck(List<Card> cards) {
		Random rand = new Random();
		for (int i = cards.size() - 1; i > 0; i--)
		{
		    int n = rand.nextInt(i + 1);
		    Card temp = cards.get(i);
		    cards.set(i, cards.get(n));
		    cards.set(n, temp);
		}
	}
	
}
