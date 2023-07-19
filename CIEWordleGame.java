import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


public class CIEWordleGame implements ActionListener {

    class WordPanel extends JPanel {

        JLabel[] wordColumns = new JLabel[5];

        public WordPanel() {
            this.setLayout(new GridLayout(1, 5));
            this.setBackground(Color.black);
            Border greyBord = BorderFactory.createLineBorder(Color.lightGray);
            for (int i = 0; i < 5; i++) {
                wordColumns[i] = new JLabel();
                wordColumns[i].setHorizontalAlignment(JLabel.CENTER);
                wordColumns[i].setOpaque(true);
                wordColumns[i].setBorder(greyBord);
                wordColumns[i].setFont(new Font("SansSerif Bold", Font.PLAIN, 50));
                wordColumns[i].setForeground(Color.white);
                wordColumns[i].setBackground(Color.black); // not going to work

                this.add(wordColumns[i]);
            }
        }

        public void clearWordPanel() {
            for (int i = 0; i < 5; i++) {
                wordColumns[i].setText("");
            }
        }

        public void setPanelText(String charValue, int index, Color c) {
            this.wordColumns[index].setText(charValue);
            this.wordColumns[index].setBackground(c);
        }
    }

    class UserPanel extends JPanel {
        private JTextField userInput;
        private JButton enter;

        public UserPanel() {
            this.setLayout(new GridLayout(1, 2));
            userInput = new JTextField();
            userInput.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            this.add(userInput);
            enter = new JButton("Enter");
            // enter.setMnemonic(KeyEvent.VK_ENTER); doesn't do anything
            enter.setFont(new Font("Sans Serif", Font.PLAIN, 20));
            this.add(enter);
        }

        public JTextField getUserInput() {
            return userInput;
        }

        public JButton getEnter() {
            return enter;
        }
    }

    class FirstKeyboardPanel extends JPanel {
        JLabel[] firstRow = new JLabel[10];
        String oneString = "qwertyuiop";

        public FirstKeyboardPanel() {
            this.setLayout(new GridLayout(1, 10));
            this.setBackground(Color.black);
            Border greyBord = BorderFactory.createLineBorder(Color.lightGray);
            for (int i = 0; i < 10; i++) {
                firstRow[i] = new JLabel();
                firstRow[i].setText(oneString.substring(i, i + 1));
                firstRow[i].setHorizontalAlignment(JLabel.CENTER);
                firstRow[i].setOpaque(true);
                firstRow[i].setBorder(greyBord);
                firstRow[i].setFont(new Font("SansSerif Bold", Font.PLAIN, 12));
                firstRow[i].setForeground(Color.white);
                firstRow[i].setBackground(Color.gray);
                this.add(firstRow[i]);
            }
        }

        public void setKeyboardOneText(int index, Color c) {
            this.firstRow[index].setBackground(c);
        }
    }

    class SecondKeyboardPanel extends JPanel {
        JLabel[] secondRow = new JLabel[10];
        String twoString = "asdfghjkl";

        public SecondKeyboardPanel() {
            this.setLayout(new GridLayout(1, 9));
            this.setBackground(Color.black);
            Border greyBord = BorderFactory.createLineBorder(Color.lightGray);
            for (int i = 0; i < 9; i++) {
                secondRow[i] = new JLabel();
                secondRow[i].setText(twoString.substring(i, i + 1));
                secondRow[i].setHorizontalAlignment(JLabel.CENTER);
                secondRow[i].setOpaque(true);
                secondRow[i].setBorder(greyBord);
                secondRow[i].setFont(new Font("SansSerif Bold", Font.PLAIN, 12));
                secondRow[i].setForeground(Color.white);
                secondRow[i].setBackground(Color.gray);
                this.add(secondRow[i]);
            }
        }

        public void setKeyboardTwoText(int index, Color c) {
            this.secondRow[index].setBackground(c);
        }

    }

    class ThirdKeyBoardPanel extends JPanel {
        String threeString = "zxcvbnm";
        JLabel[] thirdRow = new JLabel[10];

        public ThirdKeyBoardPanel() {
            this.setLayout(new GridLayout(1, 7));
            this.setBackground(Color.black);
            Border greyBord = BorderFactory.createLineBorder(Color.lightGray);
            for (int i = 0; i < 7; i++) {
                thirdRow[i] = new JLabel();
                thirdRow[i].setText(threeString.substring(i, i + 1));
                thirdRow[i].setHorizontalAlignment(JLabel.CENTER);
                thirdRow[i].setOpaque(true);
                thirdRow[i].setBorder(greyBord);
                thirdRow[i].setFont(new Font("SansSerif Bold", Font.PLAIN, 12));
                thirdRow[i].setForeground(Color.white);
                thirdRow[i].setBackground(Color.gray);
                this.add(thirdRow[i]);
            }
        }

        public void setKeyboardThreeText(int index, Color c) {
            this.thirdRow[index].setBackground(c);
        }
    }

    private JFrame gameFrame;
    private WordPanel[] wordPanelArray = new WordPanel[6];
    private UserPanel up;
    private FirstKeyboardPanel oneP;
    private SecondKeyboardPanel twoP;
    private ThirdKeyBoardPanel threeP;
    private String wordleString;
    private int count = 0;
    private ArrayList<String> fiveDictionary;


    public CIEWordleGame() {
        gameFrame = new JFrame("WordleGame");
        gameFrame.setSize(600, 700);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout(new GridLayout(10, 1));
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);

        createDictionary();

        for (int i = 0; i < 6; i++) {
            wordPanelArray[i] = new WordPanel();
            gameFrame.add(wordPanelArray[i]);
        }
        up = new UserPanel();
        oneP = new FirstKeyboardPanel();
        twoP = new SecondKeyboardPanel();
        threeP = new ThirdKeyBoardPanel();
        up.getEnter().addActionListener(this);
        gameFrame.add(up);
        gameFrame.add(oneP);
        gameFrame.add(twoP);
        gameFrame.add(threeP);
        gameFrame.revalidate();


        wordleString = getWordleString();
        System.out.println("Word for the day: " + wordleString);

    }

    public static void main(String[] args) {
        new CIEWordleGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userWord = this.up.getUserInput().getText();
        if (userWord.length() > 4) {
            if (isWordleWordEqualTo(userWord.trim().toUpperCase(Locale.ROOT))) {
                clearAllPanels();
                JOptionPane.showMessageDialog(null, "You Win!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                return;

            }
            if (count > 5) {
                JOptionPane.showMessageDialog(null, wordleString, "You lose!", JOptionPane.INFORMATION_MESSAGE);
                gameFrame.dispose();
                return;
            }
            count++;
        }
    }

    private void clearAllPanels() {
        for (int i = 0; i <= count; i++) {
            wordPanelArray[i].clearWordPanel();
        }
    }


    private boolean isWordleWordEqualTo(String userWord) {

        String one = "qwertyuiop";
        String two = "asdfghjkl";
        String three = "zxcvbnm";
        ArrayList<String> wordleWordsList = new ArrayList<>();
        for (int i = 0; i < wordleString.length(); i++) {
            wordleWordsList.add(wordleString.substring(i, i + 1)); // might error out of bounds
        }
        String[] userWordsArray = userWord.split("");
        ArrayList<Boolean> wordMatchList = new ArrayList<Boolean>();
        for (int i = 0; i < 5; i++) {
            if (wordleWordsList.contains(userWordsArray[i])) {
                if (wordleWordsList.get(i).equals(userWordsArray[i])) {
                    getActivePanel().setPanelText(userWordsArray[i], i, Color.GREEN.darker());
                    wordMatchList.add(true);
                    if (one.contains(userWordsArray[i])) {
                        oneP.setKeyboardOneText(one.indexOf(userWordsArray[i]), Color.GREEN.darker());
                    }
                    if (two.contains(userWordsArray[i])) {
                        twoP.setKeyboardTwoText(two.indexOf(userWordsArray[i]), Color.GREEN.darker());
                    }
                    if (three.contains(userWordsArray[i])) {
                        threeP.setKeyboardThreeText(three.indexOf(userWordsArray[i]), Color.GREEN.darker());
                    }
                } else {
                    getActivePanel().setPanelText(userWordsArray[i], i, Color.YELLOW.darker());
                    wordMatchList.add(false);
                    if (one.contains(userWordsArray[i])) {
                        oneP.setKeyboardOneText(one.indexOf(userWordsArray[i]), Color.YELLOW.darker());
                    }
                    if (two.contains(userWordsArray[i])) {
                        twoP.setKeyboardTwoText(two.indexOf(userWordsArray[i]), Color.YELLOW.darker());
                    }
                    if (three.contains(userWordsArray[i])) {
                        threeP.setKeyboardThreeText(three.indexOf(userWordsArray[i]), Color.YELLOW.darker());
                    }
                }
            } else {
                getActivePanel().setPanelText(userWordsArray[i], i, Color.GRAY.darker());
                if (one.contains(userWordsArray[i])) {
                    oneP.setKeyboardOneText(one.indexOf(userWordsArray[i]), Color.GRAY.darker());
                }
                if (two.contains(userWordsArray[i])) {
                    twoP.setKeyboardTwoText(two.indexOf(userWordsArray[i]), Color.GRAY.darker());
                }
                if (three.contains(userWordsArray[i])) {
                    threeP.setKeyboardThreeText(three.indexOf(userWordsArray[i]), Color.GRAY.darker());
                }
                wordMatchList.add(false);
            }
        }
        return !wordMatchList.contains(false);

    }

    public WordPanel getActivePanel() {
        return this.wordPanelArray[count];
    }

    public void createDictionary() {
        try {
            Scanner fromFile = new Scanner(new File("fivewords.txt"));
            fiveDictionary = new ArrayList<>();
            for (int i = 0; i < 12478; i++) {
                fiveDictionary.add(fromFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public String getWordleString() {
        try {

            Scanner fromFile = new Scanner(new File("WWords.txt"));
            ArrayList<String> wordList = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                wordList.add(fromFile.nextLine());
            }
            Random r = new Random();
            int position = r.nextInt(wordList.size());

            return wordList.get(position).trim().toUpperCase();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return "FileNotFound";
        }
    }

}
