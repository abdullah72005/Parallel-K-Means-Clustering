package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {

    private JComboBox<String> modeSelect;
    private JComboBox<String> sizeSelect;
    private JButton runButton;
    public static MainGUI thisWindow;


    public MainGUI() {
        MainGUI.thisWindow = this;

        setTitle("K-Means Clustering Runner");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        modeSelect = new JComboBox<>(new String[]{
                "Sequential", "Parallel", "Comparison"
        });

        sizeSelect = new JComboBox<>(new String[]{
                "Small", "Medium", "Large"
        });

        runButton = new JButton("Run");

        add(new JLabel("Select mode:", SwingConstants.CENTER));
        add(modeSelect);

        add(new JLabel("Select dataset size:", SwingConstants.CENTER));
        add(sizeSelect);

        add(runButton);

        runButton.addActionListener(this::runKMeans);

        setVisible(true);
    }

    private void runKMeans(ActionEvent e) {
        String mode = (String) modeSelect.getSelectedItem();
        String size = (String) sizeSelect.getSelectedItem();

        String filename = switch (size) {
            case "Small" -> "dataSet/great_small.csv";
            case "Medium" -> "dataSet/great_medium.csv";
            default -> "dataSet/great_large.csv";
        };

        try {
            List<Point> tmp = dataSet.DataSetLoader.loadCSV(filename);
            int k = dataSet.DataSetLoader.getK(tmp);
            ArrayList<Point> points = new ArrayList<>(tmp);

            KMeansConfig config = new KMeansConfig(k, 10000, k / 3, 50, 1000, 10);
            KMeansExperiment exp = new KMeansExperiment(points, config);

            switch (mode) {

                case "Sequential" -> {
                    exp.runSequential();

                    new KMeansVisualizer(
                        exp.getSequential().getClusters(),
                        points,
                        exp.getSequential().getSSE(),
                        exp.getSequential_time() / 1_000_000_000.0,
                        exp.getSequential().getNoOfIterations(),
                        true   
                    );
                }

                case "Parallel" -> {
                    exp.runParallel();

                    new KMeansVisualizer(
                        exp.getParallel().getClusters(),
                        points,
                        exp.getParallel().getSSE(),
                        exp.getParallel_time() / 1_000_000_000.0,
                        exp.getParallel().getNoOfIterations(),
                        true   
                    );
                }

                case "Comparison" -> {
                    
                    exp.runBoth();

                    new ComparisonWindow(exp, points);
                    this.setVisible(false);          
                    this.setEnabled(false);          
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading dataset: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
