package InterfacePackage;

import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class creates the new node's structure to implement the tree
 * 
 */
class Node {///
	Node leftChild;
	Node rightChild;
	int ssn;
	String last;
	String first;
	double sal;
	double hours;

	/**
	 * Construct the tree that has key values of the social security, first and
	 * last name the salary and worked hours.
	 */
	public Node(int ssn, String last, String first, double sal, double hours) {
		this.ssn = ssn;
		this.last = last;
		this.first = first;
		this.sal = sal;
		this.hours = hours;
		leftChild = null;
		rightChild = null;
	}

	/**
	 * Print the node's key values of the tree
	 */
	public String toString() {
		return ssn + "\t" + last + "\t" + first + "\t" + sal + "\t" + hours + "\n";
	}

	/**
	 * Set the left child value
	 * 
	 * @param nl
	 */
	public void setleftChild(Node nl) {
		this.leftChild = nl;
	}

	/**
	 * Search for the key(social security)
	 * 
	 * @param find
	 * @return
	 */
	public boolean search(int find) throws IOException {
		if (find == this.ssn) {
			System.out.println("Found entry " + this.toString());
			java.io.File file = new java.io.File("found.txt");
			try (FileWriter writer = new FileWriter(file, true)) {
				BufferedWriter output = new BufferedWriter(writer);
				output.write(
						this.ssn + "\t" + this.last + "\t" + this.first + "\t" + this.sal + "\t" + this.hours + "\n");
				output.close();
			}
		} else if (find < this.ssn) {
			if (leftChild == null)
				System.out.println("Not found ");
			else
				return leftChild.search(find);
		} else if (find > this.ssn) {
			if (rightChild == null)
				System.out.println("Not found ");
			else
				return rightChild.search(find);
		}
		return false;
	}

	public void search(String find) throws IOException {

		if (this.last.compareToIgnoreCase(find) == 0 || this.first.compareToIgnoreCase(find) == 0) {
			System.out.println("Found entry " + this.toString());
			java.io.File file = new java.io.File("found.txt");
			try (FileWriter writer = new FileWriter(file, true)) {
				BufferedWriter output = new BufferedWriter(writer);
				output.write(
						this.ssn + "\t" + this.last + "\t" + this.first + "\t" + this.sal + "\t" + this.hours + "\n");
				output.close();
			}
		} else {
			if(leftChild != null)
				leftChild.search(find);
			if(rightChild != null)
				rightChild.search(find);
		}

	}

	/**
	 * Check if such key(employee) exists
	 * 
	 * @param find
	 * @return
	 */
	public boolean exists(int find) {
		if (find == this.ssn)
			return true;
		else if (find < this.ssn) {
			if (leftChild == null)
				return false;
			else
				return leftChild.exists(find);
		} else if (find > this.ssn) {
			if (rightChild == null)
				return false;
			else
				return rightChild.exists(find);
		}
		return false;
	}

	/**
	 * removes the selected key(employee)
	 * 
	 * @param remove
	 * @param parent
	 * @return
	 */
	public boolean remove(int remove, Node parent) {
		if (remove < this.ssn) {
			if (leftChild != null)
				return leftChild.remove(remove, this);
			else
				return false;
		} else if (remove > this.ssn) {
			if (rightChild != null)
				return rightChild.remove(remove, this);
			else
				return false;
		} else {
			if (leftChild != null && rightChild != null) {
				this.ssn = rightChild.minValue();
				rightChild.remove(this.ssn, this);
			} else if (parent.leftChild == this) {
				parent.leftChild = (leftChild != null) ? leftChild : rightChild;
			} else if (parent.rightChild == this) {
				parent.rightChild = (leftChild != null) ? leftChild : rightChild;
			}
			return true;
		}
	}

	/**
	 * Checks for minimum key value
	 * 
	 * @return
	 */
	public int minValue() {
		if (leftChild == null)
			return ssn;
		else
			return leftChild.minValue();
	}
}

/**
 * This class creates the tree that will hold the list of employees with the
 * corresponding data.
 * 
 */
class Tree {
	Node root;

	/**
	 * This method adds a new node to the tree.
	 */
	public void addNode(int ssn, String last, String first, double sal, double hours) {
		Node newNode = new Node(ssn, last, first, sal, hours);
		if (root == null) {
			root = newNode;
		} else {
			Node focusNode = root;
			Node parent;
			while (true) {
				parent = focusNode;
				if (ssn < focusNode.ssn) {
					focusNode = focusNode.leftChild;
					if (focusNode == null) {
						parent.leftChild = newNode;
						return;
					}
				} else {
					focusNode = focusNode.rightChild;
					if (focusNode == null) {
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}

	/**
	 * Sort the key values(list of the employees) in a descending order and
	 * writes it in a new file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void inOrderTraverse() throws FileNotFoundException, IOException {
		System.out.println("Calling inorderTraverse\n");
		System.out.print("\nPrinting list\n");
		inorder(root);
	}

	/**
	 * Sort the key values(list of the employees) in a ascending order and
	 * writes it in a new file
	 * 
	 * @param r
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void inorder(Node r) throws FileNotFoundException, IOException {
		if (r != null) {
			java.io.File file = new java.io.File("update1.txt");
			try (FileWriter writer = new FileWriter(file, true)) {
				BufferedWriter output = new BufferedWriter(writer);
				inorder(r.leftChild);
				System.out.print(r);
				output.write(r.ssn + "\t" + r.last + "\t" + r.first + "\t" + r.sal + "\t" + r.hours + "\n");
				removeNode(r.ssn);
				output.close();
				inorder(r.rightChild);
			}
		}
	}

	/**
	 * Find the key value(by Social Security).
	 * 
	 * @param find
	 * @return
	 */
	public boolean findNode(int find) throws IOException {
		if (root == null)
			return false;
		else
			return root.search(find);
	}

	public void findNode(String find) throws IOException {
		if (root == null)
			;
			//return false;
		else
			root.search(find);
	}

	/**
	 * Checks if that key exists
	 * 
	 * @param id
	 * @return
	 */
	public boolean Exist(int id) {
		if (root == null) {
			System.out.println("empty tree");
			return false;
		} else if (root.exists(id) == false) {
			System.out.println("entry doesnt exist ");
			return false;
		} else {
			System.out.println("entry exists ");
			return true;
		}
	}

	/**
	 * Remove from the tree
	 * 
	 * @param remove
	 * @return
	 */
	public boolean removeNode(int remove) {
		if (root == null)
			return false;
		else {
			if (root.ssn == remove) {
				Node newRoot = new Node(remove, "last", "first", remove, remove);
				newRoot.setleftChild(root);
				boolean result = root.remove(remove, newRoot);
				root = newRoot.leftChild;
				return result;
			} else {
				return root.remove(remove, null);
			}
		}
	}

	/**
	 * Creates a new file
	 * 
	 * @throws FileNotFoundException
	 */
	void create() throws FileNotFoundException {
            File f = new File("update1.txt");
            if (f.exists()) {
                try (Scanner inFile = new Scanner(new File("update1.txt"))) {
                    while (inFile.hasNext()) {
                        String line = inFile.nextLine();
                        int ssn = line.substring(0, line.indexOf("\t")).length();
                        int id = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                        line = line.substring(ssn + 1);
                        String name = line.substring(0, line.indexOf("\t"));
                        int lind = line.substring(0, line.indexOf("\t")).length();
                        line = line.substring(lind + 1);
                        String first = line.substring(0, line.indexOf("\t"));
                        int firstind = line.substring(0, line.indexOf("\t")).length();
                        line = line.substring(firstind + 1);
                        double sal = Double.parseDouble(line.substring(0, line.indexOf("\t")));
                        int sall = line.substring(0, line.indexOf("\t")).length();
                        line = line.substring(sall + 1);
                        double hours = Double.parseDouble(line.substring(0));
                        this.addNode(id, name, first, sal, hours);
                        System.out.println("Adding new entry from file");
                    }
            }
                f.delete();
            }
	}
}

/**
 * This class creates the interface that displays the list of existing
 * employees. It allows the user to manipulate the data and make changes if
 * necessary.
 * 
 * @author Alexandr Buga and Nastassia Pincuk
 */
public class JFrame_Table extends javax.swing.JFrame {
	boolean edit = false;

	/**
	 * default construct that initiates the list
	 */
	public JFrame_Table() {
		initComponents();
		jTextFirst.setDocument(new JTextFieldLimit(13));
		jTextLast.setDocument(new JTextFieldLimit(13));
		jTextSocial.setDocument(new JTextFieldLimit(5));
		jTextWage.setDocument(new JTextFieldLimit(5));
		jTextHours.setDocument(new JTextFieldLimit(5));
		jTableEmployees.setModel((new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { jLabelSocial.getText(), jLabelLastName.getText(), jLabelFirstName.getText(),
				jLabelWage.getText(), jLabelHourMonth.getText(), "OverTime(hours)", "BiWeeklySalary" })));
		runTable();
	}

	/**
	 * This subclass limits the user input
	 */
	class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		/**
		 * Construct the limits
		 * 
		 * @param limit
		 */
		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		/**
		 * Limits the input
		 * 
		 * @param limit
		 * @param upper
		 */
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

	/**
	 * checks the input
	 * 
	 * @param offset
	 * @param str
	 * @param attr
	 * @throws BadLocationException
	 */
	public class LimitTextField extends JFrame {
		JTextField textfield1;

		JLabel label1;

		/**
		 * This subclass sets the limit of the input to 5 characters.
		 */
		public void init() {
			setLayout(new FlowLayout());
			label1 = new JLabel("max 5 chars");
			textfield1 = new JTextField(15);
			add(label1);
			add(textfield1);
			textfield1.setDocument(new JTextFieldLimit(5));

			setSize(300, 300);
			setVisible(true);
		}
	}

	/**
	 * This methods checks if the string has any digits
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {

				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jButtonRemove1 = new javax.swing.JButton();
		jPanelMainBackground = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jLabelLogoMeet = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jLabelLogoAt = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabelFirstName = new javax.swing.JLabel();
		jLabelLastName = new javax.swing.JLabel();
		jTextFirst = new javax.swing.JTextField();
		jLabelJohn = new javax.swing.JLabel();
		jTextLast = new javax.swing.JTextField();
		jLabelSmith = new javax.swing.JLabel();
		jLabelSocial = new javax.swing.JLabel();
		jTextSocial = new javax.swing.JTextField();
		jLabel12345 = new javax.swing.JLabel();
		jTextWage = new javax.swing.JTextField();
		jLabelExampleWage = new javax.swing.JLabel();
		jLabelHourMonth = new javax.swing.JLabel();
		jTextHours = new javax.swing.JTextField();
		jLabelWage = new javax.swing.JLabel();
		jLabelExampleHours = new javax.swing.JLabel();
		jButtonAdd = new javax.swing.JButton();
		jButtonEdit = new javax.swing.JButton();
		jButtonRemove = new javax.swing.JButton();
		jPanel7 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTableEmployees = new javax.swing.JTable();
		jLabel14 = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jBtnLogOut = new javax.swing.JButton();
		jLabelWelcomeTitle = new javax.swing.JLabel();
		jPanelBackgroundBtns = new javax.swing.JPanel();
		jPanelCommands = new javax.swing.JPanel();
		jButtonSearch = new javax.swing.JButton();
		jTextBtSearch = new javax.swing.JTextField();
		jLabel55 = new javax.swing.JLabel();
		jPanelPay = new javax.swing.JPanel();
		jBtnPay = new javax.swing.JButton();

		jButtonRemove1.setText("Remove");
		jButtonRemove1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonRemove1ActionPerformed(evt);
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanelMainBackground.setBackground(new java.awt.Color(255, 255, 204));
		jPanelMainBackground.setBorder(javax.swing.BorderFactory.createTitledBorder("DATABASE"));
		jPanelMainBackground.setPreferredSize(new java.awt.Dimension(1200, 700));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Employee Information"));

		jLabelLogoMeet.setFont(new java.awt.Font("Marker Felt", 2, 24)); // NOI18N
		jLabelLogoMeet.setText("Meet");

		jPanel3.setBackground(new java.awt.Color(255, 204, 0));

		jLabelLogoAt.setFont(new java.awt.Font("Marker Felt", 1, 36)); // NOI18N
		jLabelLogoAt.setText("at");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jLabelLogoAt)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addGap(0, 6, Short.MAX_VALUE).addComponent(jLabelLogoAt)));

		jLabel4.setText("Please Enter:");

		jLabelFirstName.setText("First Name");

		jLabelLastName.setText("Last Name");

		jTextFirst.setMaximumSize(new java.awt.Dimension(2, 2));
		jTextFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextFirstActionPerformed(evt);
			}
		});

		jLabelJohn.setText("e.g.  John");

		jLabelSmith.setText("e.g.  Smith");

		jLabelSocial.setText("Social Security");

		jTextSocial.setToolTipText("");

		jLabel12345.setText("e.g.  12345");

		jTextWage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextWageActionPerformed(evt);
			}
		});

		jLabelExampleWage.setText("e.g.  100.00");

		jLabelHourMonth.setText("Hours/month");

		jTextHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextHoursActionPerformed(evt);
			}
		});

		jLabelWage.setText("Wage");

		jLabelExampleHours.setText("e.g. 100.00");

		jButtonAdd.setText("ADD");
		jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonAddActionPerformed(evt);
			}
		});

		jButtonEdit.setText("Edit");
		jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonEditActionPerformed(evt);
			}
		});

		jButtonRemove.setText("Remove");
		jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonRemoveActionPerformed(evt);
			}
		});

		jTableEmployees.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jTableEmployees
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jTableEmployees.setMaximumSize(new java.awt.Dimension(1200, 150));
		jTableEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTableEmployeesMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jTableEmployees);

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addGap(40, 40, 40)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabelSocial).addComponent(jLabel4)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jLabelLastName).addComponent(jLabelFirstName))
								.addComponent(jLabelWage)
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabelHourMonth)
										.addGap(47, 47, 47)
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel2Layout.createSequentialGroup()
														.addComponent(jLabelLogoMeet)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(jTextHours, javax.swing.GroupLayout.DEFAULT_SIZE,
																97, Short.MAX_VALUE)
														.addComponent(jTextWage,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jTextLast,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jTextFirst,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jTextSocial,
																javax.swing.GroupLayout.Alignment.LEADING))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(jPanel2Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addGroup(jPanel2Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabelJohn)
																		.addComponent(jLabelSmith,
																				javax.swing.GroupLayout.Alignment.TRAILING))
																.addComponent(jLabel12345,
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(jLabelExampleWage)
																.addComponent(jLabelExampleHours,
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))))))
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 74,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(28, 28, 28).addComponent(jButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE,
										86, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel7,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(44, 44, 44)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(
								jLabelLogoMeet))
						.addGroup(
								jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel2Layout.createSequentialGroup().addGap(11, 11, 11)
														.addComponent(jLabel4).addGap(18, 18, 18)
														.addGroup(jPanel2Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(jLabelFirstName).addComponent(jTextFirst,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel2Layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jLabelJohn)))
						.addGap(18, 18, 18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelSmith)
								.addComponent(jTextLast, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabelLastName))
						.addGap(18, 18, 18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelSocial)
								.addComponent(jTextSocial, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel12345))
						.addGap(18, 18, 18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextWage, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabelExampleWage).addComponent(jLabelWage))
						.addGap(24, 24, 24)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelHourMonth)
								.addComponent(jTextHours, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabelExampleHours, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(28, 28, 28))
				.addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jPanel4.setBackground(new java.awt.Color(255, 255, 102));
		jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 51)));
		jPanel4.setToolTipText("Welcome");

		jBtnLogOut.setText("Log Out");
		jBtnLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBtnLogOutActionPerformed(evt);
			}
		});

		jLabelWelcomeTitle.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
		jLabelWelcomeTitle.setText("Welcome to Payroll Database");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(
						jPanel4Layout
								.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanel4Layout.createSequentialGroup()
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabelWelcomeTitle).addGap(434, 434, 434)
												.addComponent(jBtnLogOut).addGap(18, 18, 18)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelWelcomeTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 22,
										Short.MAX_VALUE)
						.addComponent(jBtnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
						.addGap(11, 11, 11)));

		jPanelBackgroundBtns.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

		jPanelCommands.setBorder(javax.swing.BorderFactory.createTitledBorder("Commands"));

		jButtonSearch.setText("Search");
		jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonSearchActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanelCommandsLayout = new javax.swing.GroupLayout(jPanelCommands);
		jPanelCommands.setLayout(jPanelCommandsLayout);
		jPanelCommandsLayout
				.setHorizontalGroup(jPanelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelCommandsLayout.createSequentialGroup()
								.addGap(65, 65, 65).addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE,
										86, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(66, Short.MAX_VALUE)));
		jPanelCommandsLayout
				.setVerticalGroup(jPanelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelCommandsLayout.createSequentialGroup()
								.addGap(46, 46, 46).addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE,
										34, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(56, Short.MAX_VALUE)));

		jTextBtSearch.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jTextBtSearch.setText("Enter SSN");
		jTextBtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTextBtSearchMouseClicked(evt);
			}
		});
		jTextBtSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextBtSearchActionPerformed(evt);
			}
		});

		jLabel55.setIcon(new javax.swing.ImageIcon("/Users/alexandrbuga/NetBeansProjects/ProjectFinalCIS146/icons/Screen Shot 2015-05-07 at 9.12.13 AM.png")); // NOI18N
		jLabel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

		jPanelPay.setBackground(new java.awt.Color(204, 204, 204));
		jPanelPay.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

		jBtnPay.setText("Pay");
		jBtnPay.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBtnPayActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanelPayLayout = new javax.swing.GroupLayout(jPanelPay);
		jPanelPay.setLayout(jPanelPayLayout);
		jPanelPayLayout.setHorizontalGroup(jPanelPayLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPayLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jBtnPay,
								javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(66, 66, 66)));
		jPanelPayLayout.setVerticalGroup(jPanelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelPayLayout
						.createSequentialGroup().addGap(24, 24, 24).addComponent(jBtnPay,
								javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanelBackgroundBtnsLayout = new javax.swing.GroupLayout(jPanelBackgroundBtns);
		jPanelBackgroundBtns.setLayout(jPanelBackgroundBtnsLayout);
		jPanelBackgroundBtnsLayout.setHorizontalGroup(
				jPanelBackgroundBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelBackgroundBtnsLayout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabel55).addGap(67, 67, 67))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelBackgroundBtnsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jPanelBackgroundBtnsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jPanelPay, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanelCommands, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jTextBtSearch)).addContainerGap()));
		jPanelBackgroundBtnsLayout.setVerticalGroup(
				jPanelBackgroundBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelBackgroundBtnsLayout.createSequentialGroup().addContainerGap().addComponent(jLabel55)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTextBtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(34, 34, 34)
								.addComponent(jPanelCommands, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(31, 31, 31)
								.addComponent(jPanelPay, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanelMainBackgroundLayout = new javax.swing.GroupLayout(jPanelMainBackground);
		jPanelMainBackground.setLayout(jPanelMainBackgroundLayout);
		jPanelMainBackgroundLayout.setHorizontalGroup(jPanelMainBackgroundLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelMainBackgroundLayout.createSequentialGroup().addContainerGap()
						.addGroup(jPanelMainBackgroundLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanelMainBackgroundLayout.createSequentialGroup()
										.addComponent(jPanelBackgroundBtns, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(743, 743, 743)
						.addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 438,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanelMainBackgroundLayout.setVerticalGroup(
				jPanelMainBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelMainBackgroundLayout.createSequentialGroup()
								.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanelMainBackgroundLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanelBackgroundBtns, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(0, 87, Short.MAX_VALUE))
				.addGroup(jPanelMainBackgroundLayout.createSequentialGroup().addGap(35, 35, 35)
						.addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 416,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelMainBackground,
						javax.swing.GroupLayout.PREFERRED_SIZE, 1530, javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout
						.createSequentialGroup().addComponent(jPanelMainBackground,
								javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 44, Short.MAX_VALUE)));

		getAccessibleContext().setAccessibleName("Payroll Interface");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	public Tree tree = new Tree();

	/**
	 * This methods transfer the data from the file to the table.
	 * 
	 * @param evt
	 */
	public void runTable() {
		DefaultTableModel tm = (DefaultTableModel) jTableEmployees.getModel();
		tm.setRowCount(0);
		File f = new File("update1.txt");
		if (f.exists()) {
			try (Scanner inFile = new Scanner(new File("update1.txt"))) {
				while (inFile.hasNext()) {
					String line = inFile.nextLine();
					int ssn = line.substring(0, line.indexOf("\t")).length();
					int idt = Integer.parseInt(line.substring(0, line.indexOf("\t")));
					line = line.substring(ssn + 1);
					String namet = line.substring(0, line.indexOf("\t"));
					int lind = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(lind + 1);
					String firstt = line.substring(0, line.indexOf("\t"));
					int firstind = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(firstind + 1);
					double salt = Double.parseDouble(line.substring(0, line.indexOf("\t")));
					int sall = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(sall + 1);
					double hourst = Double.parseDouble(line.substring(0));
					System.out.println("Adding new entry to the table");
					double overtime = 0;
					if (hourst > 80) {
						overtime = hourst - 80;
					}
					double weekpay = hourst * salt + overtime * salt * 1.5;
					double paycheck = weekpay * 2;
					tm.addRow(new Object[] { idt, namet, firstt, salt, hourst, overtime, paycheck });
					jTableEmployees.setModel(tm);
				}
				inFile.close();
			} catch (FileNotFoundException ex) {
				Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public String capitalizeFirstLetter(String original) {
		if (original.length() == 0)
			return original;
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	/**
	 * This methods creates an event that add a list to the table
	 * 
	 * @param evt
	 */
	private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonAddActionPerformed
		try {
			int id = Integer.parseInt(jTextSocial.getText().trim());
			String name = jTextLast.getText().trim();
			name = capitalizeFirstLetter(name);
			String first = jTextFirst.getText().trim();
			first = capitalizeFirstLetter(first);
			double sal = Double.parseDouble(jTextWage.getText());
			double hours = Double.parseDouble(jTextHours.getText().trim());

			isNumeric(name);
			isNumeric(first);
			if (!isNumeric(first)) {
				first = null;
			}
			if (!isNumeric(name)) {
				name = null;
			}

			tree.create();

			if (tree.Exist(id) == true) {
				System.out.println("Entry exists");
			} else {
				tree.addNode(id, name, first, sal, hours);
				System.out.println("Adding new entry from console");
			}
			System.out.println("calling inordertraverse");
			tree.inOrderTraverse();

			jTextFirst.setText(null);
			jTextLast.setText(null);
			jTextSocial.setText(null);
			jTextWage.setText(null);
			jTextHours.setText(null);

			jTextFirst.requestFocus();

			runTable();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Social Security, Wage, Hours: Must contain Digits!\n No Digits in First Last Name \n Employee exists!  "
							+ ex);
		}
	}// GEN-LAST:event_jButtonAddActionPerformed

	/**
	 * This methods creates an event that edit a list to the table
	 * 
	 * @param evt
	 */
	private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonEditActionPerformed
		try {
			tree.create();

			int id = Integer.parseInt(jTextSocial.getText().trim());
			String name = jTextLast.getText().trim();
			tree.removeNode(id);
			System.out.println("calling inordertraverse");
			tree.inOrderTraverse();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			int id = Integer.parseInt(jTextSocial.getText().trim());
			String name = jTextLast.getText().trim();
			String first = jTextFirst.getText().trim();
			double sal = Double.parseDouble(jTextWage.getText());
			double hours = Double.parseDouble(jTextHours.getText().trim());

			isNumeric(name);
			isNumeric(first);
			if (!isNumeric(first)) {
				first = null;
			}
			if (!isNumeric(name)) {
				name = null;
			}
			tree.create();

			if (tree.Exist(id) == true) {
				System.out.println("Entry exists");
			} else {
				tree.addNode(id, name, first, sal, hours);
				System.out.println("Adding new entry from console");
			}
			System.out.println("calling inordertraverse");
			tree.inOrderTraverse();
			runTable();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Social Security, Wage, Hours: Must contain Digits!\n No Digits in First Last Name \n Employee exists!  "
							+ ex);
		}

	}// GEN-LAST:event_jButtonEditActionPerformed

	/**
	 * This methods creates an event that removes a list to the table
	 * 
	 * @param evt
	 */
	private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRemoveActionPerformed

		try {
			tree.create();

			int id = Integer.parseInt(jTextSocial.getText().trim());
			String name = jTextLast.getText().trim();

			tree.removeNode(id);
			System.out.println("calling inordertraverse");
			tree.inOrderTraverse();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			DefaultTableModel tm = (DefaultTableModel) jTableEmployees.getModel();
			tm.removeRow(jTableEmployees.getSelectedRow());

			jTableEmployees.setModel(tm);

			jTextFirst.setText(null);
			jTextLast.setText(null);
			jTextSocial.setText(null);
			jTextWage.setText(null);
			jTextHours.setText(null);
			JOptionPane.showMessageDialog(null, "  You have Successfully Removed the Employee!    ");
			jTextFirst.requestFocus();

		} catch (Exception ex) {
		}
	}// GEN-LAST:event_jButtonRemoveActionPerformed

	private void jTextFirstActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFirstActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextFirstActionPerformed

	private void jBtnLogOutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnLogOutActionPerformed

		JFrame_Table add = new JFrame_Table();
		JOptionPane.showMessageDialog(null, "Logged Out");
		JFrameAdmin adm = new JFrameAdmin();
		dispose();
		adm.admin();

	}// GEN-LAST:event_jBtnLogOutActionPerformed

	private void jTextBtSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextBtSearchActionPerformed

	}// GEN-LAST:event_jTextBtSearchActionPerformed

	private void jButtonRemove1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRemove1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jButtonRemove1ActionPerformed

	/**
	 * This methods creates an event that search for the employee with the
	 * specific social security
	 * 
	 * @param evt
	 */
	private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSearchActionPerformed
		try {
			tree.create();
			if (("0123456789").indexOf(jTextBtSearch.getText().trim().charAt(0)) >= 0) {
				int id = Integer.parseInt(jTextBtSearch.getText().trim());
				tree.findNode(id);
			} else if (("abcdefghijklmnopqrtsuvwxyz")
					.indexOf(Character.toLowerCase(jTextBtSearch.getText().trim().charAt(0))) >= 0) {
				String id = jTextBtSearch.getText().trim();
				tree.findNode(id);

			}

			File f = new File("found.txt");
			if (f.exists()) {
				try (Scanner inFile = new Scanner(new File("found.txt"))) {
					String line = inFile.nextLine();

					int ssn = line.substring(0, line.indexOf("\t")).length();
					int idt = Integer.parseInt(line.substring(0, line.indexOf("\t")));
					line = line.substring(ssn + 1);

					String namet = line.substring(0, line.indexOf("\t"));
					int lind = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(lind + 1);

					String firstt = line.substring(0, line.indexOf("\t"));
					int firstind = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(firstind + 1);

					double salt = Double.parseDouble(line.substring(0, line.indexOf("\t")));
					int sall = line.substring(0, line.indexOf("\t")).length();
					line = line.substring(sall + 1);

					double hourst = Double.parseDouble(line.substring(0));

					jTextFirst.setText(firstt.toString());
					jTextLast.setText(namet.toString());
					jTextSocial.setText(Integer.toString(idt).toString());
					jTextWage.setText(Double.toString(salt).toString());
					jTextHours.setText(Double.toString(hourst));

					inFile.close();
					f.delete();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Not found");
			}
			tree.inOrderTraverse();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_jButtonSearchActionPerformed

	/**
	 * This methods creates an event that imports the data from textFields to
	 * the table
	 * 
	 * @param evt
	 */
	private void jTableEmployeesMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableEmployeesMouseClicked
		try {
			DefaultTableModel tm = (DefaultTableModel) jTableEmployees.getModel();

			int fileModify = jTableEmployees.getSelectedRow();
			jTextFirst.setText(tm.getValueAt(fileModify, 2).toString());
			jTextLast.setText(tm.getValueAt(fileModify, 1).toString());
			jTextSocial.setText(tm.getValueAt(fileModify, 0).toString());
			jTextWage.setText(tm.getValueAt(fileModify, 3).toString());
			jTextHours.setText(tm.getValueAt(fileModify, 4).toString());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error No Empty Label" + ex);
		}

	}// GEN-LAST:event_jTableEmployeesMouseClicked

	/**
	 * This methods creates an event that make the TextFields null
	 * 
	 * @param evt
	 */
	private void jTextBtSearchMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTextBtSearchMouseClicked
		jTextBtSearch.setText(null);
		jTextFirst.setText(null);
		jTextLast.setText(null);
		jTextSocial.setText(null);
		jTextWage.setText(null);
		jTextHours.setText(null);

	}// GEN-LAST:event_jTextBtSearchMouseClicked

	/**
	 * This methods creates an event that creates a text file that will display
	 * the pay checks for all the employees.
	 * 
	 * @param evt
	 */
	private void jBtnPayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBtnPayActionPerformed

		try {
			java.io.PrintWriter output = null;
			java.io.File file = new java.io.File("paycheck.txt");
			output = new java.io.PrintWriter(file);

			java.io.PrintWriter total = null;
			java.io.File money = new java.io.File("total.txt");
			total = new java.io.PrintWriter(money);

			double thours = 0;
			double tover = 0;
			double cash = 0;
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48
			File f = new File("update1.txt");
			if (f.exists()) {
				try (Scanner inFile = new Scanner(new File("update1.txt"))) {
					while (inFile.hasNext()) {

						String line = inFile.nextLine();
						int ssn = line.substring(0, line.indexOf("\t")).length();
						int id = Integer.parseInt(line.substring(0, line.indexOf("\t")));
						line = line.substring(ssn + 1);
						String name = line.substring(0, line.indexOf("\t"));
						int lind = line.substring(0, line.indexOf("\t")).length();
						line = line.substring(lind + 1);
						String first = line.substring(0, line.indexOf("\t"));
						int firstind = line.substring(0, line.indexOf("\t")).length();
						line = line.substring(firstind + 1);
						double sal = Double.parseDouble(line.substring(0, line.indexOf("\t")));
						int sall = line.substring(0, line.indexOf("\t")).length();
						line = line.substring(sall + 1);
						double hours = Double.parseDouble(line.substring(0));

						double overtime = 0;
						if (hours > 80) {
							overtime = hours - 80;
						}
						double weekpay = hours * sal + overtime * sal * 1.5;
						double paycheck = weekpay * 2;
						thours = thours + hours;
						tover = tover + overtime;
						cash = cash + paycheck;

						System.out.print("\nPrinting list\n");

						output.write("************************************************************\r\n");
						output.write("*PAYCHECK*" + dateFormat.format(date) + "                              *\r\n");
						output.write("*Employee SSN: " + id + "\t" + "Name: " + name + "\t" + first + "\t"
								+ "           *\r\n");
						output.write("*Total hours worked: " + hours + "\t Overtime: " + overtime + "\t"
								+ "           *\r\n");
						output.write(
								"*Total: $" + weekpay + "\t" + "                                           * \r\n");
						output.write("************************************************************\r\n");
						output.write("");
						output.write("");
						output.write("");
					}

					output.close();

				}
				JOptionPane.showMessageDialog(null, "  Paycheck generated   ");
			}

			total.write("************************************************************\r\n");
			total.write("*Total all employees*" + dateFormat.format(date) + "                   *\r\n");

			total.write("*Total hours worked: " + thours + "\t Overtime: " + tover + "\t" + "   *\r\n");
			total.write("*Total due: $" + cash + "\t" + "                                   * \r\n");
			total.write("************************************************************\r\n");
			total.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(JFrame_Table.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_jBtnPayActionPerformed

	private void jTextWageActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextWageActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextWageActionPerformed

	private void jTextHoursActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextHoursActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextHoursActionPerformed

	/**
	 * This method initiate the interface JFrame_Table by making it visible
	 */
	public static void addTable() {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JFrame_Table.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JFrame_Table.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JFrame_Table.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JFrame_Table.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame_Table add = new JFrame_Table();
				add.pack();
				add.setTitle("Database Interface");
				add.setVisible(true);

			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jBtnLogOut;
	private javax.swing.JButton jBtnPay;
	private javax.swing.JButton jButtonAdd;
	private javax.swing.JButton jButtonEdit;
	private javax.swing.JButton jButtonRemove;
	private javax.swing.JButton jButtonRemove1;
	private javax.swing.JButton jButtonSearch;
	private javax.swing.JLabel jLabel12345;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel55;
	private javax.swing.JLabel jLabelExampleHours;
	private javax.swing.JLabel jLabelExampleWage;
	private javax.swing.JLabel jLabelFirstName;
	private javax.swing.JLabel jLabelHourMonth;
	private javax.swing.JLabel jLabelJohn;
	private javax.swing.JLabel jLabelLastName;
	private javax.swing.JLabel jLabelLogoAt;
	private javax.swing.JLabel jLabelLogoMeet;
	private javax.swing.JLabel jLabelSmith;
	private javax.swing.JLabel jLabelSocial;
	private javax.swing.JLabel jLabelWage;
	private javax.swing.JLabel jLabelWelcomeTitle;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanelBackgroundBtns;
	private javax.swing.JPanel jPanelCommands;
	private javax.swing.JPanel jPanelMainBackground;
	private javax.swing.JPanel jPanelPay;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTableEmployees;
	private javax.swing.JTextField jTextBtSearch;
	private javax.swing.JTextField jTextFirst;
	private javax.swing.JTextField jTextHours;
	private javax.swing.JTextField jTextLast;
	public static javax.swing.JTextField jTextSocial;
	private javax.swing.JTextField jTextWage;
	// End of variables declaration//GEN-END:variables
}
