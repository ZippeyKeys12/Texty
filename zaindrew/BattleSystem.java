package zaindrew;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class BattleSystem
{
    public void Battle(Player player, Enemy opponent)
    {
        Scanner sc = new Scanner(System.in);

        int choice, atkChoice;
        String potName, spellName;
        int health = 0;
        MainLoop:
        do
        {
            do{
                GameHelper.clearConsole();
                System.out.println(opponent.getName()+" Health: "+opponent.getHealth());
                System.out.println("Player Health: "+player.getHealth());
                System.out.println("Player Stamina: "+player.getStamina());
                System.out.println("Player Mana: "+player.getMana());
                System.out.println("0) Attack " + "\n"
                    + "1) Potion");
                try{
                    choice = sc.nextInt();
                }catch(InputMismatchException ex){
                    sc.next();
                    choice=-1;
                }
            }while(choice<0||choice>1);
            switch(choice) {
                case 0: 
                System.out.println("What attack do you wish to perform?");
                System.out.println("0) Slash\n"+
                    "1) Fireball\n"+
                    "2) Ice Lance\n"+
                    "3) Thunderbolt");
                atkChoice = sc.nextInt();
                switch(atkChoice) {
                    case 0:
                    if(!opponent.takeDamage(player))
                        break MainLoop;
                    break;

                    case 1:
                    opponent.addStatusEffect(Attacker.StatusType.BURN,4);
                    if(!opponent.takeDamage(player, MagicType.FIRE)){
                        System.out.println("F");
                        break MainLoop;}
                    break;

                    case 2:
                    opponent.addStatusEffect(Attacker.StatusType.FREEZE,4);
                    if(!opponent.takeDamage(player, MagicType.FROST))
                        break MainLoop;
                    break;

                    case 3:
                    opponent.addStatusEffect(Attacker.StatusType.PARALYZE,4);
                    if(!opponent.takeDamage(player, MagicType.SHOCK))
                        break MainLoop;
                    break;
                }
                break;

                case 1:
                System.out.println("What potion do you want to use?");
                for(int i = 0; i < player.getInv("potion").size(); i++)
                {
                    System.out.println(Item.Registry.get(player.getInv("potion").get(i)));
                }
                potName = sc.next();

                CheckInv:
                for(int i = 0; i < player.getInv().size(); i++)
                {
                    Potion potion=(Potion)Item.Registry.get(player.getInv("potion").get(i));
                    if(potion.getName().toLowerCase().equals(potName.toLowerCase()))
                    {
                        player.use(player.getInv("potion").get(i));
                        break CheckInv;
                    }
                }
            }
            opponent.tickEffects();
            player.tickEffects();
        }while(opponent.getHealth()>0&&player.getHealth()>0);
    }
}
