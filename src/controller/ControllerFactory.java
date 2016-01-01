package controller;

import javax.swing.JInternalFrame;

import librarySystem.ILibrary;
import librarySystem.IUndoRedo;
import view.AddBookInternalFrame;
import view.AddReaderInternalFrame;
import view.BookAvailbilityInternalFrame;
import view.BorrowBookInternalFrame;
import view.ReaderAccountBalanceInternalFrame;
import view.RemoveBookInternalFrame;
import view.RemoveBorrowInternalFrame;
import view.RemoveReaderInternalFrame;

public class ControllerFactory {
	public static void createController(JInternalFrame view, ILibrary library, IUndoRedo undoObject) {
		if (view instanceof AddBookInternalFrame)
			new AddBookInternalFrameController(view, library, undoObject);
		else if (view instanceof AddReaderInternalFrame)
			new AddReaderInternalFrameController(view, library, undoObject);
		else if (view instanceof BookAvailbilityInternalFrame)
			new BookAvailbilityInternalFrameController(view, library, undoObject);
		else if (view instanceof BorrowBookInternalFrame)
			new BorrowBookInternalFrameController(view, library, undoObject);
		else if (view instanceof ReaderAccountBalanceInternalFrame)
			new ReaderAccountBalanceInternalFrameController(view, library, undoObject);
		else if (view instanceof RemoveBookInternalFrame)
			new RemoveBookInternalFrameController(view, library, undoObject);
		else if (view instanceof RemoveBorrowInternalFrame)
			new RemoveBorrowInternalFrameController(view, library, undoObject);
		else if (view instanceof RemoveReaderInternalFrame)
			new RemoveReaderInternalFrameController(view, library, undoObject);
	}
}
