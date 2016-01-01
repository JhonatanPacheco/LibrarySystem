package librarySystem;

import java.util.Observable;
import java.util.Stack;

import command.ICommand;

public class UndoRedo extends Observable implements IUndoRedo {

	private Stack<ICommand> _undoCommands = new Stack<ICommand>();
	private Stack<ICommand> _redoCommands = new Stack<ICommand>();

	public void Redo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_redoCommands.size() != 0) {
				ICommand command = _redoCommands.pop();
				command.Execute();
				_undoCommands.push(command);
				setChanged();
				notifyObservers(this);
			}
		}
	}

	public void Undo(int levels) {
		for (int i = 1; i <= levels; i++) {
			if (_undoCommands.size() != 0) {
				ICommand command = _undoCommands.pop();
				command.UnExecute();
				_redoCommands.push(command);
				setChanged();
				notifyObservers(this);
			}
		}
	}

	public void insertInUnDoRedo(ICommand command) {
		_undoCommands.push(command);
		_redoCommands.clear();
		setChanged();
		notifyObservers();
	}

	public boolean IsUndoPossible() {
		if (_undoCommands.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean IsRedoPossible() {
		if (_redoCommands.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
}
