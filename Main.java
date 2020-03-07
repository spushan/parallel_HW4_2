
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Main {

    public static class Player implements Runnable {
        private String name;
        private int gesture;
        private ArrayList<Player> playerList;
        public Object lock;
        

        public Player(String name, ArrayList<Player> playerList, Object lock) {
            this.name = "Player " + name;
            this.playerList = playerList;
            this.lock = lock;
        }

        public String getName() {
            return name;
        }

        public int getGesture() {
            return gesture;
        }

        public void setGesture() {
            Random random = new Random();
            this.gesture = random.nextInt(3);
        }

        public boolean check(Player opponent) {
            switch (opponent.getGesture()) {
                case 1:
                    if (gesture == 2) {
                        return true;
                    } else if (gesture == 3) {
                        return false;
                    }
                    break;
                case 2:
                    if (gesture == 3) {
                        return true;
                    } else if (gesture == 1) {
                        return false;
                    }
                    break;
                case 3:
                    if (gesture == 1) {
                        return true;
                    } else if (gesture == 2) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
        Random random = new Random();
        
        @Override
        public void run() {
            while(playerList.size()>1) {
                System.out.println("Inside run");
                synchronized(lock){
                    System.out.println(this.name + "inside the lock");
                    int randomNUmber =random.nextInt(playerList.size());
                    Player opponent = playerList.get(randomNUmber);
                    System.out.println("Checking gesture");
                    this.setGesture();
                    boolean isWinner = check(opponent);
                    if(isWinner) {
                        System.out.println("removing the number");
                        playerList.remove(randomNUmber);
                    }
                }
            }       
        }

    }

    public static void main (String[] args) throws InterruptedException {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of Players: ");
        int playerNum = scanner.nextInt();
        scanner.close();
        Object lock = new Object();
        ExecutorService service = Executors.newCachedThreadPool();
        
        ArrayList<Player> playerList = new ArrayList<Player>();
        for(int i=0; i < playerNum;i++) {
            Player temp = new Player(String.valueOf(i), playerList, lock);
            System.out.println("adding player");
            playerList.add(temp);
        }
        Iterator<Player> playerIterator = playerList.iterator();
        while(playerIterator.hasNext()) {
            Player temp = playerIterator.next();
            System.out.println("Setting gesture");
            temp.setGesture();
        }
        System.out.println("end gesture");

       // while (playerIterator.hasNext()) {
            
        for(Player i : playerList) {
            System.out.println("before executing");
            service.execute(i);
            System.out.println("executing");
            
        }
        System.out.println("the size is " + playerList.size());
        System.out.println(playerList.get(0).getName());

        


    }

}
    
