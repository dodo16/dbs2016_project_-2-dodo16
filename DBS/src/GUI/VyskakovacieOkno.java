package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JTextField;

import System.Vykonavanie;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSet;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class VyskakovacieOkno {

	private JFrame frame = new JFrame();
	static java.sql.ResultSet rs ;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextPane textPane;
	private JTextPane textPane_1;
	private JTextPane textPane_2;
	private JTextPane textPane_3;
	private JTextPane textPane_4;
	private JTextPane textPane_5;
	private JTextPane textPane_6;
	private JTextPane textPane_7;
	private JTextPane textPane_8;
	private JTextPane textPane_9;
	private JLabel lblZopakujNovHeslo;
	private JLabel lblZadajNovHeslo;
	private JLabel lblZadajStarHeslo;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private Timer timer ;
	String stav;
	
	Vykonavanie vykonavanie = new Vykonavanie();
	private JTable table;

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	void zmenaHes() {
		
		frame.setVisible(true);
		frame.setTitle("Zmena hesla");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 350, 260);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(141, 29, 156, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 60, 156, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 91, 156, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnPotvrdi = new JButton("Potvrdiù");
		btnPotvrdi.setBounds(114, 152, 112, 46);
		frame.getContentPane().add(btnPotvrdi);
		
		lblZopakujNovHeslo = new JLabel("Zopakuj novÈ heslo");
		lblZopakujNovHeslo.setBounds(20, 94, 121, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("Zadaj novÈ heslo");
		lblZadajNovHeslo.setBounds(20, 63, 99, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		lblZadajStarHeslo = new JLabel("Zadaj starÈ heslo");
		lblZadajStarHeslo.setBounds(20, 32, 99, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		label_1 = new JLabel("");
		label_1.setBounds(32, 127, 265, 14);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(label_1);			
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                try {
                	if(textField_1.getText().equals(textField_2.getText())){
						if (vykonavanie.zmenaHesla(textField.getText(),textField_1.getText(),textField_2.getText()) == true){
							frame.setVisible(false); 
							frame.dispose(); 
						}
						else
							label_1.setText("Nespr·vne zadanÈ starÈ heslo!");
                	}
                	else
                		label_1.setText("NovÈ hesl· sa nezhoduj˙!");
                	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
	
		
	}
	
	void pridelObjednavky() throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 850, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("PridelenÈ objedn·vky");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		table = new JTable(new DefaultTableModel(new Object[]{"»Ìslo obj", "N·stup", "V˝stup", "»as vyzdvihnutia", "D·tum", "Z·kaznÌk", "Login", "Stav"}, 0));
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		rs = vykonavanie.zobrPridObjed("vöetky");
		while(rs.next()){
			if (rs.getString(8).equals("0"))
				stav = "nevybaven·";
			else
				stav = "vybaven·";
			model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getString(6),rs.getString(7),stav});
		}
		table.setBounds(18,30, 800, 250);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 30, 800, 250);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table.setVisible(true);
		scrollPane.setVisible(true);
		
		lblZadajStarHeslo = new JLabel("Zobraziù podæa stavu objedn·vky:");
		lblZadajStarHeslo.setBounds(25, 290, 200, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		lblZopakujNovHeslo = new JLabel("OznaËiù objedn·vku ako vybaven˙:");
		lblZopakujNovHeslo.setBounds(599, 290, 220, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("Objedn·vka bola oznaËen· ako vybaven·!");
		lblZadajNovHeslo.setVisible(false);
		lblZadajNovHeslo.setBounds(510, 340, 240, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(25, 310, 156, 20);
		comboBox.addItem("vöetky");
		comboBox.addItem("vybaven·");
		comboBox.addItem("nevybaven·");
		frame.getContentPane().add(comboBox);
		
		JButton btnPotvrdi = new JButton("Zobraziù");
		btnPotvrdi.setBounds(185, 310, 112, 25);
		frame.getContentPane().add(btnPotvrdi);
		
		final JButton btnPotvrdi1 = new JButton("OznaËiù");
		btnPotvrdi1.setBounds(631, 310, 150, 25);
		btnPotvrdi1.setEnabled(false);
		frame.getContentPane().add(btnPotvrdi1);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	int selectedRowIndex = table.getSelectedRow();
            	if (selectedRowIndex != -1)
            		btnPotvrdi1.setEnabled(true);
            	else if(selectedRowIndex == -1)
            		btnPotvrdi1.setEnabled(false);
            	}
            }, 0, 1300);
		
		btnPotvrdi1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	int selectedRowIndex = table.getSelectedRow();
            	Object selectedObject = (Object) table.getModel().getValueAt(selectedRowIndex, 0);
            	try {
					vykonavanie.upravObj(selectedObject.toString());
					lblZadajNovHeslo.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
            		model.setRowCount(0);
					rs = vykonavanie.zobrPridObjed(comboBox.getSelectedItem().toString());
					while(rs.next()){
						if (rs.getString(8).equals("0"))
							stav = "nevybaven·";
						else
							stav = "vybaven·";
							model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
									rs.getString(5),rs.getString(6),rs.getString(7),stav});
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
	
	void pozriObjednavky(String typ) throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 850, 390);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Objedn·vky");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		final JTable table = new JTable(new DefaultTableModel(new Object[]{"»Ìslo obj", "N·stup", "V˝stup", "»as vyzdvihnutia", "D·tum", "Z·kaznÌk", "Login", "Stav"}, 0));
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		rs = vykonavanie.zobrObjed("vöetky");
		while(rs.next()){
			if (rs.getString(8).equals("0"))
				stav = "nevybaven·";
			else
				stav = "vybaven·";
			model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getString(6),rs.getString(7),stav});
		}
		table.setBounds(18,30, 800, 250);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 30, 800, 250);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		table.setVisible(true);
		scrollPane.setVisible(true);
		
		lblZadajStarHeslo = new JLabel("Zobraziù podæa stavu objedn·vky:");
		lblZadajStarHeslo.setBounds(25, 290, 200, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		lblZadajNovHeslo = new JLabel("Objedn·vka bola ˙speöne pridelen·!");
		lblZadajNovHeslo.setVisible(false);
		lblZadajNovHeslo.setBounds(539, 335, 220, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(25, 310, 156, 20);
		comboBox.addItem("vöetky");
		comboBox.addItem("vybaven·");
		comboBox.addItem("nevybaven·");
		frame.getContentPane().add(comboBox);
		
		JButton btnPotvrdi = new JButton("Zobraziù");
		btnPotvrdi.setBounds(185, 310, 112, 25);
		frame.getContentPane().add(btnPotvrdi);
		
		if(typ.equals("dispecer")){
			rs = vykonavanie.aktTaxik();
			final JComboBox<String> comboBox1 = new JComboBox<String>();
			comboBox1.setBounds(539, 310, 156, 20);
			while(rs.next())
				comboBox1.addItem(rs.getString(1));
			frame.getContentPane().add(comboBox1);
			
			lblZopakujNovHeslo = new JLabel("Prideliù objedn·vku taxik·rovi:");
			lblZopakujNovHeslo.setBounds(539, 290, 200, 14);
			frame.getContentPane().add(lblZopakujNovHeslo);
			
			final JButton btnPotvrdi1 = new JButton("Prideliù");
			btnPotvrdi1.setBounds(699, 310, 112, 25);
			frame.getContentPane().add(btnPotvrdi1);
			
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	            	int selectedRowIndex = table.getSelectedRow();
	            	if (selectedRowIndex != -1)
	            		btnPotvrdi1.setEnabled(true);
	            	else if(selectedRowIndex == -1)
	            		btnPotvrdi1.setEnabled(false);
	            	}
	            }, 0, 1300);
			
			btnPotvrdi1.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	            	int selectedRowIndex = table.getSelectedRow();
	            	Object selectedObject = (Object) table.getModel().getValueAt(selectedRowIndex, 0);
	            	try {
						vykonavanie.pridelObjedn(selectedObject.toString(), comboBox1.getSelectedItem().toString());
						lblZadajNovHeslo.setVisible(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
		}
		
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
            		model.setRowCount(0);
					rs = vykonavanie.zobrObjed(comboBox.getSelectedItem().toString());
					while(rs.next()){
						if (rs.getString(8).equals("0"))
							stav = "nevybaven·";
						else
							stav = "vybaven·";
							model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
									rs.getString(5),rs.getString(6),rs.getString(7),stav});
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
	
	void pozriPouzivatelov(String typ) throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 850, 390);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("PouûÌvatelia");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		final JTable table = new JTable(new DefaultTableModel(new Object[]{"Meno", "Priezvisko", "Telefon", "Email", "Login", "»Ìslo ˙Ëtu", "Typ"}, 0));
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		rs = vykonavanie.zobrPouzi("vöetci");
		while(rs.next()){
			model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getString(6),rs.getString(7)});
		}
		table.setBounds(18,30, 800, 250);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 30, 800, 250);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		lblZadajStarHeslo = new JLabel("Zobraziù podæa typu pouûÌvateæa:");
		lblZadajStarHeslo.setBounds(25, 290, 200, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(25, 310, 156, 20);
		comboBox.addItem("vöetci");
		comboBox.addItem("dispecer");
		comboBox.addItem("taxikar");
		comboBox.addItem("zakaznik");
		frame.getContentPane().add(comboBox);
		
		JButton btnPotvrdi = new JButton("Zobraziù");
		btnPotvrdi.setBounds(185, 310, 112, 25);
		frame.getContentPane().add(btnPotvrdi);
		
		lblZadajNovHeslo = new JLabel("PouûÌvateæ bol vymazan˝!");
		lblZadajNovHeslo.setVisible(false);
		lblZadajNovHeslo.setBounds(350, 290, 220, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		table.setVisible(true);
		scrollPane.setVisible(true);
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
            		model.setRowCount(0);
					rs = vykonavanie.zobrPouzi(comboBox.getSelectedItem().toString());
					while(rs.next()){
							model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
									rs.getString(5),rs.getString(6),rs.getString(7)});
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		if (typ.equals("dispecer")){
			lblZopakujNovHeslo = new JLabel("Vymazaù oznaËanÈho taxik·ra:");
			lblZopakujNovHeslo.setBounds(599, 290, 200, 14);
			frame.getContentPane().add(lblZopakujNovHeslo);
			
			final JButton btnPotvrdi1 = new JButton("Vymazaù");
			btnPotvrdi1.setBounds(699, 310, 112, 25);
			frame.getContentPane().add(btnPotvrdi1);
			
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	            	int selectedRowIndex = table.getSelectedRow();
	            	if (selectedRowIndex != -1 && !table.getModel().getValueAt(selectedRowIndex, 6).toString().equals("dispecer") 
	            			&& !table.getModel().getValueAt(selectedRowIndex, 6).toString().equals("zakaznik")){
	            		btnPotvrdi1.setEnabled(true);
	            	}
	            	else 
	            		btnPotvrdi1.setEnabled(false);
	            	}
	            }, 0, 1000);
			
			btnPotvrdi1.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	            	int selectedRowIndex = table.getSelectedRow();
	            	Object selectedObject = (Object) table.getModel().getValueAt(selectedRowIndex, 4);
	            	vykonavanie.vymazatPou(selectedObject.toString());
					lblZadajNovHeslo.setVisible(true);
	            }
	        });
		}
	}
	
	void pridajZamestnanca(){
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 350, 260);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Vytvorenie objedn·vky");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 40, 156, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 70, 156, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(141, 100, 156, 20);
		comboBox.addItem("dispecer");
		comboBox.addItem("taxikar");
		frame.getContentPane().add(comboBox);
		
		lblZopakujNovHeslo = new JLabel("Login pouûÌvateæa");
		lblZopakujNovHeslo.setBounds(25, 40, 121, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("Heslo pouûÌvateæa");
		lblZadajNovHeslo.setBounds(25, 70, 121, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		lblZadajStarHeslo = new JLabel("Typ pouûÌvateæa");
		lblZadajStarHeslo.setBounds(25, 100, 121, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		JButton btnPotvrdi = new JButton("Pridaù");
		btnPotvrdi.setBounds(114, 152, 112, 46);
		frame.getContentPane().add(btnPotvrdi);
		
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
					vykonavanie.pridatPouzivatela(textField_1.getText(),textField_2.getText(),comboBox.getSelectedItem().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	frame.setVisible(false); 
				frame.dispose();
            }
        });
		
	}
	
	void pridajVoz() throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 370, 290);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Pridaù vozidlo");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 40, 156, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 70, 156, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(141, 100, 156, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		
		textField_4 = new JTextField();
		textField_4.setBounds(141, 130, 156, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		
		rs = vykonavanie.aktTaxik();
		final JComboBox<String> comboBox1 = new JComboBox<String>();
		comboBox1.setBounds(141, 160, 156, 20);
		while(rs.next())
			comboBox1.addItem(rs.getString(1));
		frame.getContentPane().add(comboBox1);
		
		lblZopakujNovHeslo = new JLabel("Typ vozidla");
		lblZopakujNovHeslo.setBounds(25, 40, 121, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("ZnaËka v˝robcu");
		lblZadajNovHeslo.setBounds(25, 70, 121, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		lblZadajStarHeslo = new JLabel("äPZ");
		lblZadajStarHeslo.setBounds(25, 100, 121, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		label_1 = new JLabel("PoËet pasaûierov");
		label_1.setBounds(25, 130, 121, 14);
		frame.getContentPane().add(label_1);
		
		label_2 = new JLabel("äofÈr");
		label_2.setBounds(25, 160, 121, 14);
		frame.getContentPane().add(label_2);
		
		JButton btnPotvrdi = new JButton("Pridaù");
		btnPotvrdi.setBounds(114, 202, 112, 25);
		frame.getContentPane().add(btnPotvrdi);
		
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
					vykonavanie.pridatVozidlo(textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText(),comboBox1.getSelectedItem().toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	frame.setVisible(false); 
				frame.dispose();
            }
        });
		
	}
	
	void pozriVozidla(String typ) throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 1150, 390);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Zobrazenie vozidiel");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
	
		JTable table = new JTable(new DefaultTableModel(new Object[]{"Typ auta", "Znacka", "äPZ", "PoËet pasaûiÈrov", "Taxik·r","Poistenie v rokoch","Cena za mesiac",
						"Nazov servisu", "Email servisu", "Telefon servisu"}, 0));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(150);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		rs = vykonavanie.zobrVozidla();
		while(rs.next()){
			model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
					rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),
					rs.getString(10)});
		}
		table.setBounds(18,30, 1100, 250);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 30, 1100, 250);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		table.setVisible(true);
		scrollPane.setVisible(true);
		
	}
	
	void vytvorObjednavku() throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 350, 260);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Vytvorenie objedn·vky");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		label_4 = new JLabel(vykonavanie.vratCisloObjednavky());
		label_4.setBounds(141, 30, 156, 14);
		frame.getContentPane().add(label_4);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 50, 156, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(141, 70, 156, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(141, 90, 156, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(141, 110, 156, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		
		lblZopakujNovHeslo = new JLabel("»Ìslo objedn·vky");
		lblZopakujNovHeslo.setBounds(20, 30, 121, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("Adresa n·stupu");
		lblZadajNovHeslo.setBounds(20, 50, 99, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		lblZadajStarHeslo = new JLabel("Adresa v˝stupu");
		lblZadajStarHeslo.setBounds(20, 70, 99, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		label_2 = new JLabel("»as vyzdvihnutia");
		label_2.setBounds(20, 90, 99, 14);
		frame.getContentPane().add(label_2);
		
		label_3 = new JLabel("D·tum");
		label_3.setBounds(20, 110, 99, 14);
		frame.getContentPane().add(label_3);
		
		JButton btnPotvrdi = new JButton("Objednaù");
		btnPotvrdi.setBounds(114, 152, 112, 46);
		frame.getContentPane().add(btnPotvrdi);
		
		
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	try {
					vykonavanie.vytvorObjednavku(label_4.getText(),textField_1.getText(),textField_2.getText(),
							textField_3.getText(),textField_4.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	frame.setVisible(false); 
				frame.dispose();
            }
        });
	}
	
	void aktivita(){
		List<String> vysledok = new LinkedList<String>();
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 490, 290);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("⁄pravy v systÈme");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		final JTable table = new JTable(new DefaultTableModel(new Object[]{"D·tum a Ëas oper·cie", "Vykonan· oper·cia"}, 0));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		vysledok = vykonavanie.vratDatabazu();
		
		for (int i=0;i<vysledok.size();i+=2){
			if(vysledok.get(i+1).equals("Zmena hesla") || vysledok.get(i+1).equals("Zmena profilu") || vysledok.get(i+1).equals("Vytvorenie objedn·vky"))
				model.addRow(new Object[]{vysledok.get(i),vysledok.get(i+1)});
			else
				model.addRow(new Object[]{vysledok.get(i+1),vysledok.get(i)});
		}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		table.setBounds(20, 40, 430, 160);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 40, 430, 160);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		final JButton btnPotvrdi1 = new JButton("Vratiù sp‰ù");
		btnPotvrdi1.setBounds(300, 200, 150, 25);
		btnPotvrdi1.setEnabled(false);
		frame.getContentPane().add(btnPotvrdi1);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	int selectedRowIndex = table.getSelectedRow();
            	if (selectedRowIndex != -1)
            		btnPotvrdi1.setEnabled(true);
            	else if(selectedRowIndex == -1)
            		btnPotvrdi1.setEnabled(false);
            	}
            }, 0, 1300);
		
		btnPotvrdi1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	int selectedRowIndex = table.getSelectedRow();
            	Object selectedObject = (Object) table.getModel().getValueAt(selectedRowIndex, 1);

            	if(selectedObject.toString().equals("Zmena hesla")){
					try {
						vykonavanie.zmeneneHesla();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	else if(selectedObject.toString().equals("Vytvorenie objedn·vky")){
            		try {
						vykonavanie.zmazObj();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	else if(selectedObject.toString().equals("Zmena profilu")){
            		 vykonavanie.zmenitProfil();
            	}
            	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
		
	}
	
	void profil() throws SQLException{
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 350, 310);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Profil");
		
		frame.getContentPane().removeAll();
		frame.revalidate();
		frame.repaint();
		
		textPane = new JTextPane();
		textPane.setBounds(141, 10, 156, 20);
		frame.getContentPane().add(textPane);
		
		textPane_1 = new JTextPane();
		textPane_1.setBounds(141, 30, 156, 20);
		frame.getContentPane().add(textPane_1);
		
		textPane_2 = new JTextPane();
		textPane_2.setBounds(141, 50, 156, 20);
		frame.getContentPane().add(textPane_2);
		
		textPane_3 = new JTextPane();
		textPane_3.setBounds(141, 70, 156, 20);
		frame.getContentPane().add(textPane_3);
		
		textPane_4 = new JTextPane();
		textPane_4.setBounds(141, 90, 156,20);
		textPane_4.setEnabled(false);
		frame.getContentPane().add(textPane_4);
		
		textPane_5 = new JTextPane();
		textPane_5.setBounds(141, 110, 156, 20);
		frame.getContentPane().add(textPane_5);
		
		textPane_6 = new JTextPane();
		textPane_6.setBounds(141, 130, 156, 20);
		frame.getContentPane().add(textPane_6);
		
		textPane_7 = new JTextPane();
		textPane_7.setBounds(141, 150, 156, 20);
		frame.getContentPane().add(textPane_7);
		
		textPane_8 = new JTextPane();
		textPane_8.setBounds(141, 170, 156, 20);
		frame.getContentPane().add(textPane_8);
		
		textPane_9 = new JTextPane();
		textPane_9.setBounds(141, 190, 156, 20);
		frame.getContentPane().add(textPane_9);
			
		lblZopakujNovHeslo = new JLabel("Meno");
		lblZopakujNovHeslo.setBounds(40, 10, 121, 14);
		frame.getContentPane().add(lblZopakujNovHeslo);
		
		lblZadajNovHeslo = new JLabel("Priezvisko");
		lblZadajNovHeslo.setBounds(40, 30, 99, 14);
		frame.getContentPane().add(lblZadajNovHeslo);
		
		lblZadajStarHeslo = new JLabel("TelefÛn");
		lblZadajStarHeslo.setBounds(40,50, 99, 14);
		frame.getContentPane().add(lblZadajStarHeslo);
		
		label_2 = new JLabel("Email");
		label_2.setBounds(40, 70, 99, 14);
		frame.getContentPane().add(label_2);
		
		label_3 = new JLabel("Login");
		label_3.setBounds(40, 90, 99, 14);
		frame.getContentPane().add(label_3);
		
		label_4 = new JLabel("»islo ˙Ëtu");
		label_4.setBounds(40, 110, 99, 14);
		frame.getContentPane().add(label_4);
		
		label_5 = new JLabel("Ulica");
		label_5.setBounds(40, 130, 99, 14);
		frame.getContentPane().add(label_5);
		
		label_6 = new JLabel("Mesto");
		label_6.setBounds(40, 150, 99, 14);
		frame.getContentPane().add(label_6);
		
		label_7 = new JLabel("PS»");
		label_7.setBounds(40, 170, 99, 14);
		frame.getContentPane().add(label_7);
		
		label_8 = new JLabel("ät·t");
		label_8.setBounds(40, 190, 99, 14);
		frame.getContentPane().add(label_8);
		
		JButton btnPotvrdi = new JButton("Uloûiù");
		btnPotvrdi.setBounds(114, 232, 112, 26);
		frame.getContentPane().add(btnPotvrdi);
		
		rs = vykonavanie.pozrietProfil();
		if(rs.next()){
			textPane.setText(rs.getString("meno"));
			textPane_1.setText(rs.getString("priezvisko"));
			textPane_2.setText(rs.getString("telefon"));
			textPane_3.setText(rs.getString("email"));
			textPane_4.setText(rs.getString("login"));
			textPane_5.setText(rs.getString("cisloUctu"));
		}
		ResultSet sr = (ResultSet) vykonavanie.pozrietAdresu();
		if(sr.next()){
			textPane_6.setText(sr.getString("ulica"));
			textPane_7.setText(sr.getString("mesto"));
			textPane_8.setText(sr.getString("PSC"));
			textPane_9.setText(sr.getString("stat"));
		}
		btnPotvrdi.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	vykonavanie.upravitProfil(textPane.getText(), textPane_1.getText(), textPane_2.getText(), textPane_3.getText(),textPane_4.getText(), textPane_5.getText(),
						textPane_6.getText(),textPane_7.getText(),textPane_8.getText(),textPane_9.getText());
                frame.setVisible(false); 
				frame.dispose();
            }
        });
		
	}
}
