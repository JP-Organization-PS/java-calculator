import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Calculator implements ActionListener {

    boolean isOperatorClicked = false;
    double newValue, oldValue;
    int calculation;

    JFrame jf;
    JLabel displayLabel;
    JButton[] numberButtons = new JButton[10];
    JButton plusButton, minusButton, mulButton, divButton;
    JButton dotButton, equalButton, clearButton, backspaceButton, toggleSignButton;

    public Calculator() {
        jf = new JFrame("Calculator");
        jf.setBounds(0, 0, 500, 600);
        jf.setLayout(null);
        jf.getContentPane().setBackground(Color.BLACK);

        displayLabel = new JLabel();
        displayLabel.setBounds(20, 30, 440, 90);
        displayLabel.setBackground(Color.white);
        displayLabel.setOpaque(true);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setForeground(Color.BLACK);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        jf.add(displayLabel);

        int x = 20, y = 150;
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = createButton(String.valueOf(i), x, y);
            x += 90;
            if (i % 3 == 0) {
                x = 20;
                y += 90;
            }
        }

        numberButtons[0] = createButton("0", 110, 420);
        dotButton = createButton(".", 20, 420);
        equalButton = createButton("=", 200, 420);
        clearButton = createButton("Clear", 290, 420, 170, 70);
        plusButton = createButton("+", 290, 150);
        minusButton = createButton("-", 385, 150);
        mulButton = createButton("x", 290, 240, 170, 70);
        divButton = createButton("/", 290, 330, 170, 70);

        backspaceButton = createButton("\u232B", 20, 510, 110, 40); // Unicode for backspace
        toggleSignButton = createButton("+/-", 140, 510, 110, 40);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String text, int x, int y) {
        return createButton(text, x, y, 70, 70);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.addActionListener(this);
        jf.add(button);
        return button;
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i <= 9; i++) {
            if (source == numberButtons[i]) {
                handleDigitInput(String.valueOf(i));
                return;
            }
        }

        if (source == dotButton) {
            if (!displayLabel.getText().contains(".")) {
                displayLabel.setText(displayLabel.getText() + ".");
            }
        } else if (source == plusButton) {
            prepareOperation(1);
        } else if (source == minusButton) {
            prepareOperation(2);
        } else if (source == mulButton) {
            prepareOperation(3);
        } else if (source == divButton) {
            prepareOperation(4);
        } else if (source == clearButton) {
            displayLabel.setText("");
        } else if (source == equalButton) {
            executeCalculation();
        } else if (source == backspaceButton) {
            String text = displayLabel.getText();
            if (!text.isEmpty()) {
                displayLabel.setText(text.substring(0, text.length() - 1));
            }
        } else if (source == toggleSignButton) {
            String text = displayLabel.getText();
            if (!text.isEmpty()) {
                if (text.startsWith("-")) {
                    displayLabel.setText(text.substring(1));
                } else {
                    displayLabel.setText("-" + text);
                }
            }
        }
    }

    private void handleDigitInput(String digit) {
        if (isOperatorClicked) {
            displayLabel.setText(digit);
            isOperatorClicked = false;
        } else {
            displayLabel.setText(displayLabel.getText() + digit);
        }
    }

    private void prepareOperation(int operationType) {
        try {
            oldValue = Double.parseDouble(displayLabel.getText());
            calculation = operationType;
            isOperatorClicked = true;
        } catch (NumberFormatException ignored) {
        }
    }

    private void executeCalculation() {
        try {
            newValue = Double.parseDouble(displayLabel.getText());
            double result = 0;

            switch (calculation) {
                case 1: result = oldValue + newValue; break;
                case 2: result = oldValue - newValue; break;
                case 3: result = oldValue * newValue; break;
                case 4:
                    if (newValue == 0) {
                        displayLabel.setText("Error");
                        return;
                    }
                    result = oldValue / newValue;
                    break;
            }

            String resultStr = Double.toString(result);
            if (resultStr.endsWith(".0")) {
                resultStr = resultStr.replace(".0", "");
            }
            displayLabel.setText(resultStr);

        } catch (NumberFormatException ignored) {
        }
    }
} 
