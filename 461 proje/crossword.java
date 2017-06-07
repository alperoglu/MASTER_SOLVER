package project;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import java.awt.Checkbox;
import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTextPane;

public class crossword extends JFrame {

	private JPanel contentPane;
	private Button button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					crossword frame = new crossword();
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
	public crossword() {
		
		PuzzleGenerator puzzle = new PuzzleGenerator();
		puzzle.getCluesAndCrossword();
		
		ArrayList<Character> answers = new ArrayList<Character>(25); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 300, 250);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(new GridLayout(5, 5, 0, 0));
		
		
		ArrayList<TextField> squares = new ArrayList<TextField>(25);
		for(int i=0; i<25; i++){
			TextField textField = new TextField();
			textField.setFont(new Font("Arial Black", Font.BOLD, 22));
			textField.addKeyListener(new KeyAdapter() {
			    public void keyTyped(KeyEvent e) { 
			        if (textField.getText().length() >= 1 ) // limit textfield to 3 characters
			            e.consume(); 
			    }  
			});
			if(puzzle.blackPos[i] == 1){
				textField.setEditable(false);
				textField.setBackground(Color.BLACK);
			}
			
			panel.add(textField);
			squares.add(i, textField);
		}
				
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(315, 5, 272, 299);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 0, 183, 137);
		String across = "ACROSS\n";
		for(int i=0; i<puzzle.cuesAcrossList.size();i++){
			across = across + ""+ puzzle.cuesAcrossList.get(i) + "\n";
		}
		
		textPane.setText(across);
		textPane.setEditable(false);
		panel_1.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(10, 148, 183, 140);
		String down = "DOWN\n";
		for(int i=0; i<puzzle.cuesDownList.size();i++){
			down = down + ""+ puzzle.cuesDownList.get(i) + "\n";
		}
		
		textPane_1.setText(down);
		textPane_1.setEditable(false);
		panel_1.add(textPane_1);
		
		button = new Button("Submit");
		button.setBounds(104, 269, 78, 35);
		contentPane.add(button);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for(int i=0; i<25; i++){
					if(squares.get(i).getText().length()>0)
						answers.add(i, squares.get(i).getText().charAt(0));
					else
						answers.add(i, '1');
				}
				
				String output = "";
				for(int i=0; i<answers.size();i++){
					if(answers.get(i) == '1')
						output = output + "| ";
					else
						output = output + "|" + answers.get(i);
					if(i%5 == 4)
						output = output+"|\n";
				}
				System.out.println(output);
			}
		});

	}
}
