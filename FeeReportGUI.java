import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Student {
    private String studentId;
    private String name;
    private double totalFees;
    private double paidFees;
    private List<Double> paymentHistory;

    public Student(String studentId, String name, double totalFees) {
        this.studentId = studentId;
        this.name = name;
        this.totalFees = totalFees;
        this.paidFees = 0.0;
        this.paymentHistory = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public double getPaidFees() {
        return paidFees;
    }

    public void makePayment(double amount) {
        paidFees += amount;
        paymentHistory.add(amount);
    }

    public double getRemainingFees() {
        return totalFees - paidFees;
    }

    public List<Double> getPaymentHistory() {
        return paymentHistory;
    }
}

class FeeReport {
    private List<Student> students;

    public FeeReport() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getStudents() {
        return students;
    }
}

public class FeeReportGUI {
    private FeeReport feeReport;
    private JFrame frame;
    private JTextField studentIdField;
    private JTextField paymentAmountField;

    public FeeReportGUI() {
        feeReport = new FeeReport();
    }

    private void initialize() {
        frame = new JFrame("Fee Report System");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField();
        JLabel paymentAmountLabel = new JLabel("Payment Amount:");
        paymentAmountField = new JTextField();

        JButton makePaymentButton = new JButton("Make Payment");
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = studentIdField.getText();
                String paymentAmountText = paymentAmountField.getText();

                if (!studentId.isEmpty() && !paymentAmountText.isEmpty()) {
                    try {
                        double paymentAmount = Double.parseDouble(paymentAmountText);
                        Student student = feeReport.findStudent(studentId);
                        if (student != null) {
                            student.makePayment(paymentAmount);
                            JOptionPane.showMessageDialog(frame, "Payment successful.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Student not found.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid payment amount. Please enter a valid number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter student ID and payment amount.");
                }
            }
        });

        JButton viewFeeReportButton = new JButton("View Student's Fee Report");
        viewFeeReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = studentIdField.getText();
                Student student = feeReport.findStudent(studentId);
                if (student != null) {
                    String feeReportMessage = "Name: " + student.getName() +
                            "\nTotal Fees: $" + student.getTotalFees() +
                            "\nPaid Fees: $" + student.getPaidFees() +
                            "\nRemaining Fees: $" + student.getRemainingFees();
                    JOptionPane.showMessageDialog(frame, feeReportMessage);
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found.");
                }
            }
        });

        JButton displayAllStudentsButton = new JButton("Display All Students");
        displayAllStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder allStudentsMessage = new StringBuilder("-------- List of Students --------\n");
                for (Student student : feeReport.getStudents()) {
                    allStudentsMessage.append("Student ID: ").append(student.getStudentId()).append("\n")
                            .append("Name: ").append(student.getName()).append("\n")
                            .append("Total Fees: $").append(student.getTotalFees()).append("\n")
                            .append("Paid Fees: $").append(student.getPaidFees()).append("\n")
                            .append("Remaining Fees: $").append(student.getRemainingFees()).append("\n\n");
                }
                JTextArea textArea = new JTextArea(allStudentsMessage.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(frame, scrollPane);
            }
        });

        JButton viewPaymentHistoryButton = new JButton("View Payment Transaction History");
        viewPaymentHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = studentIdField.getText();
                Student student = feeReport.findStudent(studentId);
                if (student != null) {
                    List<Double> paymentHistory = student.getPaymentHistory();
                    StringBuilder paymentHistoryMessage = new StringBuilder("-------- Payment Transaction History for Student "
                            + studentId + " --------\n");
                    for (int i = 0; i < paymentHistory.size(); i++) {
                        paymentHistoryMessage.append("Payment ").append(i + 1).append(": $").append(paymentHistory.get(i)).append("\n");
                    }
                    JTextArea textArea = new JTextArea(paymentHistoryMessage.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(frame, scrollPane);
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found.");
                }
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(studentIdLabel);
        panel.add(studentIdField);
        panel.add(paymentAmountLabel);
        panel.add(paymentAmountField);
        panel.add(makePaymentButton);
        panel.add(viewFeeReportButton);
        panel.add(displayAllStudentsButton);
        panel.add(viewPaymentHistoryButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FeeReportGUI gui = new FeeReportGUI();
        gui.initialize();

        // Add sample students for demonstration
        gui.feeReport.addStudent(new Student("2023001", "Sharath", 2000));
        gui.feeReport.addStudent(new Student("2023002", "Ananth", 2000));
    }
}
