import javax.swing.*;
import java.awt.*;

public class CacheWizard extends JFrame{
    private static final int DEFAULT_WIDTH=600;
    public static final int DEFAULT_HEIGHT=300;
    private WizardFileManager wizard;
    public CacheWizard(){
        this.init();
        wizard=new WizardFileManager();
    }
    public void init(){
        this.setBounds(new Rectangle(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        this.setLocation(new Point(600,400));

        this.setVisible(true);
    }
    public static void main(String[] args) {
    CacheWizard wz=new CacheWizard();

    }

}
