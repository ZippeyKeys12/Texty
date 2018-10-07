package zaindrew;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import zaindrew.graphics.Direction;

public class Player extends Attacker {

    public final Sprite Sprite;
    private final String[] equipped = new String[2];
    private final HashMap<String, ArrayList<String>> items = new HashMap<>();
    private final ArrayList<String> armors = new ArrayList<>();
    private final ArrayList<String> weapons = new ArrayList<>();
    private final ArrayList<String> potions = new ArrayList<>();
    private int gold;
    private int xp;
    private int lvl;

    public Player(final String name, final int x, final int y, final double[] stats,
    final double[] magicStats) {
        super(name, stats, magicStats, null, 0);
        items.put("armor", armors);
        items.put("weapon", weapons);
        items.put("potion", potions);
        xp = gold = 0;
        Sprite = new Sprite(x, y, Direction.UP);
    }

    public void use(final String type_ID) {
        final String header = type_ID.substring(0, type_ID.indexOf("_"));
        assert header.equals("armor") || header.equals("weapon") || header
        .equals("potion") : "Incorrect Header";
        assert items.get(header)
        .contains(type_ID.substring(type_ID.indexOf("_") + 1)) : "Inventory Does Not Contain Item";

        switch (header) {
            case "armor":
            equipped[0] = type_ID;
            return;
            case "weapon":
            equipped[1] = type_ID;
            return;
            case "potion":
            final Equipable equipable = (Equipable) Item.Registry.get(equipped[0]);
            final double[] effects = Potion.Registry.get(type_ID.substring(type_ID.indexOf("_") + 1))
                .getEffects();
            setHealth(
                (effects[0] + getHealth() <= getTotalHealth() + equipable.getStats()[0]) ? effects[0]
                    + getHealth() : getTotalHealth() + equipable.getStats()[0]);
            setStamina(
                (effects[1] + getStamina() <= getTotalStamina() + equipable.getStats()[1]) ? effects[1]
                    + getStamina() : getTotalStamina() + equipable.getStats()[1]);
            setMana((effects[2] + getMana() <= getTotalMana() + equipable.getStats()[2]) ? effects[2]
                    + getMana() : getTotalMana() + equipable.getStats()[2]);
            break;
            default:
            break;
        }
    }

    public boolean add(final String type_ID) {
        if (armors.size() + weapons.size() + potions.size() > Integer
        .parseInt(Settings.get("player", "inventoryLimit"))) {
            return false;
        }
        items.get(type_ID.substring(0, type_ID.indexOf("_")))
        .add(type_ID.substring(type_ID.indexOf("_") + 1));
        return true;
    }

    public void addGold(int newGold) {
        gold+=newGold;
    }

    public int getGold() {
        return gold;
    }

    public int getXp() {
        return xp;
    }

    public int getLvl() {
        return lvl = Calculator.calcLvl(xp);
    }

    public int getResistancePerc() {
        return (int) Math.ceil(100 * getResistance());
    }

    public double getResistancePerc(final MagicType type) {
        return (int) Math.ceil(100 * getResistance(type));
    }

    /**
     * Calculates xp to add
     *
     * @param enemyID ID of Enemy to gain xp from
     * @return Did the Player level up
     */
    public boolean addExp(final String enemyID) {
        if (getLvl() == 250) {
            return false;
        }
        final int gain;
        final int enemyLvl = Enemy.Registry.get(enemyID).getRarity().getValue();
        if (enemyLvl < lvl) {
            gain = Calculator.calcXp(1);
        } else {
            gain = Calculator.calcXp(enemyLvl);
        }
        if (xp + gain > Integer.parseInt(Settings.get("player", "maxXP"))) {
            xp = 2500000;
        } else {
            xp += gain;
        }
        return Calculator.calcLvl(xp) > lvl;
    }

    public ArrayList<String> getInv(final String type) {
        assert type.equals("armor") || type.equals("weapon") || type
        .equals("potion") : "Invalid Inventory Type";
        switch (type) {
            case "armor":
            return armors;
            case "weapon":
            return weapons;
            case "potion":
            return potions;
            default:
            return null;
        }
    }

    public ArrayList<String> getInv() {
        return new ArrayList<String>() {
            {
                addAll(armors);
                addAll(weapons);
                addAll(potions);
            }
        };
    }

    private static class Sprite {

        private boolean rightFoot = true;
        private int x, y;
        private Direction direction;

        public Sprite(final int x, final int y, final Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public boolean isRightFoot() {
            return rightFoot;
        }

        public Pair<Pair<Integer, Integer>, Direction> getInfo() {
            return new Pair<>(new Pair<>(x, y), direction);
        }

        public void move(final Direction direction) {
            if (this.direction != direction) {
                this.direction = direction;
            } else {
                rightFoot = !rightFoot;
            }
            int move = 1;
            switch (direction) {
                case DOWN:
                move = -1;
                case UP:
                y += move;

                case LEFT:
                move = -1;
                case RIGHT:
                x += move;
            }
        }
    }
}
