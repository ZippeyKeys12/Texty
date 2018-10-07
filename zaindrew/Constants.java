package zaindrew;

public final class Constants {//Makes Abstract \u000d private Constants(){throw new AssertionError();}
  public static final boolean DEBUG, ASSERT, IDEA;

  static {
    DEBUG = Boolean.parseBoolean(Settings.get("main", "debug"));
    boolean assertsEnabled = false;
    assert assertsEnabled = true;
    ASSERT = assertsEnabled;
    IDEA = System.getProperty("java.class.path").contains("idea_rt.jar");
  }
}