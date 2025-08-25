package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import model.Users;
import service.impl.ExpenseServiceImpl;
import service.impl.PaymentServiceImpl;
import util.Tools;

public class ReportsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
    private JComboBox<String> monthSelector;
    private ChartPanel pieChartPanel;

    // --- Financial Report ---
    private JTable financialTable;
    private DefaultTableModel financialModel;

    private PaymentServiceImpl paymentService = new PaymentServiceImpl();
    private ExpenseServiceImpl expenseService = new ExpenseServiceImpl();

    // yyyy-MM 與 中文顯示文字的對應表
    private Map<String, String> displayToKeyMap = new HashMap<>();

    public ReportsPanel(Users currentUser) {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        
        // ----------------- Financial Report Tab -----------------
        JPanel financialPanel = new JPanel(new BorderLayout());
        financialModel = new DefaultTableModel(
                new Object[]{"月份", "總收入費用", "總支出費用", "餘額"}, 0);
        financialTable = new JTable(financialModel);

        // 載入資料並拿到月份清單
        List<String> sortedMonths = Tools.loadFinancialData(financialTable, paymentService, expenseService);

        // Table
        JScrollPane scrollPane = new JScrollPane(financialTable);

        // Bar Chart
        DefaultCategoryDataset barDataset =
                Tools.createFinancialDataset((DefaultTableModel) financialTable.getModel());
        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Payment & Expense",
                "Month",
                "Total Amount",
                barDataset
        );
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(400, 300));

        // Pie Chart (default month: first row if exists)
        DefaultPieDataset pieDataset = Tools.createPieDataset(financialModel, 0);
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Payment & Expense",
                pieDataset,
                true, true, false
        );
        pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(400, 300));

        // Line Chart (Balance Trend)
        DefaultCategoryDataset lineDataset = Tools.createBalanceDataset(financialModel);
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Balance Trend",
                "Month",
                "Balance",
                lineDataset
        );
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setPreferredSize(new Dimension(400, 300));

        // Month Selector
        monthSelector = new JComboBox<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (String monthKey : sortedMonths) {
            try {
                Date date = sdf.parse(monthKey);
                String displayText = new SimpleDateFormat("yyyy年M月").format(date);
                displayToKeyMap.put(displayText, monthKey);
                monthSelector.addItem(displayText);
            } catch (Exception ex) {
                ex.printStackTrace();
                monthSelector.addItem(monthKey);
                displayToKeyMap.put(monthKey, monthKey);
            }
        }
        
        monthSelector.addActionListener(e -> {
            String displayText = (String) monthSelector.getSelectedItem();
            String monthKey = displayToKeyMap.get(displayText);
            Tools.updatePieChart(financialModel, monthKey, pieChartPanel);
        });

        // Controls Panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(new JLabel("選擇月份："));
        controlsPanel.add(monthSelector);

        // Charts Layout
        JPanel chartsTop = new JPanel(new GridLayout(1, 2));
        chartsTop.add(barChartPanel);
        chartsTop.add(pieChartPanel);

        JPanel chartsPanel = new JPanel(new GridLayout(2, 1));
        chartsPanel.add(chartsTop);
        chartsPanel.add(lineChartPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, chartsPanel);
        splitPane.setDividerLocation(200);

        financialPanel.add(controlsPanel, BorderLayout.NORTH);
        financialPanel.add(splitPane, BorderLayout.CENTER);
        financialPanel.add(Tools.makeButtonPanel(financialModel, financialTable), BorderLayout.SOUTH);

        // ----------------- Add Tabs -----------------
        tabbedPane.addTab("財務收支報告", financialPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

}
