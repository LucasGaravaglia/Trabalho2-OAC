package src.log;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.io.FileWriter;  
import java.io.File;
import java.io.IOException;


public class Log {

    private static Log uniqueStance = null;
    public final static String fileName = "../logs/logs.txt";


    private Log(){
        RemoveFile();
        createFile();
    }

    public static void doLog(String value){
        if(uniqueStance == null){
            uniqueStance = new Log();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        uniqueStance.appendToAFile("[" + dtf.format(now) + "]:\t[" + value + "]\n");
    }

    
    private void RemoveFile() {
        try {
          File myObj = new File(fileName);
          myObj.getParentFile().mkdirs();
          myObj.delete();
        } catch (Exception e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    } 

    private void createFile() {
        try {
          File myObj = new File(fileName);
          myObj.getParentFile().mkdirs();
          myObj.createNewFile();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }   


    private void appendToAFile(String value){
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.append(value);
            myWriter.close();
          } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
