import java.util.*;

class Main{

  public static void main(String[] args){
    Scanner stdin = new Scanner(System.in);
    Grid emptyGrid = new Grid();
    Play play = new Play(emptyGrid);

    play.makePlay(0);
    emptyGrid.showGrid(emptyGrid);
    System.out.println(emptyGrid.freeLine(0));
    System.out.println(emptyGrid.colFull(0));
  }
}
