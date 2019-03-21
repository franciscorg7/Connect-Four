import java.util.*;

class Main{

  public static void main(String[] args){
    Scanner stdin = new Scanner(System.in);
    Grid grid = new Grid();
    Play play = new Play(grid);
    FrontEnd visuals = new FrontEnd();
    boolean restart=true;
do {


    visuals.title();
    visuals.sleep(1000);
    visuals.firstPlayerMenu();
    int option = stdin.nextInt();

    // Human wants to go first
    if(option == 1){
      Player player = new Player(1);
      System.out.println();
      System.out.println("First player: " + player.player1);
      System.out.println("Second player: " + player.player2);


      int picker=0;
      while(!grid.gridFull()){

        int winner = grid.winnerCheck();

        // To check if there is any winner
        if(winner != -1){
          switch(winner){
            case 0:
              System.out.println("Player 1 wins");
            return;

            case 1:
              System.out.println("Player 2 wins");
            return;
          }
          return;
        }

    System.out.print(visuals.ANSI_BLUE + "Please choose your move: " +visuals.ANSI_RESET);
        int col = stdin.nextInt();

        visuals.clearScreen();

        play.makePlay(col, picker);
        grid.showGrid(grid);

        picker++;
      }
    }

    // CPU goes first
    else if(option == 2){
      Player player = new Player(2);
      System.out.println();
      System.out.println("First player: " + player.player1);
      System.out.println("Second player: " + player.player2);

      int picker=1;
      while(!grid.gridFull()){

        int winner = grid.winnerCheck();

        // To check if there is any winner
        if(winner != -1){
          switch(winner){
            case 0:
              System.out.println("Player 1 wins");
            return;

            case 1:
              System.out.println("Player 2 wins");
            return;
          }


        return;
        }
    System.out.print(visuals.ANSI_BLUE + "Please choose your move: " +visuals.ANSI_RESET);
        int col = stdin.nextInt();

        visuals.clearScreen();

        play.makePlay(col, picker);
        grid.showGrid(grid);

        picker++;
      }
    }

  }
while (restart);
}
}


/*
    // DEBUGGING
    play.makePlay(4);
    grid.showGrid(emptyGrid);
    System.out.println(grid.freeLine(0));
    System.out.println(grid.gridFull());
*/
