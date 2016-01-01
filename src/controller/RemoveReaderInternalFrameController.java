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
import command.RemoveReaderCommand;
import helper.AbstractUpdateModels;
import helper.FilteredListModel;
import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import model.Reader;
import view.RemoveReaderInternalFrame;

public class RemoveReaderInternalFrameController {
	private ILibrary _model;
	private RemoveReaderInternalFrame _view;
	private DefaultListModel<Reader> _readerModel;
	private FilteredListModel _filteredListModel;

	public RemoveReaderInternalFrameController(final JInternalFrame view, final ILibrary model,
			final IUndoRedo undoRedo) {
		_model = model;
		_view = (RemoveReaderInternalFrame) view;
		((Observable) _model).addObserver(_view);
		_filteredListModel = _view.getFilteredModel();

		_readerModel = _view.get_readerModel();
		_readerModel = AbstractUpdateModels.updateReaderModel(_readerModel, model);
		_filteredListModel.setFilter(new FilteredListModel.Filter() {
			public boolean accept(Object element) {
				return true;
			}
		});

		_view.btmRemoveReaderAddListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reader reader = _view.listSelectedReader();
				ICommand command = new RemoveReaderCommand(reader, model);
				undoRedo.insertInUnDoRedo(command);
				command.Execute();
				_view.setEnabledBtnRemoveReader(false);
				_view.listReaderClearSelection();
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
	}
}
