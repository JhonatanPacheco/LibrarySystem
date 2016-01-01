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
import javax.swing.event.ListSelectionListener;

import helper.AbstractUpdateModels;
import helper.BtnCancelAddListener;
import helper.FilteredListModel;
import helper.JTextFieldLimit;
import librarySystem.ILibrary;
import model.Book;
import model.Borrow;
import model.Reader;

public class BorrowBookInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	
	private JTextField _textFieldSearch;
	private JButton _btnReturnBook;
	private JButton _btnBorrowBook;
	private ILibrary _model;
	private JList<Reader> _readersList;
	private JList<Book> _booksBorrowedList;
	private JList<Book> _searchBookList;
	private FilteredListModel _filteredListModel;
	private FilteredListModel _filteredSearchBookListModel;
	private FilteredListModel _filteredBorrowListModel;
	
	private DefaultListModel<Borrow> _borrowModel = new DefaultListModel<Borrow>();
	private DefaultListModel<Reader> _readerModel = new DefaultListModel<Reader>();
	private DefaultListModel<Book> _borrowedModel = new DefaultListModel<Book>();
	private DefaultListModel<Book> _searchBookModel = new DefaultListModel<Book>();
	private JTextField _textFieldSearchBook;
	private JButton _btnCancel;

	public DefaultListModel<Borrow> get_borrowModel() {
		return _borrowModel;
	}

	public String getTextFieldSearch() {
		return _textFieldSearch.getText();
	}
		
	public String getTextFieldBookSearch() {
		return _textFieldSearchBook.getText();
	}

	public DefaultListModel<Reader> getBookModel() {
		return _readerModel;
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}
	
	public FilteredListModel getFilteredSearchBookModel() {
		return _filteredSearchBookListModel;
	}

	public Reader getReadersListSelectedValue() {
		return _readersList.getSelectedValue();
	}

	public Book getBooksListSelectedValue() {
		return _booksBorrowedList.getSelectedValue();
	}
	
	public Book getSearchBookListSelectedValue() {
		return _searchBookList.getSelectedValue();
	}
	
	public void setEnabledBtnReturnBook(boolean b) {
		_btnReturnBook.setEnabled(b);
	}
	
	public void setEnabledBtnBorrowBook(boolean b) {
		_btnBorrowBook.setEnabled(b);
	}

	public DefaultListModel<Reader> getReaderModel() {
		return _readerModel;
	}
	
	public DefaultListModel<Book> getBorrowedModel() {
		return _borrowedModel;
	}

	public DefaultListModel<Book> geSearchBookModel() {
		return _searchBookModel;
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public BorrowBookInternalFrame(ILibrary library) throws SQLException {
		_model = library;
		
		setTitle("Borrow Book");
		int textFieldCharactersLimit = 25;
		setClosable(true);
		setBounds(100, 100, 900, 497);
		getContentPane().setLayout(null);

		_textFieldSearch = new JTextField();
		_textFieldSearch.setBounds(145, 222, 180, 23);
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

		_btnReturnBook.setBounds(10, 222, 125, 23);
		getContentPane().add(_btnReturnBook);

		_btnBorrowBook = new JButton("Borrow Book");
		_btnBorrowBook.setEnabled(false);

		_btnBorrowBook.setBounds(749, 9, 125, 23);
		getContentPane().add(_btnBorrowBook);

		JScrollPane scrollPaneReadersList = new JScrollPane(_readersList);
		scrollPaneReadersList.setBounds(10, 11, 450, 200);
		getContentPane().add(scrollPaneReadersList);

		_booksBorrowedList = new JList(_borrowedModel);
		_booksBorrowedList.setBounds(107, 375, 95, -16);

		JScrollPane scrollPaneBooksList = new JScrollPane(_booksBorrowedList);
		scrollPaneBooksList.setBounds(10, 256, 450, 200);
		getContentPane().add(scrollPaneBooksList);
		
		_searchBookModel = new DefaultListModel<Book>();
		_filteredSearchBookListModel = new FilteredListModel(_searchBookModel);

		_searchBookList = new JList<Book>(_filteredSearchBookListModel);
		
		JScrollPane scrollPaneBookSearch = new JScrollPane(_searchBookList);
		scrollPaneBookSearch.setBounds(470, 44, 404, 412);
		getContentPane().add(scrollPaneBookSearch);
		
		_textFieldSearchBook = new JTextField();
		_textFieldSearchBook.setToolTipText("Search Field");
		_textFieldSearchBook.setColumns(10);
		_textFieldSearchBook.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		_textFieldSearchBook.setBounds(470, 10, 269, 23);
		getContentPane().add(_textFieldSearchBook);
		
		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(335, 222, 125, 23);
		getContentPane().add(_btnCancel);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Book){
			_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
			_searchBookModel = AbstractUpdateModels.updateBookModel(_searchBookModel, _model);	
		}
		
		if(arg instanceof Borrow){
			_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
			try{
				_borrowedModel = AbstractUpdateModels.updateBorrowedModel(_borrowModel, _borrowedModel, getReadersListSelectedValue().getId());			
			}
			catch(Exception exc){
				_borrowedModel.clear();
			}
			_searchBookModel = AbstractUpdateModels.updateBookModel(_searchBookModel, _model);	
		}
		
		if(arg instanceof Reader){
			_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, _model);
			_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
			try{
				_borrowedModel = AbstractUpdateModels.updateBorrowedModel(_borrowModel, _borrowedModel, getReadersListSelectedValue().getId());			
			}
			catch(Exception exc){
				_borrowedModel.clear();
			}
		}
	}
	
	public void textFieldSearchAddKeyListener(KeyAdapter adapter) {
		_textFieldSearch.addKeyListener(adapter);
	}
	
	public void textFieldSearchBookAddKeyListener(KeyAdapter adapter) {
		_textFieldSearchBook.addKeyListener(adapter);
	}

	public void btnReturnBookAddActionListener(ActionListener action) {
		_btnReturnBook.addActionListener(action);
	}
	
	public void btnBorrowBookAddActionListener(ActionListener action) {
		_btnBorrowBook.addActionListener(action);
	}
	
	public void listReaderListAddListSelectionListener(ListSelectionListener listener){
		_readersList.addListSelectionListener(listener);
	}
	
	public void listSearchBookAddListSelectionListener(ListSelectionListener listener){
		_searchBookList.addListSelectionListener(listener);
	}
	
	public void listBooksBorrowedAddListSelectionListener(ListSelectionListener listener){
		_booksBorrowedList.addListSelectionListener(listener);
	}
	
	public void listSearchBookClearSelection(){
		_searchBookList.clearSelection();
	}
	
	public void listReaderkClearSelection(){
		_readersList.clearSelection();
	}
	
	public void listBooksClearSelection(){
		_booksBorrowedList.clearSelection();
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
