package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import System.Vykonavanie;


public class Login {

	private JFrame frame;
	//private JTextField textField;
	//private JTextField textField_1;
	//private JTextField textField_2;
	Vykonavanie vykonavanie = new Vykonavanie();


	/**
	 * Create the application.
	 */
	

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		 
		final JTextField textField_1 = new JTextField(15);
     	final JTextField textField_2 = new JTextField(15);
     	final JTextField textField_3 = new JTextField(15);
     	final JButton btnPrihlsi = new JButton();
     	JButton btnPrihlsi1 = new JButton();
     	final JLabel label1 = new JLabel();
		final JLabel label2 = new JLabel();
		final JLabel label3 = new JLabel();
		final JLabel label4 = new JLabel("");
		final Timer timer = new Timer();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame("SystÈm taxisluûby");
		
		frame.setBounds(100, 100, 300, 160);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
		
		frame.getContentPane().add(btnPrihlsi);
		btnPrihlsi.setText("Prihl·siù");
		btnPrihlsi.setBounds(75, 25, 150, 25);
		btnPrihlsi.setPreferredSize(new Dimension(100, 40));
		
		btnPrihlsi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	frame.getContentPane().removeAll();
         		
         		frame.revalidate();
    			frame.repaint();
    			
    			label1.setText("Zadaj meno:");
    	        label1.setBounds(50, 20, 100, 20);
    	        frame.getContentPane().add(label1);
    	        
    	    	label2.setText("Zadaj heslo:");
    	    	label2.setBounds(50, 40, 100, 20);
    	    	frame.getContentPane().add(label2);
    			
    	    	frame.getContentPane().add(textField_1);
    	    	//textField_1.setText("Jan01");
    	    	textField_1.setText("Adam");
    	    	textField_1.setBounds(150, 20, 100, 20);
    			
    	    	frame.getContentPane().add(textField_2);
    	    	//textField_2.setText("sss");
    	    	textField_2.setText("aa");
    	    	textField_2.setBounds(150, 40, 100, 20);
    	    	
    			label4.setBounds(55, 65, 200, 14);
    			frame.getContentPane().add(label4);
    			
    	    	frame.getContentPane().add(btnPrihlsi);
    			btnPrihlsi.setText("Prihl·siù");
    			btnPrihlsi.setBounds(105, 85, 100, 30);
    			btnPrihlsi.setPreferredSize(new Dimension(100, 40));
    			
    			btnPrihlsi.addMouseListener(new MouseAdapter() {
    	            public void mousePressed(MouseEvent evt) {
    	                try {
    						if (!vykonavanie.prihlasenie(textField_1.getText(),textField_2.getText())){
	    						label4.setText("Nespr·vne meno alebo heslo!");
	    						label4.setHorizontalAlignment(SwingConstants.CENTER);
    						}
    						else{
	    						frame.setVisible(false); 
	    						frame.dispose(); 
    						}
    					} catch (SQLException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	            }
    	        });
            }
        });
		
		frame.getContentPane().add(btnPrihlsi1);
		btnPrihlsi1.setText("Registrovaù");
		btnPrihlsi1.setBounds(75, 70, 150, 25);
		btnPrihlsi1.setPreferredSize(new Dimension(100, 40));
		
		btnPrihlsi1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	frame.getContentPane().removeAll();
         		
         		frame.revalidate();
    			frame.repaint();
    			
    			label1.setText("Zadaj meno:");
    	        label1.setBounds(50, 20, 100, 20);
    	        frame.getContentPane().add(label1);
    	        
    	    	label2.setText("Zadaj heslo:");
    	    	label2.setBounds(50, 40, 100, 20);
    	    	frame.getContentPane().add(label2);
    	    	
    	    	label3.setText("Zopakuj heslo:");
    	    	label3.setBounds(50, 60, 100, 20);
    	    	frame.getContentPane().add(label3);
    			
    	    	frame.getContentPane().add(textField_1);
    	    	textField_1.setBounds(150, 20, 100, 20);
    			
    	    	frame.getContentPane().add(textField_2);
    	    	textField_2.setBounds(150, 40, 100, 20);
    	    	
    	    	frame.getContentPane().add(textField_3);
    	    	textField_3.setBounds(150, 60, 100, 20);
    	    	
    	    	frame.getContentPane().add(btnPrihlsi);
    			btnPrihlsi.setText("Registrovaù");
    			btnPrihlsi.setBounds(75, 85, 150, 30);
    			btnPrihlsi.setEnabled(false);
    			btnPrihlsi.setPreferredSize(new Dimension(100, 40));
    			
    			timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                    	if ((textField_2.getText().equals(textField_3.getText())) && (textField_2.getText().trim().isEmpty() == false))
                    		btnPrihlsi.setEnabled(true);
                    	else if((!textField_2.getText().equals(textField_3.getText())) && (textField_2.getText().trim().isEmpty() == false))
                    		btnPrihlsi.setEnabled(false);
                    	}
                    }, 0, 1300);
    			
    			btnPrihlsi.addMouseListener(new MouseAdapter() {
    	            public void mousePressed(MouseEvent evt) {  	            	
    	                try {
							vykonavanie.registrovanie(textField_1.getText(),textField_2.getText());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						frame.setVisible(false); 
						frame.dispose();
						initialize();
    	            }
    	        });
            }
        });
		
	
	}

}
/*btnPrihlsi.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) throws SQLException {
		first.prihlasenie(textField_1.getText(),textField_2.getText());
	}

@Override
public void actionPerformed(java.awt.event.ActionEvent e) {
	// TODO Auto-generated method stub
	
}
});*/