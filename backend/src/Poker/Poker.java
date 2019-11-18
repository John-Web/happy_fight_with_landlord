package poker;

/**
 * @author: beibe
 * @date: 2019/11/18 12:33
 *
 * pokerNumber:
 *  3->1; 4->2; 10->8; Jack->9; Queen->10; King->11; Ace->12; 2->13;
 *  Small joker->14; Big joker->15;
 * pokerColor:
 *  Clubs, Diamonds, Hearts, Spades
 *  https://en.wikipedia.org/wiki/Standard_52-card_deck#Rank_and_color
 */

public abstract class Poker {
    public int pokerNumber = 0;
    public String pokerColor = "";
    public String imgPath = "";
}
