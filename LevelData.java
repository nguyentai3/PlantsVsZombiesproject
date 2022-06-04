
import java.io.*;
import java.util.logging.Logger;

public class LevelData {
    static String Lvl = "1";
   static String [][] Level = {{"NormalZombie","ConeHeadZombie","NewspaperZombie","BucketZombie"},{"NormalZombie","ConeHeadZombie","NewspaperZombie","BucketZombie"}};
   static int [][][] LevelValue = {{{0,25},{26,50},{51,75},{76,99}},{{0,25},{26,50},{51,75},{76,99}}} ;
   public LevelData() {
       try {
           File f = new File("Level.vbhv");
           
           if(!f.exists()) {
               BufferedWriter bwr = new BufferedWriter(new FileWriter(f));
               bwr.write("1");
               bwr.close();
               Lvl = "1";
           } else {
               BufferedReader br = new BufferedReader(new FileReader(f));
               Lvl = br.readLine();
               br.close();
           }
       } catch (Exception ex) {
           
           
       }
   }
   public static void write(String lvl) {
       File f = new File("Level.vbhv");
        try {
            BufferedWriter bwr = new BufferedWriter(new FileWriter(f));
            bwr.write(lvl);
            bwr.close();
            Lvl = lvl;
        } catch (IOException ex) {
            Logger.getLogger(LevelData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
               
   }
}
