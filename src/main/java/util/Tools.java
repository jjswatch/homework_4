package util;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Expense;
import model.Manager;
import model.Payment;
import model.Resident;
import model.Unit;
import service.ExpenseService;
import service.PaymentService;
import service.ResidentService;
import service.impl.ExpenseServiceImpl;
import service.impl.ManagerServiceImpl;
import service.impl.PaymentServiceImpl;
import service.impl.ResidentServiceImpl;
import service.impl.UnitServiceImpl;

public class Tools {
	
	private static UnitServiceImpl unitService = new UnitServiceImpl();
	private static ResidentServiceImpl residentService = new ResidentServiceImpl();
	private static PaymentServiceImpl paymentService = new PaymentServiceImpl();
	private static ExpenseServiceImpl expenseService = new ExpenseServiceImpl();
	private static ManagerServiceImpl managerService = new ManagerServiceImpl();
	
	private static ChartPanel pieChartPanel;

	public static void reloadUnitsTable(JTable table) {
		List<Unit> units = unitService.getAllUnit();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Unit unit : units) {
			model.addRow(new Object[] {unit.getId(), unit.getBuilding(), unit.getFloor(), unit.getNumber()});
		}
	}
	
	public static void addUnit() {
		JTextField building = new JTextField();
		JTextField floor = new JTextField();
		JTextField number = new JTextField();
		Object[] form = {"棟別：", building, "樓層：", floor, "門號：", number};
		if (JOptionPane.showConfirmDialog(null, form, "增加單元", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			Unit unit = new Unit();
			unit.setBuilding(building.getText());
			unit.setFloor(Integer.parseInt(floor.getText()));
			unit.setNumber(number.getText());
			unitService.addUnit(unit);
		}
	}
	
	public static void editUnit(JTable table) {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "請選擇一列編輯。");
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int) model.getValueAt(row, 0);  // assuming ID is column 0
		
		String b = (String) model.getValueAt(row, 1);
		int f = (int) model.getValueAt(row, 2);
		String n = (String) model.getValueAt(row, 3);
		
		JTextField building = new JTextField(b);
		JTextField floor = new JTextField(String.valueOf(f));
		JTextField number = new JTextField(n);
		Object[] form = {"棟別：", building, "樓層：", floor, "門號：", number};
		if (JOptionPane.showConfirmDialog(null, form, "編輯單元", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            Unit u = new Unit();
            u.setId(id);
            u.setBuilding(building.getText());
            u.setFloor(Integer.parseInt(floor.getText()));
            u.setNumber(number.getText());
            unitService.updateUnit(u);
        }
	}
	
	public static void deleteUnit(JTable table) {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "請選擇一列刪除。");
			return;
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int id = (int) model.getValueAt(row, 0);
		unitService.deleteUnit(id);
	}
	
	public static void reloadResidentsTable(JTable table) {

		List<Resident> residents = residentService.getAllResident();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		model.setRowCount(0);
		for (Resident resident : residents) {
			Unit u = (resident.getUnitId() == 0) ? null : unitService.getUnitById(resident.getUnitId());
			model.addRow(new Object[] {resident.getId(), resident.getName(), resident.getPhone(), resident.getEmail(), (u==null?"":u.toString())});
		}
	}
	
	public static void addResident() {

		JTextField name = new JTextField();
		JTextField phone = new JTextField();
		JTextField email = new JTextField();
		
		// unit selection
        List<Unit> units = unitService.getAllUnit();
        
        
        // Units already occupied
        List<Resident> residents = residentService.getAllResident();
        Set<Integer> occupiedUnitIds = new HashSet<>();
        for (Resident r : residents) {
            if (r.getUnitId() != 0) {
                occupiedUnitIds.add(r.getUnitId());
            }
        }

        // Filter only free units
        List<Unit> freeUnits = new ArrayList<>();
        for (Unit u : units) {
            if (!occupiedUnitIds.contains(u.getId())) {
                freeUnits.add(u);
            }
        }
        // ComboBox with free units only
        JComboBox<Unit> unitBox = new JComboBox<>(freeUnits.toArray(new Unit[0]));
        
        unitBox.insertItemAt(null, 0); // allow empty
        unitBox.setSelectedIndex(0);
        
        Object[] form = {"姓名：", name, "電話：", phone, "Email：", email, "單元：", unitBox};
		if (JOptionPane.showConfirmDialog(null, form, "增加住戶", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			Resident resident = new Resident();
			resident.setName(name.getText());
			resident.setPhone(phone.getText());
			resident.setEmail(email.getText());
			Unit selected = (Unit) unitBox.getSelectedItem();
			
			if (selected != null) {
				resident.setUnitId(selected.getId());
	        }
			
			residentService.addResident(resident);
		}
	}
	
	public static void editResident(JTable table, ResidentServiceImpl residentService, UnitServiceImpl unitService) {
        int row = table.getSelectedRow();
        if (row<0) { JOptionPane.showMessageDialog(null,"請選擇一列編輯。"); return; }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int) model.getValueAt(row, 0);
        Resident resident = residentService.getResidentById(id);
        if (resident==null) return;

        JTextField name = new JTextField(resident.getName());
        JTextField phone = new JTextField(resident.getPhone());
        JTextField email = new JTextField(resident.getEmail());
        
        List<Unit> units = unitService.getAllUnit();
        
        
        // Units already taken
        List<Resident> residents = residentService.getAllResident();
        Set<Integer> occupiedUnitIds = new HashSet<>();
        for (Resident r : residents) {
            if (r.getUnitId() != 0 && r.getId() != resident.getId()) { 
                // exclude this resident’s own unit
                occupiedUnitIds.add(r.getUnitId());
            }
        }

        // Free units + current resident’s unit
        List<Unit> freeUnits = new ArrayList<>();
        for (Unit u : units) {
            if (!occupiedUnitIds.contains(u.getId()) || 
                (resident.getUnitId() != 0 && u.getId() == resident.getUnitId())) {
                freeUnits.add(u);
            }
        }
        JComboBox<Unit> unitBox = new JComboBox<>(freeUnits.toArray(new Unit[0]));

        
        if (resident.getUnitId() != 0) {
            for (int i = 0; i < unitBox.getItemCount(); i++) {
                if (unitBox.getItemAt(i).getId() == resident.getUnitId()) {
                    unitBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        
        Object[] form = {"姓名：", name, "電話：", phone, "Email：", email, "單元：", unitBox};
        if (JOptionPane.showConfirmDialog(null, form, "編輯住戶", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            resident.setName(name.getText());
            resident.setPhone(phone.getText());
            resident.setEmail(email.getText());
            Unit selectedUnit = (Unit) unitBox.getSelectedItem();
            if (selectedUnit != null) {
                resident.setUnitId(selectedUnit.getId());
            }
            residentService.updateResident(resident);
        }
        
    }
	
	public static void deleteResident(JTable table) {
        int row = table.getSelectedRow();
        if (row<0) { JOptionPane.showMessageDialog(null,"請選擇一列刪除。"); return; }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int)model.getValueAt(row,0);
        residentService.deleteResident(id);
 
    }
	
	public static void reloadPyamentsTable(JTable table) {
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Payment p : paymentService.getAllPaymentsJoined()) {
			model.addRow(new Object[]{p.getId(), p.getResidentName(), p.getAmount(), p.getPaymentDate(), p.getType(), p.getDescription()});
		}
	}
	
	public static void addPayment(ResidentService residentService, PaymentService paymentService) {
	    List<Resident> residents = residentService.getAllResident();
	    JComboBox<Resident> residentBox = new JComboBox<>(residents.toArray(new Resident[0]));
	    JTextField amount = new JTextField();
	    JTextField date = new JTextField(Date.valueOf(java.time.LocalDate.now()).toString());
	    JComboBox<String> type = new JComboBox<>(new String[]{"租賃費用","維修費用","停車費用","其他費用"});
	    JTextField desc = new JTextField();

	    Object[] form = {"住戶：", residentBox, "總額：", amount, "日期 (YYYY-MM-DD)：", date, "類別：", type, "描述：", desc};
	    if (JOptionPane.showConfirmDialog(null, form, "增加收入", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
	        Payment p = new Payment();
	        p.setResidentId(((Resident) residentBox.getSelectedItem()).getId());
	        p.setAmount(Integer.parseInt(amount.getText()));
	        p.setPaymentDate(Date.valueOf(date.getText()));
	        p.setType((String) type.getSelectedItem());
	        p.setDescription(desc.getText());
	        paymentService.addPayment(p);
	    }
	}
	
	public static void editPayment(JTable table, ResidentService residentService, PaymentService paymentService) {
	    int row = table.getSelectedRow();
	    if (row < 0) {
	        JOptionPane.showMessageDialog(null, "請選擇一列編輯。");
	        return;
	    }

	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int id = (int) model.getValueAt(row, 0);
	    String residentName = (String) model.getValueAt(row, 1);
	    int amt = (int) model.getValueAt(row, 2);
	    Date d = (Date) model.getValueAt(row, 3);
	    String t = (String) model.getValueAt(row, 4);
	    String descStr = (String) model.getValueAt(row, 5);

	    List<Resident> residents = residentService.getAllResident();
	    JComboBox<Resident> residentBox = new JComboBox<>(residents.toArray(new Resident[0]));
	    for (int i = 0; i < residentBox.getItemCount(); i++) {
	        if (residentBox.getItemAt(i).getName().equals(residentName)) {
	            residentBox.setSelectedIndex(i);
	            break;
	        }
	    }

	    JTextField amount = new JTextField(String.valueOf(amt));
	    JTextField date = new JTextField(d.toString());
	    JComboBox<String> type = new JComboBox<>(new String[]{"租賃費用", "維修費用", "停車費用", "其他費用"});
	    type.setSelectedItem(t);
	    JTextField desc = new JTextField(descStr);

	    Object[] form = {"住戶：", residentBox, "總額：", amount, "日期 (YYYY-MM-DD)：", date, "類別：", type, "描述：", desc};
	    if (JOptionPane.showConfirmDialog(null, form, "編輯收入", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
	        Payment p = new Payment();
	        p.setId(id);
	        p.setResidentId(((Resident) residentBox.getSelectedItem()).getId());
	        p.setAmount(Integer.parseInt(amount.getText()));
	        p.setPaymentDate(Date.valueOf(date.getText()));
	        p.setType((String) type.getSelectedItem());
	        p.setDescription(desc.getText());
	        paymentService.updatePayment(p);
	    }
	}
	
	public static void deletePayment(JTable table) {
        int row = table.getSelectedRow();
        if (row<0) { JOptionPane.showMessageDialog(null,"請選擇一列刪除。"); return; }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (Integer)model.getValueAt(row,0);
        new PaymentServiceImpl().deletePayment(id);
    }
	
	public static void reloadExpensesTable(JTable table) {
		List<Expense> list = expenseService.getAllExpense();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Expense e : list) {
            model.addRow(new Object[]{e.getId(), e.getCategory(), e.getAmount(), e.getExpenseDate(), e.getDescription()});
        }
	}
	
	public static void addExpense(ResidentService residentService, ExpenseService expenseService) {
		String[] CATS = {"維修費用","公共費用","服務費用","保全費用","清潔費用","其他費用"};
        JComboBox<String> category = new JComboBox<>(CATS);
        JTextField amount = new JTextField();
        JTextField date = new JTextField(Date.valueOf(java.time.LocalDate.now()).toString());
        JTextField desc = new JTextField();

        Object[] form = {"類別：", category, "總額：", amount, "日期 (YYYY-MM-DD)：", date, "描述：", desc};
        if (JOptionPane.showConfirmDialog(null, form, "增加支出", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            Expense e = new Expense();
            e.setCategory((String) category.getSelectedItem());
            e.setAmount(Integer.parseInt(amount.getText()));
            e.setExpenseDate(Date.valueOf(date.getText()));
            e.setDescription(desc.getText());
            expenseService.addExpense(e);
        }
    }
	
	public static void editExpense(JTable table, ResidentService residentService, ExpenseService expenseService) {
        int row = table.getSelectedRow();
        if (row<0) { JOptionPane.showMessageDialog(null,"請選擇一列編輯。"); return; }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (Integer)model.getValueAt(row,0);
        String cat = (String) model.getValueAt(row,1);
        int amt = (Integer)model.getValueAt(row,2);
        Date d = (Date) model.getValueAt(row,3);
        String descStr = (String) model.getValueAt(row,4);
        
        String[] CATS = {"維修費用","公共費用","服務費用","保全費用","清潔費用","其他費用"};
        JComboBox<String> category = new JComboBox<>(CATS); category.setSelectedItem(cat);
        JTextField amount = new JTextField(String.valueOf(amt));
        JTextField date = new JTextField(d.toString());
        JTextField desc = new JTextField(descStr);

        Object[] form = {"類別：", category, "總額：", amount, "日期 (YYYY-MM-DD)：", date, "描述：", desc};
        if (JOptionPane.showConfirmDialog(null, form, "編輯支出", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            Expense e = new Expense();
            e.setId(id);
            e.setCategory((String) category.getSelectedItem());
            e.setAmount(Integer.parseInt(amount.getText()));
            e.setExpenseDate(Date.valueOf(date.getText()));
            e.setDescription(desc.getText());
            expenseService.updateExpense(e);
        }
    }
	
	public static void deleteExpense(JTable table) {
        int row = table.getSelectedRow();
        if (row<0) { JOptionPane.showMessageDialog(null,"請選擇一列刪除。"); return; }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (Integer)model.getValueAt(row,0);
        expenseService.deleteExpense(id);
        
    }
	
	public static void reloadManagersTable(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Manager> list = managerService.getAllManager();
        for (Manager m : list) {
            model.addRow(new Object[]{m.getId(), m.getUsername(), m.getFullName(), m.getRole()});
        }
    }
	
	public static void addManager() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField fullNameField = new JTextField();
        String[] roles = {"保全員"};
        //String[] roles = {"總幹事", "保全員"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] fields = {
                "帳號：", usernameField,
                "密碼：", passwordField,
                "全名：", fullNameField,
                "職務：", roleBox
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "增加管理員", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Manager m = new Manager();
            m.setUsername(usernameField.getText().trim());
            m.setPassword(new String(passwordField.getPassword()));
            m.setFullName(fullNameField.getText().trim());
            m.setRole((String) roleBox.getSelectedItem());

            managerService.addManager(m);
        }
    }
	
	public static void editManager(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "請選擇一列編輯。");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int) model.getValueAt(row, 0);
        String username = (String) model.getValueAt(row, 1);
        String fullName = (String) model.getValueAt(row, 2);
        String role = (String) model.getValueAt(row, 3);

        JTextField usernameField = new JTextField(username);
        JPasswordField passwordField = new JPasswordField(); // empty means unchanged
        JTextField fullNameField = new JTextField(fullName);
        String[] roles = {"管理員"};
        //String[] roles = {"總幹事", "管理員"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setSelectedItem(role);

        Object[] fields = {
                "帳號：", usernameField,
                "密碼(留空不更改)：", passwordField,
                "全名：", fullNameField,
                "職務：", roleBox
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "編輯管理員", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Manager m = new Manager();
            m.setId(id);
            m.setUsername(usernameField.getText().trim());
            String pwd = new String(passwordField.getPassword());
            if (!pwd.isEmpty()) {
                m.setPassword(pwd);
            } else {
                m.setPassword(managerService.getManagerById(id).getPassword()); // keep old password
            }
            m.setFullName(fullNameField.getText().trim());
            m.setRole((String) roleBox.getSelectedItem());

            managerService.updateManager(m);
        }
    }
	
	public static void deleteManager(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "請選擇一列刪除。");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "刪除管理員？", "確認", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            managerService.deleteManager(id);
        }
    }
	
	public static void loadResidentData(JTable table, ResidentService residentService, PaymentService paymentService, ExpenseService expenseService) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Resident> residents = residentService.getAllResident();
        List<Payment> payments = paymentService.getAllPaymentsJoined();
        List<Expense> expenses = expenseService.getAllExpense();

        for (Resident r : residents) {
            int totalPayments = payments.stream()
                    .filter(p -> p.getResidentId() == r.getId())
                    .mapToInt(Payment::getAmount)
                    .sum();

            int totalExpenses = expenses.stream()
                    .filter(e -> e.getId() == r.getId())
                    .mapToInt(Expense::getAmount)
                    .sum();
            
            model.addRow(new Object[]{
                    r.getName(),
                    r.getUnitId(),
                    totalPayments,
                    totalExpenses
            });
        }
    }
	
	public static List<String> loadFinancialData(
	        JTable table,
	        PaymentService paymentService,
	        ExpenseService expenseService) {

	    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);

	    List<Payment> payments = paymentService.getAllPaymentsJoined();
	    List<Expense> expenses = expenseService.getAllExpense();

	    Map<String, Double> paymentMap = new HashMap<>();
	    Map<String, Double> expenseMap = new HashMap<>();

	    // --- 加總付款 ---
	    for (Payment p : payments) {
	        String month = monthFormat.format(p.getPaymentDate());
	        paymentMap.put(month, paymentMap.getOrDefault(month, 0.0) + p.getAmount());
	    }

	    // --- 加總支出 ---
	    for (Expense ex : expenses) {
	        String month = monthFormat.format(ex.getExpenseDate());
	        expenseMap.put(month, expenseMap.getOrDefault(month, 0.0) + ex.getAmount());
	    }

	    // --- 收集所有月份 ---
	    Set<String> months = new HashSet<>();
	    months.addAll(paymentMap.keySet());
	    months.addAll(expenseMap.keySet());

	    List<String> sortedMonths = new ArrayList<>(months);
	    Collections.sort(sortedMonths); // yyyy-MM 可直接字串排序

	    // --- 輸出到 Table ---
	    for (String month : sortedMonths) {
	        double totalPayments = paymentMap.getOrDefault(month, 0.0);
	        double totalExpenses = expenseMap.getOrDefault(month, 0.0);
	        double balance = totalPayments - totalExpenses;

	        model.addRow(new Object[]{month, totalPayments, totalExpenses, balance});
	    }

	    return sortedMonths; // 回傳月份清單
	}
	
	public static JPanel makeButtonPanel(DefaultTableModel model, JTable table) {
		
        JPanel buttonPanel = new JPanel();

        JButton csvBtn = new JButton("匯出 CSV");
        csvBtn.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        JButton excelBtn = new JButton("匯出 Excel");
        excelBtn.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        JButton pdfBtn = new JButton("匯出 PDF");
        pdfBtn.setFont(new Font("微軟正黑體", Font.BOLD, 12));

        buttonPanel.add(csvBtn);
        buttonPanel.add(excelBtn);
        buttonPanel.add(pdfBtn);

        csvBtn.addActionListener(e -> exportCSV(model));
        excelBtn.addActionListener(e -> exportExcel(model));
        pdfBtn.addActionListener(e -> exportPDF(model));

        return buttonPanel;
    }
	
	public static void exportCSV(DefaultTableModel model) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("report.csv"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i) + (i < model.getColumnCount() - 1 ? "," : "\n"));
                }
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        writer.write(model.getValueAt(row, col).toString() + (col < model.getColumnCount() - 1 ? "," : "\n"));
                    }
                }
                JOptionPane.showMessageDialog(null, "CSV 已匯出！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "錯誤 匯出 CSV: " + ex.getMessage());
            }
        }
    }

    public static void exportExcel(DefaultTableModel model) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("report.xlsx"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("報表");

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    headerRow.createCell(i).setCellValue(model.getColumnName(i));
                }

                for (int row = 0; row < model.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        excelRow.createCell(col).setCellValue(model.getValueAt(row, col).toString());
                    }
                }

                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile())) {
                    workbook.write(fos);
                }
                JOptionPane.showMessageDialog(null, "Excel 已匯出！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "錯誤 匯出 Excel: " + ex.getMessage());
            }
        }
    }

    public static void exportPDF(DefaultTableModel model) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("report.pdf"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile())) {
                Document doc = new Document();
                PdfWriter.getInstance(doc, fos);
                doc.open();

                doc.add(new Paragraph("公寓大廈管理報表"));
                doc.add(new Paragraph(" "));

                PdfPTable pdfTable = new PdfPTable(model.getColumnCount());
                for (int i = 0; i < model.getColumnCount(); i++) {
                    pdfTable.addCell(model.getColumnName(i));
                }
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        pdfTable.addCell(model.getValueAt(row, col).toString());
                    }
                }
                doc.add(pdfTable);
                doc.close();

                JOptionPane.showMessageDialog(null, "PDF 已匯出！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "錯誤 匯出 PDF: " + ex.getMessage());
            }
        }
    }
    
    public static DefaultCategoryDataset createFinancialDataset(DefaultTableModel financialModel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int row = 0; row < financialModel.getRowCount(); row++) {
            String month = financialModel.getValueAt(row, 0).toString();
            double payments = Double.parseDouble(financialModel.getValueAt(row, 1).toString());
            double expenses = Double.parseDouble(financialModel.getValueAt(row, 2).toString());
            double balance = Double.parseDouble(financialModel.getValueAt(row, 3).toString());

            dataset.addValue(payments, "Payment Item", month);
            dataset.addValue(expenses, "Expense Item", month);
            dataset.addValue(balance, "Balance", month);
        }

        return dataset;
    }
    
    public static DefaultPieDataset createPieDataset(DefaultTableModel financialModel, int rowIndex) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (rowIndex >= 0 && rowIndex < financialModel.getRowCount()) {
            double payments = Double.parseDouble(financialModel.getValueAt(rowIndex, 1).toString());
            double expenses = Double.parseDouble(financialModel.getValueAt(rowIndex, 2).toString());

            dataset.setValue("Payment Items", payments);
            dataset.setValue("Expenese Items", expenses);
        }
        return dataset;
    }
    
    public static void updatePieChart(DefaultTableModel model, String monthKey, ChartPanel pieChartPanel) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).toString().equals(monthKey)) {
                DefaultPieDataset pieDataset = createPieDataset(model, row);
                JFreeChart newChart = ChartFactory.createPieChart(
                        "Income & Payment (" + monthKey + ")",
                        pieDataset,
                        true, true, false
                );
                pieChartPanel.setChart(newChart);
                break;
            }
        }
    }
    
    public static DefaultCategoryDataset createBalanceDataset(DefaultTableModel financialModel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int row = 0; row < financialModel.getRowCount(); row++) {
            String month = financialModel.getValueAt(row, 0).toString();
            double balance = Double.parseDouble(financialModel.getValueAt(row, 3).toString());
            dataset.addValue(balance, "Balance", month);
        }
        return dataset;
    }
}
