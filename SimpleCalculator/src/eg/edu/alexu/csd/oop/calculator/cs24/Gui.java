package eg.edu.alexu.csd.oop.calculator.cs24;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import java.awt.SystemColor;

public class Gui {

	private JFrame frmSimpleCalculator;
	private JTextField formulaInput;
	private JLabel answerLbl;
	
	private Calculator calc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Gui window = new Gui();
			window.frmSimpleCalculator.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		calc = new CalculatorImp();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame();
		
		setMenuBar();
		
		setNumberAndOperationBtns();
				
		setEqualBtn();
		
		setIOFields();
		
		setUndoRedoBtn();
	}
	
	private void setFrame() {
		frmSimpleCalculator = new JFrame();
		frmSimpleCalculator.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Icons/Calculator.png")));
		frmSimpleCalculator.setTitle("Simple Calculator");
		frmSimpleCalculator.setBounds(100, 100, 279, 338);
		frmSimpleCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSimpleCalculator.getContentPane().setLayout(null);
	}
	
	private void setNumberAndOperationBtns() {
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		JButton oneBtn = new JButton("1");
		oneBtn.setBounds(10, 95, 45, 45);
		frmSimpleCalculator.getContentPane().add(oneBtn);
		buttons.add(oneBtn);
		
		JButton twoBtn = new JButton("2");
		twoBtn.setBounds(60, 95, 45, 45);
		frmSimpleCalculator.getContentPane().add(twoBtn);
		buttons.add(twoBtn);
		
		JButton threeBtn = new JButton("3");
		threeBtn.setBounds(109, 95, 45, 45);
		frmSimpleCalculator.getContentPane().add(threeBtn);
		buttons.add(threeBtn);
		
		JButton fourBtn = new JButton("4");
		fourBtn.setBounds(10, 146, 45, 45);
		frmSimpleCalculator.getContentPane().add(fourBtn);
		buttons.add(fourBtn);
		
		JButton fiveBtn = new JButton("5");
		fiveBtn.setBounds(60, 146, 45, 45);
		frmSimpleCalculator.getContentPane().add(fiveBtn);
		buttons.add(fiveBtn);
		
		JButton sixBtn = new JButton("6");
		sixBtn.setBounds(109, 146, 45, 45);
		frmSimpleCalculator.getContentPane().add(sixBtn);
		buttons.add(sixBtn);
		
		JButton sevenBtn = new JButton("7");
		sevenBtn.setBounds(10, 195, 45, 45);
		frmSimpleCalculator.getContentPane().add(sevenBtn);
		buttons.add(sevenBtn);
		
		JButton eightBtn = new JButton("8");
		eightBtn.setBounds(60, 195, 45, 45);
		frmSimpleCalculator.getContentPane().add(eightBtn);
		buttons.add(eightBtn);
		
		JButton nineBtn = new JButton("9");
		nineBtn.setBounds(109, 195, 45, 45);
		frmSimpleCalculator.getContentPane().add(nineBtn);
		buttons.add(nineBtn);
		
		JButton zeroBtn = new JButton("0");
		zeroBtn.setBounds(60, 244, 94, 45);
		frmSimpleCalculator.getContentPane().add(zeroBtn);
		buttons.add(zeroBtn);
		
		JButton dotBtn = new JButton(".");
		dotBtn.setBounds(10, 244, 45, 45);
		frmSimpleCalculator.getContentPane().add(dotBtn);
		buttons.add(dotBtn);
				
		JButton addBtn = new JButton("+");
		addBtn.setBounds(159, 95, 45, 45);
		frmSimpleCalculator.getContentPane().add(addBtn);
		buttons.add(addBtn);
		
		JButton subBtn = new JButton("-");
		subBtn.setBounds(159, 146, 45, 45);
		frmSimpleCalculator.getContentPane().add(subBtn);
		buttons.add(subBtn);
		
		JButton mulBtn = new JButton("*");
		mulBtn.setBounds(159, 195, 45, 45);
		frmSimpleCalculator.getContentPane().add(mulBtn);
		buttons.add(mulBtn);
		
		JButton divBtn = new JButton("/");
		divBtn.setBounds(159, 244, 45, 45);
		frmSimpleCalculator.getContentPane().add(divBtn);
		buttons.add(divBtn);
		
		for(int i = 0; i < buttons.size(); i++) {
			JButton b = buttons.get(i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int pos = formulaInput.getCaretPosition();
					formulaInput.setText(setText(formulaInput.getText(), b.getText(), pos));
					formulaInput.setCaretPosition(pos+1);
				}
			});
		}
	}
	
	private void setEqualBtn() {
		JButton equalBtn = new JButton("=");
		equalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.input(formulaInput.getText());
				if((calc.current() != null) && (calc.current().equals(formulaInput.getText()))) {
					answerLbl.setText(calc.getResult());
				}else {
					answerLbl.setText("Invalid Input!");
				}
			}
		});
		equalBtn.setBounds(208, 195, 45, 94);
		frmSimpleCalculator.getContentPane().add(equalBtn);
	}
	
	private void setIOFields() {
		formulaInput = new JTextField();
		formulaInput.setBounds(10, 35, 243, 25);
		frmSimpleCalculator.getContentPane().add(formulaInput);
		formulaInput.setColumns(10);
		
		answerLbl = new JLabel("");
		answerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		answerLbl.setBounds(10, 65, 243, 20);
		frmSimpleCalculator.getContentPane().add(answerLbl);
	}
	
	private void setUndoRedoBtn() {
		JButton undoBtn = new JButton("");
		undoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = calc.prev();
				if((s != null) && (s.length() != 0)) {
					formulaInput.setText(s);
					answerLbl.setText(calc.getResult());
				}else {
					answerLbl.setText("No previous operations!");
				}
			}
		});
		undoBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/undo.png")));
		undoBtn.setBounds(208, 95, 45, 45);
		frmSimpleCalculator.getContentPane().add(undoBtn);
		
		JButton redoBtn = new JButton("");
		redoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = calc.next();
				if((s != null) && (s.length() != 0)) {
					formulaInput.setText(s);
					answerLbl.setText(calc.getResult());
				}else {
					answerLbl.setText("No more saved operations!");
				}
			}
		});
		redoBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/redo.png")));
		redoBtn.setBounds(208, 146, 45, 45);
		frmSimpleCalculator.getContentPane().add(redoBtn);
	}
	
	private void setMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.activeCaption);
		menuBar.setBounds(0, 0, 263, 24);
		frmSimpleCalculator.getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setHorizontalAlignment(SwingConstants.CENTER);
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(fileMenu);
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.save();
			}
		});
		saveMenuItem.setIcon(new ImageIcon(Gui.class.getResource("/Icons/save.png")));
		fileMenu.add(saveMenuItem);
		
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.load();
				String s = calc.current();
				formulaInput.setText(s);
				if(s != null) {
					answerLbl.setText(calc.getResult());
				}else {
					answerLbl.setText("File is empty!");
				}
			}
		});
		loadMenuItem.setIcon(new ImageIcon(Gui.class.getResource("/Icons/load.png")));
		fileMenu.add(loadMenuItem);
	}
	
	public String setText(String s, String added, int index) {
		return s.substring(0, index) + added + s.substring(index);
	}
}
