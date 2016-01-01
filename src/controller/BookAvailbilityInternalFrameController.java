package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import helper.AbstractUpdateModels;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.NotDistinctBook;
import view.BookAvailbilityInternalFrame;

public class BookAvailbilityInternalFrameController {
	private ILibrary _model;
	private BookAvailbilityInternalFrame _view;
	private DefaultListModel<NotDistinctBook> _booksModel;
	private FilteredListModel _filteredListModel;
	
	public BookAvailbilityInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoRedo) {
		_model = model;
		_view = (BookAvailbilityInternalFrame) view;
		((Observable) _model).addObserver(_view);
		
		_filteredListModel =_view.getFilteredModel();
		_booksModel =_view.getBooksModel();
		_booksModel = AbstractUpdateModels.updateBooksModel(_booksModel, _model);
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});
		
		_view.listAddListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(_view.listSelectedBooks().is_available())
					_view.setLblBookAvailability("Book Available");
				else
					_view.setLblBookAvailability("Book Not Available");				
			}
		});
		_view.textFieldSearchAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("remove books key released event");
				String searchString =_view.get_textFieldSearch();

				_filteredListModel.setFilter(new FilteredListModel.Filter() {
					public boolean accept(Object element) {
						if (((NotDistinctBook) element).getTitle().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((NotDistinctBook) element).getAuthor().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((NotDistinctBook) element).getIsbn().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if ((Integer.toString(((NotDistinctBook) element).getId()).contains(searchString.toLowerCase())))
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

