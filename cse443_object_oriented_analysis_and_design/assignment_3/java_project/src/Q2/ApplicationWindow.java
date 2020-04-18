package Q2;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * Application window that give user access to configure settings of the
 * AddAndDFT calculation. Also provides user the interface to start/cancel/restart
 * a calculation.
 * @author Yasir
 */
public class ApplicationWindow {
    /**
     * Frame that contains all the GUI
     */
    private static JFrame _frame;

    /**
     * Panel that sticks to the frame and holds the GUI elements
     */
    private static JPanel _panel;

    /**
     * Button that starts the calculation
     */
    private static JButton _calculateButton;

    /**
     * Button that stops the calculation
     */
    private static JButton _cancelButton;

    /**
     * Button that first cancels the calculation then starts it again
     */
    private static JButton _restartButton;

    /**
     * Slider that the user can utilize to adjust the number of threads involved in the calculation
     */
    private static JSlider _threadCountSlider;

    /**
     * Slider that the user can utilize the size of each dimension of each input matrix
     */
    private static JSlider _matrixSizeSlider;

    /**
     * Label that indicates the function of the thread count slider
     */
    private static JLabel _threadCountLabel;

    /**
     * Label that indicates the function of the matrix size slider
     */
    private static JLabel _matrixSizeLabel;

    /**
     * Label that indicates the method of parallelization method
     */
    private static JLabel _parallelizationMethodLabel;

    /**
     * Label that shows the current status of the overall application
     */
    private static JLabel _statusLabel;

    /**
     * Radio button that the user can utilize to choose monitors as the parallelization method
     */
    private static JRadioButton _monitorRadioButton;

    /**
     * Radio button that the user can utilize to choose mutexes as the parallelization method
     */
    private static JRadioButton _mutexRadioButton;

    /**
     * Group if radio buttons for parallelization methods so the user can select only one at a time
     */
    private static ButtonGroup _radioButtonGroup;

    /**
     * Instance of the calculator that this GUI uses
     */
    private AddAndDFT _addAndDft;

    /**
     * Constructor that creates the frame, creates the inner elements and lays them down in a structured manner
     */
    public ApplicationWindow() {
        _frame = new JFrame();
        _frame.setTitle("Threads Inc.");

        _panel = new JPanel();
        _panel.setLayout(new FlowLayout());

        _radioButtonGroup = new ButtonGroup();

        AddMatrixSizeLabel();
        AddMatrixSizeSlider();
        AddSeparator();
        AddThreadCountLabel();
        AddThreadCountSlider();
        AddSeparator();
        AddParallelizationMethodLabel();
        AddMonitorRadioButton();
        AddMutexRadioButton();
        AddSeparator();
        AddCalculateButton();
        AddCancelButton();
        AddRestartButton();
        AddSeparator();
        AddStatusLabel();

        _frame.add(_panel);
        _frame.pack();
        _frame.setSize(400, 300);
        _frame.setLocationRelativeTo(null);
        _frame.setResizable(false);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Shows the constructed window
     */
    public void Show() {
        _frame.setVisible(true);
    }

    /**
     * Starts the AddAndDFT calculation
     * @param statusText Receives what to update in the status bar. Can alternate between Calculating and Re-Calculating
     */
    private void StartCalculation(String statusText) {
        int threads = (int)Math.pow(Math.pow(2, _threadCountSlider.getValue()), 2);
        int matrixSize = (int)Math.pow(2, _matrixSizeSlider.getValue() + 8);
        System.out.println("Thread Count: " + threads);
        System.out.println("Matrix Size: " + matrixSize);

        if(_radioButtonGroup.isSelected(_monitorRadioButton.getModel())) {
            System.out.println("Selected Method: Monitor");
            _addAndDft = new AddAndDFTMonitor(threads, matrixSize);
        }
        else if (_radioButtonGroup.isSelected(_mutexRadioButton.getModel())) {
            System.out.println("Selected Method: Mutex");
            _addAndDft = new AddAndDFTMutex(threads, matrixSize);
        }

        _statusLabel.setText("Status: " + statusText + "...");

        Thread calcThread = new Thread(() -> {
            _addAndDft.Calculate();
        });

        calcThread.start();
    }

    /**
     * Creates and adds the calculate button to the window
     */
    private void AddCalculateButton() {
        _calculateButton = new JButton("Calculate");
        _calculateButton.addActionListener(e -> {
            _calculateButton.setEnabled(false);
            _cancelButton.setEnabled(true);
            _restartButton.setEnabled(true);

            StartCalculation("Calculating");
        });
        _calculateButton.setPreferredSize(new Dimension(120, 25));
        _panel.add(_calculateButton);
    }

    /**
     * Creates and adds the cancel button to the window
     */
    private void AddCancelButton() {
        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(e -> {
            _calculateButton.setEnabled(true);
            _cancelButton.setEnabled(false);
            _restartButton.setEnabled(false);
            _addAndDft.Cancel();
        });
        _cancelButton.setPreferredSize(new Dimension(120, 25));
        _cancelButton.setEnabled(false);
        _panel.add(_cancelButton);
    }

    /**
     * Creates and adds the restart button to the window
     */
    private void AddRestartButton() {
        _restartButton = new JButton("Restart");
        _restartButton.addActionListener(e -> {
            _addAndDft.Cancel();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            _calculateButton.setEnabled(false);
            _cancelButton.setEnabled(true);
            _restartButton.setEnabled(true);

            StartCalculation("Re-Calculating");
        });
        _restartButton.setPreferredSize(new Dimension(120, 25));
        _restartButton.setEnabled(false);
        _panel.add(_restartButton);
    }

    /**
     * Creates and adds the thread count slider to the window
     */
    private void AddThreadCountSlider() {
        _threadCountSlider = new JSlider(JSlider.HORIZONTAL, 0, 4, 0);
        _threadCountSlider.setMajorTickSpacing(4);
        _threadCountSlider.setMinorTickSpacing(1);
        _threadCountSlider.setPaintTicks(true);
        _threadCountSlider.setPaintLabels(true);
        _threadCountSlider.setPreferredSize(new Dimension(360, 50));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("1"));
        labelTable.put(1, new JLabel("2"));
        labelTable.put(2, new JLabel("4"));
        labelTable.put(3, new JLabel("8"));
        labelTable.put(4, new JLabel("16"));
        _threadCountSlider.setLabelTable(labelTable);

        _panel.add(_threadCountSlider);
    }

    /**
     * Creates and adds the matrix size slider to the window
     */
    private void AddMatrixSizeSlider() {
        _matrixSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
        _matrixSizeSlider.setMajorTickSpacing(2);
        _matrixSizeSlider.setMinorTickSpacing(1);
        _matrixSizeSlider.setPaintTicks(true);
        _matrixSizeSlider.setPaintLabels(true);
        _matrixSizeSlider.setPreferredSize(new Dimension(360, 50));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("256"));
        labelTable.put(1, new JLabel("512"));
        labelTable.put(2, new JLabel("1024"));
        labelTable.put(3, new JLabel("2048"));
        _matrixSizeSlider.setLabelTable(labelTable);

        _panel.add(_matrixSizeSlider);
    }

    /**
     * Creates and adds the thread count label to the window
     */
    private void AddThreadCountLabel() {
        _threadCountLabel = new JLabel("Thread Count Per Dimension");
        _panel.add(_threadCountLabel);
    }

    /**
     * Creates and adds the matrix size label to the window
     */
    private void AddMatrixSizeLabel() {
        _matrixSizeLabel = new JLabel("Matrix Size Per Dimension");
        _panel.add(_matrixSizeLabel);
    }

    /**
     * Creates and adds the parallelization method label to the window
     */
    private void AddParallelizationMethodLabel() {
        _parallelizationMethodLabel = new JLabel("Parallelization Method");
        _panel.add(_parallelizationMethodLabel);
    }

    /**
     * Creates and adds the Monitors radio button to the window
     */
    private void AddMonitorRadioButton() {
        _monitorRadioButton = new JRadioButton("Monitors");
        _monitorRadioButton.setSelected(true);
        _radioButtonGroup.add(_monitorRadioButton);
        _panel.add(_monitorRadioButton);
    }

    /**
     * Creates and adds the Mutex radio button to the window
     */
    private void AddMutexRadioButton() {
        _mutexRadioButton = new JRadioButton("Mutex");
        _radioButtonGroup.add(_mutexRadioButton);
        _panel.add(_mutexRadioButton);
    }

    /**
     * Creates and adds the status label to the window
     */
    private void AddStatusLabel() {
        _statusLabel = new JLabel("Status: Idle");
        _statusLabel.setPreferredSize(new Dimension(380, 25));
        _statusLabel.setVerticalAlignment(JLabel.CENTER);
        _panel.add(_statusLabel);
    }

    /**
     * Adds a separator line that takes up the width of the window
     */
    private void AddSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(400, 1));
        _panel.add(separator);
    }

    /**
     * Gets called by the calculator whenever the calculation ends. Gets the total time elapsed as the parameter
     * @param ms If this value is -1, this means that the calculation got cancelled. Otherwise it is the total time elapsed for the calculation
     */
    public static void FinishCalculation(long ms) {
        _calculateButton.setEnabled(true);
        _cancelButton.setEnabled(false);
        _restartButton.setEnabled(false);

        if(ms == -1) {
            _statusLabel.setText("Status: Cancelled, Idle");
        }
        else {
            _statusLabel.setText("Status: Operation Done In: " + ms + "ms" + ", Idle");
        }
    }
}
