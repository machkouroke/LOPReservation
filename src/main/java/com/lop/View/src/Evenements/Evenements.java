package com.lop.View.src.Evenements;

import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.EtchedBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Evenements extends JFrame  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JPanel contenu;
	CardLayout cardlayout;


	private JPanel contentPane;
	private JTextField add_numSalle;
	private JTextField add_numBloc;
	private JTextField add_idReserv;
	private JTextField add_nomEvent;
	private JTextField update_numSalle;
	private JTextField update_numBloc;
	private JTextField update_idReserv;
	private JTextField update_nomEvent;
	private JTable update_table;
	private JTextField recherche_update;
	JDateChooser add_date_event;
	private JTable table_delete;
	private JTextField delete_id_text;
	private JTable table_lecture;
	private JTextField text_reserv_donneId;
	private JTextField text_bloc_donne;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Evenements frame = new Evenements();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unused")
	public Evenements() throws IOException {
		
		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (dim.width-this.getSize().width)/2;
		int locationY = (dim.height-this.getSize().height)/2;
		setLocation(locationX,locationY);*/


		setTitle("GESTION DES EVENEMENTS");
		setResizable(false);
		setName("GESTION DES EVENEMENTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 542);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/evenement.png")
		JLabel icone_even = new JLabel("");
		ImageIcon img_event = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/evenement.png")));
		icone_even.setIcon((img_event));
		icone_even.setBounds(48, 16, 86, 53);
		contentPane.add(icone_even);

		JPanel titre_p = new JPanel();
		titre_p.setBackground(new Color(220, 20, 60));
		titre_p.setBounds(10, 80, 174, 23);
		contentPane.add(titre_p);
		titre_p.setLayout(null);

		JLabel lbltitre_p = new JLabel("EVENEMENTS");
		lbltitre_p.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitre_p.setBounds(10, 5, 142, 14);
		lbltitre_p.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbltitre_p.setForeground(Color.WHITE);
		titre_p.add(lbltitre_p);

		JPanel menu = new JPanel();
		menu.setBackground(new Color(255, 140, 0));
		menu.setBounds(10, 114, 174, 380);
		contentPane.add(menu);
		menu.setLayout(null);

		JLabel menu_quit = new JLabel("QUITTER ...");
		menu_quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rep = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter !?");
				if(rep == JOptionPane.YES_OPTION) dispose();
			}
		});
		menu_quit.setHorizontalAlignment(SwingConstants.CENTER);
		menu_quit.setForeground(Color.WHITE);
		menu_quit.setFont(new Font("Tahoma", Font.BOLD, 13));
		menu_quit.setBackground(new Color(255, 140, 0));
		menu_quit.setBounds(40, 341, 87, 28);
		menu.add(menu_quit);

		JPanel pan_menu_add = new JPanel();
		pan_menu_add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardlayout.show(contenu, "add");
			}
		});
		pan_menu_add.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pan_menu_add.setBackground(new Color(255, 140, 0));
		pan_menu_add.setBounds(7, 70, 160, 45);
		menu.add(pan_menu_add);
		pan_menu_add.setLayout(null);

		JLabel menu_add = new JLabel("AJOUT");
		menu_add.setBounds(58, 13, 44, 19);
		menu_add.setHorizontalAlignment(SwingConstants.CENTER);
		menu_add.setForeground(new Color(0, 0, 0));
		menu_add.setFont(new Font("Tahoma", Font.BOLD, 13));
		menu_add.setBackground(new Color(255, 140, 0));
		pan_menu_add.add(menu_add);

		JLabel menu_add_icon = new JLabel("");
		menu_add_icon.setBounds(8, 2, 37, 41);
		pan_menu_add.add(menu_add_icon);
		ImageIcon icon_ad_menu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_ajout.png")));
		menu_add_icon.setIcon(icon_ad_menu);

		JPanel pan_menu_update = new JPanel();
		pan_menu_update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardlayout.show(contenu, "update");
			}
		});
		pan_menu_update.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pan_menu_update.setBackground(new Color(255, 140, 0));
		pan_menu_update.setBounds(7, 122, 160, 45);
		menu.add(pan_menu_update);
		pan_menu_update.setLayout(null);

		JLabel menu_update = new JLabel("MODIFICATION");
		menu_update.setBounds(44, 10, 106, 25);
		menu_update.setHorizontalAlignment(SwingConstants.CENTER);
		menu_update.setForeground(new Color(0, 0, 0));
		menu_update.setFont(new Font("Tahoma", Font.BOLD, 13));
		menu_update.setBackground(new Color(255, 140, 0));
		pan_menu_update.add(menu_update);

		JLabel menu_update_icon = new JLabel("");
		menu_update_icon.setBounds(8, 5, 38, 35);
		pan_menu_update.add(menu_update_icon);
		ImageIcon update_menu_icon = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_modif.png")));
		menu_update_icon.setIcon(update_menu_icon);

		JPanel pan_menu_delete = new JPanel();
		pan_menu_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardlayout.show(contenu, "delete");
			}
		});
		pan_menu_delete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pan_menu_delete.setBackground(new Color(255, 140, 0));
		pan_menu_delete.setBounds(7, 174, 160, 45);
		menu.add(pan_menu_delete);
		pan_menu_delete.setLayout(null);

		JLabel menu_delete = new JLabel("SUPPRESSION");
		menu_delete.setBounds(44, 14, 98, 17);
		menu_delete.setHorizontalAlignment(SwingConstants.CENTER);
		menu_delete.setForeground(new Color(0, 0, 0));
		menu_delete.setFont(new Font("Tahoma", Font.BOLD, 13));
		menu_delete.setBackground(new Color(255, 140, 0));
		pan_menu_delete.add(menu_delete);

		JLabel menu_delete_icon = new JLabel("");
		menu_delete_icon.setBounds(8, 8, 34, 29);
		pan_menu_delete.add(menu_delete_icon);
		ImageIcon delete_icon_menu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_sup.png")));
		menu_delete_icon.setIcon(delete_icon_menu);

		JPanel pan_menu_home = new JPanel();
		pan_menu_home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardlayout.show(contenu,"home");
			}
		});
		pan_menu_home.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pan_menu_home.setBackground(new Color(255, 140, 0));
		pan_menu_home.setBounds(7, 18, 160, 45);
		menu.add(pan_menu_home);
		pan_menu_home.setLayout(null);

		JLabel menu_homr = new JLabel("ACCUEIL");
		menu_homr.setBounds(56, 14, 59, 17);
		menu_homr.setForeground(new Color(0, 0, 0));
		menu_homr.setFont(new Font("Tahoma", Font.BOLD, 13));
		pan_menu_home.add(menu_homr);

		JLabel menu_home_icon = new JLabel("New label");
		menu_home_icon.setBounds(8, 9, 36, 27);
		pan_menu_home.add(menu_home_icon);
		ImageIcon home_icon_menu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_home.png")));
		menu_home_icon.setIcon(home_icon_menu);

		JPanel pan_menu_autres = new JPanel();
		pan_menu_autres.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardlayout.show(contenu, "autres");
			}
		});
		pan_menu_autres.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pan_menu_autres.setBackground(new Color(255, 140, 0));
		pan_menu_autres.setBounds(7, 226, 160, 45);
		menu.add(pan_menu_autres);
		pan_menu_autres.setLayout(null);

		JLabel menu_autres = new JLabel("AUTRES");
		menu_autres.setForeground(new Color(0, 0, 0));
		menu_autres.setHorizontalAlignment(SwingConstants.CENTER);
		menu_autres.setFont(new Font("Tahoma", Font.BOLD, 13));
		menu_autres.setBounds(43, 15, 83, 15);
		pan_menu_autres.add(menu_autres);

		JLabel menu_autres_icon = new JLabel("");
		menu_autres_icon.setBounds(8, 6, 49, 33);
		pan_menu_autres.add(menu_autres_icon);
		ImageIcon autres_menu_icon = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_autres_.png")));
		menu_autres_icon.setIcon(autres_menu_icon);

		contenu = new JPanel();
		contenu.setBounds(194, 16, 646, 478);
		contentPane.add(contenu);
		contenu.setLayout(new CardLayout(0, 0));
		cardlayout = (CardLayout)contenu.getLayout();

		JPanel home = new JPanel();
		home.setForeground(new Color(220, 220, 220));
		home.setBackground(new Color(250, 240, 230));
		contenu.add(home, "home");
		home.setLayout(null);

		JLabel icon_home = new JLabel("");
		icon_home.setBounds(192, 68, 271, 256);
		home.add(icon_home);
		ImageIcon img_home = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/home2.png")));
		icon_home.setIcon(img_home);

		JLabel lblBienvenu = new JLabel("Bienvenue !!");
		lblBienvenu.setForeground(new Color(128, 128, 128));
		lblBienvenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 60));
		lblBienvenu.setBounds(175, 322, 335, 94);
		home.add(lblBienvenu);


		JPanel pan_add = new JPanel();
		pan_add.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pan_add.setBackground(new Color(250, 240, 230));
		contenu.add(pan_add, "add");
		pan_add.setLayout(null);

		JPanel formulaire = new JPanel();
		formulaire.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		formulaire.setBounds(159, 64, 338, 332);
		pan_add.add(formulaire);
		formulaire.setLayout(null);

		JLabel lab_id_Salle = new JLabel("Numero de Salle ");
		lab_id_Salle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_Salle.setBounds(22, 83, 118, 17);
		formulaire.add(lab_id_Salle);

		JLabel lab_id_Bloc = new JLabel("Numero de Bloc");
		lab_id_Bloc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_Bloc.setBounds(22, 140, 118, 14);
		formulaire.add(lab_id_Bloc);

		JLabel lab_id_reservataire = new JLabel("ID du reservataire");
		lab_id_reservataire.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_reservataire.setBounds(22, 188, 118, 20);
		formulaire.add(lab_id_reservataire);

		JLabel lab_event = new JLabel("Nom Evenement");
		lab_event.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_event.setBounds(22, 244, 118, 14);
		formulaire.add(lab_event);

		JLabel lab_date_event = new JLabel("Date Evenement");
		lab_date_event.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_date_event.setBounds(22, 292, 118, 17);
		formulaire.add(lab_date_event);

		add_numSalle = new JTextField();
		add_numSalle.setBorder(null);
		add_numSalle.setBounds(150, 85, 178, 20);
		formulaire.add(add_numSalle);
		add_numSalle.setColumns(10);

		add_numBloc = new JTextField();
		add_numBloc.setBorder(null);
		add_numBloc.setBounds(150, 138, 178, 20);
		formulaire.add(add_numBloc);
		add_numBloc.setColumns(10);

		add_idReserv = new JTextField();
		add_idReserv.setBorder(null);
		add_idReserv.setBounds(150, 189, 178, 20);
		formulaire.add(add_idReserv);
		add_idReserv.setColumns(10);

		add_nomEvent = new JTextField();
		add_nomEvent.setBorder(null);
		add_nomEvent.setBounds(150, 242, 178, 20);
		formulaire.add(add_nomEvent);
		add_nomEvent.setColumns(10);

		add_date_event = new JDateChooser();
		add_date_event.setBounds(150, 292, 178, 20);
		formulaire.add(add_date_event);

		JLabel icon_add_form = new JLabel("");
		icon_add_form.setBounds(142, 11, 57, 63);
		formulaire.add(icon_add_form);
		ImageIcon img_add_form = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/ajout_ev2.png")));
		icon_add_form.setIcon(img_add_form);

		JButton bouton_ajout = new JButton("AJOUTER");
		bouton_ajout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(add_numSalle.getText().equals("") || add_numBloc.getText().equals("") || add_idReserv.getText().equals("") || add_nomEvent.getText().equals("") || add_date_event.getDate() == null) {
					JOptionPane.showMessageDialog( null, "Certaines cases sont vides.\nEntrez toutes les valeurs.");
				}
				else {}

			}
		});
		bouton_ajout.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		bouton_ajout.setBounds(383, 407, 114, 23);
		pan_add.add(bouton_ajout);

		JButton bouton_restore = new JButton("REINITIALISER");
		bouton_restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rep = JOptionPane.showConfirmDialog(null, "Etes-vous surs de vouloir tout remettre a zero");
				if(rep == JOptionPane.YES_OPTION) {
					add_numSalle.setText("");
					add_numBloc.setText("");
					add_idReserv.setText("");
					add_nomEvent.setText("");
					add_date_event.setDate(null);
				}

			}
		});
		bouton_restore.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		bouton_restore.setBounds(158, 407, 114, 23);
		pan_add.add(bouton_restore);

		JLabel add_title = new JLabel(">> AJOUTER UN EVENEMENT");
		add_title.setForeground(new Color(255, 140, 0));
		add_title.setHorizontalAlignment(SwingConstants.CENTER);
		add_title.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		add_title.setBounds(35, 11, 318, 42);
		pan_add.add(add_title);

		JPanel pan_update = new JPanel();
		pan_update.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pan_update.setBackground(new Color(250, 240, 230));
		contenu.add(pan_update, "update");
		pan_update.setLayout(null);

		JPanel formulaire_update = new JPanel();
		formulaire_update.setLayout(null);
		formulaire_update.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formulaire_update.setBounds(27, 124, 276, 284);
		pan_update.add(formulaire_update);

		JLabel lab_id_Salle_1 = new JLabel("Numero de Salle ");
		lab_id_Salle_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_Salle_1.setBounds(22, 46, 94, 17);
		formulaire_update.add(lab_id_Salle_1);

		JLabel lab_id_Bloc_1 = new JLabel("Numero de Bloc");
		lab_id_Bloc_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_Bloc_1.setBounds(22, 94, 106, 14);
		formulaire_update.add(lab_id_Bloc_1);

		JLabel lab_id_reservataire_1 = new JLabel("ID du reservataire");
		lab_id_reservataire_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_id_reservataire_1.setBounds(22, 134, 94, 20);
		formulaire_update.add(lab_id_reservataire_1);

		JLabel lab_event_1 = new JLabel("Nom Evenement");
		lab_event_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_event_1.setBounds(22, 177, 80, 14);
		formulaire_update.add(lab_event_1);

		JLabel lab_date_event_1 = new JLabel("Date Evenement");
		lab_date_event_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lab_date_event_1.setBounds(22, 214, 85, 17);
		formulaire_update.add(lab_date_event_1);

		update_numSalle = new JTextField();
		update_numSalle.setColumns(10);
		update_numSalle.setBorder(null);
		update_numSalle.setBounds(126, 45, 138, 20);
		formulaire_update.add(update_numSalle);

		update_numBloc = new JTextField();
		update_numBloc.setColumns(10);
		update_numBloc.setBorder(null);
		update_numBloc.setBounds(126, 92, 138, 20);
		formulaire_update.add(update_numBloc);

		update_idReserv = new JTextField();
		update_idReserv.setColumns(10);
		update_idReserv.setBorder(null);
		update_idReserv.setBounds(126, 135, 138, 20);
		formulaire_update.add(update_idReserv);

		update_nomEvent = new JTextField();
		update_nomEvent.setColumns(10);
		update_nomEvent.setBorder(null);
		update_nomEvent.setBounds(126, 175, 138, 20);
		formulaire_update.add(update_nomEvent);

		JDateChooser update_dateEvent = new JDateChooser();
		update_dateEvent.setBorder(null);
		update_dateEvent.setBounds(126, 214, 138, 20);
		formulaire_update.add(update_dateEvent);





		JScrollPane scrollPane_update = new JScrollPane();
		scrollPane_update.setBounds(338, 77, 298, 331);
		pan_update.add(scrollPane_update);

		update_table = new JTable();
		MyTableModel model = new MyTableModel();

		model.setColumnIdentifiers(new String[] {"Nom Salle","Nom Bloc","ID","Nom Event","Date Event"});

		model.addRow(new Object [] {"1","A","FGHG","dfgh","13-10-2016"});
		model.addRow(new Object [] {"2","B","OKE","cb","12-5-2022"});
		model.addRow(new Object [] {"3","C","erty","dfdg","16-1-1990"});
		model.addRow(new Object [] {"3","B","err","d'ac","15-7-2000"});
		model.addRow(new Object [] {"1","C","ergvhty","dfdjjg","18-2-1995"});

		update_table.setModel(model);
		update_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				int i = update_table.convertRowIndexToModel(update_table.getSelectedRow());
				update_numSalle.setText((String) model.getValueAt(i, 0));
				update_numBloc.setText((String) model.getValueAt(i, 1));
				update_idReserv.setText((String) model.getValueAt(i, 2));
				update_nomEvent.setText((String) model.getValueAt(i, 3));
				try {
					update_dateEvent.setDate(new SimpleDateFormat("dd-MM-yyyy").parse((String) model.getValueAt(i, 4)));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});




		scrollPane_update.setViewportView(update_table);

		JButton bouton_update = new JButton("MODIFIER ...");
		bouton_update.setBounds(27, 419, 111, 23);
		pan_update.add(bouton_update);

		JButton update_butt_cancel = new JButton("ANNULER ...\r\n");
		update_butt_cancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		update_butt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_numSalle.setText("");
				update_numBloc.setText("");
				update_idReserv.setText("");
				update_nomEvent.setText("");
				update_dateEvent.setDate(null);
			}
		});
		update_butt_cancel.setBounds(525, 419, 111, 23);
		pan_update.add(update_butt_cancel);

		JLabel lblNewLabel = new JLabel(">> MODIFIER UN EVENEMENT");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 140, 0));
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		lblNewLabel.setBounds(53, 21, 318, 23);
		pan_update.add(lblNewLabel);

		recherche_update = new JTextField();
		recherche_update.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String research = recherche_update.getText();
				searchInTable(research,update_table);
			}
		});
		recherche_update.setBounds(27, 93, 276, 20);
		pan_update.add(recherche_update);
		recherche_update.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Recherchez un evenement ici");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(53, 66, 250, 14);
		pan_update.add(lblNewLabel_1);
		ImageIcon img_recherche= new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/recherche.png")));

		JPanel pan_delete = new JPanel();
		pan_delete.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pan_delete.setBackground(new Color(250, 240, 230));
		contenu.add(pan_delete, "delete");
		pan_delete.setLayout(null);

		JLabel Id_delete = new JLabel("Entrez l'ID de l'evenement \r\n\u00E0 supprimer\r\n");
		Id_delete.setFont(new Font("Tahoma", Font.PLAIN, 17));
		Id_delete.setBounds(22, 137, 306, 52);
		pan_delete.add(Id_delete);

		delete_id_text = new JTextField();
		delete_id_text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String id_to_delete = delete_id_text.getText();
				searchInTable(id_to_delete,table_delete);
			}
		});
		delete_id_text.setBounds(32, 200, 273, 37);
		pan_delete.add(delete_id_text);
		delete_id_text.setColumns(10);

		JScrollPane scrollpane_delete = new JScrollPane();
		scrollpane_delete.setBounds(338, 66, 298, 331);
		pan_delete.add(scrollpane_delete);

		table_delete = new JTable();
		MyTableModel model_del = new MyTableModel();
		table_delete.setModel(model_del);
		table_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				int i = table_delete.convertRowIndexToModel(table_delete.getSelectedRow());
				delete_id_text.setText((String) model_del.getValueAt(i, 0));


			}
		});

		scrollpane_delete.setViewportView(table_delete);


		JButton btn_del = new JButton("SUPPRIMER\r\n");
		btn_del.setBounds(32, 270, 132, 30);
		pan_delete.add(btn_del);

		JButton btn_cancel = new JButton("ANNULER\r\n");
		btn_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delete_id_text.setText("");
			}
		});
		btn_cancel.setBounds(173, 355, 132, 30);
		pan_delete.add(btn_cancel);

		JLabel lblNewLabel_2 = new JLabel(">> SUPPRIMER UN EVENEMENT");
		lblNewLabel_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		lblNewLabel_2.setBounds(60, 11, 333, 32);
		pan_delete.add(lblNewLabel_2);

		JPanel pan_autres = new JPanel();
		pan_autres.setBackground(new Color(250, 240, 230));
		contenu.add(pan_autres, "autres");
		pan_autres.setLayout(null);

		JPanel questions = new JPanel();
		questions.setBounds(66, 90, 533, 76);
		pan_autres.add(questions);
		questions.setLayout(new CardLayout(0, 0));
		CardLayout cardlayout_questions = (CardLayout) questions.getLayout();

		JPanel autres_req = new JPanel();
		autres_req.setBackground(new Color(250, 240, 230));
		questions.add(autres_req, "autres");

		JPanel reservataire_donne = new JPanel();
		reservataire_donne.setBackground(new Color(250, 240, 230));
		questions.add(reservataire_donne, "reservataire_donne");
		reservataire_donne.setLayout(null);

		JLabel lbl_reserv_id = new JLabel("Entrez l'ID du reservataire .");
		lbl_reserv_id.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_reserv_id.setBackground(new Color(250, 240, 230));
		lbl_reserv_id.setBounds(139, 11, 241, 21);
		reservataire_donne.add(lbl_reserv_id);

		text_reserv_donneId = new JTextField();
		text_reserv_donneId.setBounds(87, 38, 332, 33);
		reservataire_donne.add(text_reserv_donneId);
		text_reserv_donneId.setColumns(10);

		JPanel bloc_donne = new JPanel();
		bloc_donne.setBackground(new Color(250, 240, 230));
		questions.add(bloc_donne, "bloc_donne");
		bloc_donne.setLayout(null);

		JLabel lbl_bloc_donne = new JLabel("Entrez le numero du bloc");
		lbl_bloc_donne.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_bloc_donne.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bloc_donne.setBounds(151, 5, 196, 29);
		bloc_donne.add(lbl_bloc_donne);

		text_bloc_donne = new JTextField();
		text_bloc_donne.setBounds(91, 36, 338, 29);
		bloc_donne.add(text_bloc_donne);
		text_bloc_donne.setColumns(10);

		JPanel date_donne = new JPanel();
		date_donne.setBackground(new Color(250, 240, 230));
		questions.add(date_donne, "date_donne");
		date_donne.setLayout(null);

		JLabel lbl_date_donne = new JLabel("Selectionnez la date");
		lbl_date_donne.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_date_donne.setBounds(170, 5, 191, 28);
		date_donne.add(lbl_date_donne);

		JDateChooser text_datedonne = new JDateChooser();
		text_datedonne.setBounds(135, 36, 247, 28);
		date_donne.add(text_datedonne);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(66, 177, 533, 274);
		pan_autres.add(scrollPane);

		table_lecture = new JTable();
		table_lecture.setBorder(null);
		scrollPane.setViewportView(table_lecture);
		MyTableModel model_lecture = new MyTableModel();
		table_lecture.setModel(model_lecture);



		String [] req = {"              -------Que voulez-vous afficher ?-------              ","Reservations d'un individu donne","Reservations actifs dans un bloc donne","Individu ayant des reservations en cours","Reservations d'un jour donne","Evenements passees"};
		JComboBox request = new JComboBox(req);
		request.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if(request.getSelectedIndex()==1) {
					model_lecture.setColumnIdentifiers(new String [] {"ID","Nom"});
					cardlayout_questions.show(questions, "reservataire_donne");

				}

				if(request.getSelectedIndex()==2) {
					model_lecture.setColumnIdentifiers(new String [] {"ID","NomEven"});
					cardlayout_questions.show(questions, "bloc_donne");

				}

				if(request.getSelectedIndex()==3) {
					model_lecture.setColumnIdentifiers(new String [] {"ID","Age_Ind"});
					cardlayout_questions.show(questions, "autres");

				}

				if(request.getSelectedIndex()==4) {
					model_lecture.setColumnIdentifiers(new String [] {"IDEvent","Nom"});
					cardlayout_questions.show(questions, "date_donne");


				}

				if(request.getSelectedIndex()==5) {
					model_lecture.setColumnIdentifiers(new String [] {"ID","Nom","Date"});
					cardlayout_questions.show(questions, "autres");

				}
			}
		});
		request.setBounds(66, 57, 533, 22);
		pan_autres.add(request);






	}

	public void searchInTable(String str, JTable table) {

		MyTableModel model = (MyTableModel) table.getModel();
		TableRowSorter<MyTableModel> trs =new TableRowSorter<>(model);
		table.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter(str));
		model.fireTableDataChanged();


	}
}
