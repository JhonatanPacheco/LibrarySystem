package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import helper.BtnCancelAddListener;
import helper.JTextFieldLimit;
import librarySystem.ILibrary;

public class AddReaderInternalFrame extends JInternalFrame implements Observer,BtnCancelAddListener {
	private JTextField _textFieldFirstName;
	private JTextField _textFieldLastName;
	private JButton _btnAddReader;
	private JButton _btnCancel;

	public String get_textFieldFirstName() {
		return _textFieldFirstName.getText();
	}

	public String get_textFieldLastName() {
		return _textFieldLastName.getText();
	}

	/**
	 * Create the _view.
	 */
	public AddReaderInternalFrame(ILibrary library) {
		setClosable(true);
		setTitle("Add Reader");
		int textFieldCharactersLimit = 25;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblFirstName);

		_textFieldFirstName = new JTextField();
		_textFieldFirstName.setColumns(10);
		_textFieldFirstName.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblLastName);

		_textFieldLastName = new JTextField();
		_textFieldLastName.setColumns(10);
		_textFieldLastName.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldLastName);

		_btnAddReader = new JButton("Add Reader");
		_btnAddReader.setEnabled(false);
		getContentPane().add(_btnAddReader);

		_btnCancel = new JButton("Cancel");
		getContentPane().add(_btnCancel);

	}

	public void clearTextFields() {
		_textFieldFirstName.setText("");
		_textFieldLastName.setText("");
		_btnAddReader.setEnabled(false);
	}

	public void btnAddReaderAddListener(ActionListener actionListener) {
		_btnAddReader.addActionListener(actionListener);
	}

	public void btnAddReaderSetEnabled(boolean flag) {
		_btnAddReader.setEnabled(flag);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update _view add reader");
	}

	public void TextFieldsKeyListener(KeyAdapter keyadapter) {
		_textFieldFirstName.addKeyListener(keyadapter);
		_textFieldLastName.addKeyListener(keyadapter);
	}
	
	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
