package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import command.AddBookCommand;
import command.ICommand;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Book;
import view.AddBookInternalFrame;

public class AddBookInternalFrameController {
	private ILibrary _model;
	private AddBookInternalFrame _view;

	public AddBookInternalFrameController(final JInternalFrame view, final ILibrary model, final IUndoRedo undoRedo) {
		_model = model;
		_view = (AddBookInternalFrame) view;
		((java.util.Observable) _model).addObserver(_view);
		
		_view.btnAddBookAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String author = _view.get_textFieldAuthor();
				String title = _view.get_textFieldTitle();
				String isbn = _view.get_textFieldIsbn();
				if (author.length() > 0 && title.length() > 0 && isbn.length() > 0) {
					Book book = new Book(title, author, isbn);
					ICommand command = new AddBookCommand(book, model);
					undoRedo.insertInUnDoRedo(command);
					command.Execute();
					_view.clearTextFields();
				}
			}
		});

		_view.TextFieldsKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("add book key released event");
				String author = _view.get_textFieldAuthor();
				String title = _view.get_textFieldTitle();
				String isbn = _view.get_textFieldIsbn();
				if (!(title.equals("")) && !(author.equals("")) && !(isbn.equals(""))) {
					_view.btnAddBookSetEnabled(true);
				} else {
					_view.btnAddBookSetEnabled(false);
				}
			}
		});
		
		_view.btnCancelAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					_view.setClosed(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
