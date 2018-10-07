package zaindrew;

import java.util.HashMap;

public final class Calculator {//Makes Abstract \u000d private Calculator(){throw new AssertionError();}

  public static int calcLvl(final int x) {
    return (int) Math.ceil(((x / 10000.0) + Math.pow(.0000063 * x, 2)) / 2.0);
  }

  public static int calcXp(final int x) {
    //TODO Solve for xp
    return x;
  }

  public static double calculateResistance(final int k, final double x) {
    switch (k) {
      case 1:
        return calculateResistance1(x);
      case 2:
        return calculateResistance2(x);
      case 3:
        return calculateResistance3(x);
      default:
        return x / 100.0;
    }
  }

  public static double calculateResistance(final int k,
      final HashMap<MagicType, double[]> magicStats,
      final MagicType type) {
    double product = 1.0;
    for (final MagicType i : MagicType.values()) {
      product *= calculateResistance(k, magicStats.get(i)[1]);
    }
    return Math.sqrt(Math.pow(product, 1 / 3.0) * calculateResistance(k, magicStats.get(type)[1]));
  }

  private static double calculateResistance1(final double x) {
    return (x == 0) ? 0 : (.00975242 * (
        27.5436 * Math.pow(x / 100.0, 3) + 25 * x / 100.0 + 50 * Math.sqrt(x / 100.0) - 1 / (100.0
            * (x / 100.0 + 1))));
  }

  private static double calculateResistance2(final double x) {
    return (x == 0) ? 0 : (.005 * (Math.sqrt(3) * Math.pow(7, (1 / 5.0)) * (
        (Math.pow(2, (x / 50.0 - 1 / 2.0)) * Math.pow(5, (x / 50.0)) * (Math.pow(x, (2 / 3.0)) - 21)
            * Math
            .pow(x, (5 / 3.0)) * Math.sqrt((Math.pow(x, (1 / 9.0)) * (
            3 * (Math.pow(x, (5 / 9.0)) * Math.pow((Math.pow(x, (2 / 3.0)) - 21), 2) + 21 * Math
                .pow((Math.log(3) + Math.log(7)), 2)) + 28 * Math.pow(Math.log(x), 2) - 84 * (
                Math.log(3) + Math.log(7)) * Math.log(x))) / Math
            .pow((2.0 * Math.log(x) - 3 * (Math.log(3) + Math.log(7))), 2))) / Math
            .pow((2.0 * Math.log(x) - 3 * (Math.log(3) + Math.log(7))), (1 / 5.0))) + 10 * x));
  }

  private static double calculateResistance3(final double x) {
    final double a = Math.pow(x, 3 / 2.0) / 10.0;
    final double b = ((Math.pow(x, 2) / 100.0 + x) / 2.0 + 100 * Math.sqrt(x)) / 2.0;
    final double c = Math.pow(x / 10.0, 2);
    final double m1 = Math.pow(a * b * c, 1 / 3.0);
    final double m2 = 3 / (1.0 / a + 1.0 / b + 1.0 / c);
    final double m3 = (a - b) / (Math.log(a) - Math.log(b));
    final double m4 = Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)) / 2.0);
    final double e = Math.max(Math.max(m1, m2), Math.max(m3, m4));
    final double f = Math.min(Math.min(m1, m2), Math.min(m3, m4));
    final double g = (e + f) / 2.0;
    return Math.max(e, g);
  }
}