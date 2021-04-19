package src.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class load {
  public load() {

  }

  public String[] loadFile(String path) {
    String[] content = new String(50);
    int i = 0;
    try {
      FileReader arc = new FileReader(path);
      BufferedReader readArc = BufferedReader(arc);
      String line = "";
      try {
        line = readArc.readLine();
        while (line != null) {
          content[i] = line;
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
      return "";
    } else {
      return content;
    }
  }
}
