import java.util.HashMap;
import java.util.Map;

public class CardButton {

   // Mapping between card names and image files
   public static final Map<String, String> cardImageMap = new HashMap<>();

   static {

      cardImageMap.put("s2", "2Spades.jpg");
      cardImageMap.put("s3", "3Spades.jpg");
      cardImageMap.put("s4", "4Spades.jpg");
      cardImageMap.put("s5", "5Spades.jpg");
      cardImageMap.put("s6", "6Spades.jpg");
      cardImageMap.put("s7", "7Spades.jpg");
      cardImageMap.put("s8", "8Spades.jpg");
      cardImageMap.put("s9", "9Spades.jpg");
      cardImageMap.put("s10", "10Spades.jpg");
      cardImageMap.put("sJ", "JackSpades.jpg");
      cardImageMap.put("sQ", "QueenSpades.jpg");
      cardImageMap.put("sK", "KingSpades.jpg");
      cardImageMap.put("sA", "AceSpades.jpg");

      cardImageMap.put("d2", "2Diamonds.jpg");
      cardImageMap.put("d3", "3Diamonds.jpg");
      cardImageMap.put("d4", "4Diamonds.jpg");
      cardImageMap.put("d5", "5Diamonds.jpg");
      cardImageMap.put("d6", "6Diamonds.jpg");
      cardImageMap.put("d7", "7Diamonds.jpg");
      cardImageMap.put("d8", "8Diamonds.jpg");
      cardImageMap.put("d9", "9Diamonds.jpg");
      cardImageMap.put("d10", "10Diamonds.jpg");
      cardImageMap.put("dJ", "JackDiamonds.jpg");
      cardImageMap.put("dQ", "QueenDiamonds.jpg");
      cardImageMap.put("dK", "KingDiamonds.jpg");
      cardImageMap.put("dA", "AceDiamonds.jpg");

      cardImageMap.put("c2", "2Club.jpg");
      cardImageMap.put("c3", "3Club.jpg");
      cardImageMap.put("c4", "4Club.jpg");
      cardImageMap.put("c5", "5Club.jpg");
      cardImageMap.put("c6", "6Club.jpg");
      cardImageMap.put("c7", "7Club.jpg");
      cardImageMap.put("c8", "8Club.jpg");
      cardImageMap.put("c9", "9Club.jpg");
      cardImageMap.put("c10", "10Club.jpg");
      cardImageMap.put("cJ", "JackClub.jpg");
      cardImageMap.put("cQ", "QueenClub.jpg");
      cardImageMap.put("cK", "KingClub.jpg");
      cardImageMap.put("cA", "AceClub.jpg");

      cardImageMap.put("h2", "2Hearts.jpg");
      cardImageMap.put("h3", "3Hearts.jpg");
      cardImageMap.put("h4", "4Hearts.jpg");
      cardImageMap.put("h5", "5Hearts.jpg");
      cardImageMap.put("h6", "6Hearts.jpg");
      cardImageMap.put("h7", "7Hearts.jpg");
      cardImageMap.put("h8", "8Hearts.jpg");
      cardImageMap.put("h9", "9Hearts.jpg");
      cardImageMap.put("h10", "10Hearts.jpg");
      cardImageMap.put("hJ", "JackHearts.jpg");
      cardImageMap.put("hQ", "QueenHearts.jpg");
      cardImageMap.put("hK", "KingHearts.jpg");
      cardImageMap.put("hA", "AceHearts.jpg");

   }
}
