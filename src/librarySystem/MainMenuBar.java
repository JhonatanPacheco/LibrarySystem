package librarySystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class MainMenuBar extends JMenuBar{
	private JDesktopPane _desktop;
	private ILibrary _library;
	private UndoRedo _undoObject;

	public MainMenuBar(JDesktopPane desktop, ILibrary library, UndoRedo undoObject) {
		super();
		_desktop = desktop;
		_library = library;
		_undoObject = undoObject;
		// Readers
		JMenu menuReaders = new JMenu("Readers");
		add(menuReaders);

		JMenuItem menuItemReaders = new JMenuItem("Reader Account Balance");
		menuItemReaders.setActionCommand("readerAccountBalance");
		menuItemReaders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemReaders.getActionCommand(), desktop, library,
						undoObject);
			}
		});
		menuReaders.add(menuItemReaders);

		JMenuItem menuItemAddReader = new JMenuItem("Add Reader");
		menuItemAddReader.setActionCommand("addReader");
		menuItemAddReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemAddReader.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuReaders.add(menuItemAddReader);

		JMenuItem menuItemRemoveReader = new JMenuItem("Remove Reader");
		menuItemRemoveReader.setActionCommand("removeReader");
		menuItemRemoveReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemRemoveReader.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuReaders.add(menuItemRemoveReader);

		// Readers
		JMenu menuBooks = new JMenu("Books");
		add(menuBooks);

		JMenuItem menuItemBookAvailability = new JMenuItem("Book Availability");
		menuItemBookAvailability.setActionCommand("bookAvailbility");
		menuItemBookAvailability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemBookAvailability.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuBooks.add(menuItemBookAvailability);

		JMenuItem menuItemAddBook = new JMenuItem("Add Book");
		menuItemAddBook.setActionCommand("addBook");
		menuItemAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemAddBook.getActionCommand(), desktop, library,
						undoObject);
			}
		});
		menuBooks.add(menuItemAddBook);

		JMenuItem menuItemRemoveBook = new JMenuItem("Remove Book");
		menuItemRemoveBook.setActionCommand("removeBook");
		menuItemRemoveBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemRemoveBook.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuBooks.add(menuItemRemoveBook);

		// Borrow
		JMenu menuBorrow = new JMenu("Borrow");
		add(menuBorrow);

		JMenuItem menuItemRemoveBorrow = new JMenuItem("Remove Borrow");
		menuItemRemoveBorrow.setActionCommand("removeBorrow");
		menuItemRemoveBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemRemoveBorrow.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuBorrow.add(menuItemRemoveBorrow);

		JMenuItem menuItemBorrowBook = new JMenuItem("Borrow Book");
		menuItemBorrowBook.setActionCommand("borrowBook");
		menuItemBorrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractViewControllerFactory.createViewController(menuItemBorrowBook.getActionCommand(), desktop,
						library, undoObject);
			}
		});
		menuBorrow.add(menuItemBorrowBook);

		// Edit Action
		JMenu menuEdit = new JMenu("Edit");
		add(menuEdit);

		MenuUndo menuItemUndo = new MenuUndo("Undo");
		menuItemUndo.setActionCommand("undo");
		menuItemUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		menuItemUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoObject.Undo(1);
			}
		});
		menuEdit.add(menuItemUndo);

		MenuRedo menuItemRedo = new MenuRedo("Redo");
		menuItemRedo.setActionCommand("redo");
		menuItemRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,
				java.awt.Event.CTRL_MASK + java.awt.Event.SHIFT_MASK));
		menuItemRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoObject.Redo(1);
			}
		});
		menuEdit.add(menuItemRedo);


		undoObject.addObserver(menuItemUndo);
		undoObject.addObserver(menuItemRedo);
	}
}
