import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CacheWizard extends JFrame{
    private static final int DEFAULT_WIDTH=400;
    public static final int DEFAULT_HEIGHT=200;
    private WizardFileManager wizard;

    private JButton button;
    private JLabel newMediaLabel;


    public CacheWizard(){
        this.init();
        wizard=new WizardFileManager();
    }
    public void init(){


        this.setBounds(new Rectangle(DEFAULT_WIDTH,DEFAULT_HEIGHT));
        this.setLocation(new Point(600,400));
        this.setVisible(true);



        this.button=new JButton("Find cache");
        button.setBounds(new Rectangle(100,30));
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            buttonKeyPressed(e);
            }
        });
        button.setLocation(10,50);


        this.newMediaLabel=new JLabel("Privet");
        newMediaLabel.setVisible(true);
        newMediaLabel.setBounds(new Rectangle(100,30));


        this.add(button);
        this.add(newMediaLabel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void buttonKeyPressed(ActionEvent event){
        int mediaSize=findMediaSize();
        if(mediaSize==0){
            newMediaLabel.setText("No new media files found");
        }else{
            newMediaLabel.setText(mediaSize+" new files found.");
        }
    }
    private int findMediaSize(){
        try {
            return wizard.findNewMedia().size();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
    CacheWizard wz=new CacheWizard();

    }

}
