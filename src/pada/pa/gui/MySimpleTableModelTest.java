package pada.pa.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial") 
class MySimpleTableModel extends AbstractTableModel
{
    private int rowCount;
    private int columnCount;

    public MySimpleTableModel(int rows, int columns)
    {
        rowCount = rows;
        columnCount = columns;
    }

    public int getRowCount()
    {
        return rowCount;
    }

    public int getColumnCount()
    {
        return columnCount;
    }

    public String getColumnName(int column)
    {
        column++;
        return column + "er";
    }

    public Object getValueAt(int row, int column)
    {
        row++;
        column++;
        int result = row * column;
        return row + " * " + column + " = " + result;
    }
}

public class MySimpleTableModelTest
{
    public static void main(String args[])
    {
        JFrame f = new JFrame("Einmaleins");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MySimpleTableModel model = new MySimpleTableModel(10, 10);
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        f.add(pane);
        f.setLocation(100, 100);
        f.setSize(760, 240);
        f.setVisible(true);
    }
}