import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        WizardFileManager wfm=new WizardFileManager();
        try {
           List<Path> list=wfm.findNewMedia();
           if(!list.isEmpty())
            wfm.copyMedia(list);
        }
        catch(IOException e){
            e.getMessage();
        }
    }
}
