package command;

import librarySystem.ILibrary;
import model.Book;

public class AddBookCommand implements ICommand {
	private ILibrary _library;
	private Book _book;

	public AddBookCommand(Book book, ILibrary library) {
		_library = library;
		_book = book;
	}

	@Override
	public void Execute() {
		if (_book != null)
			_library.add_book(_book);
	}

	@Override
	public void UnExecute() {
		// TODO Auto-generated method stub
		if (_book != null)
			_library.remove_book(_book);
	}
}
