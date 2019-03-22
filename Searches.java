import java.util.*;

public class Searches{

  //fazer para user dar profundidade máxima
  int maxDepth=5;

  public Play alphaBeta(Play node, int depth, int currentPlayer,int alpha,int beta){ //Decisao
    int v;
    if(node.getBoardState().winnerCheck() != 0 || depth == maxDepth){
      node.score=node.getBoardState().utility(currentPlayer);
      return node;
    }

    else if(currentPlayer == 0){ //PC turn
      v = alphaBetaMaxfunction(node, currentPlayer, depth,alpha,beta).score;
      node.score = v;
      return node;
    }

    else{ //MY turn
      v = alphaBetaMinfunction(node, currentPlayer, depth,alpha,beta).score;
      node.score = v;
      return node;
    }
  }

  private Play alphaBetaMaxfunction(Play node,int currentPlayer, int depth,int alpha,int beta){
    int v = Integer.MIN_VALUE;
    int best = Integer.MIN_VALUE;
    int col = 0;
    node.beta=beta;
    node.getSons();
    if(currentPlayer==1)
    currentPlayer=0;
    else{
      currentPlayer=1;
    }
    for(Play w : node.getfilhos()){
      Play tmp = new Play(w.getBoardState(),currentPlayer);
      tmp.copyNode(alphaBeta(w,depth+1, currentPlayer,node.alpha,node.beta));
      v = Math.max(v,tmp.score);
      if(v>best){
        best = v;
        col = tmp.col;
      }
      if(v >= node.beta){
        node.score=v;
        return node;
      }
      alpha=Math.max(alpha,v);
    }
    node.score=v;
    if(depth==0)
    node.col=col;
    return node;
  }

  private Play alphaBetaMinfunction(Play node, int currentPlayer,int depth, int alpha,int beta) {
    int v = Integer.MAX_VALUE;
    int best = Integer.MAX_VALUE;
    int col = 0;
    node.alpha=alpha;
    node.getSons();
    if (currentPlayer == 1)
    currentPlayer = 0;
    else {
      currentPlayer = 1;
    }
    for (Play w : node.getfilhos()) {
      Play tmp = new Play(w.getBoardState(),currentPlayer);
      tmp.copyNode(alphaBeta(w,depth+1, currentPlayer,alpha,beta));
      v = Math.min(v, tmp.score);
      if(v<best){
        best = v;
        col = tmp.col;
      }
      if(v <= node.alpha){

        node.score=v;
        return node;

      }
      beta=Math.min(beta,v);

    }
    node.score=v;
    if(depth==0)
    node.col=col;
    return node;
  }

  //MINIMAX ---------------------------------------------------------------------------------------

  public Play minimax(Play node, int depth, int currentPlayer){
    int v;
    if(node.getBoardState().winnerCheck() != 0 || depth == maxDepth){ //Decisao
      node.score=node.getBoardState().utility(currentPlayer);
      return node;
    }
    else if(currentPlayer == 0){ //PC
      v = maxfunction(node, currentPlayer, depth).score;
      node.score = v;
      return node;
    }

    else{ //MY turn
      v = minfunction(node, currentPlayer, depth).score;
      node.score = v;
      return node;
    }
  }

  private Play maxfunction(Play node,int currentPlayer, int depth){
    int v = Integer.MIN_VALUE;
    int best = Integer.MIN_VALUE;
    int col = 0;

    node.getSons();
    if(currentPlayer==1) //muda o jogador
     currentPlayer=0;
    else{
      currentPlayer=1;
    }

    for(Play w : node.getfilhos()){
      Play tmp = new Play(w.getBoardState(),currentPlayer);
      tmp.copyNode(minimax(w,depth+1, currentPlayer));
      v = Math.max(v,tmp.score);
      if(v>best){
        best = v;
        col = tmp.col;
      }
    }
    node.score=v;
    if(depth==0)
    node.col=col;
    return node;
  }

  private Play minfunction(Play node, int currentPlayer,int depth) {
    int v = Integer.MAX_VALUE;
    int best = Integer.MAX_VALUE;
    int col = 0;

    node.getSons();

    if(currentPlayer==1)
    currentPlayer=0;
    else{
      currentPlayer=1;
    }
    for (Play w : node.getfilhos()) {
      Play tmp = new Play(w.getBoardState(),currentPlayer);
      tmp.copyNode(minimax(w,depth+1, currentPlayer));
      v = Math.min(v, tmp.score);
      if(v<best){
        best = v;
        col = tmp.col;
      }

    }
    node.score=v;
    if(depth==0)
    node.col=col;
    return node;
  }


}
