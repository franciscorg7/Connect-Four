import java.util.*;

public class Searches{

  // Closer to 7, harder to defeat CPU mind
  public int maxDepth = 5;

  // Exploration parameter - Auxiliar to MCTS algorithm
  public double expParam = Math.sqrt(2);

  // Random number for simulation process
  public Random r = new Random();

  static double epsilon = 1e-6;

  public Play minimax(Play play, int depth, int currentPlayer){
    int currentValue;

    // Algorithm stops running when someone wins or search reaches maxDepth
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value = play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 1){ // Flag for Player depth field
      currentValue = min(play, depth, play.nextPlayer(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for CPU depth field
      currentValue = max(play, depth, play.nextPlayer(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play min(Play play, int depth, int currentPlayer) {
    int currentValue = Integer.MAX_VALUE;
    int min = Integer.MAX_VALUE;
    int col = 0;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);

      aux.duplicatePlay(minimax(w, depth+1, currentPlayer));
      currentValue = Math.min(currentValue, aux.value);

      if(currentValue < min){
        min = currentValue;
        col = aux.col; // Saves optimal collumn that player can play
      }
    }

    play.value = currentValue;

    if(depth == 0) play.col = col;

    return play;
  }

  private Play max(Play play, int depth, int currentPlayer){
    int currentValue = Integer.MIN_VALUE;
    int max = Integer.MIN_VALUE;
    int col = 0;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);
      aux.duplicatePlay(minimax(w, depth+1, currentPlayer));
      currentValue = Math.max(currentValue, aux.value);

      if(currentValue > max){
        max = currentValue;
        col = aux.col; // Saves optimal collumn for CPU to play
      }
    }

    play.value = currentValue;

    if(depth == 0) play.col = col;

    return play;
  }


  // Adapted from MiniMax
  public Play alphaBeta(Play play, int depth, int currentPlayer, int alpha, int beta){ //Decisao
    int currentValue;

    // Algorithm stops running when someone wins or search reaches maxDepth
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value=play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 0){ // Flag for CPU turn
      currentValue = auxBeta(play, play.nextPlayer(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for player turn
      currentValue = auxAlpha(play, play.nextPlayer(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play auxBeta(Play play, int currentPlayer, int depth, int alpha, int beta){
    int currentValue = Integer.MIN_VALUE;
    int best = Integer.MIN_VALUE;
    int col = 0;
    play.beta = beta;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);
      aux.duplicatePlay(alphaBeta(w, depth+1, currentPlayer, play.alpha, play.beta));
      currentValue = Math.max(currentValue, aux.value);

      if(currentValue > best){
        best = currentValue;
        col = aux.col;
      }

      // If current alpha >= beta then the other sons are rejected, because they won't be helpful
      if(currentValue >= play.beta){
        play.value = currentValue;
        return play;
      }

      alpha = Math.max(alpha, currentValue);
    }

    play.value = currentValue;
    if(depth == 0) play.col = col;

    return play;
  }

  private Play auxAlpha(Play play, int currentPlayer, int depth, int alpha, int beta) {
    int currentValue = Integer.MAX_VALUE;
    int best = Integer.MAX_VALUE;
    int col = 0;
    play.alpha = alpha;
    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);
      aux.duplicatePlay(alphaBeta(w, depth+1, currentPlayer,alpha,beta));
      currentValue = Math.min(currentValue, aux.value);

      if(currentValue < best){
        best = currentValue;
        col = aux.col;
      }

      // If current alpha >= beta then the other sons are rejected, because they won't be helpful
      if(currentValue <= play.alpha){
        play.value = currentValue;
        return play;
      }

      beta = Math.min(beta, currentValue);
    }

    play.value = currentValue;
    if(depth == 0) play.col = col;

    return play;
  }


  // MCTS is not working properly...

  public Play MCTS(Play play, int currentPlayer){

    int col = 0;;

    for(int i=0; i < 1000; i++){

      List<Play> visited = new LinkedList<>();
      Play cur = play;
      visited.add(play);

      while(!cur.isLeaf()){
        cur = select(cur);
        visited.add(cur);
      }

      expand(cur);
      Play newChild = select(cur);
      double value = rollOut(newChild);

      for(Play w : visited){
        backPropag(w, value);
        col = w.col;
      }

      play.col = col;
    }
    return play;
  }

  private Play select(Play play){
    int col = 0;
    Play selected = null;
    double bestValue = Double.MIN_VALUE;
    play.getSons();
    for(Play w : play.sons){
      double uctValue = w.value / (w.visits + epsilon) + Math.sqrt(ln(w.visits + 1) / (w.visits + epsilon)) + r.nextDouble() * epsilon;
      if(uctValue > bestValue){
        selected = w;
        bestValue = uctValue;
      }
    }

    return selected;
  }

  private void expand(Play play){
    play.getSons();
  }

  // Simulation step
  private double rollOut(Play play){
    Play aux = play;
    int random = 0;
    double uctValue = 0.0;

    while(aux.grid.winnerCheck() == 0){
      aux.getSons();
      int size = aux.sons.size();
      random = r.nextInt(size);
      aux = aux.sons.get(random);
      uctValue = aux.value / (aux.visits + epsilon) + Math.sqrt(ln(aux.visits + 1) / (aux.visits + epsilon)) + r.nextDouble() * epsilon;
    }

    return uctValue;
  }

  public void backPropag(Play play, double val) {
    play.visits++;
    play.value += val;
  }

  // Auxiliar to ln()
  public static double ln(double d){
    return Math.log(d) / Math.log(2.0);
  }
}
