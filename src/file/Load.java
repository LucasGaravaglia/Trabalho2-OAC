package src.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for read and treat file.
 * 
 * @author Lucas Garavaglia
 */
public class Load {
  /**
   * Method responsible for reading all commands and return to processor
   * 
   * @param path path to file of test.
   * @return string array with all commands
   * @exception All If an exception occurs it returns an empty string
   */
  public String[] loadFile(String path) {
    ArrayList<String> content = new ArrayList<String>();
    String[] finalContent;
    int i = 0;
    try {
      FileReader arc = new FileReader(path);
      BufferedReader readArc = new BufferedReader(arc);
      String line = "";
      try {
        line = readArc.readLine();
        while (line != null) {
          content.add(line.replace(" ", "").replace("\r", ""));
          line = readArc.readLine();
        }
        arc.close();
      } catch (IOException eIO) {
        content.add(0, "Could not read file.\n" + eIO.getMessage());
      }
    } catch (FileNotFoundException eNFE) {
      content.add(0, "Could not open file.\n" + eNFE.getMessage());
    }
    if (content.get(0).contains("Could not")) {
      String[] Err = { "" };
      return Err;
    } else {
      finalContent = new String[content.size()];
      for (String string : content) {
        finalContent[i++] = string;
      }
      return finalContent;
    }
  }
}
