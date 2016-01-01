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

import command.ICommand;
import command.RemoveBorrowCommand;
import helper.AbstractUpdateModels;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Book;
import model.Borrow;
import model.Reader;
import view.ReaderAccountBalanceInternalFrame;

public class ReaderAccountBalanceInternalFrameController {
	private ILibrary _model;
	private ReaderAccountBalanceInternalFrame _view;
	private DefaultListModel<Reader> _readerModel;
	private FilteredListModel _filteredListModel;
	private DefaultListModel<Borrow> _borrowModel;
	private DefaultListModel<Book> _borrowedModel;

	public ReaderAccountBalanceInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoObject) {
		_model = model;
		_view = (ReaderAccountBalanceInternalFrame) view;
		((Observable) _model).addObserver(_view);
		
		_filteredListModel = _view.getFilteredModel();
		_borrowModel = new DefaultListModel<Borrow>();
		_borrowModel = _view.get_borrowModel();
		_borrowedModel = _view.get_borrowedModel();
		_readerModel = _view.get_readerModel();
		_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, model);
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});

		_view.textFieldSearchAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("remove reader key released event");
				String searchString = _view.get_textFieldSearch();

				_filteredListModel.setFilter(new FilteredListModel.Filter() {
					public boolean accept(Object element) {
						if (((Reader) element).getFirstName().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Reader) element).getLastName().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if ((Integer.toString(((Reader) element).getId()).contains(searchString.toLowerCase())))
							return true;
						return false;
					}
				});
			}
		});

		_view.btnReturnBookAddActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int idx = 0; idx < _borrowModel.size(); idx++) {
					Borrow borrow = _borrowModel.get(idx);
					if (borrow.getReader().getId() == _view
							.getReadersListSelectedValue().getId()
							&& borrow.getBook().getId() == _view
									.getBooksListSelectedValue().getId()) {
						ICommand command = new RemoveBorrowCommand(borrow, model);
						undoObject.insertInUnDoRedo(command);
						command.Execute();
						System.out.println("Remove borrow from account balance controller");
					}

				}
			}
		});
		
		_view.listReaderListAddListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try{
					_borrowedModel = AbstractUpdateModels.updateBorrowedModel(_borrowModel, _borrowedModel, _view.getReadersListSelectedValue().getId());			
				}
				catch(Exception exc){
					_borrowedModel.clear();
				}
				_view.setEnabledBtnReturnBook(false);
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
