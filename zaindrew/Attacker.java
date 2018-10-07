package zaindrew;

import java.util.Arrays;
import java.util.HashMap;

public class Attacker {

    private final double[] vitals = new double[3];
    private final String name;
    private final HashMap<MagicType, double[]> magicStats = new HashMap<>();
    private int statusCounter;
    private double[] stats = new double[6];
    private Attacker.StatusType status;

    public Attacker(
    final String name, final double[] stats, final double[] magicStats, final Attacker.StatusType status,
    final int statusCounter) {
        assert stats.length == 7 : "Stats Array Length Invalid";
        assert magicStats.length == 6 : "Magic Stats Array Length Invalid";
        this.name = name;
        this.stats = Arrays.copyOf(stats, stats.length);
        System.arraycopy(stats, 0, vitals, 0, vitals.length);
        this.status = status;
        this.statusCounter = statusCounter;
        for (int i = 0; i < MagicType.values().length; i++) {
            final double[] array = new double[2];
            System.arraycopy(magicStats, i * 2, array, 0, 2);
            this.magicStats.put(MagicType.values()[i], array);
        }
    }

    public void addStatusEffect( Attacker.StatusType type,  int counter) {
        status = type;
        statusCounter = counter;
    }

    public boolean tickEffects() {
        if (status == null) {
            return true;
        }
        switch (status) {
            case BURN:
            vitals[0] -=
            stats[0] * 1 - Double.parseDouble(Settings.get("statusEffects", "burnPercent"));
            case FREEZE:
            case PARALYZE:
            break;
            default:
            return true;
        }
        statusCounter--;
        if (statusCounter <= 0) {
            status = null;
        }
        return !(vitals[0] <= 0);
    }

    public String getName() {
        return name;
    }

    public double[] getStats() {
        return Arrays.copyOf(stats, stats.length);
    }

    public StatusType getStatus() {
        return status;
    }

    public double getHealth() {
        return vitals[0];
    }

    public void setHealth(final double dub) {
        vitals[0] = dub;
    }

    public double getStamina() {
        return vitals[1];
    }

    public void setStamina(final double dub) {
        vitals[1] = dub;
    }

    public double getMana() {
        return vitals[2];
    }

    public void setMana(final double dub) {
        vitals[2] = dub;
    }

    public double getTotalHealth() {
        return stats[0];
    }

    public double getTotalStamina() {
        return stats[1];
    }

    public double getTotalMana() {
        return stats[2];
    }

    public double getStrength() {
        return stats[3];
    }

    public double getStrength(final MagicType type) {
        return Math.sqrt(stats[4] * magicStats.get(type)[0]);
    }

    public double getResistance() {
        return Calculator.calculateResistance(1, stats[3]);
    }

    public double getResistance(final MagicType type) {
        return Calculator.calculateResistance(1, magicStats, type);
    }

    /**
     * Deals damage and returns if user is still alive
     *
     * @param attacker Attacking character
     * @return Am I still alive?
     */
    public boolean takeDamage(final Attacker attacker) {
        if (attacker.getStatus() != null) {
            switch (attacker.getStatus()) {
                case FREEZE:
                return true;
                case BURN:
                break;
                case PARALYZE:
                if (Math.random() < Double.parseDouble(Settings.get("statusEffects", "paralyzeChance"))) {
                    return true;
                }
            }
        }
        vitals[0] -= attacker.getStrength() - (1 - Calculator.calculateResistance(1, stats[5]));
        return !(vitals[0] <= 0);
    }

    public boolean takeDamage(final Attacker attacker, final MagicType type) {
        if (attacker.getStatus() != null) {
            switch (attacker.getStatus()) {
                case FREEZE:
                return true;
                case BURN:
                break;
                case PARALYZE:
                if (Math.random() < Double.parseDouble(Settings.get("statusEffects", "paralyzeChance"))) {
                    return true;
                }
            }
        }
        vitals[0] -= attacker.getStrength(type) * (1 - getResistance(type));
        return !(vitals[0] <= 0);
    }

    public enum StatusType {
        FREEZE, BURN, PARALYZE;

        @Override
        public String toString() {
            switch (this) {
                case FREEZE:
                return "Frozen";
                case BURN:
                return "Burned";
                case PARALYZE:
                return "Paralyzed";
                default:
                return "None";
            }
        }
    }

}
