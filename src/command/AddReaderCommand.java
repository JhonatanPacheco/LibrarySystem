package command;

import librarySystem.ILibrary;
import model.Reader;

public class AddReaderCommand implements ICommand {
	private ILibrary _library;
	private Reader _reader;

	public AddReaderCommand(final Reader reader, ILibrary library) {
		_library = library;
		_reader = reader;
	}

	@Override
	public void Execute() {
		if (_reader != null)
			_library.add_reader(_reader);
	}

	@Override
	public void UnExecute() {
		if (_reader != null)
			_library.remove_reader(_reader);
	}
}
