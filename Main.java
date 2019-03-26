import java.util.*;

public class Main{

  static int currentPlayer = 0;

  public static void main(String[] args) {
    Scanner stdin = new Scanner (System.in);
    FrontEnd visuals = new FrontEnd();
    Searches CPU = new Searches();

    boolean restart = true;

    while(restart){
    currentPlayer = 0; // It is needed to reset currentPlayer after program restarts
    int algorithm = 0;

    visuals.title();
    visuals.sleep(1000);
    Grid grid = new Grid();

    visuals.gameModeMenu();
    int gameMode = stdin.nextInt(); // {Single or Multiplayer}
    visuals.clearScreen();

    if(gameMode == 1){ // Single Player Mode
      visuals.difficulty();
      int difficulty = stdin.nextInt();

      switch(difficulty){

        case 1:
          CPU.maxDepth = 1;
          System.out.println();
          System.out.print(visuals.ANSI_CYAN + "MESSAGE: " + visuals.ANSI_RESET + "Really? What are you afraid of...?");
        break;

        case 2: CPU.maxDepth = 2;
          System.out.println();
          System.out.print(visuals.ANSI_CYAN + "MESSAGE: " + visuals.ANSI_RESET + "Well, ok. Good luck!");
        break;

        case 3: CPU.maxDepth = 4;
          System.out.println();
          System.out.print(visuals.ANSI_CYAN + "MESSAGE: " + visuals.ANSI_RESET + "Let's see if you can win ahah!");
        break;

        case 4: CPU.maxDepth = 7;
          System.out.println();
          System.out.print(visuals.ANSI_CYAN + "MESSAGE: " + visuals.ANSI_RESET + "You are actually crazy.");
        break;
      }

      visuals.firstPlayerMenu1();
      int firstPlayer = stdin.nextInt();
      visuals.clearScreen();

      visuals.algorithmMenu();
      algorithm = stdin.nextInt();

      // Player goes first
      if(firstPlayer == 1){
        visuals.clearScreen();
        grid.showGrid();
        System.out.println();
        System.out.print("Play: ");
        int col = stdin.nextInt();

        if(grid.makePlay(col, currentPlayer) == 1){
          visuals.clearScreen();
          grid.showGrid();

          nextPlayer();
        }

        else{
          visuals.sleep(1000);
          visuals.clearScreen();
          grid.showGrid();
          currentPlayer = 0;
        }
      }

      // CPU starts to play
      else nextPlayer();
    }

      int col = 0;

      // Game started, then enters loop (it only ends when the game ends)
      while(grid.winnerCheck() == 0){

        long startTime = System.currentTimeMillis();

        // Single Player Mode
        if(gameMode == 1){
          if(currentPlayer==0) { // Player is playing
            System.out.println();
            System.out.print("Play: ");
            col = stdin.nextInt();

            if(grid.makePlay(col, currentPlayer) == 1){
              visuals.clearScreen();
              grid.showGrid();

              nextPlayer();
            }

            else{
              visuals.sleep(1000);
              visuals.clearScreen();
              grid.showGrid();
              currentPlayer = 0;
            }
          }

          else{ // CPU is playing
            Play root = new Play(grid, currentPlayer);
            Play play = null;
            if(algorithm == 1) play = CPU.minimax(root, 0, currentPlayer);
            else play = CPU.alphaBeta(root, 0, currentPlayer, root.alpha, root.beta);

            grid.makePlay(play.col, currentPlayer);
            visuals.sleep(500);
            visuals.clearScreen();
            grid.showGrid();
            long endTime= (long)(System.currentTimeMillis());
            System.out.println();
            System.out.println(visuals.ANSI_GREEN + "GEEK STATISTICS: " + visuals.ANSI_RESET);
            System.out.printf(" > CPU response time: %.3fs%n", (endTime - startTime) / 1000d);
            System.out.println(" > Generated plays: " + play.genPlays);
            play.genPlays = 0; // Resets each play

            nextPlayer();
          }
        }

        // Multiplayer Mode
        else{
          grid.showGrid();
          System.out.println();
          System.out.print("Play: "); col = stdin.nextInt();

          if(grid.makePlay(col, currentPlayer) == 1){
            visuals.clearScreen();

            nextPlayer();
          }

          else{
            visuals.sleep(1000);
            visuals.clearScreen();
          }
        }

        int winner = grid.winnerCheck();
        if(winner == 1){
          if(gameMode == 1){
          System.out.println();
          System.out.println(visuals.ANSI_CYAN +"YOU WON!" + visuals.ANSI_RESET );
          System.out.println();
          visuals.sleep(1000);
          visuals.restart();
          int restartOption = stdin.nextInt();

           if(restartOption == 1) restart = true;
           else if(restartOption == 2){
            visuals.clearScreen();
            visuals.goodbye();
              restart = false;
            }
           }

          else if(gameMode == 2){
            System.out.println(visuals.ANSI_YELLOW + "- WE HAVE A WINNER -" + visuals.ANSI_RESET);
            System.out.println();
            System.out.println("(Player X won!)");
            System.out.println();
            visuals.sleep(1000);
            visuals.restart();
            int restartOption = stdin.nextInt();

            if(restartOption == 1) restart = true;
            else if(restartOption == 2) {
                visuals.clearScreen();
              visuals.goodbye();

            restart = false;
          }
          }
         }

        // CPU or Player2 won
        else if(winner == -1){
          if(gameMode == 1){
           visuals.winnerCPU();
           System.out.println();
           visuals.sleep(1000);
           visuals.restart();

           int restartOption = stdin.nextInt();

           if(restartOption == 1) restart = true;
           else if(restartOption == 2){
               visuals.clearScreen();
              visuals.goodbye();
             restart = false;
           }
           }

          else if(gameMode == 2){
            System.out.println(visuals.ANSI_YELLOW + "- WE HAVE A WINNER -" + visuals.ANSI_RESET);
            System.out.println();
            System.out.println("(Player O won!)");
            System.out.println();
            visuals.sleep(1000);
            visuals.restart();
            int restartOption = stdin.nextInt();

            if(restartOption == 1) restart = true;
            else if(restartOption == 2){
              visuals.clearScreen();
              visuals.goodbye();
              restart = false;
            }
          }
  }


         else if(grid.gridFull()){
            visuals.clearScreen();
           System.out.println(visuals.ANSI_CYAN +"It's a draw!" + visuals.ANSI_RESET);
             System.out.println();
            visuals.sleep(1000);
            visuals.restart();
            int restartOption = stdin.nextInt();

            if(restartOption == 1) {restart = true; break;}
            else if(restartOption == 2){
              visuals.clearScreen();
              visuals.goodbye();

              return;

            }
          }

      }
    }
  }

  // Iterate throw currentPlayer value
  public static void nextPlayer(){
    if(currentPlayer==0) currentPlayer=1;
    else currentPlayer=0;
  }
}
