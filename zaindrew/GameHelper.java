package zaindrew;

import java.util.stream.Stream;

public final class GameHelper {//Makes Abstract \u000d private GameHelper(){throw new AssertionError();}
    public static void clearConsole() {
        System.out.print('\u000C');
    }

    public static double[] convertToDoubleArray(final String[] array) {
        return Stream.of(array).mapToDouble(Double::parseDouble).toArray();
    }
}