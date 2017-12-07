/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patienthealthservicefinder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import softwarehut.SpreadsheetReader;

/**
 *
 * @author George
 */
public class Application {

    static final int FRAME_WIDTH = 1000;
    static final int FRAME_HEIGHT = 600;
    static String tableHeaders0[] = {"Distance", "Name", "Postcode", "Address 1", "Address 2", "Town", "County", "Number"};
    static String tableHeaders1[] = {"Distance", "Name", "Postcode", "Address", "Town", "County", "Number", "Age", "Medium"};
    static String tableHeaders2[] = {"Distance", "Name", "Postcode", "Address 1", "Address 2", "Town", "County"};
    static String tableHeaders3[] = {"Distance", "Name", "Postcode", "Address 1", "Address 2", "Town", "County"};
    static int unitType;

    static JFrame applicationFrame;
    static InputPanel piip;
    static JSeparator separator;
    static OutputPanel rp;

    // input data variables
    static String patientFirstName;
    static String patientLastName;
    static String patientDOB;
    static int dobDay;
    static int dobMonth;
    static int dobYear;
    static String patientPostCode;
    static InfoPanelButtonListener bl;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        applicationFrame = new JFrame("Service Finder");
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.getContentPane().setLayout(
                new BoxLayout(
                        applicationFrame.getContentPane(), BoxLayout.X_AXIS)
        );

        /**
         *
         * Ignore this - wrote this with a different UI and user story in mind.
         * Plus I didn't finish it anyway.
         *
         * 1) add 'PatientInfoInputView' panel to the frame 2) listen for inputs
         * - once all information is added, set active the 'Next' button 3)
         * listen for 'Next' button press 4) on 'Next' click, store patient
         * information in patientInfo variables 5) clear frame and add
         * 'ServiceSelectionView' panel to frame 6) listen for user to toggle
         * select the patient's preferences 7) once at least 1 option is
         * selected and 1 of public / private is selected, set active the
         * 'Search' button 8) listen for 'Search' button press 9)
         *
         */
        piip = new InputPanel();
        addButtonListeners();

        rp = new OutputPanel();

        applicationFrame.add(piip);
        separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(Color.gray);
        applicationFrame.getContentPane().add(separator);
        applicationFrame.add(rp);
        applicationFrame.pack();
//        applicationFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        applicationFrame.setVisible(true);

    }

    static void addButtonListeners() {
        bl = new InfoPanelButtonListener();
        piip.searchButton.addActionListener(bl);
        piip.saveRecordButton.addActionListener(bl);
    }

    static void getPatientInfo() {
        patientFirstName = piip.getFirstName();
        patientLastName = piip.getLastName();
        dobDay = piip.getDay();
        dobMonth = piip.getMonth();
        dobYear = piip.getYear();
        patientDOB = piip.getDOB();
        patientPostCode = piip.getPostCode();
        unitType = piip.getUnitType();
    }

    static class InfoPanelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(piip.searchButton)) {
                getPatientInfo();
                System.out.println(patientDetailsToString());
                try {
                    search();
                } catch (IOException ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (e.getSource().equals(piip.saveRecordButton)) {
                // save record to DB
            }
        }

        private void search() throws IOException {
            SpreadsheetReader theFirst = new SpreadsheetReader(patientPostCode, 0, unitType);
            rp.surgeriesTable.setModel(new DefaultTableModel(theFirst.closestArrayContent, tableHeaders0));
            SpreadsheetReader theSecond = new SpreadsheetReader(patientPostCode, 1, unitType);
            rp.schoolsTable.setModel(new DefaultTableModel(theSecond.closestArrayContent, tableHeaders1));
            SpreadsheetReader theThird = new SpreadsheetReader(patientPostCode, 2, unitType);
            rp.dentistsTable.setModel(new DefaultTableModel(theThird.closestArrayContent, tableHeaders2));
            SpreadsheetReader theForth = new SpreadsheetReader(patientPostCode, 3, unitType);
            rp.opticiansTable.setModel(new DefaultTableModel(theForth.closestArrayContent, tableHeaders3));
        }
    }

    public static String patientDetailsToString() {
        String patientDetails = patientFirstName + "\n"
                + patientLastName + "\n"
                + patientDOB + "\n"
                + patientPostCode + "\n";
        return patientDetails;
    }

}
