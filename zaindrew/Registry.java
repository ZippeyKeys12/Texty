package zaindrew;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Registry<Content extends Registry.Item<Type>, Type extends Registry.Typing> implements Iterable<Content>, Map<String, Content> {

  private final HashMap<String, Content> mapID = new HashMap<>();
  private final HashMap<Rarity, HashSet<Content>> mapRarity = new HashMap<>();
  private final HashMap<Type, HashSet<Content>> mapType = new HashMap<>();

  public Registry(final Type[] types) {
    for (final Rarity rarity : Rarity.values())
      mapRarity.put(rarity, new HashSet<>());
    for (final Type type : types) {
      mapType.put(type, new HashSet<>());
    }
  }

  public synchronized void register(final Content content) {
    assert !mapID.containsKey(content.getID()) : "Overlapping IDs";
    mapID.put(content.getID(), content);
    if (Constants.ASSERT)
      assert mapRarity.get(content.getRarity()).add(content) && mapType.get(content.getType())
          .add(content) : "Registration Failed";
    else {
      mapRarity.get(content.getRarity()).add(content);
      mapType.get(content.getType()).add(content);
    }
  }

  public synchronized Content get(final Rarity rarity) {
    return mapRarity.get(rarity).stream().findAny().get();
  }

  public synchronized Content get(final Type type) {
    return mapType.get(type).stream().findAny().get();
  }

  public synchronized Collection<Content> getAll() {
    return mapID.values();
  }

  //Object Methods
  @Override
  public synchronized int hashCode() {
    return (mapID.hashCode() + mapRarity.hashCode() + mapType.hashCode()) / 3;
  }

  @Override
  public synchronized boolean equals(final Object registry) {
    return registry instanceof Registry && getAll().equals(((Registry) registry).getAll());
  }

  //Map Methods
  @Override
  public synchronized int size() {return mapID.size();}

  @Override
  public synchronized boolean isEmpty() {
    return mapID.isEmpty() && mapRarity.isEmpty() && mapType.isEmpty();
  }

  @Override
  public synchronized boolean containsKey(final Object id) {return mapID.containsKey(id);}

  @Override
  public synchronized boolean containsValue(final Object content) {
    return mapID.containsValue(content);
  }

  @Override
  public synchronized Content get(final Object id) {return mapID.get(id);}

  @Override
  public synchronized Content put(final String id, final Content content) {
    assert id.equals(content.getID()) : "IDs did not match: " + id;
    register(content);
    return null;
  }

  @Override
  public synchronized Content remove(final Object id) {
    final Content content = mapID.remove(id);
    mapRarity.get(content.getRarity()).remove(content);
    mapType.get(content.getType()).remove(content);
    return content;
  }

  @Override
  public synchronized void putAll(final Map<? extends String, ? extends Content> map) {
    map.forEach(this::put);
  }

  @Override
  public synchronized void clear() {
    mapID.clear();
    mapRarity.clear();
    mapType.clear();
  }

  @Override
  public synchronized Set<String> keySet() {return mapID.keySet();}

  @Override
  public synchronized Collection<Content> values() {return mapID.values();}

  @Override
  public synchronized Set<Map.Entry<String, Content>> entrySet() {return mapID.entrySet();}

  @Override
  public synchronized void forEach(final BiConsumer<? super String, ? super Content> action) {
    mapID.forEach(action);
  }

  //Iterable Methods
  @Override
  public synchronized Iterator<Content> iterator() {
    return mapID.values().iterator();
  }

  @Override
  public synchronized void forEach(final Consumer<? super Content> action) {
    mapID.values().forEach(action);
  }

  @Override
  public synchronized Spliterator<Content> spliterator() {
    return mapID.values().spliterator();
  }

  //Interfaces
  public interface Typing {

    String getTypeStr();
  }

  public interface Item<Type extends Typing> {

    String getID();

    Rarity getRarity();

    Type getType();
  }
}