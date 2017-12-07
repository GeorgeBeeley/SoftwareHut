/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patienthealthservicefinder;

// import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import static patienthealthservicefinder.Application.separator;

/**
 *
 * @author George
 */
public class InputPanel extends JPanel {

    // GUI sectioning panel objects
    JPanel mainPanel;
    JPanel contentPanel;
    JPanel headingPanel;
    JPanel namePanel;
    JPanel firstNamePanel;
    JPanel lastNamePanel;
    JPanel dateOfBirthPanel;
    JPanel dateOfBirthFieldsPanel;
    JPanel postcodeInputPanel;
    JPanel rangeInputPanel;
    JPanel locationPanel;
    JPanel buttonsPanel;

    // headingPanel
    JLabel viewHeading;
    Font headingFont;

//    Image icon;            // icons are not vital but will aid in user experience
    // firstNamePanel objects
    JLabel patientFistNameLabel;
    JTextField patientFirstNameInput;
    JLabel patientLastNameLabel;
    JTextField patientLastNameInput;

    // dateOfBirthPanel objects
    JLabel dateOfBirthLabel;
    JComboBox day;
    JComboBox month;
    JComboBox year;

    // locationPanel objects
    JLabel postcodeLabel;
    JTextField postcodeInput;
    JLabel rangeLabel;
    JComboBox rangeDropDown;
    JComboBox rangeUnitDropDown;
    int unitType;

    // buttonsPanel objects
    JButton saveRecordButton;
    JButton searchButton;

    public InputPanel() {

        createPanels();
        createComponents();
        addComponentsToPanels();
        this.add(mainPanel);

    }

    public String getFirstName() {
        return patientFirstNameInput.getText();
    }

    public String getLastName() {
        return patientLastNameInput.getText();
    }

    public int getDay() {
        return day.getSelectedIndex() + 1;
    }

    public int getMonth() {
        return month.getSelectedIndex() + 1;
    }

    public int getYear() {
        return year.getSelectedIndex() + 1900;
    }

    public String getDOB() {
        int dd = day.getSelectedIndex() + 1;
        int mm = month.getSelectedIndex() + 1;
        int yyyy = year.getSelectedIndex() + 1900;
        String dob = Integer.toString(dd) + "/"
                + Integer.toString(mm) + "/"
                + Integer.toString(yyyy);
        return dob;
    }

    public String getPostCode() {
        return postcodeInput.getText();
    }

    public int getRangeType() {
        return rangeUnitDropDown.getSelectedIndex();
    }

    public int getUnitType() {
        return unitType;
    }

    private void createPanels() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        headingPanel = new JPanel();
        namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.X_AXIS));
        lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.X_AXIS));
        dateOfBirthPanel = new JPanel();
        dateOfBirthFieldsPanel = new JPanel();
        locationPanel = new JPanel();
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        postcodeInputPanel = new JPanel();
        postcodeInputPanel.setLayout(new BoxLayout(postcodeInputPanel, BoxLayout.X_AXIS));
        rangeInputPanel = new JPanel();
        rangeInputPanel.setLayout(new BoxLayout(rangeInputPanel, BoxLayout.X_AXIS));
        buttonsPanel = new JPanel();

    }

    private void createComponents() {

        // headingPanel
        viewHeading = new JLabel("Patient Details");
        headingFont = new Font("Sans Serif", Font.BOLD, 14);
        viewHeading.setFont(headingFont);
        viewHeading.setBorder(new EmptyBorder(10, 0, 10, 0));

        // firstNamePanel
        patientFistNameLabel = new JLabel("First Name");
        patientFistNameLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        patientFirstNameInput = new JTextField();
        patientLastNameLabel = new JLabel("Last Name");
        patientLastNameLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        patientLastNameInput = new JTextField();

        // dateOfBirthPanel
        dateOfBirthLabel = new JLabel("Date of Birth");
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = Integer.toString(i);
        }
        day = new JComboBox(days);
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = Integer.toString(i);
        }
        month = new JComboBox(months);
        String[] years = new String[118];
        for (int i = 1900; i <= 2017; i++) {
            years[i - 1900] = Integer.toString(i);
        }
        year = new JComboBox(years);

        // locationPanel
        postcodeLabel = new JLabel("Postcode");
        postcodeLabel.setBorder(new EmptyBorder(0, 0, 0, 26));
        postcodeInput = new JTextField();
        rangeLabel = new JLabel("Range");
        rangeLabel.setBorder(new EmptyBorder(0, 0, 0, 44));
        String[] distances = {"1", "3", "5", "10"};
        rangeDropDown = new JComboBox(distances);
        rangeDropDown.setBorder(new EmptyBorder(0, 0, 0, 10));
        String[] units = {"Miles", "Kilometers"};
        rangeUnitDropDown = new JComboBox(units);
        rangeUnitDropDown.addActionListener(new MileSelect());
        // buttonsPanel
        saveRecordButton = new JButton("Save Patient");
        searchButton = new JButton("Search");

    }

    private void addComponentsToPanels() {

        // headingPanel
        headingPanel.add(viewHeading);

        // namePanel
        firstNamePanel.add(patientFistNameLabel);
        firstNamePanel.add(patientFirstNameInput);
        firstNamePanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        lastNamePanel.add(patientLastNameLabel);
        lastNamePanel.add(patientLastNameInput);
        lastNamePanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        namePanel.add(firstNamePanel);
        namePanel.add(lastNamePanel);
        namePanel.setBorder(new EmptyBorder(0, 5, 0, 10));

        // dateOfBirthPanel
        dateOfBirthFieldsPanel.add(day);
        dateOfBirthFieldsPanel.add(month);
        dateOfBirthFieldsPanel.add(year);
        dateOfBirthPanel.add(dateOfBirthLabel);
        dateOfBirthPanel.add(dateOfBirthFieldsPanel);

        // locationPanel
        postcodeInputPanel.add(postcodeLabel);
        postcodeInputPanel.add(postcodeInput);
        rangeInputPanel.add(rangeLabel);
        rangeInputPanel.add(rangeDropDown);
        rangeInputPanel.add(rangeUnitDropDown);
        rangeInputPanel.setBorder(new EmptyBorder(5, 0, 0, 5));
        locationPanel.add(postcodeInputPanel);
        locationPanel.add(rangeInputPanel);
        locationPanel.setBorder(new EmptyBorder(0, 5, 0, 10));

        // buttonsPanel
        buttonsPanel.add(saveRecordButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // contentPanel
        contentPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        contentPanel.add(namePanel);
        contentPanel.add(dateOfBirthPanel);
        contentPanel.add(locationPanel);
        contentPanel.add(buttonsPanel);

        // main panel
        mainPanel.add(headingPanel);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(separator);
        mainPanel.add(contentPanel);

    }

    public class MileSelect implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            unitType = rangeUnitDropDown.getSelectedIndex();
        }
    }
}
