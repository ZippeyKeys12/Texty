package zaindrew;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private static ArrayList<Item> items;
    private static Player player;
    private static Item bought;

    public static void init(final Player p) {
        items=Item.Registry.getAll();
        player=p;
    }

    public static void shop() {
        
        for(final Equipable e : Equipable.Registry.getRegistry())
        {
            items.add(e);
        }
        
        final Scanner sc = new Scanner(System.in);
        boolean on = true;
        int ch;

        while (on) {
            System.out.println("Welcome!");
            System.out.println("0) Exit " + "\n"
                + "1) Add item" + "\n"
                + "2) Remove item");
            ch = sc.nextInt();
 
            switch (ch) {
                default:
                case 0:
                System.out.println("\n" + "Good bye!");
                on = false;
                break;

                case 1:
                for(int i = 0; i < items.size(); i++)
                {
                    System.out.println(items.get(i));
                }
                System.out.println("What item do you want");
                final String itemName = sc.next();
                
                
                for (int i = 0; i < Item.Registry.getAll().size(); i++) {
                    if (Item.Registry.getAll().get(i).getName().equals(itemName)) {
                        player.addGold(-1*Item.Registry.getAll().get(i).getPrice());
                        player.getInv().add("ARMOR"+Item.Registry.getAll().get(i));
                        System.out.println("Item bought.");
                        break;
                    } 
                    
                }
                System.out.println("\n" + "Good bye!");
                on = false;
                break;
 
                case 2:
                player.getInv().forEach(System.out::println);
                System.out.println("What do you want to sell");
                final String item = sc.next();
                for (int i = 0; i < player.getInv().size(); i++) {
                    if (player.getInv().get(i).equals(item)) {
                        player.addGold(Item.Registry.get(player.getInv().get(i)).getPrice());
                        player.getInv().remove(player.getInv().get(i));
                        System.out.println("Item sold.");
                        break;
                    } 
                    
                }
                System.out.println("\n" + "Good bye!");
                on = false;
            }

        }
    }

}
