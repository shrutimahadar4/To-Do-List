import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TodoListApp extends JFrame {
    private final DefaultListModel<String> taskListModel;
    private final JList<String> taskList;
    private final JTextField taskInput;

    public TodoListApp() {
        setTitle("To-Do List");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top panel: Input field and Add button
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        taskInput = new JTextField();
        JButton addButton = new JButton("Add");

        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Centre panel: Scrollable task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Bottom panel: Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton completeButton = new JButton("Mark Done");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear All");

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Add panels to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        ActionListener addTaskAction = e -> addTask();
        addButton.addActionListener(addTaskAction);
        taskInput.addActionListener(addTaskAction); // Pressing Enter adds the task

        completeButton.addActionListener(e -> markTaskDone());
        deleteButton.addActionListener(e -> deleteTask());
        clearButton.addActionListener(e -> taskListModel.clear());
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskInput.setText("");
        }
    }

    private void markTaskDone() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String task = taskListModel.getElementAt(selectedIndex);
            if (!task.startsWith("[Done] ")) {
                taskListModel.set(selectedIndex, "[Done] " + task);
            }
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TodoListApp().setVisible(true));
    }
}