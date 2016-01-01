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

import command.AddBorrowCommand;
import command.ICommand;
import command.RemoveBorrowCommand;
import helper.AbstractUpdateModels;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Book;
import model.Borrow;
import model.Reader;
import view.BorrowBookInternalFrame;

public class BorrowBookInternalFrameController {
	private ILibrary _model;
	private BorrowBookInternalFrame _view;
	private DefaultListModel<Reader> _readerModel;
	private FilteredListModel _filteredListModel;
	private FilteredListModel _filteredSearchBookListModel;
	private DefaultListModel<Borrow> _borrowModel;
	private DefaultListModel<Book> _borrowedModel;
	private DefaultListModel<Book> _searchBookModel;
	
	public BorrowBookInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoObject) {
		_model = model;
		_view = (BorrowBookInternalFrame) view;
		((Observable) _model).addObserver(_view);
		
		_borrowModel = new DefaultListModel<Borrow>();
		_borrowModel = _view.get_borrowModel();

		_filteredSearchBookListModel = _view.getFilteredSearchBookModel();
		_searchBookModel = _view.geSearchBookModel();
		_searchBookModel = AbstractUpdateModels.updateBookModel(_searchBookModel, model);
		_filteredSearchBookListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				if(isBookInBorrowList(element,_borrowModel))
					return false;
				return true;
			}
		});

		_view.textFieldSearchBookAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("search book key released event");
				String searchString = _view.getTextFieldBookSearch();

				_filteredSearchBookListModel.setFilter(new FilteredListModel.Filter() {
					public boolean accept(Object element) {
						if(isBookInBorrowList(element,_borrowModel))
							return false;
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
		
		_filteredListModel = _view.getFilteredModel();
		_borrowedModel = _view.getBorrowedModel();
		_readerModel = _view.getReaderModel();
		_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, model);
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});

		_view.textFieldSearchAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("remove reader key released event");
				String searchString = _view.getTextFieldSearch();

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
				if(_view.getReadersListSelectedValue()!=null && _view.getBooksListSelectedValue()!=null){
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
							_view.setEnabledBtnReturnBook(false);
							//_view.listReaderkClearSelection();
							return;
						}
					}
				}
			}
		});
		
		_view.btnBorrowBookAddActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(_view.getReadersListSelectedValue()!=null && _view.getSearchBookListSelectedValue()!=null){
					Borrow borrow = new Borrow(_view.getSearchBookListSelectedValue(),_view.getReadersListSelectedValue());
					ICommand command = new AddBorrowCommand(borrow, _model);
					undoObject.insertInUnDoRedo(command);
					command.Execute();
					System.out.println("add borrow from borrow book controller");
					_view.setEnabledBtnBorrowBook(false);
					_view.listSearchBookClearSelection();
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
				setBorrowBookState();
			}
		});
		
		_view.listSearchBookAddListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setBorrowBookState();
			}
		});
		
		_view.listBooksBorrowedAddListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				_view.setEnabledBtnReturnBook(true);
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
	
	private void setBorrowBookState() {
		Object reader = _view.getReadersListSelectedValue();
		if(_readerModel.isEmpty() || _searchBookModel.isEmpty()){
			_view.setEnabledBtnBorrowBook(false);
			return;
		}

		Object book = _view.getSearchBookListSelectedValue();
		if(reader!=null && (reader instanceof Reader) && book!=null && (book instanceof Book)){
			_view.setEnabledBtnBorrowBook(true);					
		}
		else{
			_view.setEnabledBtnBorrowBook(false);
		}
	}
	
	private boolean isBookInBorrowList(Object element, DefaultListModel<Borrow> model){
		if(element == null)
			return false;
		Book bookElement = (Book) element;
		for (int idx = 0; idx < model.size(); idx++) {
			Book book = model.get(idx).getBook();
			if(book == null)
				return false;
			if (bookElement.getId() == book.getId())
				return true;
		}
		return false;
	}
}
