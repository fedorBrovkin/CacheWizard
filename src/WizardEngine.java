/**
 * in progress...
 * It will analyze and group files, rename them and set correct titles. But later)
 */

import java.nio.file.Files;
import java.nio.file.Path;


public class WizardEngine implements Runnable{
    public static boolean isUnique(Path file) {
        boolean flag = false;
        if (Files.isRegularFile(file)) {
            String title = "";
            String artist = "";
            String data = "";
        }
        return flag;
    }
    public static String[] getMeta(Path file){
        return null;
    }
    @Override
    public void run(){
//
}

}
