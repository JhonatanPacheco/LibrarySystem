package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Observable;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import command.ICommand;
import command.RemoveBookCommand;
import helper.AbstractUpdateModels;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Book;
import model.Borrow;
import view.RemoveBookInternalFrame;

public class RemoveBookInternalFrameController {
	private ILibrary _model;
	private RemoveBookInternalFrame _view;
	private DefaultListModel<Book> _bookModel;
	private DefaultListModel<Borrow> _borrowModel;
	private FilteredListModel _filteredListModel;

	public RemoveBookInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoRedo) {
		_model = model;
		_view = (RemoveBookInternalFrame) view;
		((Observable) _model).addObserver(_view);
		_filteredListModel = _view.getFilteredModel();

		_bookModel = _view.get_bookModel();
		_bookModel = AbstractUpdateModels.updateBookModel(_bookModel, model);
		_borrowModel = new DefaultListModel<Borrow>();
		_borrowModel = AbstractUpdateModels.updateBorrowModel(_borrowModel, model);
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});

		_view.btmRemoveBookAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Book book = _view.listSelectedBook();
				ICommand command = new RemoveBookCommand(book, model);
				undoRedo.insertInUnDoRedo(command);
				command.Execute();
				_view.setEnabledBtnRemoveBook(false);
				_view.listBookClearSelection();
			}
		});
		_view.textFieldSearchAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("remove book key released event");
				String searchString = _view.get_textFieldSearch();

				_filteredListModel.setFilter(new FilteredListModel.Filter() {
					public boolean accept(Object element) {
						if (((Book) element).getTitle().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Book) element).getAuthor().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Book) element).getIsbn().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if ((Integer.toString(((Book) element).getId()).contains(searchString.toLowerCase())))
							return true;
						return false;
					}
				});
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
