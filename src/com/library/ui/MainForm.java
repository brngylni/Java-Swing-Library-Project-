package com.library.ui;

import com.library.db.concretes.BookDAL;
import com.library.db.concretes.BookPublisherDAL;
import com.library.db.concretes.BookTypeDAL;
import com.library.db.concretes.BookWriterDAL;
import com.library.entities.concretes.Book;
import com.library.entities.concretes.BookPublisher;
import com.library.entities.concretes.BookType;
import com.library.entities.concretes.BookWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MainForm extends JFrame{
    private JTextField bookWriterField;
    private JTextField bookTypeField;
    private JTextField bookNameField;
    private JTextField bookPublisherField;
    private JButton addBtn;
    private JPanel mainPanel;
    private JTable bookTable;
    private JButton deleteBtn;
    private JButton editBtn;
    private JTextField searchField;
    private JLabel bookNameLabel;
    private JPanel formContainer;
    private JLabel bookWriterLabel;
    private JLabel bookTypeLabel;
    private JLabel bookPublisherLabel;
    private JScrollPane scrollPane;
    private JLabel idDisplayLabel;

    private BookDAL bookDAL;
    private BookWriterDAL bookWriterDAL;
    private BookPublisherDAL bookPublisherDAL;
    private BookTypeDAL bookTypeDAL;

    public MainForm(String title, String connectionString, String dbUsername, String password){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        String[] headers = {"ID", "Book Name", "Book Writer", "Book Publisher", "Book Type"};
        this.bookTable.setModel(new DefaultTableModel(headers, 0));
        this.bookDAL = new BookDAL(connectionString, dbUsername, password);
        this.bookWriterDAL = new BookWriterDAL(connectionString, dbUsername, password);
        this.bookPublisherDAL = new BookPublisherDAL(connectionString, dbUsername, password);
        this.bookTypeDAL = new BookTypeDAL(connectionString, dbUsername, password);
        bookDAL.getAll().forEach(book -> ((DefaultTableModel)this.bookTable.getModel())
                .addRow(((Book)book).asString()));
        this.bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try {
                    int row = bookTable.getSelectedRow();
                    idDisplayLabel.setText((String) bookTable.getValueAt(row, 0));
                }catch (Exception exception){

                }
            }
        });
        this.bookTable.setRowSelectionInterval(0, 0);

        // Click event Listener for Add Button
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookName = bookNameField.getText();
                String bookWriter = bookWriterField.getText();
                String bookPublisher = bookPublisherField.getText();
                String bookType = bookTypeField.getText();
                if (bookDAL.getByName(bookName) == null){
                    control_db(bookWriter, bookPublisher, bookType);
                    bookDAL.add(packBook(bookWriter, bookPublisher, bookType, bookName));
                    refresh_table();
                }
            }
        });

        // Click event listener for Edit Button
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = bookTable.getSelectedRow();
                    int id = Integer.parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                    String bookName = (String) bookTable.getValueAt(selectedRow, 1);
                    String bookWriter = (String) bookTable.getValueAt(selectedRow, 2);
                    String bookPublisher = (String) bookTable.getValueAt(selectedRow, 3);
                    String bookType = (String) bookTable.getValueAt(selectedRow, 4);
                    var book = packBookId(id, bookWriter, bookPublisher, bookType, bookName);
                    bookDAL.edit(book);
                    refresh_table();
                }catch (Exception exception){

                }
            }
        });

        // Click event listener for Delete Button
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = bookTable.getSelectedRow();
                    int id = Integer.parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                    bookDAL.delete(id);
                    refresh_table();
                }catch (Exception exception){

                }
            }
        });

        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
            public void search(){
                String text = searchField.getText();
                Comparator<String> bookComparator = new Comparator<String>() {
                    public int similarity(String st1, String st2){
                        String smaller = st1.length() < st2.length() ? st1 : st2;
                        String larger = smaller == st1 ? st2 : st1;
                        int[] counter = {0};
                        long result = smaller.chars().peek(x -> counter[0]++).filter(x -> (char)x == larger.charAt(counter[0] - 1)).count();

                        return (int)result;
                    }

                    @Override
                    public int compare(String o1, String o2) {
                        return similarity(o1, text) - similarity(o2, text);
                    }
                };
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(bookTable.getModel());
                int columnIndexToSort = 1;
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.setComparator(1, bookComparator);
                bookTable.setRowSorter(sorter);
                sorter.sort();


            }
        });
    }

    public void control_db(String bookWriter, String bookPublisher, String bookType){
        BookWriter writer = ((BookWriter)bookWriterDAL.getByName(bookWriter));
        if(writer == null){
            bookWriterDAL.add(new BookWriter(-1, bookWriter));
        }
        BookPublisher publisher = ((BookPublisher)bookPublisherDAL.getByName(bookPublisher));
        if(publisher == null){
            bookPublisherDAL.add(new BookPublisher(-1, bookPublisher));
        }
        BookType type = ((BookType)bookTypeDAL.getByName(bookType));
        if(type == null){
            bookTypeDAL.add(new BookType(-1, bookType));
        }
    }
    public Book packBook(String bookWriter, String bookPublisher, String bookType, String bookName){
        var writer = (BookWriter)bookWriterDAL.getByName(bookWriter);
        var publisher = (BookPublisher) bookPublisherDAL.getByName(bookPublisher);
        var type = (BookType)bookTypeDAL.getByName(bookType);
        return new Book(-1, bookName, writer, publisher, type);
    }
    public Book packBookId(int id, String bookWriter, String bookPublisher, String bookType, String bookName){
        var writer = (BookWriter)bookWriterDAL.getByName(bookWriter);
        var publisher = (BookPublisher) bookPublisherDAL.getByName(bookPublisher);
        var type = (BookType)bookTypeDAL.getByName(bookType);
        return new Book(id, bookName, writer, publisher, type);
    }
    public void refresh_table(){
        ((DefaultTableModel)bookTable.getModel()).setRowCount(0);
        bookDAL.getAll().forEach(book -> ((DefaultTableModel)bookTable.getModel())
                .addRow(((Book)book).asString()));
    }
}
