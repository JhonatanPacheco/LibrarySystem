package librarySystem;

import java.util.Observer;

import javax.swing.JMenuItem;

public class MenuUndo extends JMenuItem implements Observer {

	public MenuUndo(String string) {
		super(string);
		setEnabled(false);
	}

	@Override
	public void update(java.util.Observable o, Object arg) {
		setEnabled(((UndoRedo) o).IsUndoPossible());
		System.out.println("update undo history");
	}

}