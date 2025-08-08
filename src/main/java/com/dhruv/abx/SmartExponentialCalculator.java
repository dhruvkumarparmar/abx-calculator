package com.dhruv.abx;

import com.dhruv.abx.exception.CalculatorException;
import com.dhruv.abx.exception.InvalidInputException;
import com.dhruv.abx.exception.NegativeBaseException;
import com.dhruv.abx.exception.OverflowException;
import com.dhruv.abx.math.AbxCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SmartExponentialCalculator extends JFrame {

    private JTextField aField;
    private JTextField bField;
    private JTextField xField;
    private JLabel resultLabel;

    public SmartExponentialCalculator() {
        setTitle("Exponential Calculator — ab^x (v" + Version.VERSION + ")");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(580, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Compute ab^x");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        JLabel aLabel = new JLabel("a (coefficient):");
        aLabel.setDisplayedMnemonic(KeyEvent.VK_A);
        aLabel.setFont(font);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(aLabel, gbc);

        aField = new JTextField();
        aField.setFont(font);
        aField.getAccessibleContext().setAccessibleName("Coefficient a");
        aField.getAccessibleContext().setAccessibleDescription("Enter a real number for coefficient a");
        aLabel.setLabelFor(aField);
        gbc.gridx = 1;
        panel.add(aField, gbc);

        JLabel bLabel = new JLabel("b (base > 0):");
        bLabel.setDisplayedMnemonic(KeyEvent.VK_B);
        bLabel.setFont(font);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(bLabel, gbc);

        bField = new JTextField();
        bField.setFont(font);
        bField.getAccessibleContext().setAccessibleName("Base b");
        bField.getAccessibleContext().setAccessibleDescription("Enter a positive real number for base b");
        bLabel.setLabelFor(bField);
        gbc.gridx = 1;
        panel.add(bField, gbc);

        JLabel xLabel = new JLabel("x (exponent):");
        xLabel.setDisplayedMnemonic(KeyEvent.VK_X);
        xLabel.setFont(font);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(xLabel, gbc);

        xField = new JTextField();
        xField.setFont(font);
        xField.getAccessibleContext().setAccessibleName("Exponent x");
        xField.getAccessibleContext().setAccessibleDescription("Enter a real number for exponent x");
        xLabel.setLabelFor(xField);
        gbc.gridx = 1;
        panel.add(xField, gbc);

        JButton calcBtn = new JButton("Calculate");
        calcBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calcBtn.setBackground(new Color(0, 123, 255));
        calcBtn.setForeground(new Color(34, 34, 102));
        calcBtn.setMnemonic(KeyEvent.VK_C);
        calcBtn.addActionListener(this::onCalculate);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(calcBtn, gbc);

        resultLabel = new JLabel("Result will appear here");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(new Color(34, 34, 102));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(resultLabel, gbc);

        // Enter key triggers calculation
        Action computeAction = new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { onCalculate(e); }
        };
        aField.addActionListener(computeAction);
        bField.addActionListener(computeAction);
        xField.addActionListener(computeAction);

        setContentPane(panel);
    }

    private void onCalculate(ActionEvent e) {
        try {
            String aText = aField.getText().trim();
            String bText = bField.getText().trim();
            String xText = xField.getText().trim();
            if (aText.isEmpty() || bText.isEmpty() || xText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double a = Double.parseDouble(aText);
            double b = Double.parseDouble(bText);
            double x = Double.parseDouble(xText);

            double result = AbxCalculator.compute(a, b, x);
            resultLabel.setText("Result: " + result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format: enter numeric values only.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (NegativeBaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Base Error", JOptionPane.ERROR_MESSAGE);
        } catch (OverflowException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Overflow", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (CalculatorException ex) {
            JOptionPane.showMessageDialog(this, "Unexpected calculator error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SmartExponentialCalculator().setVisible(true));
    }
}
