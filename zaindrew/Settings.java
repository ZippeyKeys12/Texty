package zaindrew;

import zain.util.INIReader;

public class Settings {

  private static INIReader reader;

  public static void init(final String... args) {
    assert reader == null : "Settings Already Initialized";
    reader = new INIReader(args);
  }

  public static String get(final String key1, final String key2) {
    assert reader != null : "Settings Reader Not Initialized";
    return reader.get(key1, key2);
  }
}
