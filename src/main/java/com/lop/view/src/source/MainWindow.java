package com.lop.view.src.source;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import com.lop.controller.Controller;
import com.lop.error_event.ErrorListener;
import com.lop.communication.Request;
import com.lop.communication.Response;
import com.toedter.calendar.JDateChooser;

import java.util.logging.*;

public class MainWindow extends JFrame implements ErrorListener, ViewToController {
    MyTableModel modelLecture;
    MyTableModel modelDel;
    MyTableModel model;
    Logger logger = Logger.getLogger("flogger");
    JDateChooser updateDateEvent;
    JPanel contentPane;
    JPanel contenu;
    CardLayout cardlayout;
    JTextField textBlocDonne;

    private JComboBox<String> addNumSalle;
    private JComboBox<String> addNumBloc;
    private JTextField addIdReservataire;
    private JTextField addNomEvent;
    private JComboBox<String> updateNumSalle;
    private JComboBox<String> updateNumBloc;
    private JTextField updateIdReservataire;
    private JTextField updateNomEvent;
    private JTable updateTable;
    private JTextField rechercheUpdate;
    private JDateChooser addDateEvent;
    private JTable tableDelete;
    private JTextField deleteIdText;
    private final transient Controller controller;

    String[] salles = {"--Numéro de Salle--", "1", "2", "3", "4"};
    String[] blocs = {"--Numéro de Bloc--", "A", "B", "C", "D"};
    private JDateChooser textDateDonne;

    public MainWindow(Controller controller) throws IOException {
        //my_log.logger.setLevel(Level.WARNING);
        this.controller = controller;
        this.controller.addListener(this);
        setTitle("GESTION DES EVENEMENTS");
        setResizable(false);
        setName("GESTION DES EVENEMENTS");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 864, 542);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(250, 240, 230));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel iconeEven = new JLabel("");
        ImageIcon imgEvent = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/evenement.png")));
        iconeEven.setIcon((imgEvent));
        iconeEven.setBounds(48, 16, 86, 53);
        contentPane.add(iconeEven);

        JPanel titreP = new JPanel();
        titreP.setBackground(new Color(220, 20, 60));
        titreP.setBounds(10, 80, 174, 23);
        contentPane.add(titreP);
        titreP.setLayout(null);

        JLabel lbltitreP = new JLabel("EVENEMENTS");
        lbltitreP.setHorizontalAlignment(SwingConstants.CENTER);
        lbltitreP.setBounds(10, 5, 142, 14);
        lbltitreP.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbltitreP.setForeground(Color.WHITE);
        titreP.add(lbltitreP);

        //menu
        menuFunc();

        contenu = new JPanel();
        contenu.setBounds(194, 16, 646, 478);
        contentPane.add(contenu);
        contenu.setLayout(new CardLayout(0, 0));
        cardlayout = (CardLayout) contenu.getLayout();

        panelHome();

        panelAdd();

        panelUpdate();

        panelDelete();

        panelAutres();

    }
    public void affichage(MyTableModel model,Response response){
        //response = controller.getAllReservations();
        List<List<String>> data = response.getData();
        String[] columns = new String[data.get(0).size()];
        data.get(0).toArray(columns);
        Object[] row = new Object[data.get(0).size()];
        model.setColumnIdentifiers(columns);

        int i = 1;
        int j;
        while (i < data.size() - 1) {
            for (j = 0; j < data.get(0).size(); j++) {
                row[j] = data.get(i).get(j);
            }
            model.addRow(row);
            //....
            i++;
        }
    }

    public void add(ActionEvent action) {
        String r;
        if (addNumSalle.getSelectedIndex() == 0 || addNumBloc.getSelectedIndex() == 0 || addIdReservataire.getText().equals("") || addNomEvent.getText().equals("") || addDateEvent.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Certaines cases sont vides.\nEntrez toutes les valeurs.");
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("idReservataire", this.addIdReservataire.getText());
            params.put("numSalle", (String) this.addNumSalle.getSelectedItem());
            params.put("numBloc", (String) this.addNumBloc.getSelectedItem());
            params.put("eventName", this.addNomEvent.getText());
            params.put("eventDate", new SimpleDateFormat("yyyy-MM-dd").format(this.addDateEvent.getDate()));

            Response response = this.controller.add(new Request("Ajout d'un utilisateur", params));
            r = response.getError();
            if (r != null) {
                this.ErrorLog(r);
            }

        }
    }

    public void searchInTable(String str, JTable table) {

        MyTableModel modelSearch = (MyTableModel) table.getModel();
        TableRowSorter<MyTableModel> trs = new TableRowSorter<>(modelSearch);
        table.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
        model.fireTableDataChanged();


    }

    public void menuFunc() throws IOException {

        JPanel menu = new JPanel();
        menu.setBackground(new Color(255, 140, 0));
        menu.setBounds(10, 114, 174, 380);
        contentPane.add(menu);
        menu.setLayout(null);

        JLabel menuQuit = new JLabel("QUITTER ...");
        menuQuit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rep = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter !?");
                if (rep == JOptionPane.YES_OPTION) dispose();
            }
        });
        menuQuit.setHorizontalAlignment(SwingConstants.CENTER);
        menuQuit.setForeground(Color.WHITE);
        menuQuit.setFont(new Font("Tahoma", Font.BOLD, 13));
        menuQuit.setBackground(new Color(255, 140, 0));
        menuQuit.setBounds(40, 341, 87, 28);
        menu.add(menuQuit);

        JPanel panMenuAdd = new JPanel();
        panMenuAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardlayout.show(contenu, "add");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panMenuAdd.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panMenuAdd.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuAdd, 70);
        menu.add(panMenuAdd);


        JLabel menuAdd = new JLabel("AJOUT");
        menuLabelFunction(menuAdd, new int[]{58, 13, 44, 19});
        panMenuAdd.add(menuAdd);

        JLabel menuAddIcon = new JLabel("");
        menuAddIcon.setBounds(8, 2, 37, 41);
        panMenuAdd.add(menuAddIcon);
        ImageIcon iconAdMenu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_ajout.png")));
        menuAddIcon.setIcon(iconAdMenu);

        JPanel panMenuUpdate = new JPanel();
        panMenuUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               Response response= controller.getAllReservations();
                affichage(model,response);
                cardlayout.show(contenu, "update");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panMenuUpdate.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panMenuUpdate.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuUpdate, 122);
        menu.add(panMenuUpdate);


        JLabel menuUpdate = new JLabel("MODIFICATION");
        menuLabelFunction(menuUpdate, new int[]{44, 10, 106, 25});
        panMenuUpdate.add(menuUpdate);

        JLabel menuUpdateIcon = new JLabel("");
        menuUpdateIcon.setBounds(8, 5, 38, 35);
        panMenuUpdate.add(menuUpdateIcon);
        ImageIcon updateMenuIcon = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_modif.png")));
        menuUpdateIcon.setIcon(updateMenuIcon);

        JPanel panMenuDelete = new JPanel();
        panMenuDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Response response=controller.getAllReservations();
                 affichage(modelDel,response);
                cardlayout.show(contenu, "delete");
                //set les differents textes à vide....
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panMenuDelete.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panMenuDelete.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuDelete, 174);
        menu.add(panMenuDelete);


        JLabel menuDelete = new JLabel("SUPPRESSION");
        menuLabelFunction(menuDelete, new int[]{44, 14, 98, 17});
        panMenuDelete.add(menuDelete);

        JLabel menuDeleteIcon = new JLabel("");
        menuDeleteIcon.setBounds(8, 8, 34, 29);
        panMenuDelete.add(menuDeleteIcon);
        ImageIcon deleteIconMenu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_sup.png")));
        menuDeleteIcon.setIcon(deleteIconMenu);

        JPanel panMenuHome = new JPanel();
        panMenuHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardlayout.show(contenu, "home");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panMenuHome.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panMenuHome.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuHome, 18);
        menu.add(panMenuHome);


        JLabel menuHome = new JLabel("ACCUEIL");
        menuLabelFunction(menuHome, new int[]{56, 14, 59, 17});
        panMenuHome.add(menuHome);

        JLabel menuHomeIcon = new JLabel("New label");
        menuHomeIcon.setBounds(8, 9, 36, 27);
        panMenuHome.add(menuHomeIcon);
        ImageIcon homeIconMenu = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_home.png")));
        menuHomeIcon.setIcon(homeIconMenu);

        JPanel panMenuAutres = new JPanel();
        panMenuAutres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardlayout.show(contenu, "autres");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panMenuAutres.setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panMenuAutres.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuAutres, 226);
        menu.add(panMenuAutres);


        JLabel menuAutres = new JLabel("AUTRES");
        menuLabelFunction(menuAutres, new int[]{43, 15, 83, 15});
        panMenuAutres.add(menuAutres);

        JLabel menuAutresIcon = new JLabel("");
        menuAutresIcon.setBounds(8, 6, 49, 33);
        panMenuAutres.add(menuAutresIcon);
        ImageIcon autresMenuIcon = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_autres_.png")));
        menuAutresIcon.setIcon(autresMenuIcon);


    }


    public void menuPanelPosition(JPanel panel, int y) {
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.setBackground(new Color(255, 140, 0));
        panel.setBounds(7, y, 160, 45);
        panel.setLayout(null);
    }

    public void menuLabelFunction(JLabel label, int[] bounds) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(new Color(0, 0, 0));
        label.setFont(new Font("Tahoma", Font.BOLD, 13));
    }

    public void panelHome() throws IOException {
        JPanel home = new JPanel();
        home.setForeground(new Color(220, 220, 220));
        home.setBackground(new Color(250, 240, 230));
        contenu.add(home, "home");
        home.setLayout(null);

        JLabel iconHome = new JLabel("");
        iconHome.setBounds(192, 68, 271, 256);
        home.add(iconHome);
        ImageIcon imgHome = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/home2.png")));
        iconHome.setIcon(imgHome);

        JLabel lblBienvenu = new JLabel("Bienvenue !!");
        lblBienvenu.setForeground(new Color(128, 128, 128));
        lblBienvenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenu.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 60));
        lblBienvenu.setBounds(175, 322, 335, 94);
        home.add(lblBienvenu);
    }

    public void panelAdd() throws IOException {
        JPanel panAdd = new JPanel();
        contenuPanel(panAdd);
        contenu.add(panAdd, "add");

        JPanel formulaire = new JPanel();
        formulaire.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        formulaire.setBounds(159, 64, 338, 332);
        panAdd.add(formulaire);
        formulaire.setLayout(null);

        JLabel labIdSalle = new JLabel("Numero de Salle ");
        formLabel(labIdSalle, 83, 118);
        formulaire.add(labIdSalle);

        JLabel labIdBloc = new JLabel("Numero de Bloc");
        formLabel(labIdBloc, 140, 118);
        formulaire.add(labIdBloc);

        JLabel labIdReservataire = new JLabel("ID du reservataire");
        formLabel(labIdReservataire, 188, 118);
        formulaire.add(labIdReservataire);

        JLabel labEvent = new JLabel("Nom Evenement");
        formLabel(labEvent, 244, 118);
        formulaire.add(labEvent);

        JLabel labDateEvent = new JLabel("Date Evenement");
        formLabel(labDateEvent, 292, 118);
        formulaire.add(labDateEvent);


        addNumSalle = new JComboBox<>(salles);
        addNumSalle.setBounds(150, 85, 178, 20);
        formulaire.add(addNumSalle);

        addNumBloc = new JComboBox<>(blocs);
        addNumBloc.setBounds(150, 138, 178, 20);
        formulaire.add(addNumBloc);

        addIdReservataire = new JTextField();
        formTextField(addIdReservataire, 189, "add");
        formulaire.add(addIdReservataire);

        addNomEvent = new JTextField();
        formTextField(addNomEvent, 242, "add");
        formulaire.add(addNomEvent);

        addDateEvent = new JDateChooser();
        addDateEvent.setBounds(150, 292, 178, 20);
        formulaire.add(addDateEvent);

        JLabel iconAddForm = new JLabel("");
        iconAddForm.setBounds(142, 11, 57, 63);
        formulaire.add(iconAddForm);
        ImageIcon imgAddForm = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/ajout_ev2.png")));
        iconAddForm.setIcon(imgAddForm);

        JButton boutonAjout = new JButton("AJOUTER");
        boutonAjout.addActionListener(this::add);
        boutonAjout.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        boutonAjout.setBounds(383, 407, 114, 23);
        panAdd.add(boutonAjout);

        JButton boutonRestore = new JButton("REINITIALISER");
        boutonRestore.addActionListener(e -> {

            int rep = JOptionPane.showConfirmDialog(null, "Etes-vous surs de vouloir tout remettre a zero");
            if (rep == JOptionPane.YES_OPTION) {
                addNumSalle.setSelectedIndex(0);
                addNumBloc.setSelectedIndex(0);
                addIdReservataire.setText("");
                addNomEvent.setText("");
                addDateEvent.setDate(null);
            }

        });
        boutonRestore.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        boutonRestore.setBounds(158, 407, 114, 23);
        panAdd.add(boutonRestore);

        JLabel addTitle = new JLabel(">> AJOUTER UN EVENEMENT");
        titleLabel(addTitle);
        panAdd.add(addTitle);
    }

    public void formTextField(JTextField tf, int y, String namePanel) {

        if (namePanel.equals("add")) tf.setBounds(150, y, 178, 20);
        if (namePanel.equals("update")) tf.setBounds(126, y, 138, 20);
        tf.setColumns(10);
    }

    public void formLabel(JLabel label, int y, int width) {
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        label.setBounds(22, y, width, 15);
    }

    public void titleLabel(JLabel lb) {
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setForeground(new Color(255, 140, 0));
        lb.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        lb.setBounds(35, 11, 318, 42);
    }

    public void panelUpdate() {
        JPanel panUpdate = new JPanel();
        contenuPanel(panUpdate);
        contenu.add(panUpdate, "update");

        JPanel formulaireUpdate = new JPanel();
        formulaireUpdate.setLayout(null);
        formulaireUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        formulaireUpdate.setBounds(27, 124, 276, 284);
        panUpdate.add(formulaireUpdate);

        JLabel labIdSalle1 = new JLabel("Numéro de Salle ");
        formLabel(labIdSalle1, 46, 94);
        formulaireUpdate.add(labIdSalle1);

        JLabel labIdBloc1 = new JLabel("Numéro de Bloc");
        formLabel(labIdBloc1, 94, 106);
        formulaireUpdate.add(labIdBloc1);

        JLabel labIdReservataire1 = new JLabel("ID du reservataire");
        formLabel(labIdReservataire1, 134, 94);
        formulaireUpdate.add(labIdReservataire1);

        JLabel labEvent1 = new JLabel("Nom Evenement");
        formLabel(labEvent1, 177, 80);
        formulaireUpdate.add(labEvent1);

        JLabel labDateEvent1 = new JLabel("Date Evenement");
        formLabel(labDateEvent1, 214, 85);
        formulaireUpdate.add(labDateEvent1);

        updateNumSalle = new JComboBox<>(salles);
        updateNumSalle.setBounds(126, 45, 138, 20);
        formulaireUpdate.add(updateNumSalle);

        updateNumBloc = new JComboBox<>(blocs);
        updateNumBloc.setBounds(126, 92, 138, 20);
        formulaireUpdate.add(updateNumBloc);

        updateIdReservataire = new JTextField();
        formTextField(updateIdReservataire, 135, "update");
        formulaireUpdate.add(updateIdReservataire);

        updateNomEvent = new JTextField();
        formTextField(updateNomEvent, 175, "update");
        formulaireUpdate.add(updateNomEvent);

        updateDateEvent = new JDateChooser();
        updateDateEvent.setBorder(null);
        updateDateEvent.setBounds(126, 214, 138, 20);
        formulaireUpdate.add(updateDateEvent);


        JScrollPane scrollPaneUpdate = new JScrollPane();
        scrollPaneUpdate.setBounds(338, 77, 298, 331);
        panUpdate.add(scrollPaneUpdate);

        updateTable = new JTable();
        model = new MyTableModel();

        updateTable.setModel(model);
        updateTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int i = updateTable.convertRowIndexToModel(updateTable.getSelectedRow());
                updateNumSalle.setSelectedItem(model.getValueAt(i, 0));
                updateNumBloc.setSelectedItem(model.getValueAt(i, 1));
                updateIdReservataire.setText((String) model.getValueAt(i, 2));
                updateNomEvent.setText((String) model.getValueAt(i, 3));
                try {
                    updateDateEvent.setDate(new SimpleDateFormat("dd-MM-yyyy").parse((String) model.getValueAt(i, 4)));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }


            }
        });


        scrollPaneUpdate.setViewportView(updateTable);

        JButton boutonUpdate = new JButton("MODIFIER ...");
        boutonUpdate.addActionListener(this::update);
        boutonUpdate.setBounds(27, 419, 111, 23);
        panUpdate.add(boutonUpdate);

        JButton updateButtCancel = new JButton("ANNULER ...\r\n");
        updateButtCancel.setBorder(new BevelBorder(BevelBorder.RAISED));
        updateButtCancel.addActionListener(e -> {
            updateNumSalle.setSelectedIndex(0);
            updateNumBloc.setSelectedIndex(0);
            updateIdReservataire.setText("");
            updateNomEvent.setText("");
            updateDateEvent.setDate(null);
        });
        updateButtCancel.setBounds(525, 419, 111, 23);
        panUpdate.add(updateButtCancel);

        JLabel updateTitle = new JLabel(">> MODIFIER UN EVENEMENT");
        titleLabel(updateTitle);
        panUpdate.add(updateTitle);

        rechercheUpdate = new JTextField();
        rechercheUpdate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String research = rechercheUpdate.getText();
                searchInTable(research, updateTable);

            }
        });
        rechercheUpdate.setBounds(27, 93, 276, 20);
        panUpdate.add(rechercheUpdate);
        rechercheUpdate.setColumns(10);

        JLabel lblNewLabel1 = new JLabel("Recherchez un évènement ici");
        lblNewLabel1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel1.setBounds(53, 66, 250, 14);
        panUpdate.add(lblNewLabel1);

    }

    public void panelDelete() {
        JPanel panDelete = new JPanel();
        contenuPanel(panDelete);
        contenu.add(panDelete, "delete");

        JLabel idDelete = new JLabel("Entrez l'ID de l'evenement \r\n\u00E0 supprimer\r\n");
        idDelete.setFont(new Font("Tahoma", Font.PLAIN, 17));
        idDelete.setBounds(22, 137, 306, 52);
        panDelete.add(idDelete);

        deleteIdText = new JTextField();
        deleteIdText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String idToDelete = deleteIdText.getText();
                searchInTable(idToDelete, tableDelete);
            }
        });
        deleteIdText.setBounds(32, 200, 273, 37);
        panDelete.add(deleteIdText);
        deleteIdText.setColumns(10);

        JScrollPane scrollpaneDelete = new JScrollPane();
        scrollpaneDelete.setBounds(338, 66, 298, 331);
        panDelete.add(scrollpaneDelete);

        tableDelete = new JTable();
        modelDel = new MyTableModel();
        tableDelete.setModel(modelDel);
        tableDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int i = tableDelete.convertRowIndexToModel(tableDelete.getSelectedRow());
                deleteIdText.setText((String) modelDel.getValueAt(i, 0));


            }
        });

        scrollpaneDelete.setViewportView(tableDelete);


        JButton btnDel = new JButton("SUPPRIMER\r\n");
        btnDel.addActionListener(this::delete);
        btnDel.setBounds(32, 270, 132, 30);
        panDelete.add(btnDel);

        JButton btnCancel = new JButton("ANNULER\r\n");
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteIdText.setText("");
            }
        });
        btnCancel.setBounds(173, 355, 132, 30);
        panDelete.add(btnCancel);

        JLabel deleteTitle = new JLabel(">> SUPPRIMER UN EVENEMENT");
        titleLabel(deleteTitle);
        panDelete.add(deleteTitle);
    }

    public void panelAutres() {
        JPanel panAutres = new JPanel();
        contenuPanel(panAutres);
        contenu.add(panAutres, "autres");

        JPanel questions = new JPanel();
        questions.setBounds(66, 90, 533, 76);
        panAutres.add(questions);
        questions.setLayout(new CardLayout(0, 0));
        CardLayout cardlayoutQuestions = (CardLayout) questions.getLayout();

        JPanel autresReq = new JPanel();
        autresReq.setBackground(new Color(250, 240, 230));
        questions.add(autresReq, "autres");

        JPanel reservataireDonne = new JPanel();
        displayQuestionsPanel(reservataireDonne);
        questions.add(reservataireDonne, "reservataireDonne");

        JLabel lblReservId = new JLabel("Entrez l'ID du reservataire .");
        lblReservId.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblReservId.setBackground(new Color(250, 240, 230));
        lblReservId.setBounds(139, 11, 241, 21);
        reservataireDonne.add(lblReservId);

        JTextField textReserveDonneId = new JTextField();
        textReserveDonneId.setBounds(48, 35, 332, 33);
        reservataireDonne.add(textReserveDonneId);
        textReserveDonneId.setColumns(10);

        JButton buttReserveDonne = new JButton("AFFICHER");
        buttReserveDonne.addActionListener(this::dayReservation);
        buttReserveDonne.setBounds(408, 39, 89, 23);
        reservataireDonne.add(buttReserveDonne);

        JPanel blocDonne = new JPanel();
        displayQuestionsPanel(blocDonne);
        questions.add(blocDonne, "bloc_donne");

        JLabel lblBlocDonne = new JLabel("Entrez le numero du bloc");
        lblBlocDonne.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblBlocDonne.setHorizontalAlignment(SwingConstants.CENTER);
        lblBlocDonne.setBounds(151, 5, 196, 29);
        blocDonne.add(lblBlocDonne);

        textBlocDonne = new JTextField();
        textBlocDonne.setBounds(48, 36, 338, 29);
        blocDonne.add(textBlocDonne);
        textBlocDonne.setColumns(10);

        JButton buttBlocDonne = new JButton("AFFICHER");
        buttBlocDonne.addActionListener(this::evtInBloc);
        buttBlocDonne.setBounds(408, 39, 89, 23);
        blocDonne.add(buttBlocDonne);

        JPanel dateDonne = new JPanel();
        displayQuestionsPanel(dateDonne);
        questions.add(dateDonne, "date_donne");

        JLabel lblDateDonne = new JLabel("Selectionnez la date");
        lblDateDonne.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDateDonne.setBounds(170, 5, 191, 28);
        dateDonne.add(lblDateDonne);

        textDateDonne = new JDateChooser();
        textDateDonne.setBounds(48, 36, 247, 28);
        dateDonne.add(textDateDonne);

        JButton buttDateDonne = new JButton("AFFICHER");
        buttDateDonne.addActionListener(this::dayReservation);
        buttDateDonne.setBounds(408, 39, 89, 23);
        dateDonne.add(buttDateDonne);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBounds(66, 177, 533, 274);
        panAutres.add(scrollPane);

        JTable tableLecture = new JTable();
        tableLecture.setBorder(null);
        scrollPane.setViewportView(tableLecture);
        modelLecture = new MyTableModel();
        tableLecture.setModel(modelLecture);


        String[] req = {"              -------Que voulez-vous afficher ?-------              ",
                "Reservations d'un individu donne", "Reservations actifs dans un bloc donne",
                "Individu ayant des reservations en cours", "Reservations d'un jour donne", "Evenements passés"};
        JComboBox<String> request = new JComboBox<>(req);
        request.addItemListener(e -> {

            if (request.getSelectedIndex() == 1) {
                modelLecture.setColumnIdentifiers(new String[]{"ID", "Nom"});
                cardlayoutQuestions.show(questions, "reservataireDonne");
            }

            if (request.getSelectedIndex() == 2) {
                modelLecture.setColumnIdentifiers(new String[]{"ID", "NomEven"});
                cardlayoutQuestions.show(questions, "bloc_donne");
            }

            if (request.getSelectedIndex() == 3) {
                modelLecture.setColumnIdentifiers(new String[]{"ID", "Age_Ind"});
                cardlayoutQuestions.show(questions, "autres");
            }
            if (request.getSelectedIndex() == 4) {
                modelLecture.setColumnIdentifiers(new String[]{"IDEvent", "Nom"});
                cardlayoutQuestions.show(questions, "date_donne");
            }

            if (request.getSelectedIndex() == 5) {
                modelLecture.setColumnIdentifiers(new String[]{"ID", "Nom", "Date"});
                cardlayoutQuestions.show(questions, "autres");
            }
        });
        request.setBounds(66, 57, 533, 22);
        panAutres.add(request);

        JLabel autreTitle = new JLabel(">> AFFICHAGE");
        titleLabel(autreTitle);
        panAutres.add(autreTitle);
    }

    public void displayQuestionsPanel(JPanel panel) {
        panel.setBackground(new Color(250, 240, 230));
        panel.setLayout(null);
    }

    public void contenuPanel(JPanel panel) {
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        panel.setBackground(new Color(250, 240, 230));
        panel.setLayout(null);
    }

    @Override
    public void errorOccurred(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void noError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void delete(ActionEvent e) {
        if (tableDelete.getSelectedRow() < 0) {
            //errorOccurred("veuillez au prealable selectionner l'evenement à supprimer");
            JOptionPane.showMessageDialog(null,"veuillez au prealable selectionner l'evenement à supprimer");
        } else {

            Map<String, String> parameters = new HashMap();
            parameters.put("idEvent", deleteIdText.getText());

            Response response = this.controller.delete(new Request("Suppression d'un evenement", parameters));
            String r = response.getError();

            if (r != null) {
                this.ErrorLog(r);
            }


        }
    }


    public void update(ActionEvent e) {
        Map<String, String> params = new HashMap<>();
        params.put("idReservataire", this.updateIdReservataire.getText());
        params.put("numSalle", (String) this.updateNumSalle.getSelectedItem());
        params.put("numBloc", (String) this.updateNumBloc.getSelectedItem());
        params.put("eventName", this.updateNomEvent.getText());
        params.put("eventDate", new SimpleDateFormat("yyyy-MM-dd").format(this.updateDateEvent.getDate()));

        Response response = this.controller.update(new Request("Mise à jour d'un evenement", params));
        String r = response.getError();
        if (r != null) {
            this.ErrorLog(r);
        }


    }

    public void listeSalleReservataire(ActionEvent e) {
        Map<String, String> parameters = new HashMap();

        Response response = this.controller.listeSalleReservataire(new Request("Affichage des salles reservees par un reservataire", parameters));
        String r = response.getError();
        if (r != null) {
            this.ErrorLog(r);
        }

    }

    public void evtInBloc(ActionEvent e) {
        if (textBlocDonne.getText().equals(""))
            JOptionPane.showMessageDialog(null,"veuillez entrer l'id du bloc ");
        else {
            Map<String, String> parameters = new HashMap();
            parameters.put("idBloc", this.textBlocDonne.getText());
            Response response = this.controller.evtInBloc(new Request("Evenements dans un bloc", parameters));
             affichage(modelLecture,response);
            String r = response.getError();

            //model lecture avec colonnes
            if (r != null) {
                this.ErrorLog(r);
            }
        }
    }

    public void actifReservateur(ActionEvent action) {


        Response response = this.controller.actifReservateur();
        affichage(modelLecture,response);
        String r = response.getError();
        if (r != null) {
            this.ErrorLog(r);
        }

    }

    public void dayReservation(ActionEvent e) {
        if (new SimpleDateFormat("yyyy-MM-dd").format(this.textDateDonne.getDate()).equals(""))
            errorOccurred("veuillez choisir la date");

        else {
            Map<String, String> parameters = new HashMap();
            parameters.put("dayReservation", new SimpleDateFormat("yyyy-MM-dd").format(this.textDateDonne.getDate()));
            Response response = this.controller.actifReservateur();
            affichage(modelLecture,response);
            String r = response.getError();
            if (r != null) {
                ErrorLog(r);
            }
        }
    }

    public void pastEvent(ActionEvent e) {

        Response response = this.controller.pastEvent();
        affichage(modelLecture,response);
        String r = response.getError();

        if (r != null) {
            this.ErrorLog(r);
        }
    }

    public void ErrorLog(String message) {
        try {
            FileHandler fh = new FileHandler("my_log.txt");
            logger.addHandler(fh);
            logger.log(Level.SEVERE, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}




