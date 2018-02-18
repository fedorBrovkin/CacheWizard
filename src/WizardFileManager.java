import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WizardFileManager {
    private Path targetDirectory;
    static private Path cacheDirectory;
    private int fileCounter;
    private boolean flag=false;

    static{
        if(System.getProperty("os.name").contains("Mac")){
            cacheDirectory= Paths.get(System.getProperty("user.home"), "Caches", "Google", "Chrome", "Default", "Media Cache");
        }
        if(System.getProperty("os.name").contains("XP")){
            cacheDirectory=Paths.get(System.getProperty("user.home"),"Local Settings","Application Data","Google","Chrome","User Data","Default","Cache");
        }
        else{
            cacheDirectory=Paths.get(System.getProperty("user.home"), "AppData","Local","Google", "Chrome", "User Data", "Default", "Media Cache");
        }
    }

    /**
     * Construct
     */
    public WizardFileManager(){
        this.setDefaultDirectory();
        this.fileCounter=0;
    }
    public WizardFileManager(Path target){
        this.setTargetDirectory(target);
        this.fileCounter=0;
    }
    //
    //Private inner methods
    //
    private void setTargetDirectory(Path target){
        if( target!=null && Files.isDirectory(target)){
            if(Files.notExists(target)){
                try{
                    this.targetDirectory=target;
                    Files.createDirectories(targetDirectory);
                    System.out.println("The Folder was created. Path: "+ this.targetDirectory.toAbsolutePath().toString());
                }catch(IOException e){
                    System.out.println("Got error while folder creating! Can`t create target folder.");
                    System.out.println(e.getMessage());
                    this.setDefaultDirectory();
                    System.out.println("Set to default.");
                }
            }else{
                if(checkPermissions(target)){
                    this.targetDirectory=target;
                    System.out.println("Target Path set to: "+this.targetDirectory.toAbsolutePath().toString());
                }
            }

        }else{
            System.out.println("Illegal parameter! Set to default.");
            this.setDefaultDirectory();
        }
    }
    private void setDefaultDirectory() {
        this.targetDirectory=Paths.get(System.getProperty("user.home"),"Documents","CacheWizard");
        if(Files.notExists(targetDirectory)){
            try{
                Files.createDirectories(targetDirectory);
                System.out.println("The Folder was created. Path: "+ this.targetDirectory.toAbsolutePath().toString());
            }catch(IOException e){
                System.out.println("Got error while folder creating! Can`t create target folder.");
                System.out.println(e.getMessage());
            }
        }
    }
    private boolean checkPermissions(Path target){
        return (Files.exists(target)^Files.notExists(target));
    }

    /**
     * This method helps us to find new files in the cache
     * If the is no it will return the empty List, so we can make checks with .isEmpty() method
     * to detect "no-new-files" situations.
     * @return
     * @throws IOException
     */
    public  List<Path> findNewMedia() throws IOException{
        String patternString="f_[0-9]{4}";
        Pattern p=Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
        List<Path> result=new ArrayList<>();
        /**
         * return void
         */
        Files.list(cacheDirectory).forEach((Path s)->{
                String sourceName = s.getFileName().toString();
                /**
                 * Looking for matches from the beginning of the source(String in our case).
                 * We can use simple RegularExpression to analyse only the prefix of the String made from fileName
                 */
                if (p.matcher(sourceName).lookingAt()) {
                            flag = false;
                    try{
                        /**
                         * Lambda for rangeBasedFor loop in Stream<Path> stream.I used list,
                         * becouse we have only one target folder and we dont need to search in inner folders.
                         */
                    Files.list(targetDirectory).forEach((Path t)-> {
                        String targetName = t.getFileName().toString();
                        if (targetName.contains(s.getFileName().toString())) {
                            flag=true;
                        }
                    });}
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    if (!flag) {
                        result.add(s);
                    }
                }
            });

        return result;
    }
    public boolean copyMedia(List<Path> list)throws IOException{
        if(list==null||list.isEmpty())
            return false;
        for(Path p:list){
            Files.copy(p,targetDirectory.resolve(p.getFileName()+".mp3"));
        }
        return true;
    }
}
