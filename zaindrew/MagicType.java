package zaindrew;

public enum MagicType {
  FIRE, FROST, SHOCK;

  @Override
  public String toString() {
    switch (this) {
      case FIRE:
        return "Pyromancy";
      case FROST:
        return "Cryomancy";
      case SHOCK:
        return "Electromancy";
    }
    return "";
  }
}
