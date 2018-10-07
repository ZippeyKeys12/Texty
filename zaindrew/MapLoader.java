package zaindrew;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {

  private final List<String[]> lines = new ArrayList<>();

  public MapLoader(final String location) throws IOException {
    final List<String> lines;
    lines = Files.readAllLines(Paths.get("maps", location + ".map"));
    lines.remove(0);
    lines.stream().map(line -> line.split(",")).forEach(this.lines::add);
  }

  public String[][] toArray() {
    return lines.toArray(new String[][]{});
  }
}