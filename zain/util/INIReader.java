package zain.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class INIReader implements Map<String, HashMap<String, String>> {

  private final HashMap<String, HashMap<String, String>> fileMap = new HashMap<>();

  public INIReader(final String... locations) {
    int length;
    String section = null;
    List<String> lines;
    for (final String location : locations) {
      try {
        lines = Files.readAllLines(Paths.get(System.getProperty("java.class.path")
            .contains("idea_rt.jar") ? "src/ini" : "ini", location + ".ini"));
      } catch (final IOException ex) {
        ex.printStackTrace();
        continue;
      }
      length = lines.size();
      while (lines.size() > 0) {
        final String line = lines.get(0);
        assert !(section == null && line.substring(0, 1).equals(";") && line.charAt(0) != '[') :
            "No Section Defined in INI File: " + location + ".ini";
        if (line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
          section = line.substring(1, line.length() - 1);
          if (!fileMap.containsKey(section)) {
            fileMap.put(section, new HashMap<>());
          }
        } else if (line.indexOf(';') == -1) {
          assert (line.indexOf('=') != -1) :
              "Field Missing Declaration in INI File: \"" + location + "\", Line: " + (
                  length - lines.size() + 1);
          if (line.substring(line.indexOf('=') + 1).charAt(0) == '{'
              && line.charAt(line.length() - 1) == '}') {
            fileMap.get(section).put(line.substring(0, line.indexOf('=')),
                line.substring(line.indexOf('=') + 2, line.length() - 1).replace(" ", ""));
          }
          fileMap.get(section).put(line.substring(0, line.indexOf('=')),
              line.substring(line.indexOf('=') + 1));
        }
        lines.remove(0);
      }
    }
  }

  @Override
  public int size() {
    return fileMap.size();
  }

  @Override
  public boolean isEmpty() {
    return fileMap.isEmpty();
  }

  @Override
  public boolean containsKey(final Object key) {
    return fileMap.containsKey(key);
  }

  @Override
  public boolean containsValue(final Object value) {
    return fileMap.containsValue(value);
  }

  @Override
  public HashMap<String, String> get(final Object key) {
    return fileMap.get(key);
  }

  @Override
  public HashMap<String, String> put(final String string, final HashMap<String, String> map) {
    return fileMap.put(string, map);
  }

  @Override
  public HashMap<String, String> remove(final Object key) {
    return fileMap.remove(key);
  }

  @Override
  public void putAll(final Map<? extends String, ? extends HashMap<String, String>> map) {
    fileMap.putAll(map);
  }

  @Override
  public void clear() {
    fileMap.clear();
  }

  @Override
  public Set<String> keySet() {
    return fileMap.keySet();
  }

  @Override
  public Collection<HashMap<String, String>> values() {
    return fileMap.values();
  }

  @Override
  public Set<Map.Entry<String, HashMap<String, String>>> entrySet() {
    return fileMap.entrySet();
  }

  public String get(final String key1, final String key2) {
    assert get(key1) != null : "Section Not Found";
    assert get(key1).get(key2) != null : "Value Not Found";
    return get(key1).get(key2);
  }
}
