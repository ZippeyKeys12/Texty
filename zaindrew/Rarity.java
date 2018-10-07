package zaindrew;

public enum Rarity {
  COMMON, RARE, EPIC, LEGENDARY, GODLY;

  public int getValue() {
    switch (this) {
      case COMMON:
        return 25;
      case RARE:
        return 50;
      case EPIC:
        return 75;
      case LEGENDARY:
        return 100;
      case GODLY:
        return 200;
      default:
        return 0;
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case COMMON:
        return "Common";
      case RARE:
        return "Rare";
      case EPIC:
        return "Epic";
      case LEGENDARY:
        return "Legendary";
      case GODLY:
        return "Godly";
      default:
        return "";
    }
  }
}
