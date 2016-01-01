package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Observable;

import javax.swing.JInternalFrame;

import command.AddReaderCommand;
import command.ICommand;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Reader;
import view.AddReaderInternalFrame;

public class AddReaderInternalFrameController {
	ILibrary _model;
	AddReaderInternalFrame _view;

	public AddReaderInternalFrameController(final JInternalFrame view, final ILibrary model, final IUndoRedo undoRedo) {
		_model = model;
		_view = (AddReaderInternalFrame) view;
		((Observable) _model).addObserver(_view);
		
		_view.btnAddReaderAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = _view.get_textFieldFirstName();
				String lastName = _view.get_textFieldLastName();
				if (firstName.length() > 0 && lastName.length() > 0) {
					Reader reader = new Reader(firstName, lastName);
					ICommand command = new AddReaderCommand(reader, model);
					undoRedo.insertInUnDoRedo(command);
					command.Execute();
					_view.clearTextFields();
				}
			}
		});

		_view.TextFieldsKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("add book key released event");
				String firstName = _view.get_textFieldFirstName();
				String lastName = _view.get_textFieldLastName();
				if (!(firstName.equals("")) && !(lastName.equals(""))) {
					_view.btnAddReaderSetEnabled(true);
				} else {
					_view.btnAddReaderSetEnabled(false);
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
