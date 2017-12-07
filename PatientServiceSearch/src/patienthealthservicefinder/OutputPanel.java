/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patienthealthservicefinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import static patienthealthservicefinder.Application.separator;

/**
 *
 * @author George
 */
public class OutputPanel extends JPanel {

    final static int PANEL_WIDTH = 700;
    final static int PANEL_HEIGHT = 500;

    // GUI sectioning panel objects
    JPanel mainPanel;
    JPanel headingPanel;
    JPanel contentPanel;
    JPanel filterPanel;
    JPanel dropDownPanel;
    JPanel radioButtonsPanel;
    JPanel tabbedPanesPanel;
    JPanel schools;
    JPanel dentists;
    JPanel opticians;

    // heading panel
    JLabel heading;
    Font headingFont;

    // filterPanel
    JLabel orderLabel;
    JComboBox orderDropDown;
    ButtonGroup pubPrivRadio;
    JRadioButton rPublic;
    JRadioButton rPrivate;
    JRadioButton rAll;

    // tabbedPanesPanel
    JTabbedPane resultsTabs;
    JTable surgeriesTable;
    JTable schoolsTable;
    JTable dentistsTable;
    JTable opticiansTable;

    public OutputPanel() throws IOException {

        createPanels();
        createComponents();
        addComponentsToPanels();
        this.add(mainPanel);

    }

    private void createPanels() {

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        headingPanel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        filterPanel = new JPanel();
        filterPanel.setLayout(new BorderLayout());
        filterPanel.setBorder(new EmptyBorder(0, 0, 5, 10));
        dropDownPanel = new JPanel();
        dropDownPanel.setLayout(new BoxLayout(dropDownPanel, BoxLayout.X_AXIS));
        dropDownPanel.setPreferredSize(new Dimension(200, 15));
        dropDownPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        radioButtonsPanel = new JPanel();
        radioButtonsPanel.setLayout(new BoxLayout(radioButtonsPanel, BoxLayout.X_AXIS));
        radioButtonsPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        tabbedPanesPanel = new JPanel();
        tabbedPanesPanel.setLayout(new BorderLayout());

        schools = new JPanel();
        dentists = new JPanel();
        opticians = new JPanel();

    }

    private void createComponents() throws IOException {

        heading = new JLabel("Search Results");
        heading.setBorder(new EmptyBorder(10, 0, 10, 0));
        headingFont = new Font("Sans Serif", Font.BOLD, 15);
        heading.setFont(headingFont);
        orderLabel = new JLabel("Order by:");
        orderLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        String[] orderOptions = {"Distance", "Name", "Postcode"};
        orderDropDown = new JComboBox(orderOptions);
        orderDropDown.setPreferredSize(new Dimension(40, 40));
        pubPrivRadio = new ButtonGroup();
        rPublic = new JRadioButton("Public", false);
        rPrivate = new JRadioButton("Private", false);
        rAll = new JRadioButton("All", true);
        pubPrivRadio.add(rAll);
        pubPrivRadio.add(rPublic);
        pubPrivRadio.add(rPrivate);
        resultsTabs = new JTabbedPane(JTabbedPane.TOP);
        surgeriesTable = new JTable();
        schoolsTable = new JTable();
        dentistsTable = new JTable();
        opticiansTable = new JTable();
    }

    private void addComponentsToPanels() {

        // headingPanel
        headingPanel.add(heading);

        // filterPanel
        dropDownPanel.add(orderLabel);
        dropDownPanel.add(orderDropDown);
        radioButtonsPanel.add(rAll);
        radioButtonsPanel.add(rPublic);
        radioButtonsPanel.add(rPrivate);
        filterPanel.add(dropDownPanel, BorderLayout.WEST);
        filterPanel.add(radioButtonsPanel, BorderLayout.EAST);

        // tabbedPanesPanel
        resultsTabs.add(new JScrollPane(surgeriesTable));
        resultsTabs.add(new JScrollPane(schoolsTable));
        resultsTabs.add(new JScrollPane(dentistsTable));
        resultsTabs.add(new JScrollPane(opticiansTable));
        resultsTabs.setTabComponentAt(0, new JLabel("GPs"));
        resultsTabs.setTabComponentAt(1, new JLabel("Schools"));
        resultsTabs.setTabComponentAt(2, new JLabel("Dentists"));
        resultsTabs.setTabComponentAt(3, new JLabel("Opticians"));
        tabbedPanesPanel.add(resultsTabs);
        tabbedPanesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // contentPanel
        contentPanel.add(filterPanel, BorderLayout.PAGE_START);
        contentPanel.add(tabbedPanesPanel, BorderLayout.CENTER);

        mainPanel.add(headingPanel);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(separator);
        mainPanel.add(contentPanel);

    }

}
