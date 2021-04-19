package src.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class responsible for read and treat file.
 * 
 * @author Lucas Garavaglia
 */
public class load {
  /**
   * Method responsible for reading all commands and return to processor
   * 
   * @param path path to file of test.
   * @return string array with all commands
   * @exception All If an exception occurs it returns an empty string
   */
  public String[] loadFile(String path) {
    String[] content = new String[50];
    int i = 0;
    try {
      FileReader arc = new FileReader(path);
      BufferedReader readArc = new BufferedReader(arc);
      String line = "";
      try {
        line = readArc.readLine();
        while (line != null) {
          content[i] = line.replace(" ", "").replace("\r", "");
          i++;
          line = readArc.readLine();
        }
        arc.close();
      } catch (IOException eIO) {
        content[0] = "Could not read file.\n" + eIO.getMessage();
      }
    } catch (FileNotFoundException eNFE) {
      content[0] = "Could not open file.\n" + eNFE.getMessage();
    }
    if (content[0].contains("Could not")) {
      String[] Err = { "" };
      return Err;
    } else {
      return content;
    }
  }
}
