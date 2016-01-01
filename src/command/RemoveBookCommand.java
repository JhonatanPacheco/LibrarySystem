package command;

import java.util.ArrayList;

import librarySystem.ILibrary;
import model.Book;
import model.Borrow;

public class RemoveBookCommand implements ICommand {
	private ILibrary _library;
	private Book _book;
	ArrayList<Borrow> _borrow_list;

	public RemoveBookCommand(Book book, ILibrary library) {
		_library = library;
		_book = book;
		_borrow_list= new ArrayList<Borrow>();
	}

	@Override
	public void Execute() {
		if (_book != null){		
			for(Borrow borrow : _library.getBorrowList()){
				if (borrow.getBook() != null && borrow.getBook().getId() == _book.getId())
					_borrow_list.add(borrow);
			}
			_library.remove_book(_book);	
			for(Borrow borrow : _borrow_list)
				_library.remove_borrow(borrow);
		}


	}

	@Override
	public void UnExecute() {
		if (_book != null){
			
			for(Borrow borrow : _borrow_list)
				_library.add_borrow(borrow);	
			_library.add_book(_book);
		}
	}
}
