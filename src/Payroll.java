import InterfacePackage.*;

/**
 * The Payroll class is an application program that helps a fictional company "Meet At"
 * to do the payrolls for the list of the employees. Data is extracted from a text file, 
 * then processed by the manager, and finally, stored in a new text file document. 
 * The employees paychecks are available in a file to be printed out.
 * @version 1.1 2015-12-13
 */
public class Payroll {
    
    /**
     * This main method initiates the application program and ends it up.
     * @param args 
     */
    public static void main(String args[]) {
        JFrameAdmin adm = new JFrameAdmin(); 
        adm.admin();
    }
}
