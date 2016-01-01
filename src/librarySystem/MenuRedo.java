package librarySystem;

import java.util.Observer;

import javax.swing.JMenuItem;

public class MenuRedo extends JMenuItem implements Observer {

	public MenuRedo(String string) {
		super(string);
		setEnabled(false);
	}

	@Override
	public void update(java.util.Observable o, Object arg) {
		setEnabled(((UndoRedo) o).IsRedoPossible());
		System.out.println("update redo history");
	}

}