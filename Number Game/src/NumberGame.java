import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    private final Random random;
    private final Scanner sc;
    private int origin, bound;
    private int initialChances;
    private static NumberGame gameObject = null;
    private int highestScore = 0;

    private NumberGame() {
        random = new Random();
        sc = new Scanner(System.in);
        origin = 1;
        bound = 101;
        initialChances = 4;
    }

    public static NumberGame build() {
        if (gameObject == null) {
            gameObject = new NumberGame();
        }
        return gameObject;
    }

    public NumberGame setRange(int origin, int bound) {
        this.origin = origin;
        this.bound = bound;
        return this;
    }

    public NumberGame setInitialChances(int n) {
        initialChances = n;
        return this;
    }

    private boolean wantsToPlayAgain() {
        while (true) {
            System.out.print("\nDo you want to play next round? (y/n) : ");
            char choice = sc.next().charAt(0);

            if (choice == 'y' || choice == 'Y') {
                return true;
            } else if (choice == 'N' || choice == 'n') {
                return false;
            } else {
                System.out.println("Invalid option! please type 'y' or 'n'");
            }
        }
    }

    public void start() {
        System.out.println("=================================================================");
        System.out.println(
                "I have thought of a number between " + origin + " to " + (bound - 1) + ", can you guess it?"
        );

        int roundPlayed = 0, roundChances = initialChances;
        do {
            int chancesleft = roundChances;
            int score = initialChances * 10;

            System.out.println("=================================================================");
            System.out.println("You will get total '" + chancesleft + "' chances in this round, best of luck!\n");

            int randomNum = random.nextInt(origin, bound);

            while (true) {
                System.out.print("What is the Number? : ");
                int guessedNum = sc.nextInt();

                if (guessedNum == randomNum) {
                    System.out.println("Wow! You are genius! You guessed it correct");
                    break;
                } else if (guessedNum < randomNum) {
                    System.out.println("Oops!, Too Low");
                } else {
                    System.out.println("Oops!, Too High");
                }

                chancesleft--;
                score -= 10;

                if (chancesleft == 0) {
                    System.out.println("\nROUND LOST!");
                    break;
                }

                System.out.println("\nCHANCES LEFT: " + chancesleft);
            }
            System.out.println("\nI had thought of the number '" + randomNum + "'");
            System.out.println("ROUND SCORE: " + score);

            if (score > highestScore) {
                // highest score from all the round played
                highestScore = score;
            }
            roundPlayed++;
            roundChances -= 2;
            if (roundChances == 0) {
                System.out.println("\nGAME OVER");
                break;
            }
        } while (wantsToPlayAgain());

        sc.close();
        System.out.println("\nTOTAL ROUND PLAYED: " + roundPlayed + "\tHIGHEST SCORE: " + highestScore);
        System.out.println("\nHope to see you again");
    }

    public static void main(String[] args) {
        NumberGame.build()
                .setRange(1, 101)
                .setInitialChances(10)
                .start();
    }
}
