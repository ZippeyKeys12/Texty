package zaindrew.graphics;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import zaindrew.MapLoader;
import zaindrew.Settings;

public class Map {

  ArrayList<String[][]> maps = new ArrayList<>();
  private Image top, bottom;
  private int currentMap;

  public Map() {
    final String[] mapNames = Settings.get("main", "maps").split(",");
    for (final String mapName : mapNames) {
      try {
        top = ImageIO.read(getClass().getResource("/img/" + mapName + "T.png"));
        bottom = ImageIO.read(getClass().getResource("/img/" + mapName + "B.png"));
        maps.add(new MapLoader(mapName).toArray());
      } catch (final IOException ex) {
        ex.printStackTrace();
      }
    }
    currentMap = 0;
  }

  public boolean setMap(final int index) {
    if (index > 24 || currentMap == index) {
      return false;
    }
    currentMap = index;
    return true;
  }

  public Image getTop() {
    return top;
  }

  public Image getBottom() {
    return bottom;
  }

  public boolean canMove(final int x, final int y, final Direction direction) {
    int director = 1;
    switch (direction) {
      case DOWN:
        director = -1;
      case UP:
        return y != 12 * director
            && !maps.get(currentMap)[-1 * (y + director) + 12][x + 12].equals("X");

      case LEFT:
        director = -1;
      case RIGHT:
        return x != 12 * director
            && !maps.get(currentMap)[-1 * y + 12][x + 12 + director].equals("X");
      default:
        return false;
    }
  }
}
