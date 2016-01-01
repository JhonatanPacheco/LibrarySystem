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
import model.Borrow;
import model.Reader;

public class ReaderAccountBalanceInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	
	private JTextField _textFieldSearch;
	private JButton _btnReturnBook;
	private ILibrary _model;
	private JList<Reader> _readersList;
	private JList<Book> _booksList;
	private JButton _btnCancel;
	private FilteredListModel _filteredListModel;
	private FilteredListModel _filteredBorrowListModel;
	private DefaultListModel<Borrow> _borrowModel = new DefaultListModel<Borrow>();
	private DefaultListModel<Reader> _readerModel = new DefaultListModel<Reader>();
	private DefaultListModel<Book> _borrowedModel = new DefaultListModel<Book>();

	public DefaultListModel<Borrow> get_borrowModel() {
		return _borrowModel;
	}

	public String get_textFieldSearch() {
		return _textFieldSearch.getText();
	}

	public void setReaderModel(DefaultListModel<Reader> readerModel) {
		_readerModel = readerModel;
	}

	public DefaultListModel<Reader> getBookModel() {
		return _readerModel;
	}

	public JList<Reader> getListReader() {
		return _readersList;
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}

	public Reader getReadersListSelectedValue() {
		return _readersList.getSelectedValue();
	}

	public Book getBooksListSelectedValue() {
		return _booksList.getSelectedValue();

	}

	public void setEnabledBtnReturnBook(boolean b) {
		_btnReturnBook.setEnabled(b);
	}

	public DefaultListModel<Reader> get_readerModel() {
		return _readerModel;
	}
	
	public DefaultListModel<Book> get_borrowedModel() {
		return _borrowedModel;
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public ReaderAccountBalanceInternalFrame(ILibrary library) throws SQLException {
		_model = library;
		
		setTitle("Reader Account Ballance");
		int textFieldCharactersLimit = 25;
		setClosable(true);
		setBounds(100, 100, 600, 497);
		getContentPane().setLayout(null);

		_textFieldSearch = new JTextField();
		_textFieldSearch.setBounds(170, 222, 244, 23);
		_textFieldSearch.setToolTipText("Search Field");
		_textFieldSearch.setColumns(10);
		_textFieldSearch.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldSearch);
		
		_filteredListModel = new FilteredListModel(_readerModel);
		_filteredBorrowListModel = new FilteredListModel(_borrowModel);

		_readersList = new JList<Reader>(_filteredListModel);
		_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
		
		_readersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_readersList.setBounds(10, 52, 414, 173);

		_btnReturnBook = new JButton("Return Book");
		_btnReturnBook.setEnabled(false);

		_btnReturnBook.setBounds(10, 222, 150, 23);
		getContentPane().add(_btnReturnBook);

		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(424, 222, 150, 23);
		getContentPane().add(_btnCancel);

		JScrollPane scrollPaneReadersList = new JScrollPane(_readersList);
		scrollPaneReadersList.setBounds(10, 11, 564, 200);
		getContentPane().add(scrollPaneReadersList);

		_booksList = new JList(_borrowedModel);
		_booksList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setEnabledBtnReturnBook(true);
			}
		});
		_booksList.setBounds(107, 375, 95, -16);

		JScrollPane scrollPaneBooksList = new JScrollPane(_booksList);
		scrollPaneBooksList.setBounds(10, 256, 564, 200);
		getContentPane().add(scrollPaneBooksList);

	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Update account balance _view");
		if (arg instanceof Reader) {
			_borrowedModel.clear();
			_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, _model);
			System.out.println("Update account balance _view Reader");
			listReaderClearSelection();
		}
		if (arg instanceof Borrow) {
			System.out.println("Update account balance _view BorrowModel");
			_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
			try{
				_borrowedModel = AbstractUpdateModels.updateBorrowedModel(_borrowModel, _borrowedModel, getReadersListSelectedValue().getId());			
			}
			catch(Exception exc){
				_borrowedModel.clear();
			}
			setEnabledBtnReturnBook(false);
		}
	}
	
	public void textFieldSearchAddKeyListener(KeyAdapter adapter) {
		_textFieldSearch.addKeyListener(adapter);
	}

	public void btnReturnBookAddActionListener(ActionListener action) {
		_btnReturnBook.addActionListener(action);
	}
	
	public void listReaderListAddListSelectionListener(ListSelectionListener listener){
		_readersList.addListSelectionListener(listener);
	}
	
	public void listReaderClearSelection(){
		_readersList.clearSelection();
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
