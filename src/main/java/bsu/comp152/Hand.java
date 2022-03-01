package bsu.comp152;

/** Hand - A class to create Hand objects and get to know them

  Starter code from Computer Science 111, Boston University

  Modified by Laura K. Gross, COMP 152, Bridgewater State University

  Completed by: [student name], [student email]
          date: [date of completion]
 */
public class Hand {
    /* Constants for types of hands
     * The numbers used for the hand types increase
     * with the value of the hand type.
     * For example, four-of-a-kind is the highest-valued
     * hand type that we support, and it has the highest
     * numeric value.
     */
    private static final int HIGH_CARD = 0;
    private static final int PAIR = 1;
    private static final int TWO_PAIRS = 2;
    private static final int THREE_OF_A_KIND = 3;
    private static final int FLUSH = 4;
    private static final int FOUR_OF_A_KIND = 5;

    // The instance fields for a Hand object
    // The Card objects in the Hand object are stored in array called cards.
    // Declare the array of Card objects.
    private Card[] cards;
    // The number of cards in the hand is called numCards.
    // Declare it as an integer.
    private int numCards;

    /*
     * getTotalValue - computes and returns the sum of the values
     * of the cards in the cards array.
     * If the Hand is currently empty (i.e., has no cards),
     * the method returns 0.
     */
    public int getTotalValue(){
        int value = 0;
        for (int i = 0; i < numCards; i++) {
            value += cards[i].getValue();
        }
        return value;
    }
}
