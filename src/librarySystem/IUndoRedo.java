package librarySystem;

import command.ICommand;

public interface IUndoRedo {

	public void insertInUnDoRedo(ICommand command);
}
