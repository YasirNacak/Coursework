import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;

/**
 * A window that creates and displays genetic algorithms using their fitness and generation number
 * @author Yasir
 */
public class GeneticsGraphWindow {
    /**
     * Chart that shows fitness values of each genetic algorithm
     */
    private static XYChart chart;

    /**
     * Frame that has the chart and buttons that control the simluation
     */
    private static JFrame frame;

    /**
     * Thread that runs the roulette selection genetic algorithm
     */
    private static Thread rouletteThread;

    /**
     * Thread that runs the rank selection genetic algorithm
     */
    private static Thread rankThread;

    /**
     * Thread that runs the tournament selection genetic algorithm
     */
    private static Thread tournamentThread;

    /**
     * Genetic algorithm that is configured with roulette selection method and 1 point crossover
     */
    private static Genetics rouletteGA;

    /**
     * Genetic algorithm that is configured with rank selection method and 2 point crossover
     */
    private static Genetics rankGA;

    /**
     * Genetic algorithm that is configured with tournament selection method and 1 point crossover
     */
    private static Genetics tournamentGA;

    /**
     * Constructor that initializes the components of our window
     */
    public GeneticsGraphWindow() {
        chart = CreateChart();

        frame = new JFrame();
        frame.setTitle("Genetics");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(new XChartPanel<>(chart));

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton continueButton = new JButton("Continue");
        JButton stopButton = new JButton("Stop");

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        continueButton.setEnabled(false);

        startButton.addActionListener(e -> {
            StartGenetics();
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
        });

        pauseButton.addActionListener(e -> {
            continueButton.setEnabled(true);
            pauseButton.setEnabled(false);
            rouletteGA.Pause();
            rankGA.Pause();
            tournamentGA.Pause();
        });

        continueButton.addActionListener(e -> {
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            continueButton.setEnabled(false);
            rouletteGA.Continue();
            rankGA.Continue();
            tournamentGA.Continue();
        });

        stopButton.addActionListener(e -> {
            pauseButton.setEnabled(false);
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            continueButton.setEnabled(false);
            rouletteGA.Stop();
            rankGA.Stop();
            tournamentGA.Stop();
            chart.removeSeries("Roulette");
            chart.removeSeries("Tournament");
            chart.removeSeries("Rank");
            frame.repaint();
        });

        mainPanel.add(startButton);
        mainPanel.add(pauseButton);
        mainPanel.add(stopButton);
        mainPanel.add(continueButton);

        frame.add(mainPanel);
        frame.pack();
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Displays the configured window
     */
    public void ShowWindow() {
        frame.setVisible(true);
    }

    /**
     * Creates a chart series with the given name
     * @param seriesName name of the series to create
     */
    public static void CreateSeries(String seriesName) {
        chart.addSeries(seriesName, new double[] {1}, new double[] {1});
    }

    /**
     * Updates values of the given series and redraws the window
     * @param seriesName name of the series to update
     * @param generation Max X Axis value
     * @param values All Y Axis values
     */
    public static void UpdateSeries(String seriesName, int generation, double[] values) {
        double[] generations = new double[generation];
        double[] errs = new double[generation];

        for (int i = 0; i < generation; i++) {
            generations[i] = i;
            errs[i] = 0;
        }

        chart.updateXYSeries(seriesName, generations, values, errs);
        frame.repaint();
    }

    /**
     * Initializes a XChart chart that is a line graph with x and y axes
     * @return chart used to show fitness and iteration values
     */
    private XYChart CreateChart() {
        XYChart chart = new XYChartBuilder().width(750).height(600).xAxisTitle("Generation").yAxisTitle("Fitness").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setLegendSeriesLineLength(10);
        return chart;
    }

    /**
     * Initializes threads that run each genetic algorithm and starts them one after the other
     */
    public static void StartGenetics() {
        rouletteThread = new Thread(GeneticsGraphWindow::StartRoulette);
        rankThread = new Thread(GeneticsGraphWindow::StartRank);
        tournamentThread = new Thread(GeneticsGraphWindow::StartTournament);

        rouletteThread.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rankThread.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tournamentThread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chart.getStyler().setLegendVisible(true);
    }

    /**
     * Initializes the roulette selection genetic algorithm and starts its simulation
     */
    private static void StartRoulette() {
        rouletteGA = new RouletteWheelGenetics(50, 10000, 15, 0.1, 1);
        rouletteGA.Start();
    }

    /**
     * Initializes the rank selection genetic algorithm and starts its simulation
     */
    private static void StartRank() {
        rankGA = new RankGenetics(50, 100000, 15, 0.1, 2);
        rankGA.Start();
    }

    /**
     * Initializes the tournament selection genetic algorithm and starts its simulation
     */
    private static void StartTournament() {
        tournamentGA = new TournamentGenetics(50, 100000, 15, 0.1, 1);
        tournamentGA.Start();
    }
}
