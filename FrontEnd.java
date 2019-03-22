import java.util.*;

class FrontEnd{

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";


  static void title(){
    clearScreen();
    System.out.println(ANSI_YELLOW + "* CONNECT FOUR *" + ANSI_RESET);
    System.out.println();
  }

  static void gameModeMenu(){
    System.out.println("SELECT GAMEMODE");
    System.out.println();
    System.out.println(ANSI_CYAN + "1) " + ANSI_RESET + "Single Player");
    System.out.println(ANSI_CYAN + "2) " + ANSI_RESET + "Multiplayer");
    System.out.println();
    System.out.print("Option: ");
  }

  static void firstPlayerMenu1(){
    System.out.println(ANSI_CYAN +"WELCOME TO SINGLE PLAYER MODE , GOOD LUCK" + ANSI_RESET);
    System.out.println();
    sleep(2000);
    System.out.println(ANSI_WHITE + "(Lets see if you can win)" + ANSI_RESET);

    sleep(2000);
    clearScreen();
    System.out.println("Who do you want to go first?");
    System.out.println();
    System.out.println(ANSI_CYAN + "1) " + ANSI_RESET + "Me");
    System.out.println(ANSI_CYAN + "2) " + ANSI_RESET + "CPU");
    System.out.println();
    System.out.print("Option: ");
  }

  static void firstPlayerMenu2(){
    System.out.println("Who do you want to go first?");
    System.out.println();
    System.out.println(ANSI_CYAN + "1) " + ANSI_RESET + "Player 1 (X)");
    System.out.println(ANSI_CYAN + "2) " + ANSI_RESET + "Player 2 (O)");
    System.out.println();
    System.out.print("Option: ");
  }

  static void algorithmMenu(){
    System.out.println("Choose the algorithm you want CPU to run:");
    System.out.println();
    System.out.println(ANSI_CYAN + "1) " + ANSI_RESET + "MiniMax");
    System.out.println(ANSI_CYAN + "2) " + ANSI_RESET + "Alpha-Beta");
    System.out.println();
    System.out.print("Option: ");
  }

  static void winnerCPU(){
    System.out.println("CPU won (predictable)!");
  }

  static void clearScreen(){
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  static void sleep(int a){
    try{
	     Thread.sleep(a); // 1000ms = 1s
    } catch(InterruptedException ex){
	     System.exit(0);
    }
  }

}
