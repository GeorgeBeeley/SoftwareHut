package softwarehut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author New User
 */
public final class SpreadsheetReader {

    static XSSFSheet sheet;
    static XSSFSheet sheet1;
    static double lat1 = 0;
    static double lon1 = 0;
    static double lat2;
    static double lon2;
    int arrayLength = 21;
    public int[] closestArray = new int[arrayLength]; //plus one so the table space is filled...
    public String[][] closestArrayContent = new String[arrayLength][9];
    static String patientPostcode;

    public SpreadsheetReader(String patientPostcode, int instituteType, int unitType) throws IOException {
        FileInputStream stream = new FileInputStream("./src/softwarehut/Bangor Addresses.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(stream);
        sheet = (XSSFSheet) wb.getSheetAt(0);
        Arrays.fill(closestArray, 0);
        boolean found = false;
        boolean present = true;
        for (int k = 0; !found && present; k++) {   //Determines what position postcode is in and gets associated coords
            if (patientPostcode.equals(sheet.getRow(k).getCell(0).toString())) {
                lat1 = sheet.getRow(k).getCell(1).getNumericCellValue();
                lon1 = sheet.getRow(k).getCell(2).getNumericCellValue();
                found = true;
                if (sheet.getRow(k).getCell(0).toString() != null) {    //Checks to make sure postcode is present
                    present = false;
                }
            }
        }
        distanceArrays(instituteType);
        //this swaps the postcode and the name, for preference
        String tempArray[] = new String[2];
        for (int j = 0; j < arrayLength; j++) {
            tempArray[0] = closestArrayContent[j][1];
            tempArray[1] = closestArrayContent[j][2];
            closestArrayContent[j][1] = tempArray[1];
            closestArrayContent[j][2] = tempArray[0];
        }
        boolean finished = false;
        //Sets any "N/A" cell to null, as it displays as blank, which is nicer
        for (int i = 0; i < arrayLength && !finished; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if (closestArrayContent[i][j].equals("N/A")) {
                        closestArrayContent[i][j] = null;
                    }
                } catch (NullPointerException e) {
                    finished = true;
                }
            }
        }
        finished = false;
        for (int i = 0; !finished && i < arrayLength; i++) {
            try {
                DecimalFormat myFormatter = new DecimalFormat("#0.00");
                if (unitType == 1) {
                    closestArrayContent[i][0] = myFormatter.format(Double.parseDouble(closestArrayContent[i][0]) / 1000f )+" km";
                } else {
                    closestArrayContent[i][0] = myFormatter.format(Double.parseDouble(closestArrayContent[i][0]) / 1609.34f) +" miles";
                }
            } catch (NullPointerException e) {
                finished = true;
            }
            System.out.println(closestArrayContent[i][0] + " " + closestArrayContent[i][1] + " " + closestArrayContent[i][2] + " " + closestArrayContent[i][3] + " " + closestArrayContent[i][4] + " " + closestArrayContent[i][5] + " " + closestArrayContent[i][6] + " " + closestArrayContent[i][7]);
        }
    }

    public void distanceArrays(int type) throws FileNotFoundException, IOException {
        FileInputStream stream1;
        switch (type) {
            case 0:
                stream1 = new FileInputStream("./src/softwarehut/Bangor Doctors.xlsx");
                break;
            case 1:
                stream1 = new FileInputStream("./src/softwarehut/Bangor Schools.xlsx");
                break;
            case 2:
                stream1 = new FileInputStream("./src/softwarehut/Bangor Dentals.xlsx");
                break;
            default:
                stream1 = new FileInputStream("./src/softwarehut/Bangor Opticians.xlsx");
                break;
        }
        XSSFWorkbook wb1 = new XSSFWorkbook(stream1);
        sheet1 = (XSSFSheet) wb1.getSheetAt(0);
        distances();
    }

    public void distances() {
        int dist1 = 0;
        String currentPostcode;
        boolean done;
        boolean found;
        boolean finished = false;
        for (int i = 0; !finished; i++) {
            try {
                currentPostcode = sheet1.getRow(i).getCell(0).toString();
                found = false;
                for (int k = 0; !found; k++) {   //Determines what position postcode is in and gets associated coords
                    if (currentPostcode.equals(sheet.getRow(k).getCell(0).toString())) {
                        lat2 = sheet.getRow(k).getCell(1).getNumericCellValue();
                        lon2 = sheet.getRow(k).getCell(2).getNumericCellValue();
                        found = true;
                        dist1 = (int) distance(lat1, lon1, lat2, lon2);
                    }
                }
                done = false;
                for (int j = 0; j < arrayLength && !done; j++) {
                    if (closestArray[j] > dist1 || closestArray[j] == 0) {
                        for (int l = 19; l >= j; l--) {
                            closestArray[l + 1] = closestArray[l];
                            System.arraycopy(closestArrayContent[l], 0, closestArrayContent[l + 1], 0, 9);
                        }
                        closestArray[j] = dist1;
                        closestArrayContent[j][0] = Integer.toString(dist1);
                        for (int f = 1; f < 9; f++) {
                            closestArrayContent[j][f] = sheet1.getRow(i).getCell(f - 1).toString();
                        }
                        done = true;
                    }
                }
            } catch (NullPointerException e) {
                finished = true;
            }
        }
    }

    public static double distance(double lat1, double lon1, double lat2,
            double lon2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return distance;
    }
}
