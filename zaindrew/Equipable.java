package zaindrew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Equipable extends Item implements Registry.Item<Equipable.Type>,
    Comparable<Equipable> {

  private final double[] stats;
  private final String id;
  private final Type type;
  private final Rarity rarity;
  private final HashMap<MagicType, double[]> magicStats = new HashMap<>();

  public Equipable(
      final String name, final String desc, final int price, final Type type, final Rarity rarity,
      final double[] stats, final double[] magicStats) {
    super(name, desc, price);
    this.type = type;
    this.rarity = rarity;
    this.stats = Arrays.copyOf(stats, stats.length);
    final StringBuilder id = new StringBuilder(
        "" + super.hashCode() + "" + type.toString().charAt(0) + rarity.toString().charAt(0));
    final double[][] statsArrays = new double[2][];
    statsArrays[0] = stats;
    statsArrays[1] = magicStats;
    for (final double[] i : statsArrays) {
      Arrays.stream(i).forEach(id::append);
    }
    this.id = id.toString();
    try {
      for (int i = 0; i < MagicType.values().length; i++) {
        final double[] array = new double[2];
        System.arraycopy(magicStats, i * 2, array, 0, 2);
        this.magicStats.put(MagicType.values()[i], array);
      }
    } catch (final ArrayIndexOutOfBoundsException exception) {
      assert false : "Magical Stats Array Size Mismatch";
    }
  }

  @Override
  public String toString() {
    return "Name: " + getName() +
        "\nDescription: " + getDesc() +
        "\nPrice: " + getPrice() +
        "\nType: " + type.toString() +
        "\nRarity: " + rarity.toString() +
        "\nStats: " + Arrays.toString(stats) +
        "\nMagic Stats: " + magicStats.toString();
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof Equipable)) {
      return false;
    }
    final Equipable equipable = (Equipable) object;
    return hashCode() == object.hashCode()
        && Arrays.stream(MagicType.values()).parallel()
        .allMatch(i -> Arrays.equals(magicStats.get(i), equipable.getMagicStats().get(i)))
        && (super.equals(object)
        && type.equals(equipable.getType())
        && rarity.equals(equipable.getRarity())
        && Arrays.equals(stats, equipable.getStats()));
  }

  @Override
  public int compareTo(final Equipable equipable) {
    return hashCode() - equipable.hashCode();
  }

  public double[] getStats() {
    return Arrays.copyOf(stats, stats.length);
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

  public HashMap<MagicType, double[]> getMagicStats() {
    final HashMap<MagicType, double[]> map = new HashMap<>();
    for (final MagicType magicType : MagicType.values()) {
      map.put(magicType,
          Arrays.copyOf(magicStats.get(magicType), magicStats.get(magicType).length));
    }
    return map;
  }

  public enum Type implements zaindrew.Registry.Typing {
    ARMOR, WEAPON;

    @Override
    public String getTypeStr() {
      return Settings.get("csvloaders", "equipable");
    }

    @Override
    public String toString() {
      switch (this) {
        case ARMOR:
          return "Armor";
        case WEAPON:
          return "Weapon";
        default:
          return "";
      }
    }
  }

  public static class Registry {

    private static final zaindrew.Registry<Equipable, Type> registry = new zaindrew.Registry<>(
        Type.values());

    public static zaindrew.Registry<Equipable, Type> getRegistry() {
      return registry;
    }

    public static void register(final Equipable equipable) {
      registry.register(equipable);
    }

    public static Equipable get(final String id) {
      return registry.get(id);
    }

    public static Equipable get(final Rarity rarity) {
      return registry.get(rarity);
    }

    public static Equipable get(final Type equipableType) {
      return registry.get(equipableType);
    }

    public static Collection<Equipable> getAll() {
      return registry.getAll();
    }
  }
}