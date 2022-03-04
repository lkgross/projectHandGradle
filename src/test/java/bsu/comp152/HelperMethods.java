package bsu.comp152;

public class HelperMethods {

    // A class to hold a bunch of cards for use in the test classes

    protected static Hand highCardHand10;
    protected static Hand highCardHandKing;
    protected static Hand pairHand;
    protected static Hand twoPairsHand;
    protected static Hand threeOfAKindHand;
    protected static Hand flushHand;
    protected static Hand otherFlushHand;
    protected static Hand fourOfAKindHand;


    protected static Hand makeHand(Integer... cards) {
        // helper method that makes a hand out of an arbitrary number of ranks and suit arguments
        Hand hand = new Hand(cards.length / 2);
        for (int i = 0; i < cards.length; i++) {
            hand.addCard(new Card(cards[i], cards[i + 1]));
            i++;
        }
        return hand;
    }

    public static void setupHands() {
        highCardHand10 = makeHand(
                5, Card.DIAMONDS,
                6, Card.HEARTS,
                7, Card.CLUBS,
                10, Card.SPADES,
                8, Card.CLUBS,
                9, Card.CLUBS);

        highCardHandKing = makeHand(
                2, Card.DIAMONDS,
                1, Card.HEARTS,
                3, Card.CLUBS,
                13, Card.SPADES,
                4, Card.CLUBS,
                5, Card.CLUBS);

        pairHand = makeHand(
                5, Card.DIAMONDS,
                12, Card.HEARTS, //
                7, Card.CLUBS,
                13, Card.CLUBS,
                12, Card.CLUBS,  //
                9, Card.SPADES);

        twoPairsHand = makeHand(
                5, Card.DIAMONDS,
                6, Card.HEARTS,
                5, Card.HEARTS,
                7, Card.HEARTS,
                8, Card.CLUBS,
                7, Card.SPADES);


        threeOfAKindHand = makeHand(
                7, Card.DIAMONDS,
                6, Card.HEARTS,
                5, Card.HEARTS,
                7, Card.HEARTS,
                8, Card.CLUBS,
                7, Card.SPADES);

        flushHand= makeHand(
                4, Card.HEARTS,
                5, Card.HEARTS,
                6, Card.HEARTS,
                7, Card.HEARTS,
                8, Card.HEARTS,
                12, Card.HEARTS);

        otherFlushHand = makeHand(
                8, Card.HEARTS,
                9, Card.HEARTS,
                1, Card.HEARTS,
                7, Card.HEARTS,
                8, Card.HEARTS,
                13, Card.HEARTS);


        fourOfAKindHand = makeHand(
                4, Card.DIAMONDS,
                4, Card.HEARTS,
                4, Card.CLUBS,
                4, Card.SPADES,
                5, Card.HEARTS,
                10, Card.DIAMONDS);
    }

    // helper method to get array of cards from a hand
    protected static Card[] getCards(Hand hand, int maxCards) {
        Card[] cards = new Card[maxCards];
        for (int i = 0; i < maxCards; i++) {
            try {
                cards[i] = hand.getCard(i);
            } catch (IllegalArgumentException e) {
                break;
            }
        }
        return cards;
    }

}
