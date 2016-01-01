package librarySystem;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import view.InternalFrameFactory;

public class LibrarySystem extends JFrame {

	public static final String DB_URL = "jdbc:sqlite:library.db";
	public static Library _library;
	private JDesktopPane _desktop;
	private InternalFrameFactory _internalFrameFactory;
	private static UndoRedo _undoObject;

	public UndoRedo get_undoObject() {
		return LibrarySystem._undoObject;
	}

	public void setUndoObject(UndoRedo undoObject) {
		LibrarySystem._undoObject = undoObject;
	}

	private static void SetUndoRedo() {
		_undoObject = null;
		_undoObject = new UndoRedo();
	}

	public static void main(String[] args) {
		ConnectionSource connectionSource = null;
		try {
			connectionSource = new JdbcConnectionSource(DB_URL);

			_library = new Library(connectionSource);

			SetUndoRedo();
			
			//WriteSampleData.writeSampleData(_library);

			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						createAndShowGUI();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception exc) {
			System.err.println("Problem z otwarciem polaczenia");
		} finally {
			// destroy the data source which should close underlying connections
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * 
	 * @throws SQLException
	 */
	private static void createAndShowGUI() throws SQLException {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		LibrarySystem librarySystem = new LibrarySystem();
		librarySystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		librarySystem.setVisible(true);
	}

	public LibrarySystem() throws SQLException {
		super("Library");

		// Make the big window be indented 150 pixels from each edge
		// of the screen.
		int inset = 150;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

		// Set up the GUI.
		_desktop = new JDesktopPane(); // a specialized layered pane

		// create first "window"
		setContentPane(_desktop);

		MainMenuBar mainMenuBar = new MainMenuBar(_desktop, _library, _undoObject);

		setJMenuBar(mainMenuBar);

		// Make dragging a little faster but perhaps uglier.
		_desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}
}
