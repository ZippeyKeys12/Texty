package zaindrew;

import java.util.ArrayList;

public abstract class Item {

  private final int price;
  private final String name;
  private final String desc;

  public Item(final String name, final String desc, final int price) {
    this.name = name;
    this.desc = desc;
    this.price = price;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  @Override
  public int hashCode() {
    return (name + desc + price).hashCode();
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof Item)) {
      return false;
    }
    final Item item = (Item) object;
    return (hashCode() == object.hashCode()
        && name.equals(item.getName())
        && desc.equals(item.getDesc())
        && price == item.getPrice());
  }

  public static class Registry {

    public static Item get(final String id) {
      switch (id.substring(0, id.indexOf("_"))) {
        case "armor":
        case "weapon":
          return Equipable.Registry.get(id.substring(id.indexOf("_") + 1));
        case "potion":
          return Potion.Registry.get(id.substring(id.indexOf("_") + 1));
        default:
          assert false : "Invalid Header";
      }
      return Equipable.Registry.get("");
    }

    public static ArrayList<Item> getAll() {
      return new ArrayList<Item>() {
        {
          addAll(Equipable.Registry.getAll());
          addAll(Potion.Registry.getAll());
        }
      };
    }
  }
}