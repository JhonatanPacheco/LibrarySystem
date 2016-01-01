package command;

import java.util.ArrayList;

import librarySystem.ILibrary;
import model.Borrow;
import model.Reader;

public class RemoveReaderCommand implements ICommand {
	private ILibrary _library;
	private Reader _reader;
	ArrayList<Borrow> _borrow_list;

	public RemoveReaderCommand(Reader reader, ILibrary library) {
		_library = library;
		_reader = reader;
		_borrow_list= new ArrayList<Borrow>();
	}

	@Override
	public void Execute() {
		if (_reader != null){
			for(Borrow borrow : _library.getBorrowList()){
				if (borrow.getReader() != null && borrow.getReader().getId() == _reader.getId())
					_borrow_list.add(borrow);
			}
			_library.remove_reader(_reader);	
			for(Borrow borrow : _borrow_list)
				_library.remove_borrow(borrow);
		}
	}

	@Override
	public void UnExecute() {
		if (_reader != null){
			for(Borrow borrow : _borrow_list)
				_library.add_borrow(borrow);	
			_library.add_reader(_reader);
		}
	}
}
