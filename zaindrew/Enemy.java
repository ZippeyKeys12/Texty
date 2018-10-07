package zaindrew;

import java.util.Collection;
import java.util.Arrays;

public class Enemy extends Attacker implements Registry.Item<Enemy.Type> {

  private final String id;
  private final Rarity rarity;
  private final Type type;

  public Enemy(
      final String name, final Rarity rarity, final Type type, final double[] stats,
      final double[] magicStats) {
    super(name, stats, magicStats, null, 0);
    this.rarity = rarity;
    this.type = type;
    final StringBuilder id = new StringBuilder(
        "" + super.hashCode() + "" + type.toString().charAt(0) + rarity.toString().charAt(0));
    final double[][] statsArrays = new double[2][];
    statsArrays[0] = stats;
    statsArrays[1] = magicStats;
    for (final double[] i : statsArrays) {
      Arrays.stream(i).forEach(id::append);
    }
    this.id = id.toString();
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public Rarity getRarity() {
    return rarity;
  }

  @Override
  public Type getType() {
    return type;
  }

  public enum Type implements zaindrew.Registry.Typing {
    GRUNT, ELITE, TANK, MINIBOSS, BOSS, HIDDENBOSS;

    @Override
    public String getTypeStr() {
      return Settings.get("csvloaders", "enemy");
    }

    @Override
    public String toString() {
      switch (this) {
        case GRUNT:
          return "Grunt";
        case ELITE:
          return "Elite";
        case TANK:
          return "Tank";
        case MINIBOSS:
          return "Mini-Boss";
        case BOSS:
          return "Boss";
        case HIDDENBOSS:
          return "Hidden Boss";
        default:
          return "";
      }
    }
  }

  public static class Registry {

    private static final zaindrew.Registry<Enemy, Enemy.Type> registry = new zaindrew.Registry<>(
        Type.values());

    public static zaindrew.Registry<Enemy, Type> getRegistry() {
      return registry;
    }

    public static void register(final Enemy enemy) {
      registry.register(enemy);
    }

    public static Enemy get(final String id) {
      return registry.get(id);
    }

    public static Enemy get(final Rarity rarity) {
      return registry.get(rarity);
    }

    public static Enemy get(final Type enemyType) {
      return registry.get(enemyType);
    }

    public static Collection<Enemy> getAll() {
      return registry.getAll();
    }
  }
}
