package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import helper.AbstractUpdateModels;
import helper.BtnCancelAddListener;
import helper.FilteredListModel;
import helper.JTextFieldLimit;
import librarySystem.ILibrary;
import model.NotDistinctBook;

public class BookAvailbilityInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	private JTextField _textFieldSearch;
	private ILibrary _model;
	private JList<NotDistinctBook> _list = new JList<NotDistinctBook>();
	private DefaultListModel<NotDistinctBook> _booksModel = new DefaultListModel<NotDistinctBook>();
	private JButton _btnCancel;
	private JLabel _lblBookAvailability;
	private FilteredListModel _filteredListModel;

	public NotDistinctBook listSelectedBooks() {
		return _list.getSelectedValue();
	}

	public String get_textFieldSearch() {
		return _textFieldSearch.getText();
	}

	public void textFieldSearchAddKeyListener(KeyAdapter adapter) {
		_textFieldSearch.addKeyListener(adapter);
	}

	public DefaultListModel<NotDistinctBook> getBooksModel() {
		return _booksModel;
	}

	public JList<NotDistinctBook> getListBooks() {
		return _list;
	}
	public void listAddListSelectionListener(ListSelectionListener listSelectionListener){
		_list.addListSelectionListener(listSelectionListener);
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}
	public void setLblBookAvailability(String text){
		_lblBookAvailability.setText(text);
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public BookAvailbilityInternalFrame(ILibrary library) {
		_model = library;
		
		setTitle("Book Avaibility");
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
		
		_filteredListModel = new FilteredListModel(_booksModel);

		_list = new JList<NotDistinctBook>(_filteredListModel);

		_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_list.setBounds(10, 52, 414, 173);

		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(424, 236, 150, 23);
		getContentPane().add(_btnCancel);

		JScrollPane scrollPane = new JScrollPane(_list);
		scrollPane.setBounds(10, 11, 564, 214);
		getContentPane().add(scrollPane);
		
		_lblBookAvailability = new JLabel("Check item from _list");
		_lblBookAvailability.setHorizontalAlignment(SwingConstants.LEFT);
		_lblBookAvailability.setBounds(10, 236, 150, 23);
		getContentPane().add(_lblBookAvailability);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Update Book Availbility book _view");
		_booksModel = AbstractUpdateModels.updateBooksModel(_booksModel, _model);
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
