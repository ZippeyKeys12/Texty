package zaindrew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Potion extends Item implements Registry.Item<Potion.Type> {

  private final String id;
  private final Rarity rarity;
  private final Type type;
  private final double[] effects;

  public Potion(
      final String name, final String desc, final int price, final Rarity rarity, final Type type,
      final double[] effects) {
    super(name, desc, price);
    this.rarity = rarity;
    this.type = type;
    this.effects = Arrays.copyOf(effects, 3);
    id = Arrays.stream(effects).mapToObj(String::valueOf)
        .collect(Collectors.joining("", "" + super.hashCode() + type + rarity, ""));
  }

  public double[] getEffects() {
    return Arrays.copyOf(effects, effects.length);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof Potion)) {
      return false;
    }
    final Potion potion = (Potion) object;
    return hashCode() == object.hashCode()
        && super.equals(object)
        && type.equals(potion.getType())
        && rarity.equals(potion.getRarity())
        && Arrays.equals(effects, potion.getEffects());
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
    INSTANT, OVERTIME;

    @Override
    public String getTypeStr() {
      return Settings.get("csvloaders", "potion");
    }

    @Override
    public String toString() {
      switch (this) {
        case INSTANT:
          return "Instantaneous";
        case OVERTIME:
          return "Effect Over Time";
        default:
          return "";
      }
    }
  }

  public static class Registry {

    private static final zaindrew.Registry<Potion, Type> registry = new zaindrew.Registry<>(
        Type.values());

    public static zaindrew.Registry<Potion, Type> getRegistry() {
      return registry;
    }

    public static void register(final Potion potion) {
      registry.register(potion);
    }

    public static Potion get(final String id) {
      return registry.get(id);
    }

    public static Potion get(final Rarity rarity) {
      return registry.get(rarity);
    }

    public static Potion get(final Type potionType) {
      return registry.get(potionType);
    }

    public static Collection<Potion> getAll() {
      return registry.getAll();
    }
  }
}