package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComparisonWindow extends JFrame {

    public ComparisonWindow(KMeansExperiment exp, ArrayList<Point> points) {

        for (Window w : Window.getWindows()) {
            if (w instanceof KMeansVisualizer) {
                w.dispose();
            }
        }
        

        setTitle("Parallel vs Sequential Comparison");
        setSize(1800, 900);
        setLocationRelativeTo(null);

        
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10)); 
        setContentPane(content);

        JPanel plotsPanel = new JPanel(new GridLayout(1, 2, 10, 0)); 

        // LEFT = Sequential
        plotsPanel.add(wrapWithLabel(
            extractPlotPanel(new KMeansVisualizer(
                    exp.getSequential().getClusters(),
                    points,
                    exp.getSequential().getSSE(),
                    exp.getSequential_time() / 1_000_000_000.0,
                    exp.getSequential().getNoOfIterations(),
                    false   
            )),
            "Sequential"
        ));

        // RIGHT = Parallel
        plotsPanel.add(wrapWithLabel(
            extractPlotPanel(new KMeansVisualizer(
                    exp.getParallel().getClusters(),
                    points,
                    exp.getParallel().getSSE(),
                    exp.getParallel_time() / 1_000_000_000.0,
                    exp.getParallel().getNoOfIterations(),
                    false   
            )),
            "Parallel"
        ));

        content.add(plotsPanel, BorderLayout.CENTER);

        JPanel info = new JPanel(new GridLayout(3, 2, 10, 5));
        info.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 

        info.add(new JLabel("Sequential SSE: " + exp.getSequential().getSSE(), SwingConstants.CENTER));
        info.add(new JLabel("Parallel SSE: " + exp.getParallel().getSSE(), SwingConstants.CENTER));

        info.add(new JLabel(String.format("Sequential Time (s): %.4f", exp.getSequential_time() / 1_000_000_000.0), SwingConstants.CENTER));
        info.add(new JLabel(String.format("Parallel Time (s): %.4f", exp.getParallel_time() / 1_000_000_000.0), SwingConstants.CENTER));

        info.add(new JLabel("Sequential Iterations: " + exp.getSequential().getNoOfIterations(), SwingConstants.CENTER));
        info.add(new JLabel("Parallel Iterations: " + exp.getParallel().getNoOfIterations(), SwingConstants.CENTER));

        content.add(info, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                JFrame main = MainGUI.thisWindow;
                if (main != null) {
                    main.setEnabled(true);
                    main.setVisible(true);
                    main.toFront();
                }
            }
        });
        
    }

 
    private JPanel extractPlotPanel(KMeansVisualizer viz) {
        Container cp = viz.getContentPane();
        JPanel panel;
        if (cp instanceof JPanel) {
            panel = (JPanel) cp;
        } else {
            panel = new JPanel(new BorderLayout());
            panel.add(cp, BorderLayout.CENTER);
        }
        viz.dispose(); 
        return panel;
    }

    private JPanel wrapWithLabel(Container panel, String label) {
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        container.add(new JLabel(label, SwingConstants.CENTER), BorderLayout.NORTH);
        return container;
    }
}
