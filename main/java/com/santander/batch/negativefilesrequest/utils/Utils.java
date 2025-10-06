package com.santander.batch.negativefilesrequest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

/**
 * The type Utils.
 */
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * Format path string.
     *
     * @param path the path
     * @return the string
     */
    public static String formatPath(String path) {
        if (path.endsWith(File.separator)) {
            return path;
        }
        return path.concat(File.separator);
    }


    /**
     * Write line file.
     *
     * @param line the line
     * @param file the file
     */
    public static void writeLineFile(String line, String file) {

        try (BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            br.write(line);
            br.newLine();
        } catch (IOException ex) {
            log.error("[writeLineFile][Error during file write]",ex);
        }
    }

    /**
     * Remove last line from file.
     *
     * @param file the file
     */
    public static void removeLastLineFromFile(String file) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                log.error("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader brl = new BufferedReader(new FileReader(file));
            long numLines = brl.lines().count();
            brl.close();

            readFile(file, tempFile, numLines);


            //Delete the original file
            if (!inFile.delete()) {
                log.error("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                log.error("Could not rename file");
            }
        } catch (IOException ex) {
            log.error("[removeLineFile][Error during line remove]", ex);
        }
    }

    private static void readFile(String file, File tempFile, long numLines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
             BufferedReader br = new BufferedReader(new FileReader(file))
        ) {
            String line;
            for (int i = 0; i < numLines - 1; i++) {
                line = br.readLine();
                if (line != null) {
                    pw.println(line);
                    pw.flush();
                }
            }
        }catch (IOException ex) {
            log.error("[removeLineFile][Error during line remove]", ex);
        }
    }
}

