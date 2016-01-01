package librarySystem;

import java.util.ArrayList;

import model.Book;
import model.Borrow;
import model.Reader;

public interface ILibrary {

	public ArrayList<Book> getBooks();

	public ArrayList<Reader> getReaders();

	public ArrayList<Borrow> getBorrowList();

	public boolean add_book(Book book);

	public boolean remove_book(Book book);

	public boolean add_reader(Reader czytelnik);

	public boolean remove_reader(Reader czytelnik);

	public boolean add_borrow(Borrow borrow);

	public boolean remove_borrow(Borrow borrow);
}
