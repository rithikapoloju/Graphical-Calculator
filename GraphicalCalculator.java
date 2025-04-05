import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalCalculator extends JFrame {

    private JTextField inputField;
    private JTextField resultField;
    private String currentInput = "";
    private double num1 = 0;
    private char operator = ' ';

    public GraphicalCalculator() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input and Result Fields
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        inputField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);
        textPanel.add(inputField);
        textPanel.add(resultField);
        add(textPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "Clear"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new CalculatorOperations());

            if (Character.isDigit(label.charAt(0)) || label.equals(".")) {
                button.setBackground(new Color(255, 228, 225)); // Minimal Pink
                button.setForeground(Color.BLACK);
            } else if (label.equals("Clear")) {
                button.setBackground(Color.RED); // Red Clear Button
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(173, 216, 230)); // Blue Operators
                button.setForeground(Color.BLACK);
            }
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);

        inputField.setBackground(Color.WHITE);
        resultField.setBackground(new Color(240, 240, 240));

        setVisible(true);
    }

    private class CalculatorOperations implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=":
                    calculate();
                    break;
                case "Clear":
                    currentInput = "";
                    inputField.setText("");
                    resultField.setText("");
                    num1 = 0;
                    operator = ' ';
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    if (!currentInput.isEmpty()) {
                        num1 = Double.parseDouble(currentInput);
                        operator = command.charAt(0);
                        currentInput = "";
                        inputField.setText(String.valueOf(num1) + " " + operator);
                    }
                    break;
                default:
                    currentInput += command;
                    inputField.setText(currentInput);
                    break;
            }
        }

        private void calculate() {
            if (!currentInput.isEmpty()) {
                double num2 = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            resultField.setText("Error");
                            return;
                        }
                        break;
                }

                resultField.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicalCalculator::new);
    }
}