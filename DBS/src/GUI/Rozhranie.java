package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSet;

import System.Vykonavanie;

import javax.swing.JLabel;

public class Rozhranie {

	VyskakovacieOkno okno = new VyskakovacieOkno();
	Vykonavanie vykonavanie = new Vykonavanie();
	private JFrame frame;
	ResultSet rs;
	

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @wbp.parser.entryPoint
	 */
	public void initialize(final String type) throws SQLException {
		frame = new JFrame("SystÈm taxisluûby");
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(100, 100, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	vykonavanie.ukonciSpojenie();
		    }
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 550, 27);
		frame.getContentPane().add(menuBar);
		
		JMenu mnSbor = new JMenu("S˙bor");
		menuBar.add(mnSbor);
		
		JMenuItem mntmPozrieProfil = new JMenuItem("Pozrieù profil");
		mnSbor.add(mntmPozrieProfil);
		
		JMenuItem mntmZmeniHeslo = new JMenuItem("Zmeniù heslo");
		mnSbor.add(mntmZmeniHeslo);
		
		JMenuItem mntmAktivita = new JMenuItem("HistÛria ˙konov");
		mnSbor.add(mntmAktivita);
		
		JMenuItem mntmKoniec = new JMenuItem("Koniec");
		mnSbor.add(mntmKoniec);
		
		JLabel lblDnesJe = new JLabel("Dnes je:");
		lblDnesJe.setBounds(25, 38, 46, 14);
		frame.getContentPane().add(lblDnesJe);
		
		JLabel lblStePrihlsenAko = new JLabel("PouûÌvateæ:");
		lblStePrihlsenAko.setBounds(25, 55, 68, 14);
		frame.getContentPane().add(lblStePrihlsenAko);
		
		JLabel label = new JLabel(vykonavanie.aktualnyDen());
		label.setBounds(81, 38, 73, 14);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel(vykonavanie.vratLogin());
		label_1.setBounds(98, 55, 63, 14);
		frame.getContentPane().add(label_1);
		
		mntmPozrieProfil.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                try {
					okno.profil();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		mntmAktivita.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	                okno.aktivita();
	            }
        });
		
		
		mntmZmeniHeslo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                okno.zmenaHes();
            }
        });
		
		mntmKoniec.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	vykonavanie.ukonciSpojenie();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

		if (type.equals("dispecer") || type.equals("taxikar")) {
			
			JTable table = new JTable(new DefaultTableModel(new Object[]{"Typ auta", "ZnaËka", "äPZ", "Meno", "Priezvisko","TelefÛn","Login"}, 0));
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			table.getColumnModel().getColumn(0).setPreferredWidth(75);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setPreferredWidth(90);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(135);
			table.getColumnModel().getColumn(6).setPreferredWidth(85);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			
			rs = (ResultSet) vykonavanie.zobrazTaxik();
			while(rs.next()){
				model.addRow(new Object[]{rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(8),
						rs.getString(9),rs.getString(10),rs.getString(12)});
			}
			table.setBounds(25, 140, 500, 160);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(25, 140, 500, 160);
			frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
			
			JMenu mnObjedvky = new JMenu("Objedn·vky");
			menuBar.add(mnObjedvky);
			
			JMenu vozidla = new JMenu("Vozidl·");
			menuBar.add(vozidla);
			
			JMenuItem stavVozidiel = new JMenuItem("Zobraziù vozidl·");
			vozidla.add(stavVozidiel);
			
			JMenuItem mntmPozrieObjednvky = new JMenuItem("Pozrieù objedn·vky");
			mnObjedvky.add(mntmPozrieObjednvky);
			
			
			mntmPozrieObjednvky.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	                try {
						okno.pozriObjednavky(type);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
			
			stavVozidiel.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	                try {
						okno.pozriVozidla(type);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
			
			if(type.equals("taxikar")){
				
				JMenuItem mntmObjednvky = new JMenuItem("PridelenÈ objedn·vky");
				mnObjedvky.add(mntmObjednvky);
			
				mntmObjednvky.addMouseListener(new MouseAdapter() {
		            public void mousePressed(MouseEvent evt) {
		                try {
							okno.pridelObjednavky();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
			
			}
			
			if(type.equals("dispecer")){
				JLabel label_2 = new JLabel("Aktu·lny poËet objedn·vok Ëakaj˙cich na vybavenie:");
				label_2.setBounds(25, 105, 300, 14);
				frame.getContentPane().add(label_2);
				
				JLabel label_3 = new JLabel(Integer.toString(vykonavanie.pocNevyb()));
				label_3.setBounds(325, 105, 63, 14);
				frame.getContentPane().add(label_3);
				
				JMenu mnPouziv = new JMenu("PouûÌvatelia");
				menuBar.add(mnPouziv);
				
				JMenuItem mntmPozrietPouz = new JMenuItem("Pozrieù pouûÌvateæov");
				mnPouziv.add(mntmPozrietPouz);
				
				JMenuItem mntmPridatPouz = new JMenuItem("Pridaù pouûÌvateæa");
				mnPouziv.add(mntmPridatPouz);
				
				JMenuItem pridajVozi = new JMenuItem("Pridaù vozidlo");
				vozidla.add(pridajVozi);
				
				mntmPridatPouz.addMouseListener(new MouseAdapter() {
		            public void mousePressed(MouseEvent evt) {
		                okno.pridajZamestnanca();
		            }
		        });
				
				mntmPozrietPouz.addMouseListener(new MouseAdapter() {
		            public void mousePressed(MouseEvent evt) {
		                try {
							okno.pozriPouzivatelov(type);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
				
				pridajVozi.addMouseListener(new MouseAdapter() {
		            public void mousePressed(MouseEvent evt) {
		                try {
							okno.pridajVoz();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
			}
		}
		else if(type.equals("zakaznik")){
			JMenu mnObjedvky = new JMenu("Objedn·vky");
			menuBar.add(mnObjedvky);
			
			JMenuItem mntmPozrieObjednvky = new JMenuItem("Vytvoriù objed·vku");
			mnObjedvky.add(mntmPozrieObjednvky);
			
			mntmPozrieObjednvky.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	                try {
						okno.vytvorObjednavku();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
		}
	}
	

}













