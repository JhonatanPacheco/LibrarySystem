package command;

import librarySystem.ILibrary;
import model.Borrow;

public class AddBorrowCommand implements ICommand {
	private ILibrary _library;
	private Borrow _borrow;

	public AddBorrowCommand(Borrow borrow, ILibrary library) {
		_library = library;
		_borrow = borrow;
	}

	@Override
	public void Execute() {
		if (_borrow != null)
			_library.add_borrow(_borrow);
	}

	@Override
	public void UnExecute() {
		if (_borrow != null)
			_library.remove_borrow(_borrow);
	}
}
