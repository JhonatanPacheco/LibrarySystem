package librarySystem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import model.Book;
import model.Borrow;
import model.Reader;

public class Library extends Observable implements ILibrary {

	// Fields
	private Dao<Book, Integer> _booksDao;
	private Dao<Reader, Integer> _readersDao;
	private Dao<Borrow, Integer> _borrowDao;

	private static ConnectionSource _connectionSource;

	// Properties
	public ArrayList<Book> getBooks() {
		ArrayList<Book> ksiazki = null;
		try {
			ksiazki = (ArrayList<Book>) _booksDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ksiazki;
	}

	public ArrayList<Reader> getReaders() {
		ArrayList<Reader> readers = null;
		try {
			readers = (ArrayList<Reader>) _readersDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readers;
	}

	public ArrayList<Borrow> getBorrowList() {
		ArrayList<Borrow> borrow_list = null;
		try {
			borrow_list = (ArrayList<Borrow>) _borrowDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrow_list;
	}

	public boolean add_book(Book book) {
		boolean successFlag = false;
		try {
			_booksDao.create(book);
			successFlag = true;
			setChanged();
			notifyObservers(book);
			System.out.println("added book");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	public boolean remove_book(Book book) {
		boolean successFlag = false;
		try {
			if (_booksDao.queryForMatching(book) != null) {
				_booksDao.delete(book);
				successFlag = true;
				setChanged();
				notifyObservers(book);
				System.out.println("remove book");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	public boolean add_reader(Reader reader) {
		boolean successFlag = false;
		try {
			_readersDao.create(reader);
			successFlag = true;
			setChanged();
			notifyObservers(reader);
			System.out.println("added reader");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	public boolean remove_reader(Reader reader) {
		boolean successFlag = false;
		try {
			if (_readersDao.queryForMatching(reader) != null) {
				_readersDao.delete(reader);
				successFlag = true;
				setChanged();
				notifyObservers(reader);
				
				System.out.println("remove reader");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	public boolean add_borrow(Borrow borrow) {
		boolean successFlag = false;
		try {
			_borrowDao.create(borrow);
			successFlag = true;
			setChanged();
			notifyObservers(borrow);
			System.out.println("Add borrow");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	public boolean remove_borrow(Borrow borrow) {
		boolean successFlag = false;
		try {
			if (_borrowDao.queryForMatching(borrow) != null) {
				_borrowDao.delete(borrow);
				successFlag = true;
				setChanged();
				notifyObservers(borrow);
				System.out.println("Remove borrow");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successFlag;
	}

	// Constructors
	public Library(ConnectionSource connectionSource) {
		Library._connectionSource = connectionSource;
		setupDatabase(connectionSource);
	}

	/**
	 * Setup our database and DAOs
	 */
	private void setupDatabase(ConnectionSource connectionSource) {

		try {
			_readersDao = DaoManager.createDao(connectionSource, Reader.class);
			_booksDao = DaoManager.createDao(connectionSource, Book.class);
			_borrowDao = DaoManager.createDao(connectionSource, Borrow.class);
			createTables(connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createTables(ConnectionSource connectionSource) throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
	}
}
