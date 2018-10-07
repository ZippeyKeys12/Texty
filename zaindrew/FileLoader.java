package zaindrew;

import static zaindrew.Attacker.StatusType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import zain.util.CSVReader;

public class FileLoader {//Makes Abstract \u000d private FileLoader(){throw new AssertionError();}

    /**
     * Main Runner Class for testing downloading and loading functionality
     */
    public static void main(final String[] args) {
        GameHelper.clearConsole();
        updateIni();
        updateCsv();
        loadEquipables();
        loadEnemies();
        loadPotions();
        /*if (Constants.DEBUG) {
        for (final Equipable e : Equipable.Registry.getRegistry()) {
        System.out.println(e);
        }
        }*/
        for(final Equipable e : Equipable.Registry.getRegistry())
        {
            System.out.println(e);
        }

    }

    /**
     * Downloads INI files from Google Docs
     */
    private static void updateIni() {
        final String[][] iniUrls = {{"Settings"},
                {"https://docs.google.com/document/u/1/export?format=txt&id=1KfS1_Ge5tulfy1vU0u1HI4I2vc5T07qmj7gGNFFcJgY&token=AC4w5VjQvyE7Q964oLRdtQoRB0mpj8Gbtg%3A1494106710310"}};
        final File directory = new File("ini");
        if (!directory.exists()) {
            assert directory.mkdir() : "Directory: \"ini\" Creation Failed";
        }
        int i = 0;
        try {
            while (i < iniUrls[0].length) {
                final ReadableByteChannel rbc = Channels.newChannel(new URL(iniUrls[1][i]).openStream());
                final FileOutputStream fos = new FileOutputStream(
                        "ini" + File.separator + iniUrls[0][i] + ".ini");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                ++i;
            }
        } catch (final IOException ex) {
            assert false : "Error 404: ini_" + iniUrls[0][i];
        }
        Settings.init(iniUrls[0]);
    }

    /**
     * Downloads CSV files from Google Sheets
     */
    private static void updateCsv() {
        final String[][] csvUrls = {{Enemy.Type.GRUNT.getTypeStr(), Equipable.Type.ARMOR.getTypeStr(),
                    Potion.Type.INSTANT.getTypeStr()},
                {"https://spreadsheets.google.com/spreadsheet/ccc?key=1ROlBejL9s_4MTrpT5leUP_QwB2Nfp1OBLOEC49xQ39s&output=csv",
                    "https://spreadsheets.google.com/spreadsheet/ccc?key=1mHjGq5vTKTCyzxem0YpiE0748o0GmRWEdqKuqDQrJrM&output=csv",
                    "https://spreadsheets.google.com/spreadsheet/ccc?key=15aA5qtrwFEFwx2Y2sfojcE2YbO_t4yFCeoec306J0HU&output=csv"}};
        final File directory = new File("csv");
        if (!directory.exists()) {
            assert directory.mkdir() : "Directory: \"csv\" Creation Failed";
        }
        int i = 0;
        try {
            while (i < csvUrls[0].length) {
                final ReadableByteChannel rbc = Channels.newChannel(new URL(csvUrls[1][i]).openStream());
                final FileOutputStream fos = new FileOutputStream(
                        "csv" + File.separator + csvUrls[0][i] + ".csv");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                ++i;
            }
        } catch (final IOException ex) {
            assert false : "Error 404: csv_" + csvUrls[0][i];
        }
    }

    /**
     * Loads Enemy parameters from the CSV file and registers them to Enemy.Registry
     */
    private static void loadEnemies() {
        final CSVReader reader = new CSVReader(Enemy.Type.GRUNT.getTypeStr());
        for (int lineNum = 0; lineNum < reader.lineCount(); lineNum++) {
            final String[] line = reader.getLine(lineNum);
            if (Constants.ASSERT && line.length < 16)
                assert false : "Not Enough Arguments For Enemy at Line: " + lineNum;
            try {
                Enemy.Registry.register(
                    new Enemy(line[0], Rarity.valueOf(line[1]), Enemy.Type.valueOf(line[2]),
                        GameHelper.convertToDoubleArray(
                            new String[]{line[3], line[4], line[5], line[6], line[7], line[8], line[9]}),
                        GameHelper.convertToDoubleArray(
                            new String[]{line[10], line[11], line[12], line[13], line[14], line[15]})));
            } catch (final NumberFormatException ex) {
                assert false : "Number Parsing Error in: \"csv_Enemy\", Line: " + lineNum;
            }
        }
    }

    /**
     * Loads Equipable parameters from the CSV file and registers them to Equipable.Registry
     */
    private static void loadEquipables() {
        final CSVReader reader = new CSVReader(Equipable.Type.ARMOR.getTypeStr());
        for (int lineNum = 0; lineNum < reader.lineCount(); lineNum++) {
            final String[] line = reader.getLine(lineNum);
            if (Constants.ASSERT && line.length < 18) {
                assert false : "Not Enough Arguments For Equipable at Line: " + lineNum;
            }
            try {
                Equipable.Registry.register(new Equipable(line[0], line[1], Integer.parseInt(line[2]),
                        Equipable.Type.valueOf(line[3]), Rarity.valueOf(line[4]),
                        GameHelper.convertToDoubleArray(
                            new String[]{line[5], line[6], line[7], line[8], line[9], line[10], line[11]}),
                        GameHelper.convertToDoubleArray(
                            new String[]{line[12], line[13], line[14], line[15], line[16], line[17]})));
            }catch (final NumberFormatException ex) {
                for(String s:line)
                    System.out.println(s);
                assert false : "Number Parsing Error in: \"csv_Equipable\", Line: " + lineNum;
            }
        }
    }

    /**
     * Loads Potion parameters from the CSV file and registers them to Potion.Registry
     */
    private static void loadPotions() {
        final CSVReader reader = new CSVReader(Potion.Type.INSTANT.getTypeStr());
        for (int lineNum = 0; lineNum < reader.lineCount(); lineNum++) {
            final String[] line = reader.getLine(lineNum);
            if (Constants.ASSERT && line.length < 8) {
                assert false : "Not Enough Arguments For Potion at Line: " + lineNum;
            }
            try {
                Potion.Registry.register(
                    new Potion(line[0], line[1], Integer.parseInt(line[2]), Rarity.valueOf(line[3]),
                        Potion.Type.valueOf(line[4]),
                        GameHelper.convertToDoubleArray(
                            new String[]{line[5], line[6], line[7]})));
            } catch (final NumberFormatException ex) {
                assert false : "Number Parsing Error in: \"csv_Enemy\", Line: " + lineNum;
            }
        }
    }
}
