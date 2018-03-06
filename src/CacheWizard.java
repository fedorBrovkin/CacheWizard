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
    public static final int DEFAULT_HEIGHT=150;
    private WizardFileManager wizard;

    private JButton button;
    private JButton button2;
    private JLabel newMediaLabel;
    private JPanel buttonsPanel;


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
        //button.setLocation(10,50);

        this.button2=new JButton("Copy cache");
        button2.setEnabled(false);
        button2.setBounds(new Rectangle(100,30));
        button2.setVisible(true);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2KeyPressed(e);
            }
        });


        this.newMediaLabel=new JLabel("Privet");
        newMediaLabel.setVisible(true);
        newMediaLabel.setBounds(new Rectangle(100,30));


        JPanel buttonsPanel = new JPanel(new FlowLayout());
        this.add(newMediaLabel, BorderLayout.CENTER);
        newMediaLabel.setVerticalAlignment(SwingConstants.CENTER);
        newMediaLabel.setHorizontalAlignment(0);
        buttonsPanel.add(button,BorderLayout.NORTH);
        buttonsPanel.add(button2,BorderLayout.SOUTH);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        newMediaLabel.setFont(new Font(newMediaLabel.getFont().getName(), Font.PLAIN, 28));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void buttonKeyPressed(ActionEvent event){
        int mediaSize=findMediaSize();
        if(mediaSize==0){
            newMediaLabel.setText("No new media files found");
        }else{
            newMediaLabel.setText(mediaSize+" new files found.");
            button2.setEnabled(true);
        }
    }

    private void button2KeyPressed(ActionEvent event){
        try {
            Thread textThread=new Thread(new Runnable(){
              @Override
              public void run(){
               newMediaLabel.setText("we are going to \n"+wizard.getCachePathName());
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  newMediaLabel.setText("making some cool things...");
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  newMediaLabel.setText("prepare files...");
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  newMediaLabel.setText("going to "+wizard.getTargetPathName());
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  newMediaLabel.setText("doing all this copy things...");
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

              }
            });
            textThread.start();
            if(wizard.copyMedia(wizard.findNewMedia())) {
                try {
                    textThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newMediaLabel.setText("Done!");
                button2.setEnabled(false);
            }else{
                try {
                    textThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newMediaLabel.setText("Copy Error!");
            }
        }catch (IOException e){
            newMediaLabel.setText(e.getMessage());
            e.printStackTrace();
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
