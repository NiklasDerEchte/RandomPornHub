import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Gui extends JFrame {

    private JButton btnGenerate  = new JButton("generate");

    public Gui(){
        setTitle("Random PornHub");
        setSize(400,200);
        setLocation(new Point(300,200));
        setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }

    private void initComponent() {

        this.btnGenerate.setBounds(150,50,100,50);
        this.add(btnGenerate);
    }

    private void initEvent() {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });

        this.btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main();
            }
        });
    }

}

