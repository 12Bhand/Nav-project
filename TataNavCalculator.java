import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Tata Nav Calculator - A comprehensive stock NAV calculation application
 * Features: Real-time calculations, graphical visualization, and navigation
 * 
 * @author Development Team
 * @version 2.0
 */
public class TataNavCalculator extends JFrame {
    
    // Constants
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;
    private static final int MAX_HISTORY_SIZE = 50;
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ERROR_COLOR = new Color(220, 20, 60);
    
    // UI Components
    private JTextField stockField;
    private JTextField buyingField;
    private JTextField sellingField;
    private JTextArea resultArea;
    private GraphPanel graphPanel;
    private JLabel navLabel;
    private JLabel statusLabel;
    private JProgressBar calculationProgress;
    
    // Data
    private List<NavCalculation> navHistory;
    private SimpleDateFormat dateFormat;
    
    /**
     * Data class to store calculation results
     */
    private static class NavCalculation {
        final String stock;
        final double buying;
        final double selling;
        final double nav;
        final double profitLoss;
        final double profitLossPercent;
        final Date timestamp;
        
        NavCalculation(String stock, double buying, double selling, double nav, 
                      double profitLoss, double profitLossPercent) {
            this.stock = stock;
            this.buying = buying;
            this.selling = selling;
            this.nav = nav;
            this.profitLoss = profitLoss;
            this.profitLossPercent = profitLossPercent;
            this.timestamp = new Date();
        }
    }
    
    /**
     * Constructor - Initialize the application
     */
    public TataNavCalculator() {
        navHistory = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
        addSampleData();
    }
    
    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        // Input fields with improved styling
        stockField = createStyledTextField(20);
        buyingField = createStyledTextField(10);
        sellingField = createStyledTextField(10);
        
        // Set default values
        buyingField.setText("100.00");
        sellingField.setText("120.00");
        
        // Result display area
        resultArea = new JTextArea(8, 35);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBackground(SECONDARY_COLOR);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Calculation Results"),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // NAV display label
        navLabel = new JLabel("Current NAV: ₹0.00", SwingConstants.CENTER);
        navLabel.setFont(new Font("Arial", Font.BOLD, 18));
        navLabel.setForeground(SUCCESS_COLOR);
        navLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Status label
        statusLabel = new JLabel("Ready for calculation", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        
        // Progress bar
        calculationProgress = new JProgressBar();
        calculationProgress.setVisible(false);
        calculationProgress.setStringPainted(true);
        
        // Graph panel
        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(450, 300));
        graphPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("NAV Trend Graph"),
            new EmptyBorder(10, 10, 10, 10)
        ));
    }
    
    /**
     * Create styled text field
     */
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    /**
     * Setup the main layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Create main panels
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
        add(createNavigationPanel(), BorderLayout.SOUTH);
        
        // Add padding around the main content
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
    }
    
  
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Tata Nav Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Professional Stock NAV Analysis Tool", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(220, 220, 220));
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return titlePanel;
    }
    
    /**
     * Create main content panel
     */
    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        
        // Left panel with input and results
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(createInputPanel(), BorderLayout.NORTH);
        leftPanel.add(createResultsPanel(), BorderLayout.CENTER);
        leftPanel.add(createStatusPanel(), BorderLayout.SOUTH);
        
        // Right panel with graph
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(graphPanel, BorderLayout.CENTER);
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    /**
     * Create input panel
     */
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Stock Information"),
            new EmptyBorder(15, 15, 15, 15)
        ));
        inputPanel.setBackground(SECONDARY_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Stock field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createStyledLabel("Stock Symbol:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(stockField, gbc);
        
        // Buying field
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(createStyledLabel("Buying Price (₹):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(buyingField, gbc);
        
        // Selling field
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(createStyledLabel("Selling Price (₹):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(sellingField, gbc);
        
        // Submit button
        JButton submitButton = createStyledButton("Calculate NAV", PRIMARY_COLOR);
        submitButton.addActionListener(new CalculateAction());
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 8, 8, 8);
        inputPanel.add(submitButton, gbc);
        
        // Progress bar
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 8, 8, 8);
        inputPanel.add(calculationProgress, gbc);
        
        return inputPanel;
    }
    
    /**
     * Create styled label
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    /**
     * Create styled button
     */
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
    
    /**
     * Create results panel
     */
    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        return resultsPanel;
    }
    
    /**
     * Create status panel
     */
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout(10, 10));
        statusPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        statusPanel.add(navLabel, BorderLayout.NORTH);
        statusPanel.add(statusLabel, BorderLayout.SOUTH);
        
        return statusPanel;
    }
    
    /**
     * Create navigation panel
     */
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        navPanel.setBackground(new Color(248, 248, 248));
        navPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Create navigation buttons
        JButton homeButton = createStyledButton("🏠 Home", new Color(100, 149, 237));
        JButton historyButton = createStyledButton("📊 History", new Color(60, 179, 113));
        JButton settingsButton = createStyledButton("⚙️ Settings", new Color(255, 140, 0));
        JButton clearButton = createStyledButton("🗑️ Clear", new Color(220, 20, 60));
        JButton exportButton = createStyledButton("📤 Export", new Color(138, 43, 226));
        
        // Add action listeners
        homeButton.addActionListener(e -> resetToHome());
        historyButton.addActionListener(e -> showHistory());
        settingsButton.addActionListener(e -> showSettings());
        clearButton.addActionListener(e -> clearAll());
        exportButton.addActionListener(e -> exportData());
        
        // Add buttons to panel
        navPanel.add(homeButton);
        navPanel.add(historyButton);
        navPanel.add(settingsButton);
        navPanel.add(clearButton);
        navPanel.add(exportButton);
        
        return navPanel;
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Add key listeners for Enter key submission
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new CalculateAction().actionPerformed(null);
                }
            }
        };
        
        stockField.addKeyListener(enterKeyListener);
        buyingField.addKeyListener(enterKeyListener);
        sellingField.addKeyListener(enterKeyListener);
        
        // Add input validation
        addNumericValidation(buyingField);
        addNumericValidation(sellingField);
    }
    
    /**
     * Add numeric validation to text field
     */
    private void addNumericValidation(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }
    
    /**
     * Setup window properties
     */
    private void setupWindow() {
        setTitle("Tata Nav Calculator v2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
        
        // Set application icon (if available)
        try {
            // You can add an icon here if you have one
            // setIconImage(ImageIO.read(new File("icon.png")));
        } catch (Exception e) {
            // Icon not found, continue without it
        }
    }
    
    /**
     * Add sample data for demonstration
     */
    private void addSampleData() {
        List<Double> sampleData = new ArrayList<>();
        sampleData.add(95.5);
        sampleData.add(98.2);
        sampleData.add(102.1);
        sampleData.add(99.8);
        sampleData.add(105.3);
        sampleData.add(108.7);
        sampleData.add(103.2);
        
        graphPanel.updateData(sampleData);
        updateStatus("Sample data loaded. Ready for calculations.");
    }
    
    /**
     * Calculate Action Handler
     */
    private class CalculateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            performCalculation();
        }
    }
    
    /**
     * Perform NAV calculation with validation and animation
     */
    private void performCalculation() {
        try {
            // Show progress
            showCalculationProgress();
            
            // Validate inputs
            String stock = stockField.getText().trim().toUpperCase();
            if (stock.isEmpty()) {
                showError("Please enter a stock symbol");
                return;
            }
            
            double buying = parsePrice(buyingField.getText());
            double selling = parsePrice(sellingField.getText());
            
            if (buying <= 0 || selling <= 0) {
                showError("Prices must be greater than zero");
                return;
            }
            
            // Simulate calculation delay for better UX
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    completeCalculation(stock, buying, selling);
                    ((Timer) e.getSource()).stop();
                }
            });
            timer.start();
            
        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric values for prices");
            hideCalculationProgress();
        }
    }
    
    /**
     * Complete the calculation process
     */
    private void completeCalculation(String stock, double buying, double selling) {
        try {
            // Calculate NAV and related metrics
            double nav = calculateNAV(buying, selling);
            double profitLoss = selling - buying;
            double profitLossPercent = (profitLoss / buying) * 100;
            
            // Create calculation record
            NavCalculation calculation = new NavCalculation(
                stock, buying, selling, nav, profitLoss, profitLossPercent
            );
            
            // Update history
            navHistory.add(calculation);
            if (navHistory.size() > MAX_HISTORY_SIZE) {
                navHistory.remove(0);
            }
            
            // Update displays
            updateResults(calculation);
            updateNavDisplay(nav);
            updateGraph();
            updateStatus("Calculation completed successfully");
            
            hideCalculationProgress();
            
        } catch (Exception ex) {
            showError("Calculation failed: " + ex.getMessage());
            hideCalculationProgress();
        }
    }
    
    /**
     * Parse price from string with validation
     */
    private double parsePrice(String priceText) throws NumberFormatException {
        if (priceText == null || priceText.trim().isEmpty()) {
            throw new NumberFormatException("Price cannot be empty");
        }
        return Double.parseDouble(priceText.trim());
    }
    
    /**
     * Calculate NAV with improved algorithm
     */
    private double calculateNAV(double buying, double selling) {
        // More sophisticated NAV calculation
        double baseNav = (buying + selling) / 2;
        double marketVolatility = (Math.random() - 0.5) * 10; // -5 to +5
        double trendFactor = Math.sin(System.currentTimeMillis() / 1000000.0) * 2; // Trend simulation
        
        return baseNav + marketVolatility + trendFactor;
    }
    
    /**
     * Update results display
     */
    private void updateResults(NavCalculation calc) {
        StringBuilder result = new StringBuilder();
        result.append("╔══════════════════════════════════════╗\n");
        result.append("║           CALCULATION RESULTS        ║\n");
        result.append("╠══════════════════════════════════════╣\n");
        result.append(String.format("║ Stock Symbol    : %-18s ║\n", calc.stock));
        result.append(String.format("║ Buying Price    : ₹%-17.2f ║\n", calc.buying));
        result.append(String.format("║ Selling Price   : ₹%-17.2f ║\n", calc.selling));
        result.append(String.format("║ Calculated NAV  : ₹%-17.2f ║\n", calc.nav));
        result.append(String.format("║ Profit/Loss     : ₹%-17.2f ║\n", calc.profitLoss));
        result.append(String.format("║ P/L Percentage  : %-17.2f%% ║\n", calc.profitLossPercent));
        result.append(String.format("║ Status          : %-18s ║\n", 
            calc.profitLoss >= 0 ? "PROFIT 📈" : "LOSS 📉"));
        result.append(String.format("║ Timestamp       : %-18s ║\n", 
            dateFormat.format(calc.timestamp)));
        result.append("╚══════════════════════════════════════╝\n\n");
        
        resultArea.append(result.toString());
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }
    
    /**
     * Update NAV display
     */
    private void updateNavDisplay(double nav) {
        navLabel.setText(String.format("Current NAV: ₹%.2f", nav));
        navLabel.setForeground(nav > 100 ? SUCCESS_COLOR : 
                              nav < 90 ? ERROR_COLOR : Color.ORANGE);
    }
    
    /**
     * Update graph with latest data
     */
    private void updateGraph() {
        List<Double> navValues = new ArrayList<>();
        for (NavCalculation calc : navHistory) {
            navValues.add(calc.nav);
        }
        graphPanel.updateData(navValues);
    }
    
    /**
     * Show calculation progress
     */
    private void showCalculationProgress() {
        calculationProgress.setVisible(true);
        calculationProgress.setIndeterminate(true);
        calculationProgress.setString("Calculating NAV...");
        updateStatus("Processing calculation...");
    }
    
    /**
     * Hide calculation progress
     */
    private void hideCalculationProgress() {
        calculationProgress.setVisible(false);
        calculationProgress.setIndeterminate(false);
    }
    
    /**
     * Show error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        updateStatus("Error: " + message);
    }
    
    /**
     * Update status message
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.GRAY);
    }
    
    // Navigation Methods
    
    /**
     * Reset to home state
     */
    private void resetToHome() {
        stockField.setText("");
        buyingField.setText("100.00");
        sellingField.setText("120.00");
        navLabel.setText("Current NAV: ₹0.00");
        navLabel.setForeground(SUCCESS_COLOR);
        updateStatus("Reset to home. Ready for new calculation.");
        stockField.requestFocus();
    }
    
    /**
     * Show calculation history
     */
    private void showHistory() {
        if (navHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No calculation history available.", 
                "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder history = new StringBuilder();
        history.append("CALCULATION HISTORY\n");
        history.append("===================\n\n");
        
        for (int i = navHistory.size() - 1; i >= 0; i--) {
            NavCalculation calc = navHistory.get(i);
            history.append(String.format("%d. %s - NAV: ₹%.2f (P/L: %.2f%%)\n", 
                navHistory.size() - i, calc.stock, calc.nav, calc.profitLossPercent));
        }
        
        JTextArea historyArea = new JTextArea(history.toString());
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Calculation History", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show settings dialog
     */
    private void showSettings() {
        String[] options = {"Light Theme", "Dark Theme", "Auto-save Results", "Export Format"};
        String selected = (String) JOptionPane.showInputDialog(this, 
            "Select a setting to configure:", "Settings", 
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (selected != null) {
            JOptionPane.showMessageDialog(this, 
                "Settings for '" + selected + "' - Coming soon in next version!", 
                "Settings", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Clear all data
     */
    private void clearAll() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all data?\nThis action cannot be undone.", 
            "Confirm Clear", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            resultArea.setText("");
            navHistory.clear();
            graphPanel.updateData(new ArrayList<>());
            resetToHome();
            updateStatus("All data cleared successfully.");
        }
    }
    
    /**
     * Export data functionality
     */
    private void exportData() {
        if (navHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to export.", 
                "Export", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] formats = {"CSV", "JSON", "XML", "PDF Report"};
        String format = (String) JOptionPane.showInputDialog(this, 
            "Select export format:", "Export Data", 
            JOptionPane.QUESTION_MESSAGE, null, formats, formats[0]);
        
        if (format != null) {
            JOptionPane.showMessageDialog(this, 
                "Export to " + format + " format - Feature coming soon!\n" +
                "Current data: " + navHistory.size() + " calculations", 
                "Export", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Enhanced Graph Panel with better visualization
     */
    private class GraphPanel extends JPanel {
        private List<Double> data;
        private boolean showGrid = true;
        private boolean showPoints = true;
        
        public GraphPanel() {
            data = new ArrayList<>();
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(400, 300));
        }
        
        public void updateData(List<Double> newData) {
            this.data = new ArrayList<>(newData);
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (data.isEmpty()) {
                drawEmptyState(g);
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            
            int width = getWidth() - 60;
            int height = getHeight() - 60;
            int startX = 40;
            int startY = 30;
            
            // Draw background gradient
            GradientPaint gradient = new GradientPaint(0, 0, new Color(240, 248, 255), 
                                                      0, height, Color.WHITE);
            g2d.setPaint(gradient);
            g2d.fillRect(startX, startY, width, height);
            
            // Draw border
            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(startX, startY, width, height);
            
            // Draw grid
            if (showGrid) {
                drawGrid(g2d, startX, startY, width, height);
            }
            
            // Draw axes
            drawAxes(g2d, startX, startY, width, height);
            
            if (data.size() < 2) return;
            
            // Calculate data range
            double min = data.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double max = data.stream().mapToDouble(Double::doubleValue).max().orElse(100);
            double range = max - min;
            if (range == 0) range = 1;
            
            // Draw trend area
            drawTrendArea(g2d, startX, startY, width, height, min, range);
            
            // Draw line graph
            drawLineGraph(g2d, startX, startY, width, height, min, range);
            
            // Draw data points
            if (showPoints) {
                drawDataPoints(g2d, startX, startY, width, height, min, range);
            }
            
            // Draw labels
            drawLabels(g2d, startX, startY, width, height, min, max);
        }
        
        private void drawEmptyState(Graphics g) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.ITALIC, 16));
            FontMetrics fm = g.getFontMetrics();
            String message = "No data to display";
            int x = (getWidth() - fm.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g.drawString(message, x, y);
        }
        
        private void drawGrid(Graphics2D g2d, int startX, int startY, int width, int height) {
            g2d.setColor(new Color(230, 230, 230));
            g2d.setStroke(new BasicStroke(0.5f));
            
            // Vertical grid lines
            for (int i = 1; i < 10; i++) {
                int x = startX + (width * i / 10);
                g2d.drawLine(x, startY, x, startY + height);
            }
            
            // Horizontal grid lines
            for (int i = 1; i < 10; i++) {
                int y = startY + (height * i / 10);
                g2d.drawLine(startX, y, startX + width, y);
            }
        }
        
        private void drawAxes(Graphics2D g2d, int startX, int startY, int width, int height) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(startX, startY + height, startX + width, startY + height); // X-axis
            g2d.drawLine(startX, startY, startX, startY + height); // Y-axis
        }
        
        private void drawTrendArea(Graphics2D g2d, int startX, int startY, int width, int height, 
                                  double min, double range) {
            int[] xPoints = new int[data.size() + 2];
            int[] yPoints = new int[data.size() + 2];
            
            // Create area under the curve
            for (int i = 0; i < data.size(); i++) {
                xPoints[i] = startX + (width * i / (data.size() - 1));
                yPoints[i] = startY + height - (int)((data.get(i) - min) / range * height);
            }
            
            // Close the area
            xPoints[data.size()] = startX + width;
            yPoints[data.size()] = startY + height;
            xPoints[data.size() + 1] = startX;
            yPoints[data.size() + 1] = startY + height;
            
            // Fill area with gradient
            GradientPaint areaGradient = new GradientPaint(0, startY, 
                new Color(70, 130, 180, 50), 0, startY + height, 
                new Color(70, 130, 180, 10));
            g2d.setPaint(areaGradient);
            g2d.fillPolygon(xPoints, yPoints, data.size() + 2);
        }
        
        private void drawLineGraph(Graphics2D g2d, int startX, int startY, int width, int height, 
                                  double min, double range) {
            g2d.setColor(PRIMARY_COLOR);
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            for (int i = 0; i < data.size() - 1; i++) {
                int x1 = startX + (width * i / (data.size() - 1));
                int y1 = startY + height - (int)((data.get(i) - min) / range * height);
                int x2 = startX + (width * (i + 1) / (data.size() - 1));
                int y2 = startY + height - (int)((data.get(i + 1) - min) / range * height);
                
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        
        private void drawDataPoints(Graphics2D g2d, int startX, int startY, int width, int height, 
                                   double min, double range) {
            for (int i = 0; i < data.size(); i++) {
                int x = startX + (width * i / (data.size() - 1));
                int y = startY + height - (int)((data.get(i) - min) / range * height);
                
                // Draw point shadow
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillOval(x - 4, y - 3, 8, 8);
                
                // Draw point
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x - 4, y - 4, 8, 8);
                g2d.setColor(ERROR_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(x - 4, y - 4, 8, 8);
                g2d.setColor(ERROR_COLOR);
                g2d.fillOval(x - 2, y - 2, 4, 4);
            }
        }
        
        private void drawLabels(Graphics2D g2d, int startX, int startY, int width, int height, 
                               double min, double max) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            
            // Y-axis labels
            g2d.drawString(String.format("%.1f", max), 5, startY + 5);
            g2d.drawString(String.format("%.1f", (max + min) / 2), 5, startY + height / 2);
            g2d.drawString(String.format("%.1f", min), 5, startY + height);
            
            // X-axis labels
            g2d.drawString("Start", startX, startY + height + 15);
            if (data.size() > 1) {
                g2d.drawString("Latest", startX + width - 20, startY + height + 15);
            }
            
            // Title
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("NAV Trend", startX + width / 2 - 30, startY - 10);
        }
    }
    
    /**
     * Main method - Application entry point
     */
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Enable anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Create and show application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new TataNavCalculator().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to start application: " + e.getMessage(), 
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}