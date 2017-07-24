package no.kh498.motc.level;

import no.kh498.motc.Log;

import java.io.*;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public class LevelIO {

    private static final byte VERSION = 17;


    public static void save(final Level level) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(level.getName()));

            //Write the version of the saving format
            outputStream.write(VERSION);
            outputStream.write(level.getSizeX());
            outputStream.write(level.getSizeY());
            final Region[][] regions = level.getRegions();
            for (int i = 0; i < regions.length; i++) {
                for (int j = 0; j < regions[i].length; j++) {
                    final Region region = regions[i][j];
                    if (region != null) {
                        region.write(outputStream);
                    }
                    else {
                        System.out.println("Region is null at " + i + ", " + j);
                    }
                }
            }
            /*
             *   The Tail can be used to verify the content of the file.
             *   The version is xor'd to the the first regions tile's enum ordinal
             */
            outputStream.write(VERSION ^ level.getSizeX() + level.getSizeY() +
                                         level.getRegions()[0][0].getTile(0, 0).getBackground().ordinal());
            Log.log("Verification byte is " + VERSION + " ^ " +
                    level.getRegions()[0][0].getTile(0, 0).getBackground().ordinal() + " = " +
                    (VERSION ^ level.getRegions()[0][0].getTile(0, 0).getBackground().ordinal()));
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Level load(final String fileName) {
        final File file = new File(fileName);
        final byte[] result = new byte[(int) file.length()];
        try {
            InputStream inputStream = null;
            try {
                int totalBytesRead = 0;
                inputStream = new BufferedInputStream(new FileInputStream((fileName)));
                while (totalBytesRead < result.length) {
                    final int bytesRemaining = result.length - totalBytesRead;
                    final int bytesRead = inputStream.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // the first byte should be the version byte
        final boolean versionMatch = result[0] == VERSION;
        /* The verification byte is the version xor'd to the the first regions tile's enum ordinal.
         * which is basically the first byte in the first region. File: vxyb... where v is the version, x and y is
         * the coordinate and b is the wanted byte
         */
        final boolean verificationMatch = result[result.length - 1] == (VERSION ^ (result[1] + result[2] + result[5]));
        if (!versionMatch || !verificationMatch) {
            Log.log("The file '" + fileName + "' does not match the a MotC level file!");
            return null;
        }
        final byte levelSizeX = result[1];
        final byte levelSizeY = result[2];

        final Region[][] regions = new Region[levelSizeX][levelSizeY];

        //we start a i = 3 as the three bytes is the version number and the level size
        final int startPosition = 3;
        for (int i = startPosition; i < result.length - startPosition; i++) {
            final byte regionX = result[i];
            final byte regionY = result[++i];

            final Tile[][] tiles = new Tile[Region.REGION_SIZE][Region.REGION_SIZE];

            final int worldX = regionX * Region.REGION_SIZE;
            final int worldY = regionY * Region.REGION_SIZE;

            for (int j = 0; j < Region.REGION_SIZE; j++) {
                for (int k = 0; k < Region.REGION_SIZE; k++) {
                    tiles[j][k] = new Tile(j + worldX, k + worldY, result[++i], result[++i], result[++i], result[++i]);
                }
            }
            Log.log("regionX: " + regionX + ", regionY: " + regionY + " pointer: " + i);

            regions[regionX][regionY] = new Region(regionX, regionY, tiles);
        }
        return new Level(fileName, levelSizeX, levelSizeY, regions);
    }

}
