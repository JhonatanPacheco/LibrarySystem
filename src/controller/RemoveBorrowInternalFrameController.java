package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;

import command.ICommand;
import command.RemoveBorrowCommand;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Borrow;
import view.RemoveBorrowInternalFrame;

public class RemoveBorrowInternalFrameController {
	private ILibrary _model;
	private RemoveBorrowInternalFrame _view;
	private DefaultListModel<Borrow> _borrowModel;
	private FilteredListModel _filteredListModel;

	public RemoveBorrowInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoRedo) {
		_model = model;
		_view = (RemoveBorrowInternalFrame) view;
		((Observable) _model).addObserver(_view);
		_filteredListModel = _view.getFilteredModel();

		_borrowModel = _view.get_borrowModel();
		updateBorrowModel();
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});

		(_view).btmRemoveBorrowAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Borrow borrow = _view.getListSelectedBorrow();
				ICommand command = new RemoveBorrowCommand(borrow, model);
				undoRedo.insertInUnDoRedo(command);
				command.Execute();
				_view.setEnabledBtnRemoveBorrow(false);
			}
		});
		_view.textFieldSearchAddKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				System.out.println("remove reader key released event");
				String searchString = _view.get_textFieldSearch();

				_filteredListModel.setFilter(new FilteredListModel.Filter() {
					public boolean accept(Object element) {
						if (((Borrow) element).getReader().getFirstName().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Borrow) element).getReader().getLastName().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Borrow) element).getBook().getTitle().toLowerCase().contains(searchString.toLowerCase()))
							return true;
						if (((Borrow) element).getBook().getAuthor().toLowerCase().contains(searchString.toLowerCase()))
							return true; 
						if (((Borrow) element).getBook().getIsbn().toLowerCase().contains(searchString.toLowerCase()))
							return true; 
						if ((Integer.toString(((Borrow) element).getBook().getId()).contains(searchString.toLowerCase())))
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

	private void updateBorrowModel() {
		_borrowModel.clear();
		List<Borrow> list = Collections.synchronizedList(_model.getBorrowList());
		synchronized (list) {
			Iterator<Borrow> i = list.iterator();
			while (i.hasNext())
				_borrowModel.addElement((Borrow) i.next());
		}
		_filteredListModel.setListModelSource(_borrowModel);
	}
}
