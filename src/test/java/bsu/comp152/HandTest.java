package bsu.comp152;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.ArrayMatching.arrayContaining;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static bsu.comp152.HelperMethods.*;

public class HandTest {

    private Hand testHand;

    @BeforeEach
    public void setup() {
        // 5 cards is the number of cards in a poker hand
        testHand = new Hand(5);
    }

    // getNumCards
    @Test
    public void numCardsInitializedToZero() {
        assertThat(testHand.getNumCards(), equalTo(0));
    }

    // addCard
    @Test
    public void addCardThrowsIllegalStateExceptionWhenTooManyCardsAdded() {
        for (int i = 0; i < 5; i++) {
            testHand.addCard(new Card(5, 2));
        }
        assertThrows(IllegalStateException.class, () -> testHand.addCard(new Card(5, 2)));
    }

    @Test
    public void addCardThrowsIllegalArgumentExceptionWhenNullAdded() {
        assertThrows(IllegalArgumentException.class, () -> testHand.addCard(null));
    }

    @Test
    public void addCardIncrementsNumCards() {
        int initialNumCards = testHand.getNumCards();
        testHand.addCard(new Card(2, 2));
        assertThat(testHand.getNumCards(), equalTo(initialNumCards + 1));
    }

    // toString
    @Test
    public void toStringContainsAllCardsWhenAllCardsAdded() {
        Hand bigTestHand = new Hand(52);
        ArrayList<Card> allCards = new ArrayList<Card>();
        // add all cards to a hand and a list
        for (int r = 1; r <= 13; r++) {
            for (int s = 0; s < 4; s++) {
                Card card = new Card(r, s);
                allCards.add(card);
                bigTestHand.addCard(card);
            }
        }
        String handString = bigTestHand.toString();
        // Using the list of abbreviations,
        // check if each card's abbreviation shows up in the toString
        for (Card card : allCards) {
            assertThat(handString, containsString(card.getAbbrev()));
        }
    }

    @Test
    public void toStringWithZeroCardsHasZeroCards() {
        assertThat(testHand.toString(), equalTo("[]"));
    }

    @Test
    public void toStringWithOneCardHasThatCard() {
        Card c = new Card(2, 2);
        testHand.addCard(c);
        assertThat(testHand.toString(), containsString(c.getAbbrev()));
    }

    @Test
    public void toStringStartsWithBracket() {
        testHand.addCard(new Card(2, 2));
        assertThat(testHand.toString(), startsWith("["));
    }

    @Test
    public void toStringEndsWithBracket() {
        testHand.addCard(new Card(2, 2));
        assertThat(testHand.toString(), endsWith("]"));
    }

    @Test
    public void toStringDoesntHaveTrailingComma() {
        testHand = makeHand(2, 2, 2, 2);
        assertThat(testHand.toString(), not(containsString(",]")));
        assertThat(testHand.toString(), not(containsString(", ]")));
    }

    @Test
    public void toStringOfTwoCardsContainsCommas() {
        testHand = makeHand(2, 2, 2, 2);
        assertThat(testHand.toString(), containsString(","));
    }

    // getCard
    @Test
    public void getCardThrowsIllegalArgumentExceptionWhenNoCards() {
        assertThrows(IllegalArgumentException.class, () -> testHand.getCard(0));
    }

    @Test
    public void getCardThrowsIllegalArgumentExceptionWhenNoCardInPosition() {
        testHand = makeHand(2, 2, 3, 2);
        assertThrows(IllegalArgumentException.class, () -> testHand.getCard(2));
    }

    @Test
    public void getCardThrowsIllegalArgumentExceptionWhenAboveHandMax() {
        testHand.addCard(new Card(2, 2));
        assertThrows(IllegalArgumentException.class, () -> testHand.getCard(10));
    }

    @Test
    public void getCardThrowsIllegalArgumentExceptionWhenNegativeInput() {
        testHand.addCard(new Card(2, 2));
        assertThrows(IllegalArgumentException.class, () -> testHand.getCard(-10));
    }

    @Test
    public void getCardReturnsFirstCardWhenOneCardInHand() {
        Card c = new Card(3, 2);
        testHand.addCard(c);
        assertThat(testHand.getCard(0), equalTo(c));
    }

    // playCard
    @Test
    public void playCardThrowsIllegalArgumentExceptionWhenNoCards() {
        assertThrows(IllegalArgumentException.class, () -> testHand.playCard(0));
    }

    @Test
    public void playCardThrowsIllegalArgumentExceptionWhenNoCardInPosition() {
        testHand = makeHand(2, 2, 2, 2);
        assertThrows(IllegalArgumentException.class, () -> testHand.playCard(3));
    }

    @Test
    public void playCardThrowsIllegalArgumentExceptionWhenAboveHandMax() {
        testHand.addCard(new Card(2, 2));
        assertThrows(IllegalArgumentException.class, () -> testHand.playCard(10));
    }

    @Test
    public void playCardThrowsIllegalArgumentExceptionWhenNegativeInput() {
        testHand.addCard(new Card(2, 2));
        assertThrows(IllegalArgumentException.class, () -> testHand.playCard(-10));
    }

    @Test
    public void playCardReturnsCorrectCardWhenHandHasOneCard() {
        Card c = new Card(6, Card.HEARTS);
        testHand.addCard(c);
        assertThat(testHand.playCard(0), equalTo(c));
    }

    @Test
    public void playCardRemovesCardWhenHandHasOneCard() {
        testHand.addCard(new Card(6, Card.HEARTS));
        // make sure a card is actually added
        assertThat(getCards(testHand, 5), not(equalTo(new Card[5])));
        testHand.playCard(0);
        assertThat(getCards(testHand, 5), equalTo(new Card[5]));
    }

    @Test
    public void playCardShiftsRemainingCardsOver() {
        testHand = makeHand(6, Card.HEARTS,
                            7, Card.HEARTS,
                            8, Card.HEARTS,
                            9, Card.HEARTS,
                            10, Card.HEARTS);

        testHand.playCard(2);

        Card[] shifted = new Card[]{new Card(6, Card.HEARTS),
                new Card(7, Card.HEARTS),
                new Card(9, Card.HEARTS),
                new Card(10, Card.HEARTS),
                null
        };

        assertThat(getCards(testHand, 5), arrayContaining(shifted));

    }

    @Test
    public void playCardReturnsLastCard() {
        testHand = makeHand(6, Card.HEARTS,
                            7, Card.HEARTS,
                            8, Card.HEARTS,
                            9, Card.HEARTS,
                            10, Card.HEARTS);
        assertThat(testHand.playCard(4), equalTo(new Card(10, Card.HEARTS)));
    }

    @Test
    public void playCardRemovesLastCard() {
        testHand = makeHand(6, Card.HEARTS,
                            7, Card.HEARTS,
                            8, Card.HEARTS,
                            9, Card.HEARTS,
                            10, Card.HEARTS);
        testHand.playCard(4);
        assertThat(Arrays.asList(getCards(testHand, 5)), not(hasItem(new Card(10, Card.HEARTS))));
    }

    @Test
    public void playCardDecrementsNumCards() {
        testHand = makeHand(2, 2, 2, 3);
        int initialNumCards = testHand.getNumCards();
        testHand.playCard(0);
        assertThat(testHand.getNumCards(), equalTo(initialNumCards - 1));
    }

    // highCard

    @Test
    public void highCardReturnsHighCardWhenOneCardInHand() {
        testHand.addCard(new Card(3, 2));
        assertThat(testHand.highCard(), equalTo(new Card(3, 2)));
    }

    @Test
    public void highCardReturnsHighCardWhenHandFull() {
        testHand = makeHand(2, Card.CLUBS,
                            3, Card.CLUBS,
                            4, Card.CLUBS,
                            5, Card.CLUBS,
                            6, Card.CLUBS);
        assertThat(testHand.highCard(), equalTo(new Card(6, 2)));
    }

    @Test
    public void highCardReturnsClosestToBeginningIfTie() {
        testHand = makeHand(3, 3, 4, 2, 4, 1);
        assertThat(testHand.highCard(), equalTo(new Card(4, 2)));

    }

    // numCardsOfRank

    @Test
    public void numCardsOfRankReturnsOneWhenOneOfEachCardInHand() {
        Hand bigHand = new Hand(13);
        for (int r = 1; r <= 13; r++) {
            bigHand.addCard(new Card(r, 2));
        }
        for (int r = 1; r <= 13; r++) {
            assertThat(bigHand.numCardsOfRank(r), equalTo(1));
        }
    }

    @Test
    public void numCardsOfRankReturns0WhenNoCardsInHand() {
        for (int r = 1; r <= 13; r++) {
            assertThat(testHand.numCardsOfRank(r), equalTo(0));
        }
    }

    @Test
    public void numCardsOfRankReturnsFourWhenFourOfEachInHand() {
        Hand bigHand = new Hand(52);
        for (int r = 1; r <= 13; r++) {
            bigHand.addCard(new Card(r, 0));
            bigHand.addCard(new Card(r, 1));
            bigHand.addCard(new Card(r, 2));
            bigHand.addCard(new Card(r, 3));
        }
        for (int r = 1; r <= 13; r++) {
            assertThat(bigHand.numCardsOfRank(r), equalTo(4));
        }

    }

    // hasFlush

    @Test
    public void hasFlushReturnsTrueForOneCard() {
        testHand.addCard(new Card(3, 2));
        assertThat(testHand.hasFlush(), equalTo(true));
    }

    @Test
    public void hasFlushReturnsTrueForAllHearts() {
        for (int r = 1; r <= 5; r++) {
            testHand.addCard(new Card(r, Card.HEARTS));
        }
        assertThat(testHand.hasFlush(), equalTo(true));
    }

    @Test
    public void hasFlushReturnsTrueForAllDiamonds() {
        for (int r = 1; r <= 5; r++) {
            testHand.addCard(new Card(r, Card.DIAMONDS));
        }
        assertThat(testHand.hasFlush(), equalTo(true));
    }

    @Test
    public void hasFlushReturnsTrueForAllClubs() {
        for (int r = 1; r <= 5; r++) {
            testHand.addCard(new Card(r, Card.CLUBS));
        }
        assertThat(testHand.hasFlush(), equalTo(true));
    }

    @Test
    public void hasFlushReturnsTrueForAllSpades() {
        for (int r = 1; r <= 5; r++) {
            testHand.addCard(new Card(r, Card.SPADES));
        }
        assertThat(testHand.hasFlush(), equalTo(true));
    }

    @Test
    public void hasFlushReturnsFalseWhenOneOfEachSuit() {
        for (int s = 0; s < 4; s++) {
            testHand.addCard(new Card(3, s));
        }
        assertThat(testHand.hasFlush(), equalTo(false));
    }

    @Test
    public void hasFlushReturnsFalseWhenFirstCardDoesntMatch() {
        for (int mainSuit = 0; mainSuit < 4; mainSuit++) {
            for (int otherSuit = 0; otherSuit < 4; otherSuit++) {
                testHand = makeHand(3, otherSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, mainSuit);
                if (mainSuit != otherSuit) {
                    String reason = "suit " + Card.SUIT_NAMES[otherSuit] + " does not match suit " + Card.SUIT_NAMES[mainSuit];
                    assertThat(reason, testHand.hasFlush(), equalTo(false));
                }
            }
        }
    }

    @Test
    public void hasFlushReturnsFalseWhenSecondCardDoesntMatch() {
        for (int mainSuit = 0; mainSuit < 4; mainSuit++) {
            for (int otherSuit = 0; otherSuit < 4; otherSuit++) {
                testHand = makeHand(3, mainSuit,
                        3, otherSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, mainSuit);
                if (mainSuit != otherSuit) {
                    String reason = "suit " + Card.SUIT_NAMES[otherSuit] + " does not match suit " + Card.SUIT_NAMES[mainSuit];
                    assertThat(reason, testHand.hasFlush(), equalTo(false));
                }
            }
        }
    }

    @Test
    public void hasFlushReturnsFalseWhenSecondToLastCardDoesntMatch() {
        for (int mainSuit = 0; mainSuit < 4; mainSuit++) {
            for (int otherSuit = 0; otherSuit < 4; otherSuit++) {
                testHand = makeHand(3, mainSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, otherSuit,
                        3, mainSuit);
                if (mainSuit != otherSuit) {
                    String reason = "suit " + Card.SUIT_NAMES[otherSuit] + " does not match suit " + Card.SUIT_NAMES[mainSuit];
                    assertThat(reason, testHand.hasFlush(), equalTo(false));
                }
            }
        }
    }

    @Test
    public void hasFlushReturnsFalseWhenLastCardDoesntMatch() {
        for (int mainSuit = 0; mainSuit < 4; mainSuit++) {
            for (int otherSuit = 0; otherSuit < 4; otherSuit++) {
                testHand = makeHand(3, mainSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, mainSuit,
                        3, otherSuit);
                if (mainSuit != otherSuit) {
                    String reason = "suit " + Card.SUIT_NAMES[otherSuit] + " does not match suit " + Card.SUIT_NAMES[mainSuit];
                    assertThat(reason, testHand.hasFlush(), equalTo(false));
                }
            }
        }
    }
}