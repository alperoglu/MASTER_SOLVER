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

import org.openqa.selenium.By;

import java.awt.Color;
import javax.swing.border.CompoundBorder;
import java.awt.Checkbox;
import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;

public class crossword extends JFrame {

	private JPanel contentPane;
	private Button button;
	private JTextField textField_1;
	ArrayList<JTextField> squares;
	private static ArrayList<String> movies = new ArrayList<String>() {{
	    add("director");
	    add("directors");
	    add("actor");
	    add("actress");
	    add("produce");
	    add("producer");
	    add("screenwriter");
	    add("screen writer");
	    add("screen-writer");
	    add("creator");
	    add("company");
	    add("movie");
	    add("film");
	    add("oscar");
	    add("artist");
	}};
	private static ArrayList<String> music = new ArrayList<String>() {{
	    add("singer");
	    add("writer");
	    add("album");
	    add("albums");
	    add("single");
	    add("hit");
	    add("grammy");
	    add("producer");
	    add("artist");
	    add("composition");
	    add("composer");
	    add("group");
	    add("band");
	    add("label");
	    add("song");
	}};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PuzzleGenerator puzzle = new PuzzleGenerator();
					puzzle.getCluesAndCrossword();
					crossword frame = new crossword(puzzle);
					frame.setVisible(true);
					
					ArrayList<String> downAnswers = new ArrayList<String>(5);
					ArrayList<String> acrossAnswers = new ArrayList<String>(5);
					FileInputStream fstream = new FileInputStream("C:/Users/USER/Desktop/461 proje/project/src/project/answers.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

					String strLine;

					//Read File Line By Line
					boolean encountered = false;
					while ((strLine = br.readLine()) != null)   {
					  // Print the content on the console
						if(!encountered && strLine.length() != 0){
							acrossAnswers.add(strLine.toLowerCase());
						}else if(encountered && strLine.length() != 0){
							downAnswers.add(strLine.toLowerCase());
						}else{
							
						}
						if(strLine.length() == 0)
							encountered = true;
					}

					//Close the input stream
					br.close();
					
					ArrayList<Integer> acrossCount = new ArrayList<Integer>(5);
					ArrayList<Integer> downCount = new ArrayList<Integer>(5);
					
					System.out.println(downAnswers);
					
					System.out.println("Getting the length of the words.");
					
					int counter=0;
					for(int i = 0; i<puzzle.blackPos.length; i++){
						if(i%5 == 0){
							counter =0;
						}
						if(puzzle.blackPos[i] != 1)
							counter++;
						if(i%5 == 4){
							acrossCount.add((int)(i/5), counter);
						}
					}
					
					System.out.println(acrossCount);
					
					counter=0;
					for(int i = 0; i<5; i++){
						counter=0;
						int j=i;
						while(j <= (20+i)){
							if(puzzle.blackPos[j] != 1)
								counter++;
							j=j+5;
						}
						downCount.add(i, counter);
					}
					
					System.out.println(downCount);
					
					ArrayList<Integer> acrossTry = new ArrayList<Integer>(5);
					for(int i = 0; i< 5; i++)
						acrossTry.add(0);
					ArrayList<Integer> downTry = new ArrayList<Integer>(5);
					for(int i = 0; i< 5; i++)
						downTry.add(0);
					
					int[] acrossStart = new int[5];
					int checker = 0;
					for(int i = 0; i<21; i = i + 5){
						checker = i;
						while(puzzle.blackPos[checker] == 1)
							checker++;
						acrossStart[(int)i/5] = checker; 
					}
					
					int[] downStart = new int[5];
					checker = 0;
					for(int i = 0; i<5; i++){
						checker = i;
						while(puzzle.blackPos[checker] == 1)
							checker=checker+5;
						downStart[i] = checker; 
					}

					AnswerFinder af = new AnswerFinder();
					GoogleSearch gs = new GoogleSearch();
					GoogleTranslate gt = new GoogleTranslate();
					ImdbSolver is = new ImdbSolver();
					SongSolver ss = new SongSolver();
					ThesaurusSolver ts = new ThesaurusSolver();
					
					int notFound = 1;//CHANGE
					
					
					while(notFound>0){
						System.out.println("Looking at clues across.");
						for(int i=0; i<puzzle.cuesAcrossList.size(); i++){
							System.out.println("Looking at clue "+ puzzle.cuesAcrossList.get(i).substring(3)) ;
							int clue = Character.getNumericValue(puzzle.cuesAcrossList.get(i).charAt(0));
							int pos=0;
							for(int j=0; j<puzzle.number.length; j++){
								if(puzzle.number[j]==clue){
									pos=j;
									break;
								}
							}
							if(acrossTry.get((int)pos/5) > 10 ){
								continue;
							}
							
							String clueIn = puzzle.cuesAcrossList.get(i).substring(3).replaceAll("[^a-zA-Z]", " ");
							int dub=0;
							
							ArrayList<String> possible = new ArrayList<String>();
							
							possible.addAll(gs.search(clueIn, acrossCount.get((int)pos/5)));
							
							System.out.println("Checking is string contains as to look at thesaurus");
							if(clueIn.toLowerCase().contains("as")){
								dub++;
								String temp = clueIn.replaceAll("\\s+as\\s+", " ");
								possible.addAll(ts.search(temp, acrossCount.get((int)pos/5)));
							}
							
							System.out.println("Checking if clue is about music.");
							boolean isMusic=false;
							String found ="";
							for(int j=0; j<crossword.music.size(); j++){
								
								if(clueIn.contains(crossword.music.get(j))){
									isMusic=true;
									found=crossword.music.get(j);
									break;
								}
							}
							if(isMusic){
								dub++;
								String[] temp =clueIn.split("\\s+");
					        	for(int j=0; j<temp.length; j++){
					        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
					        		possible.addAll(ss.search(temp[j], acrossCount.get((int)pos/5)));
					        		System.out.println("Check is matched word is album, artist, song");
					        		if(found.equals("album") || found.equals("artist")|| found.equals("song"))
						        		possible.addAll(ss.search(temp[j], found.concat("s"), acrossCount.get((int)pos/5)));
					        	}
					        	possible.addAll(ss.search(clueIn, acrossCount.get((int)pos/5)));
							}
							
							System.out.println("Checking if clue is about films.");
							boolean isFilm=false;
							for(int j=0; j<crossword.movies.size(); j++){
								
								if(clueIn.contains(crossword.movies.get(j))){
									isFilm=true;
									break;
								}
							}
							if(isFilm){
								dub++;
								String[] temp =clueIn.split("\\s+");
					        	for(int j=0; j<temp.length; j++){
					        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
					        		possible.addAll(is.search(temp[j], acrossCount.get((int)pos/5)));
					        	}
					        	possible.addAll(is.search(clueIn, acrossCount.get((int)pos/5)));
							}
							
							System.out.println("Checking if clue wants a translation.");
							boolean isTrans = false;
							String lang="english";
							for(int j=0; j<gt.languages.size();j++){
								if(clueIn.toLowerCase().contains(gt.languages.get(j).toLowerCase()) || clueIn.toLowerCase().contains(gt.languages.get(j))){
									isTrans=true;
									lang=gt.languages.get(j).toLowerCase();
									break;
								}
							}
							
							if(isTrans){
								dub++;
								possible.addAll(gt.search(clueIn.replaceAll(lang, ""), lang, acrossCount.get((int)pos/5)));
							}
							
							//ILLEGAL CHECK
							if(dub < 2)
								possible.addAll(af.search(clueIn, acrossCount.get((int)pos/5)));
							
							boolean isPlural=false;
							System.out.println("Check if the answer is asked as plural.");
							String[] temp =clueIn.split("\\s+");
				        	for(int j=0; j<temp.length; j++){
				        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
				        		if(EnglishNoun.isPlural(temp[j])){
				        			isPlural = true;
				        			break;
				        		}	
				        	}
				        	System.out.println("The answer might want plural: " + isPlural);
				        	Set<String> hs = new HashSet<>();
				            hs.addAll(possible);
				            possible.clear();
				            possible.addAll(hs);
				        	boolean isFound = false;
				        	int answer=-1;
				        	System.out.println("Looking if a possible answer is found.");
				        	for(int j=0; j<possible.size(); j++){
				        		if(acrossAnswers.get((int)pos/5).toLowerCase().equals(possible.get(j))){
				        			isFound = true;
				        			answer = j;
				        			break;
				        		}
				        		if(isPlural){
				        			if(acrossAnswers.get((int)pos/5).toLowerCase().concat("s").equals(possible.get(j))){
					        			isFound = true;
					        			answer = j;
					        			break;
					        		}
				        		}
				        		if(isFound)
				        			break;
				        			
				        	}
				        	if(isFound && answer != -1){
				        		int index=0;
				        		System.out.println("The answer is found as " + possible.get(answer) + "(s)");
				        		for(int j=acrossStart[(int) pos/5]; j < (acrossStart[(int) pos/5] + acrossCount.get((int) pos/5)); j++){
				        			frame.squares.get(j).setText(possible.get(answer).toUpperCase().charAt(index) + "");
				        			index++;
				        		}
				        	}
							//System.out.println(clueIn);
						}
						System.out.println("Looking at clues down.");
						for(int i=0; i<puzzle.cuesAcrossList.size(); i++){
							System.out.println("Looking at clue "+ puzzle.cuesDownList.get(i).substring(3)) ;
							int clue = Character.getNumericValue(puzzle.cuesDownList.get(i).charAt(0));
							//System.out.println(clue);
							int pos=0;
							for(int j=0; j<puzzle.number.length; j++){
								if(puzzle.number[j]==clue){
									pos=j;
									break;
								}
							}
							String clueIn = puzzle.cuesDownList.get(i).substring(3).replaceAll("[^a-zA-Z]", " ");
							int dub=0;
							
							ArrayList<String> possible = new ArrayList<String>();
							
							possible.addAll(gs.search(clueIn, acrossCount.get(pos)));
							
							System.out.println("Checking is string contains as to look at thesaurus");
							if(clueIn.toLowerCase().contains("as")){
								dub++;
								String temp = clueIn.replaceAll("\\s+as\\s+", " ");
								possible.addAll(ts.search(temp, acrossCount.get(pos)));
							}
							
							System.out.println("Checking if clue is about music.");
							boolean isMusic=false;
							String found ="";
							for(int j=0; j<crossword.music.size(); j++){
								
								if(clueIn.contains(crossword.music.get(j))){
									isMusic=true;
									found=crossword.music.get(j);
									break;
								}
							}
							if(isMusic){
								dub++;
								String[] temp =clueIn.split("\\s+");
					        	for(int j=0; j<temp.length; j++){
					        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
					        		possible.addAll(ss.search(temp[j], acrossCount.get((int)pos/5)));
					        		System.out.println("Check is matched word is album, artist, song");
					        		if(found.equals("album") || found.equals("artist")|| found.equals("song"))
						        		possible.addAll(ss.search(temp[j], found.concat("s"), acrossCount.get(pos)));
					        	}
					        	possible.addAll(ss.search(clueIn, acrossCount.get(pos)));
							}
							
							System.out.println("Checking if clue is about films.");
							boolean isFilm=false;
							for(int j=0; j<crossword.movies.size(); j++){
								
								if(clueIn.contains(crossword.movies.get(j))){
									isFilm=true;
									break;
								}
							}
							if(isFilm){
								dub++;
								String[] temp =clueIn.split("\\s+");
					        	for(int j=0; j<temp.length; j++){
					        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
					        		possible.addAll(is.search(temp[j], acrossCount.get(pos)));
					        	}
					        	possible.addAll(is.search(clueIn, acrossCount.get(pos)));
							}
							
							System.out.println("Checking if clue wants a translation.");
							boolean isTrans = false;
							String lang="english";
							for(int j=0; j<gt.languages.size();j++){
								if(clueIn.toLowerCase().contains(gt.languages.get(j).toLowerCase()) || clueIn.toLowerCase().contains(gt.languages.get(j))){
									isTrans=true;
									lang=gt.languages.get(j).toLowerCase();
									break;
								}
							}
							
							if(isTrans){
								dub++;
								possible.addAll(gt.search(clueIn.replaceAll(lang, ""), lang, acrossCount.get(pos)));
							}
							
							//ILLEGAL CHECK
							if(dub < 2)
								possible.addAll(af.search(clueIn, acrossCount.get(pos)));
							
							boolean isPlural=false;
							System.out.println("Check if the answer is asked as plural.");
							String[] temp =clueIn.split("\\s+");
				        	for(int j=0; j<temp.length; j++){
				        		temp[j] = temp[j].replaceAll("[^a-zA-Z]", "").toLowerCase();
				        		if(EnglishNoun.isPlural(temp[j])){
				        			isPlural = true;
				        			break;
				        		}	
				        	}
				        	System.out.println("The answer might want plural: " + isPlural);
				        	Set<String> hs = new HashSet<>();
				            hs.addAll(possible);
				            possible.clear();
				            possible.addAll(hs);
				        	boolean isFound = false;
				        	int answer=-1;
				        	System.out.println("Looking if a possible answer is found.");
				        	for(int j=0; j<possible.size(); j++){
				        		if(downAnswers.get(pos).toLowerCase().equals(possible.get(j))){
				        			isFound = true;
				        			answer = j;
				        			break;
				        		}
				        		if(isPlural){
				        			if(downAnswers.get(pos).toLowerCase().concat("s").equals(possible.get(j))){
					        			isFound = true;
					        			answer = j;
					        			break;
					        		}
				        		}
				        		if(isFound)
				        			break;
				        			
				        	}
				        	if(isFound && answer != -1){
				        		int index=0;
				        		System.out.println("The answer is found as " + possible.get(answer) + "(s)");
				        		for(int j=downStart[pos]; j < (downStart[pos] + ((downCount.get(pos)-1)*5))+1; j += 5){
				        			frame.squares.get(j).setText(possible.get(answer).toUpperCase().charAt(index) + "");
				        			index++;
				        		}
				        	}
							//System.out.println(clueIn);
							//System.out.println(pos);
						}
						notFound--;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public crossword(PuzzleGenerator puzzle) {
		
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
			
		squares = new ArrayList<JTextField>(25);
		for(int i=0; i<25; i++){
			JPanel panel_2 = new JPanel();
					
			JTextField textField = new JTextField();
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setFocusTraversalKeysEnabled(false);
			textField.setFont(new Font("Arial Black", Font.BOLD, 22));
			textField.addKeyListener(new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			    	if (textField.getText().length() >= 1 ) // limit textfield to 1 character
			            e.consume();
			    	if((!Character.isAlphabetic(e.getKeyChar()) && e.getKeyChar() != ' '))
			    		e.consume();
			    	if (Character.isLowerCase(e.getKeyChar())) {
			    	      e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
			    	    }
			    }  
			});						
			textField.addMouseListener(new MouseAdapter() {
				boolean isClicked = false;
				@Override
				public void mouseClicked(MouseEvent arg0) {
					for(int i=0; i<squares.size();i++){
						if(puzzle.blackPos[i] != 1)
							squares.get(i).setBackground(Color.WHITE);
					}
					if(isClicked == true){
						int pos = squares.indexOf(textField);
						for(int i=pos%5; i<squares.size(); i++){
							if(puzzle.blackPos[i] != 1 && i%5 == pos%5)
								squares.get(i).setBackground(Color.YELLOW);
						}
						isClicked =false;
						if(puzzle.blackPos[squares.indexOf(textField)] != 1)
							textField.setBackground(Color.ORANGE);
						return;
					}
					int pos = squares.indexOf(textField);
					for(int i=(pos-(pos%5)); i<(pos-(pos%5)+5); i++){
						if(puzzle.blackPos[i] != 1)
							squares.get(i).setBackground(Color.YELLOW);
					}
					isClicked = true;
					if(puzzle.blackPos[squares.indexOf(textField)] != 1)
						textField.setBackground(Color.ORANGE);
					
				}
			});
			if(i==0)
				textField.setBackground(Color.ORANGE);
			if(i>0 && i<5)
				textField.setBackground(Color.YELLOW);	
			
			if(puzzle.blackPos[i] == 1){
				textField.setEditable(false);
				textField.setBackground(Color.BLACK);
			}
			if(puzzle.number[i] != 0){
				JLabel label = new JLabel(puzzle.number[i]+"");
				//if(puzzle.number[i] < 5)
					label.setPreferredSize( new Dimension( 10, 10 ) );
				//else
					//label.setPreferredSize( new Dimension( 8, 10 ) );
				panel_2.add(label);
			}else{
				JLabel label = new JLabel("");
				label.setPreferredSize( new Dimension( 10, 10 ) );
				panel_2.add(label);
			}
			textField.setPreferredSize( new Dimension( 40, 30 ) );
			panel.add(panel_2);
			panel_2.add(textField);
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
			across = across + "" + puzzle.cuesAcrossList.get(i) + "\n";
		}
		
		
		textPane.setText(across);
		textPane.setEditable(false);
		panel_1.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(10, 148, 183, 140);
		String down = "DOWN\n";
		for(int i=0; i<puzzle.cuesDownList.size();i++){
			down = down + "" + puzzle.cuesDownList.get(i) + "\n";
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
