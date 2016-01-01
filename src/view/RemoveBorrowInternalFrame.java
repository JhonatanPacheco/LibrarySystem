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
import model.Borrow;

public class RemoveBorrowInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	private JTextField _textFieldSearch;
	private JButton _btnRemoveBorrow;
	private JList<Borrow> _list;
	private DefaultListModel<Borrow> _borrowModel;
	private ILibrary _model;
	private FilteredListModel _filteredListModel;
	private JButton _btnCancel;

	public DefaultListModel<Borrow> get_borrowModel() {
		return _borrowModel;
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}

	public String get_textFieldSearch() {
		return _textFieldSearch.getText();
	}

	public void setEnabledBtnRemoveBorrow(boolean arg) {
		_btnRemoveBorrow.setEnabled(arg);
	}

	public Borrow getListSelectedBorrow() {
		return _list.getSelectedValue();
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public RemoveBorrowInternalFrame(ILibrary library) throws SQLException {
		_model = library;
		
		setTitle("Remove Borrow");
		int textFieldCharactersLimit = 25;
		setClosable(true);
		setBounds(100, 100, 800, 300);
		getContentPane().setLayout(null);

		_textFieldSearch = new JTextField();
		_textFieldSearch.setBounds(170, 236, 444, 23);
		_textFieldSearch.setToolTipText("Search Field");
		_textFieldSearch.setColumns(10);
		_textFieldSearch.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldSearch);
		
		_borrowModel = new DefaultListModel<Borrow>();
		_filteredListModel = new FilteredListModel(_borrowModel);

		_list = new JList<Borrow>(_filteredListModel);
		_list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setEnabledBtnRemoveBorrow(true);
			}
		});
		_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_list.setBounds(10, 52, 414, 173);

		_btnRemoveBorrow = new JButton("Remove Borrow");
		_btnRemoveBorrow.setEnabled(false);
		_btnRemoveBorrow.setBounds(10, 236, 150, 23);
		getContentPane().add(_btnRemoveBorrow);

		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(624, 236, 150, 23);
		getContentPane().add(_btnCancel);

		JScrollPane scrollPane = new JScrollPane(_list);
		scrollPane.setBounds(10, 11, 764, 214);
		getContentPane().add(scrollPane);
	}

	@Override
	public void update(Observable o, Object arg) {
		_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, _model);
		System.out.println("Update Remove borrow _view");
		setEnabledBtnRemoveBorrow(false);
	}

	public void textFieldSearchAddKeyListener(KeyAdapter keyAdapter) {
		_textFieldSearch.addKeyListener(keyAdapter);
	}

	public void btmRemoveBorrowAddListener(ActionListener actionListener) {
		_btnRemoveBorrow.addActionListener(actionListener);
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
