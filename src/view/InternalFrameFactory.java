package view;

import java.sql.SQLException;

import javax.swing.JInternalFrame;

import librarySystem.ILibrary;

public abstract class InternalFrameFactory {

	public static JInternalFrame createFrameFactory(String command, ILibrary library) {
		JInternalFrame internalFrame = new JInternalFrame();
		internalFrame = null;
		switch (command) {
		case "readerAccountBalance":
			try {
				internalFrame = new ReaderAccountBalanceInternalFrame(library);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			internalFrame.setVisible(true);
			break;
		case "addReader":
			internalFrame = new AddReaderInternalFrame(library);
			internalFrame.setVisible(true);
			break;
		case "removeReader":
			try {
				internalFrame = new RemoveReaderInternalFrame(library);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			internalFrame.setVisible(true);
			break;
		case "bookAvailbility":
			internalFrame = new BookAvailbilityInternalFrame(library);
			internalFrame.setVisible(true);
			break;
		case "addBook":
			internalFrame = new AddBookInternalFrame(library);
			internalFrame.setVisible(true);
			break;
		case "removeBook":
			try {
				internalFrame = new RemoveBookInternalFrame(library);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			internalFrame.setVisible(true);
			break;
		case "removeBorrow":
			try {
				internalFrame = new RemoveBorrowInternalFrame(library);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			internalFrame.setVisible(true);
			break;
		case "borrowBook":
			try {
				internalFrame = new BorrowBookInternalFrame(library);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			internalFrame.setVisible(true);
			break;
		}
		return internalFrame;
	}
}
