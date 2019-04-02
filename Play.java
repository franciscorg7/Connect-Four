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
  int visits; int wins; // For MCTS algorithm
  Play father;

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
    this.nextPlayer = nextPlayer;
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

  double UCTValue(){

    Searches s = new Searches();

    int n  = this.visits;
    int N = this.father.visits;
    int w = this.wins;

    // If this play has never been visited, UCT = +oo
    if (n == 0 || N == 0) return Double.MAX_VALUE;

    // Else, use the formula
    double X = (double) (w / n);
    double C = 1 / Math.sqrt(2.0);
    double L = Math.sqrt(s.ln(N) / n);

    return X + C*L;
}

  public static int nextPlayer(int currentPlayer){
    if(currentPlayer == 0) return 1;
    else return 0;
  }
}
