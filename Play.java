import java.util.*;
import java.util.ArrayList;

public class Play{

  public static int genPlays = 0;
  public Grid grid;
  public ArrayList<Play> sons;
  int nextPlayer;
  int col;
  int value;
  int alpha;
  int beta;

  public Play (Grid grid, int nextPlayer, int col){
    this.grid = new Grid();
    this.grid.duplicateGrid(grid);
    this.nextPlayer = nextPlayer;
    this.col = col;
    this.value = 0;
    this.alpha = Integer.MIN_VALUE;
    this.beta = Integer.MAX_VALUE;
    sons = new ArrayList<Play>();
  }

  public Play (Grid grid, int nextPlayer){
    this.grid = new Grid();
    this.grid.duplicateGrid(grid);
    this.nextPlayer=nextPlayer;
    this.col = 0;
    this.value = 0;
    this.alpha = Integer.MIN_VALUE;
    this.beta = Integer.MAX_VALUE;
    sons = new ArrayList<Play>();
  }

  public void getSons(){
    Grid aux = new Grid();
    aux.duplicateGrid(grid);
    for(int j = 0; j < 7; j++){
      if(aux.properPlay(j)){
        aux.duplicateGrid(grid);
        aux.makePlay(j, nextPlayer);

        if(nextPlayer == 0) {
          Play son= new Play(aux, 1, j);
          sons.add(son);
        }
        else{
          Play son = new Play(aux, 0, j);
          sons.add(son);
        }
      }
    }
    genPlays += sons.size(); // Each time a play generate children, generated plays get incremented by the number of sons
  }

  // Working with auxiliar Play
  public void duplicatePlay(Play x){
    this.grid.duplicateGrid(x.grid);
    this.sons = x.sons; //faz sons adiciona a sons (ArrayList)
    this.nextPlayer = x.nextPlayer;
    this.col = x.col;
    this.value = x.value;
    this.alpha = x.alpha;
    this.beta = x.beta;
  }

  public static int proxjogador(int currentPlayer){
    if(currentPlayer == 0) return 1;
    else return 0;
  }
}
