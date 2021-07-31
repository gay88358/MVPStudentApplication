package adapter.mainview;

import application.port.in.getstudents.StudentDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentListTableView extends JTable {
    public StudentListTableView(StudentMainViewPresentationModel pm) {
        setTableModel(createStudentTableModel(), pm);
    }

    private void setTableModel(DefaultTableModel tableModel, StudentMainViewPresentationModel pm) {
        setModel(tableModel);

        this.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = getSelectedRow();
            if (!e.getValueIsAdjusting() && selectedRow != -1) {
                String id = getModel().getValueAt(selectedRow, 0).toString();
                UUID uuid = UUID.fromString(id);
                pm.selectStudent(uuid);
            }
        });
    }

    private DefaultTableModel createStudentTableModel() {
        return new StudentTableModel();
    }

    void setStudents(List<StudentDto> studentDtoList) {
        getDefaultTableModel().setRowCount(0);

        TableDataMapper
                .convertFromStudentList(studentDtoList)
                .forEach(row -> getDefaultTableModel().addRow(row));

        getDefaultTableModel().fireTableDataChanged();
    }

    private DefaultTableModel getDefaultTableModel() {
        return (DefaultTableModel)getModel();
    }


    private static class TableDataMapper {
        static List<Object[]> convertFromStudentList(List<StudentDto> students) {
            return students
                    .stream()
                    .map(s -> new Object[] { s.getId().toString(), s.getName(), s.getAge() })
                    .collect(Collectors.toList());
        }
    }

    private static class StudentTableModel extends DefaultTableModel {
        StudentTableModel() {
            super(new Object[][] {}, new String[] {
                    "Id", "Name", "Age"});
        }

        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    }
}
