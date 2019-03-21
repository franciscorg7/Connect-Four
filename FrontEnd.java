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

  static void firstPlayerMenu(){
    System.out.println("Who do you want to go first?");
    System.out.println(ANSI_CYAN + "1) " + ANSI_RESET + "Me");
    System.out.println(ANSI_CYAN + "2) " + ANSI_RESET + "CPU");
    System.out.println();
    System.out.print("Option: ");
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
  static void DrawMenu(){
  System.out.println("Well that was a hard draw,do you want to try again? ");
            System.out.print (ANSI_CYAN+ "1)" + ANSI_RESET + "Yes    ");
            System.out.println(ANSI_CYAN + "2)" + ANSI_RESET +  "No");
          }

    static void LoseWinMenu(){
  System.out.println("do you want to try again? ");
            System.out.print (ANSI_CYAN+ "1)" + ANSI_RESET + "Yes    ");
            System.out.println(ANSI_CYAN + "2)" + ANSI_RESET  + "No");
          }






  }
