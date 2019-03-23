import java.util.*;

public class Main{

  static int currentPlayer = 0;

  public static void main(String[] args) {
    Scanner stdin=new Scanner (System.in);
    FrontEnd visuals = new FrontEnd();

    int algorithm = 0;

    visuals.title();
    visuals.sleep(1000);
    Grid jogo = new Grid();

    visuals.gameModeMenu();
    int gameMode = stdin.nextInt(); // {Single or Multiplayer}
    visuals.clearScreen();

    if(gameMode == 1){ // Single Player Mode
      visuals.firstPlayerMenu1();
      int firstPlayer = stdin.nextInt();
      visuals.clearScreen();

      visuals.algorithmMenu();
      algorithm = stdin.nextInt();

      if(firstPlayer == 1){
        visuals.clearScreen();
        jogo.showGrid();
        System.out.println();
        System.out.print("Play: ");
        int col = stdin.nextInt();
        jogo.makePlay(col, currentPlayer);

        visuals.clearScreen();

        jogo.showGrid();
        proxjogador();
      }

      else proxjogador();
    }

      Searches CPU = new Searches();
      int op3 = 0;

      // Game started, then enters loop (it only ends when the game ends)
      while(jogo.winnerCheck()==0){

        long startTime = System.currentTimeMillis();

        // Single Player Mode
        if(gameMode == 1){
          if(currentPlayer==0) { // Player is playing
            System.out.println();
            System.out.print("Play: ");
            op3 = stdin.nextInt();
            jogo.makePlay(op3, currentPlayer);
            visuals.clearScreen();
            jogo.showGrid();
            proxjogador();
          }
          else{ // CPU is playing
            Play inicial = new Play(jogo, currentPlayer);
            Play play = null;
            if(algorithm == 1) play = CPU.minimax(inicial,0, currentPlayer);
            else play = CPU.alphaBeta(inicial, 0 ,currentPlayer, inicial.alpha, inicial.beta); //inicial.alpha?

            jogo.makePlay(play.col, currentPlayer); //col.col?
            visuals.sleep(500);
            visuals.clearScreen();
            jogo.showGrid();
            long endTime= (long)(System.currentTimeMillis());
            System.out.println();
            System.out.println(visuals.ANSI_GREEN + "GEEK STATISTICS: " + visuals.ANSI_RESET);
            System.out.printf(" > CPU response time: %.3fs%n", (endTime - startTime) / 1000d);
            System.out.println(" > Generated plays: " + play.genPlays);
            play.genPlays=0; // Resets each play

            proxjogador();
          }
        }

        // Multiplayer Mode
        else{
          jogo.showGrid();
          System.out.println();
          System.out.print("Play: "); op3 = stdin.nextInt();
          jogo.makePlay(op3, currentPlayer);
          System.out.println();
          visuals.clearScreen();
          proxjogador();

        }

        int winner = jogo.winnerCheck();
        if(winner == 1){
          if(gameMode == 1) System.out.println("You won (you were not suposed to)!");
          else if(gameMode == 2) System.out.println("Player X won!");
          break;
        }

        else if(winner == -1){
          if(gameMode == 1) visuals.winnerCPU();
          else if(gameMode == 2) System.out.println("Player O won!");
          break;
        }
         else if(winner==-1)
        System.out.println("Empate!");
      }
    }

    public static void proxjogador(){
      if(currentPlayer==0)
        currentPlayer=1;
      else
        currentPlayer=0;
    }
  }
