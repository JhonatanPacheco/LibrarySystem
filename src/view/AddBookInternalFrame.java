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

public class AddBookInternalFrame extends JInternalFrame implements Observer, BtnCancelAddListener {
	private JTextField _textFieldTitle;
	private JTextField _textFieldAuthor;
	private JTextField _textFieldIsbn;
	private JButton _btnAddBook;
	private JButton _btnCancel;

	public String get_textFieldTitle() {
		return _textFieldTitle.getText();
	}

	public String get_textFieldAuthor() {
		return _textFieldAuthor.getText();
	}

	public String get_textFieldIsbn() {
		return _textFieldIsbn.getText();
	}

	public void btnAddBookSetEnabled(boolean flag) {
		_btnAddBook.setEnabled(flag);
	}

	public void btnAddBookAddListener(ActionListener listener) {
		_btnAddBook.addActionListener(listener);
	}

	public void TextFieldsKeyListener(KeyAdapter keyadapter) {
		_textFieldTitle.addKeyListener(keyadapter);
		_textFieldAuthor.addKeyListener(keyadapter);
		_textFieldIsbn.addKeyListener(keyadapter);
	}

	/**
	 * Create the _view.
	 */
	public AddBookInternalFrame(ILibrary library) {
		setClosable(true);
		setTitle("Add Book");
		int textFieldCharactersLimit = 25;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblTitle = new JLabel("Title: ");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblTitle);

		_textFieldTitle = new JTextField();
		_textFieldTitle.setToolTipText("Book Title");
		_textFieldTitle.setColumns(10);
		_textFieldTitle.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldTitle);
		
		JLabel lblAuthor = new JLabel("Author: ");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblAuthor);

		_textFieldAuthor = new JTextField();
		_textFieldAuthor.setToolTipText("Book Author");
		_textFieldAuthor.setColumns(10);
		_textFieldAuthor.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldAuthor);
		
		JLabel lblIsbn = new JLabel("ISBN: ");
		lblIsbn.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblIsbn);

		_textFieldIsbn = new JTextField();
		_textFieldIsbn.setToolTipText("Book ISBN ");
		_textFieldIsbn.setColumns(10);
		_textFieldIsbn.setDocument(new JTextFieldLimit(textFieldCharactersLimit));
		getContentPane().add(_textFieldIsbn);
		
		_btnAddBook = new JButton("Add Book");
		_btnAddBook.setEnabled(false);
		getContentPane().add(_btnAddBook);

		_btnCancel = new JButton("Cancel");
		getContentPane().add(_btnCancel);
	}

	public void clearTextFields() {
		_textFieldAuthor.setText("");
		_textFieldTitle.setText("");
		_textFieldIsbn.setText("");
		_btnAddBook.setEnabled(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update add book _view");
	}

	@Override
	public void btnCancelAddListener(ActionListener listener) {
			_btnCancel.addActionListener(listener);
	}
}
