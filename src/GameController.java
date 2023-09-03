package Play_bluff;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 

public class GameController{
    private List<Player> players;
    private Deck deck;    
    private Table table;

 

    public GameController(String[] name) {
        players = new ArrayList<>();
        deck = new Deck();
        table = new Table();

 

        for (int i = 1; i <= name.length; i++) {
            players.add(new Player(name[i-1]));
        }
        deck.shuffle();
        while(!deck.isEmpty()) {
            for(Player player:players) {
                Card card = deck.dealCard();
                if(card != null)
                    player.getHand().add(card);
            }
        }
        
    }

    public void startGame() {
        deck.shuffle();
      
        Player currentPlayer = null;
        for (Player player : players) {
            if (player.getHand().contains(new Card(Card.Rank.ACE, Card.Suit.SPADES))) {
                currentPlayer = player;
                break;
            }
        }
        if (currentPlayer == null) {
            System.out.println("No player has the Ace of Spades. Game cannot continue.");
            return;
        }

 

        System.out.println(currentPlayer.getName() + " got __Ace of Spades__ she will start game");
        boolean bluff = false;

 

        while (true) {
            Scanner scanner = new Scanner(System.in);

 

            System.out.println(currentPlayer.getName() + ", your current hand: " + currentPlayer.getHand());

 

            if (!bluff) {
                System.out.print("Enter the number of cards to place on the table (k): ");
                int k = scanner.nextInt();

 

                if (k > 0 && k <= currentPlayer.getHand().size()) {
                    System.out.print("Enter the rank of the cards to place (e.g., 'two aces'): ");
                    scanner.nextLine(); 
                    String claim = scanner.nextLine().trim();

 

                    boolean validClaim = validateClaim(currentPlayer.getHand(), claim, k);

 

                    if (validClaim) {
                        List<Card> selectedCards = selectCardsByRank(currentPlayer.getHand(), claim.split(" ")[1]);
                        table.addCards(selectedCards);
                        currentPlayer.getHand().removeAll(selectedCards);
                        bluff = true;

 

                        System.out.println(currentPlayer.getName() + " placed " + k + " " + claim + " on the table.");
                    } else {
                        System.out.println("Invalid claim. Please make a homogeneous claim.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number of cards (k).");
                }
                currentPlayer = getNextPlayer(currentPlayer);
            } else {
                System.out.print(currentPlayer.getName() + ", your options: (a) pass, (b) call out 'bluff', (c) add cards to the table: ");
                String choice = scanner.nextLine().trim();

 

                if (choice.equalsIgnoreCase("a")) {
                    System.out.println(currentPlayer.getName() + " passed.");
                    currentPlayer = getNextPlayer(currentPlayer);
                } else if (choice.equalsIgnoreCase("b")) {
                    Player previousPlayer = getPreviousPlayer(currentPlayer);
                    if (!isBluff(table.revealCards(), previousPlayer.getHand())) {
                        System.out.println(previousPlayer.getName() + " was bluffing! " + currentPlayer.getName() + " wins the round.");
                        currentPlayer.getHand().addAll(table.revealCards());
                    } else {
                        System.out.println(currentPlayer.getName() + " was wrong! " + previousPlayer.getName() + " wins the round.");
                        previousPlayer.getHand().addAll(table.revealCards());
                    }
                    table.clear();
                    bluff = false;
                    break;
                } else if (choice.equalsIgnoreCase("c")) {
                    System.out.print("Enter the number of cards to add to the table (q): ");
                    int q = scanner.nextInt();

 

                    if (q > 0 && q <= currentPlayer.getHand().size()) {
                        System.out.print("Enter the rank of the cards to add (e.g., 'two aces'): ");
                        scanner.nextLine(); 
                        String claim = scanner.nextLine().trim();

 

                        boolean validClaim = validateClaim(currentPlayer.getHand(), claim, q);

 

                        if (validClaim && claim.split(" ")[1].equals(table.revealCards().get(0).getRank().toString())) {
                            List<Card> selectedCards = selectCardsByRank(currentPlayer.getHand(), claim.split(" ")[1]);
                            table.revealCards().addAll(selectedCards);
                            currentPlayer.getHand().removeAll(selectedCards);

 

                            System.out.println(currentPlayer.getName() + " added " + q + " " + claim + " to the table.");
                        } else {
                            System.out.println("Invalid claim or rank mismatch. Please make a homogeneous claim and maintain the rank.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number of cards (q).");
                    }
                } else {
                    System.out.println("Invalid choice. Please choose 'a', 'b', or 'c'.");
                }
            }
        }
    }
    private Player getNextPlayer(Player currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

 

    private Player getPreviousPlayer(Player currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        int previousIndex = (currentIndex - 1 + players.size()) % players.size();
        return players.get(previousIndex);
    }

 

    private boolean validateClaim(List<Card> hand, String claim, int k) {
        String[] parts = claim.split(" ");
        if (parts.length != 2) {
            return false;
        }

 

        String rank = parts[1];
        long count = hand.stream().filter(card -> card.getRank().toString().equalsIgnoreCase(rank)).count();
        return count >= k;
    }

 

    private List<Card> selectCardsByRank(List<Card> hand, String rank) {
        List<Card> selectedCards = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().toString().equals(rank)) {
                selectedCards.add(hand.get(i));
                if (selectedCards.size() == 3) {
                    break;
                }
            }
        }
        return selectedCards;
    }
    private boolean isBluff(List<Card> tableCards, List<Card> claimCards) {
        return tableCards.size() != claimCards.size() || !tableCards.get(0).getRank().equals(claimCards.get(0).getRank());
    }

    public void printGameState() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }
        System.out.println("Table cards: " + table.revealCards());
    }

    public Player getWinner() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return null;
    }
        public static void main(String[] args) {
            GameController bluffGame = new GameController(new String[]{ "Revathi","Sandhya","Rucha","Srima"}) ;
            for (int round = 1; round <= 3; round++) {
                System.out.println("\nRound " + round + ":");
                bluffGame.printGameState();
                bluffGame.startGame();
            }
        }
}
