import java.util.*;
import java.util.ArrayList;

public class Play{

  public static int genPlays = 0;
  private Grid grid;
  private ArrayList<Play> sons;
  int nextPlayer;
  int col;
  int score;
  int alpha;
  int beta;

  public Play (Grid grid, int nextPlayer, int col){
    this.grid = new Grid();
    this.grid.copy(grid);
    this.nextPlayer = nextPlayer;
    this.col = col;
    this.score = 0;
    this.alpha = Integer.MIN_VALUE;
    this.beta = Integer.MAX_VALUE;
    sons = new ArrayList<Play>();
  }

  public Play (Grid grid, int nextPlayer){
    this.grid=new Grid();
    this.grid.copy(grid);
    this.nextPlayer=nextPlayer;
    this.col=0;
    this.score = 0;
    this.alpha=Integer.MIN_VALUE;
    this.beta=Integer.MAX_VALUE;
    sons = new ArrayList<Play>();

  }

  public void getSons(){
    Grid tmp = new Grid();
    tmp.copy(grid);
    for(int j = 0; j < 7; j++){
      if(tmp.properPlay(j)){
        tmp.copy(grid);
        tmp.makePlay(j,nextPlayer);

        if(nextPlayer==0) {
          Play filho= new Play(tmp, 1,j);
          sons.add(filho);
        }
        else{
          Play filho = new Play(tmp,0,j);
          sons.add(filho);
        }
      }
    }
    genPlays += sons.size(); // Each time a play generate children, generated plays get incremented by the number of sons
  }

  public void copyNode(Play x){
    this.grid.copy(x.grid);
    this.sons = x.sons; //faz sons adiciona a sons (ArrayList)
    this.nextPlayer=x.nextPlayer;
    this.col=x.col;
    this.score=x.score;
    this.alpha=x.alpha;
    this.beta=x.beta;
  }


  public Grid getBoardState() {
    return grid;
  }

  public ArrayList<Play> getfilhos() {
    return sons;
  }





}
