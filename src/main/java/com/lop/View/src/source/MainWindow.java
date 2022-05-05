package com.lop.View.src.source;

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
import java.util.Map;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import com.lop.Controller.Controller;
import com.lop.ErrorEvent.ErrorListener;
import com.lop.communication.Request;
import com.lop.communication.Response;
import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame implements ErrorListener {

    JPanel contentPane;
    JPanel contenu;
    CardLayout cardlayout;

    private JComboBox addNumSalle;
    private JComboBox addNumBloc;
    private JTextField addIdReservataire;
    private JTextField addNomEvent;
    private JComboBox updateNumSalle;
    private JComboBox updateNumBloc;
    private JTextField updateIdReservataire;
    private JTextField updateNomEvent;
    private JTable updateTable;
    private JTextField rechercheUpdate;
    private JDateChooser addDateEvent;
    private JTable tableDelete;
    private JTextField deleteIdText;
    private final Controller controller;

    String [] salles = {"--Numero de Salle--","1","2","3","4"};
    String[] blocs = {"--Numero de Bloc--","A","B","C","D"};

    public MainWindow(Controller controller) throws IOException {
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
        menu_func();

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

    public void addEvent(ActionEvent action) {
        if (addNumSalle.getSelectedIndex()==0 || addNumBloc.getSelectedIndex()==0 || addIdReservataire.getText().equals("") || addNomEvent.getText().equals("") || addDateEvent.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Certaines cases sont vides.\nEntrez toutes les valeurs.");
        }
        else{
            Map<String, String> params = new HashMap<>();
            params.put("idReservataire",  this.addIdReservataire.getText());
            params.put("numSalle", (String) this.addNumSalle.getSelectedItem());
            params.put("numBloc", (String) this.addNumBloc.getSelectedItem());
            params.put("eventName",  this.addNomEvent.getText());
            params.put("eventDate", new SimpleDateFormat("yyyy-MM-dd").format(this.addDateEvent.getDate()));
//        System.out.println();
            Response response =  this.controller.add(new Request("Ajout d'un utilisateur", params));

        }
    }

    public void searchInTable(String str, JTable table) {

        MyTableModel model = (MyTableModel) table.getModel();
        TableRowSorter<MyTableModel> trs = new TableRowSorter<>(model);
        table.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
        model.fireTableDataChanged();


    }

    public void menu_func() throws IOException {

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
            public void mouseEntered(MouseEvent e){
                panMenuAdd.setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e){
                panMenuAdd.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuAdd,70);
        menu.add(panMenuAdd);


        JLabel menuAdd = new JLabel("AJOUT");
        menuLabelFunction(menuAdd,new int[] {58,13,44,19});
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
                cardlayout.show(contenu, "update");
            }
            public void mouseEntered(MouseEvent e){
                panMenuUpdate.setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e){
                panMenuUpdate.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuUpdate,122);
        menu.add(panMenuUpdate);


        JLabel menuUpdate = new JLabel("MODIFICATION");
        menuLabelFunction(menuUpdate,new int[] {44,10,106,25});
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
                cardlayout.show(contenu, "delete");
            }
            public void mouseEntered(MouseEvent e){
                panMenuDelete.setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e){
                panMenuDelete.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuDelete,174);
        menu.add(panMenuDelete);


        JLabel menuDelete = new JLabel("SUPPRESSION");
        menuLabelFunction(menuDelete,new int[] {44,14,98,17});
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
            public void mouseEntered(MouseEvent e){
                panMenuHome.setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e){
                panMenuHome.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuHome,18);
        menu.add(panMenuHome);


        JLabel menuHome = new JLabel("ACCUEIL");
        menuLabelFunction(menuHome,new int[] {56,14,59,17});
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
            public void mouseEntered(MouseEvent e){
                panMenuAutres.setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e){
                panMenuAutres.setBackground(new Color(255, 140, 0));
            }
        });
        menuPanelPosition(panMenuAutres,226);
        menu.add(panMenuAutres);


        JLabel menuAutres = new JLabel("AUTRES");
        menuLabelFunction(menuAutres,new int[] {43,15,83,15});
        panMenuAutres.add(menuAutres);

        JLabel menuAutresIcon = new JLabel("");
        menuAutresIcon.setBounds(8, 6, 49, 33);
        panMenuAutres.add(menuAutresIcon);
        ImageIcon autresMenuIcon = new ImageIcon(ImageIO.read(new File("src/main/java/com/lop/View/IMAGES/menu_autres_.png")));
        menuAutresIcon.setIcon(autresMenuIcon);


    }
/*Test commit*/

    public void menuPanelPosition(JPanel panel, int y){
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.setBackground(new Color(255, 140, 0));
        panel.setBounds(7,y,160,45);
        panel.setLayout(null);
    }

    public void menuLabelFunction(JLabel label, int[] bounds){
        label.setBounds(bounds[0],bounds[1],bounds[2],bounds[3] );
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

    public void panelAdd() throws IOException{
        JPanel panAdd = new JPanel();
        panAdd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panAdd.setBackground(new Color(250, 240, 230));
        contenu.add(panAdd, "add");
        panAdd.setLayout(null);

        JPanel formulaire = new JPanel();
        formulaire.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        formulaire.setBounds(159, 64, 338, 332);
        panAdd.add(formulaire);
        formulaire.setLayout(null);

        JLabel labIdSalle = new JLabel("Numero de Salle ");
        FormLabel(labIdSalle,83,118);
        formulaire.add(labIdSalle);

        JLabel labIdBloc = new JLabel("Numero de Bloc");
        FormLabel(labIdBloc,140,118);
        formulaire.add(labIdBloc);

        JLabel labIdReservataire = new JLabel("ID du reservataire");
        FormLabel(labIdReservataire,188,118);
        formulaire.add(labIdReservataire);

        JLabel labEvent = new JLabel("Nom Evenement");
        FormLabel(labEvent,244,118);
        formulaire.add(labEvent);

        JLabel labDateEvent = new JLabel("Date Evenement");
        FormLabel(labDateEvent,292,118);
        formulaire.add(labDateEvent);


        addNumSalle = new JComboBox(salles);
        addNumSalle.setBounds(150,85,178,20);
        formulaire.add(addNumSalle);

        addNumBloc = new JComboBox(blocs);
        addNumBloc.setBounds(150,138,178,20);
        formulaire.add(addNumBloc);

        addIdReservataire = new JTextField();
        formTextField(addIdReservataire,189,"add");
        formulaire.add(addIdReservataire);

        addNomEvent = new JTextField();
        formTextField(addNomEvent,242,"add");
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
        boutonAjout.addActionListener(this::addEvent);
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

    public void formTextField(JTextField tf,int y,String namePanel){
        //tf.setBorder(null);
        if(namePanel.equals("add")) tf.setBounds(150,y,178,20);
        if(namePanel.equals("update")) tf.setBounds(126,y,138,20);
        tf.setColumns(10);
    }

    public void FormLabel(JLabel label,int y,int width){
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        label.setBounds(22,y,width,15);
    }

    public void titleLabel(JLabel lb){
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setForeground(new Color(255, 140, 0));
        lb.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        lb.setBounds(35, 11, 318, 42);
    }

    public void panelUpdate(){
        JPanel panUpdate = new JPanel();
        panUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panUpdate.setBackground(new Color(250, 240, 230));
        contenu.add(panUpdate, "update");
        panUpdate.setLayout(null);

        JPanel formulaireUpdate = new JPanel();
        formulaireUpdate.setLayout(null);
        formulaireUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        formulaireUpdate.setBounds(27, 124, 276, 284);
        panUpdate.add(formulaireUpdate);

        JLabel labIdSalle1 = new JLabel("Numero de Salle ");
        FormLabel(labIdSalle1,46,94);
        formulaireUpdate.add(labIdSalle1);

        JLabel labIdBloc1 = new JLabel("Numero de Bloc");
        FormLabel(labIdBloc1,94,106);
        formulaireUpdate.add(labIdBloc1);

        JLabel labIdReservataire1 = new JLabel("ID du reservataire");
        FormLabel(labIdReservataire1,134,94);
        formulaireUpdate.add(labIdReservataire1);

        JLabel labEvent1 = new JLabel("Nom Evenement");
        FormLabel(labEvent1,177,80);
        formulaireUpdate.add(labEvent1);

        JLabel labDateEvent1 = new JLabel("Date Evenement");
        FormLabel(labDateEvent1,214,85);
        formulaireUpdate.add(labDateEvent1);

        updateNumSalle = new JComboBox<>(salles);
        updateNumSalle.setBounds(126,45,138,20);
        formulaireUpdate.add(updateNumSalle);

        updateNumBloc = new JComboBox<>(blocs);
        updateNumBloc.setBounds(126,92,138,20);
        formulaireUpdate.add(updateNumBloc);

        updateIdReservataire = new JTextField();
        formTextField(updateIdReservataire,135,"update");
        formulaireUpdate.add(updateIdReservataire);

        updateNomEvent = new JTextField();
        formTextField(updateNomEvent,175,"update");
        formulaireUpdate.add(updateNomEvent);

        JDateChooser updateDateEvent = new JDateChooser();
        updateDateEvent.setBorder(null);
        updateDateEvent.setBounds(126, 214, 138, 20);
        formulaireUpdate.add(updateDateEvent);


        JScrollPane scrollPaneUpdate = new JScrollPane();
        scrollPaneUpdate.setBounds(338, 77, 298, 331);
        panUpdate.add(scrollPaneUpdate);

        updateTable = new JTable();
        MyTableModel model = new MyTableModel();

        updateTable.setModel(model);
        updateTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int i = updateTable.convertRowIndexToModel(updateTable.getSelectedRow());
                updateNumSalle.setSelectedItem( model.getValueAt(i, 0));
                updateNumBloc.setSelectedItem((String) model.getValueAt(i, 1));
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

    public void panelDelete(){
        JPanel panDelete = new JPanel();
        panDelete.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panDelete.setBackground(new Color(250, 240, 230));
        contenu.add(panDelete, "delete");
        panDelete.setLayout(null);

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
        MyTableModel modelDel = new MyTableModel();
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

    public void panelAutres(){
        JPanel panAutres = new JPanel();
        panAutres.setBackground(new Color(250, 240, 230));
        contenu.add(panAutres, "autres");
        panAutres.setLayout(null);

        JPanel questions = new JPanel();
        questions.setBounds(66, 90, 533, 76);
        panAutres.add(questions);
        questions.setLayout(new CardLayout(0, 0));
        CardLayout cardlayoutQuestions = (CardLayout) questions.getLayout();

        JPanel autresReq = new JPanel();
        autresReq.setBackground(new Color(250, 240, 230));
        questions.add(autresReq, "autres");

        JPanel reservataireDonne = new JPanel();
        reservataireDonne.setBackground(new Color(250, 240, 230));
        questions.add(reservataireDonne, "reservataireDonne");
        reservataireDonne.setLayout(null);

        JLabel lblReservId = new JLabel("Entrez l'ID du reservataire .");
        lblReservId.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblReservId.setBackground(new Color(250, 240, 230));
        lblReservId.setBounds(139, 11, 241, 21);
        reservataireDonne.add(lblReservId);

        JTextField textReserveDonneId = new JTextField();
        textReserveDonneId.setBounds(87, 38, 332, 33);
        reservataireDonne.add(textReserveDonneId);
        textReserveDonneId.setColumns(10);

        JPanel blocDonne = new JPanel();
        blocDonne.setBackground(new Color(250, 240, 230));
        questions.add(blocDonne, "bloc_donne");
        blocDonne.setLayout(null);

        JLabel lblBlocDonne = new JLabel("Entrez le numero du bloc");
        lblBlocDonne.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblBlocDonne.setHorizontalAlignment(SwingConstants.CENTER);
        lblBlocDonne.setBounds(151, 5, 196, 29);
        blocDonne.add(lblBlocDonne);

        JTextField textBlocDonne = new JTextField();
        textBlocDonne.setBounds(91, 36, 338, 29);
        blocDonne.add(textBlocDonne);
        textBlocDonne.setColumns(10);

        JPanel dateDonne = new JPanel();
        dateDonne.setBackground(new Color(250, 240, 230));
        questions.add(dateDonne, "date_donne");
        dateDonne.setLayout(null);

        JLabel lblDateDonne = new JLabel("Selectionnez la date");
        lblDateDonne.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDateDonne.setBounds(170, 5, 191, 28);
        dateDonne.add(lblDateDonne);

        JDateChooser textDatedonne = new JDateChooser();
        textDatedonne.setBounds(135, 36, 247, 28);
        dateDonne.add(textDatedonne);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBounds(66, 177, 533, 274);
        panAutres.add(scrollPane);

        JTable tableLecture = new JTable();
        tableLecture.setBorder(null);
        scrollPane.setViewportView(tableLecture);
        MyTableModel modelLecture = new MyTableModel();
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

    }
    @Override
    public void errorOccurred(String message) {
        System.out.println(message);
    }

    @Override
    public void noError(String message) {
        System.out.println(message);
    }
}


