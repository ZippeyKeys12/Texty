package zaindrew;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main { 
    private static int index=0;
    private static Player player=new Player("Coolguy",0,0,new double[]{100,100,100,25,25,25,25}, new double[]{25,25,25,25,25,25});
    private static Rarity[] rarities={
            Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON,
            Rarity.RARE, Rarity.RARE, Rarity.RARE, Rarity.RARE, Rarity.RARE, Rarity.RARE, Rarity.RARE, Rarity.RARE,
            Rarity.EPIC, Rarity.EPIC, Rarity.EPIC, Rarity.EPIC,
            Rarity.LEGENDARY, Rarity.LEGENDARY,
            Rarity.GODLY};

    public static void main(String[] args) {
        FileLoader.main(new String[]{});
        run();
    }

    private static void run() {
        int input;
        do{
            GameHelper.clearConsole();
            System.out.printf(
                "1. Enter Dungeon%n"+
                "2. Enter Shop%n"+
                "3. Equip Items%n"+
                "What Would You Like To Do?(Enter An Invalid Key To Exit): "
            );
            input=takeInt();
            if(input==0)
                System.exit(0);
        }while(input<1||input>3);
        switch(input){
            case 1:
            new BattleSystem().Battle(player, Enemy.Registry.get(rarities[index++]));
            if(index==rarities.length) index=0;
            break;
            case 2:
            initShop();
            Shop.shop();
            break;
            case 3:
            checkInv();
            break;
        }
        run();
    }

    private static void checkInv() {
        GameHelper.clearConsole();
        int input;
        do{
            for(int i=1; i<=player.getInv().size(); i++)
                System.out.printf("%d. &s%n",i,Item.Registry.get(player.getInv().get(i-1)));
            System.out.println("Which do you want to use? Enter the number to the left(Enter An Invalid Key To Exit):");
            input=takeInt();
        }while(input<0||input>player.getInv().size());
        player.use(player.getInv().get(input));
    }

    private static int takeInt() {
        Scanner scan=new Scanner(System.in, "utf-8");
        int returner;
        try{
            returner=scan.nextInt();
        }catch(InputMismatchException ex){
            scan.next();
            returner=0;
        }
        return returner;
    }

    private static void initShop() {
        Shop.init(player);
    }
}