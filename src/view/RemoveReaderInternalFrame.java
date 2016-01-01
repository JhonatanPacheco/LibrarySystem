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
import model.Reader;

public class RemoveReaderInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	private JTextField _textFieldSearch;
	private JButton _btnRemoveReader;
	private JList<Reader> _readersList;
	private DefaultListModel<Reader> _readerModel;
	private ILibrary _model;
	private FilteredListModel _filteredListModel;
	private JButton _btnCancel;

	public String get_textFieldSearch() {
		return _textFieldSearch.getText();
	}

	public void setEnabledBtnRemoveReader(boolean arg) {
		_btnRemoveReader.setEnabled(arg);
	}

	public DefaultListModel<Reader> get_readerModel() {
		return _readerModel;
	}

	public FilteredListModel getFilteredModel() {
		return _filteredListModel;
	}

	/**
	 * Create the _view.
	 * 
	 * @throws SQLException
	 */
	public RemoveReaderInternalFrame(ILibrary library) throws SQLException {
		_model = library;

		setTitle("Remove Reader");
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

		_readerModel = new DefaultListModel<Reader>();
		_filteredListModel = new FilteredListModel(_readerModel);

		_readersList = new JList<Reader>(_filteredListModel);
		_readersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setEnabledBtnRemoveReader(true);
			}
		});
		_readersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_readersList.setBounds(10, 52, 414, 173);

		_btnRemoveReader = new JButton("Remove Reader");
		_btnRemoveReader.setEnabled(false);
		_btnRemoveReader.setBounds(10, 236, 150, 23);
		getContentPane().add(_btnRemoveReader);

		_btnCancel = new JButton("Cancel");
		_btnCancel.setBounds(424, 236, 150, 23);
		getContentPane().add(_btnCancel);

		JScrollPane scrollPane = new JScrollPane(_readersList);
		scrollPane.setBounds(10, 11, 564, 214);
		getContentPane().add(scrollPane);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Reader) {
			_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, _model);
			System.out.println("Update Remove reader _view");
			setEnabledBtnRemoveReader(false);
			listReaderClearSelection();
		}
	}

	public void textFieldSearchAddKeyListener(KeyAdapter keyAdapter) {
		_textFieldSearch.addKeyListener(keyAdapter);
	}

	public void listReaderClearSelection() {
		_readersList.clearSelection();
	}

	public void btmRemoveReaderAddListener(ActionListener actionListener) {
		_btnRemoveReader.addActionListener(actionListener);
	}

	public Reader listSelectedReader() {
		return _readersList.getSelectedValue();
	}

	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
