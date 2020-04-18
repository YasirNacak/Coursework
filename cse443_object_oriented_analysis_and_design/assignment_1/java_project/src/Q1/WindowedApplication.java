package Q1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * Class that utilizes Linear Equation Solver and provides the user a GUI for solving linear equations
 * @author Yasir
 */
public class WindowedApplication extends JFrame {
    /**
     * instance of linear equation solver used throughout the lifetime of this program
     */
    private LinearEquationSolverDeluxe linearEquationSolver;

    /**
     * Text area that contains the coefficient input
     */
    private JTextArea _coefficientsTextField;

    /**
     * Text area that contains the results of equations
     */
    private JTextArea _resultsTextField;

    /**
     * Text field that tells the user solution of the equations
     */
    private JTextField _resultTextField;

    /**
     * Constructor that initializes the solver, and does a call to create the window content.
     */
    public WindowedApplication() {
        linearEquationSolver = new LinearEquationSolverDeluxe();

        setTitle("Linear Equation Solver Deluxe!");
        setResizable(false);

        this.getContentPane().add(SetWindowUp());
        this.pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates necessary instances of windowing elements to provide the user a GUI
     * @return a panel that can be added to any window
     */
    private JPanel SetWindowUp() {
        JPanel mainPanel = new JPanel();
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);

        mainPanel.add(CreateInputAreaInfoLabel());
        mainPanel.add(CreateInputArea());
        mainPanel.add(CreateResultsInputAreaInfoLabel());
        mainPanel.add(CreateResultsInputArea());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(CreateMethodSelectionComboBox());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(CreateSolveButton());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(CreateOutputLabel());
        mainPanel.add(CreateOutputInfoArea());

        return mainPanel;
    }

    /**
     * Creates a label that has the information for coefficient enter text area.
     * @return a text field that can be added to any panel
     */
    private JTextField CreateInputAreaInfoLabel() {
        JTextField textAreaInfoLabel = new JTextField("Enter Coefficients:", 40);
        textAreaInfoLabel.setEditable(false);
        return textAreaInfoLabel;
    }

    /**
     * Creates a text area that the user can enter coefficients of the equations
     * @return a text area that can be added to any panel
     */
    private JTextArea CreateInputArea() {
        JTextArea inputArea = new JTextArea(5, 40);
        _coefficientsTextField = inputArea;
        return inputArea;
    }

    /**
     * Creates a label that has the information for results enter text area.
     * @return a text field that can be added to any panel
     */
    private JTextField CreateResultsInputAreaInfoLabel() {
        JTextField textAreaInfoLabel = new JTextField("Enter Results:", 40);
        textAreaInfoLabel.setEditable(false);
        return textAreaInfoLabel;
    }

    /**
     * Creates a text field that the user can enter the results of each equation
     * @return a text area that can be added to any panel
     */
    private JTextArea CreateResultsInputArea() {
        JTextArea inputArea = new JTextArea(1, 40);
        _resultsTextField = inputArea;
        return inputArea;
    }

    /**
     * Cretes a combobox that has equation solving methods and changes the solving behaviour
     * @return a combobox that has two linear equation solving methods that can be added to any panel
     */
    private JComboBox<String> CreateMethodSelectionComboBox() {
        JComboBox comboBox = new JComboBox(new Object[]{"Gaussian Elimination", "Matrix Inversion"});
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int index = comboBox.getSelectedIndex();
                if (index == 0)
                    linearEquationSolver.SetMethod(new GaussianEliminationSolveMethod());
                else if (index == 1) {
                    linearEquationSolver.SetMethod(new MatrixInversionSolveMethod());
                }
            }
        });

        comboBox.setSelectedIndex(0);

        return comboBox;
    }

    /**
     * Creates a button that solves the equations given
     * @return a button that solves equations that can be added to any panel
     */
    private JButton CreateSolveButton() {
        JButton solveButton = new JButton("Solve Equation!");

        solveButton.addActionListener(e -> {
            if (!_coefficientsTextField.getText().trim().equals("")) {
                ArrayList<String> coeffsStr = new ArrayList<>();
                for (String line : _coefficientsTextField.getText().split("\n")) {
                    coeffsStr.add(line.trim());
                }

                double[][] coeffs = Utilities.StringToMatrix(coeffsStr);
                double[] results = Utilities.StringToDoubleArray(_resultsTextField.getText());
                double[] solution = linearEquationSolver.executeMethod(coeffs, results);

                StringBuilder sb = new StringBuilder();

                for(double solVal : solution) {
                    sb.append(solVal).append(" ");
                }

                _resultTextField.setText(sb.toString());
            }
        });

        return solveButton;
    }

    /**
     * Creates a label that contains information about the results text
     * @return a text field that can be added to any panel
     */
    private JTextField CreateOutputLabel() {
        JTextField outputLabel = new JTextField("Result:", 40);
        outputLabel.setEditable(false);
        return outputLabel;
    }

    /**
     * Creates a label that tells the user the result of the equation
     * @return a text field that can be added to any panel
     */
    private JTextField CreateOutputInfoArea() {
        JTextField outputInfoArea = new JTextField(40);
        outputInfoArea.setEditable(false);
        _resultTextField = outputInfoArea;
        return outputInfoArea;
    }
}
