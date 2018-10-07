package zain.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import zaindrew.Constants;

public class CSVReader implements Iterable<String[]> {

  private final List<String[]> lines = new ArrayList<>();

  public CSVReader(final String location) {
    final List<String> lines;
    try {
      lines = Files.readAllLines(Paths.get(Constants.IDEA ? "src/csv" : "csv", location + ".csv"));
    } catch (final IOException ex) {
      ex.printStackTrace();
      return;
    }
    lines.remove(0);
    for (final String line : lines) {
      this.lines.add(line.replaceAll("[\\t\\n\\r]", "").split(","));
    }
  }

  public String[][] toArray() {
    return lines.toArray(new String[][]{});
  }

  public String[] getLine(final int lineNum) {
    return lines.get(lineNum);
  }

  public int lineCount() {
    return lines.size();
  }

  @Override
  public Iterator<String[]> iterator() {
    return lines.iterator();
  }

  @Override
  public void forEach(final Consumer<? super String[]> action) {
    lines.forEach(action);
  }

  @Override
  public Spliterator<String[]> spliterator() {
    return lines.spliterator();
  }
}