package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import helper.AbstractUpdateModels;
import helper.BtnCancelAddListener;
import helper.FilteredListModel;
import helper.JTextFieldLimit;
import librarySystem.ILibrary;
import model.Book;

public class RemoveBookInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	private JTextField _textFieldSearch;
	private JButton _btnRemoveBook;
	private ILibrary _model;
	private JList<Book> _list;
	private DefaultListModel<Book> _bookModel;
	private JButton _btnCancel;
	private FilteredListModel _filteredListModel;

	public void btmRemoveBookAddListener(ActionListener listener) {
		_btnRemoveBook.addActionListener(listener);
	}

	public Book listSelectedBook() {
		return _list.getSelectedValue();
	}

	public String get_textFieldSearch() {
		return _textFieldSearch.getText();
	}

	public void textFieldSearchAddKeyListener(KeyAdapter adapter) {
		_textFieldSearch.addKeyListener(adapter);
	}

	public void setBookModel(DefaultListModel<Book> bookModel) {
		_bookModel = bookModel;
	}

	public DefaultListModel<Book> get_bookModel() {
		return _bookModel;
	}

	public JList<Book> getListBook() {
		return _list;
	}

	public void setListBook(JList<Book> list) {
		_list = list;
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}

	public void setEnabledBtnRemoveBook(boolean arg) {
		_btnRemoveBook.setEnabled(arg);
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public RemoveBookInternalFrame(ILibrary library) throws SQLException {
		_model = library;
		
		setTitle("Remove Book");
		int textFieldCharactersLimit = 25;
		setClosable(true);
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(null);

		_textFieldSearch = new JTextField();
		_textFieldSearch.setBounds(170, 236, 244, 23);
		_textFieldSearch.setToolTipText("Search Field");
		_textFieldSearch.setColumns(10);
		_textFieldSearch.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldSearch);
		
		_bookModel = new DefaultListModel<Book>();
		_filteredListModel = new FilteredListModel(_bookModel);

		_list = new JList<Book>(_filteredListModel);
		_list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setEnabledBtnRemoveBook(true);
			}
		});
		_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_list.setBounds(10, 52, 414, 173);

		_btnRemoveBook = new JButton("Remove Book");
		_btnRemoveBook.setEnabled(false);
		_btnRemoveBook.setBounds(10, 236, 150, 23);
		getContentPane().add(_btnRemoveBook);

		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(424, 236, 150, 23);
		getContentPane().add(_btnCancel);

		JScrollPane scrollPane = new JScrollPane(_list);
		scrollPane.setBounds(10, 11, 564, 214);
		getContentPane().add(scrollPane);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Book) {
			_bookModel = AbstractUpdateModels.updateBookModel(_bookModel, _model);
			setEnabledBtnRemoveBook(false);
			System.out.println("Update Remove book _view");
			listBookClearSelection();
		}
	}
	
	public void listBookClearSelection(){
		_list.clearSelection();
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
