package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import librarySystem.Library;
import model.Book;
import model.Borrow;
import model.Reader;

public class LibraryTest {

	@Test
	public void getReadersEmptyTest_EmptyTableMustBeEmpty() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Reader> readers = library.getReaders();
		assertNotNull(readers);
		assertEquals(0, readers.size());
	}

	@Test
	public void getBooksEmptyTest_EmptyTableMustBeEmpty() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Book> books = library.getBooks();
		assertNotNull(books);
		assertEquals(0, books.size());
	}

	@Test
	public void getBorrowListEmptyTest_EmptyTableMustBeEmpty() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Borrow> borrow_list = library.getBorrowList();
		assertNotNull(borrow_list);
		assertEquals(0, borrow_list.size());
		;
	}

	@Test
	public void add_ReaderTest_MustAddReturnCzytelnicy() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Reader> readers = null;
		Reader romekBombel = new Reader("Romek", "Bombel");
		Reader adrianLuk = new Reader("Adrian", "Luk");
		Reader przemekRambo = new Reader("Przemek", "Rambo");
		library.add_reader(romekBombel);

		readers = library.getReaders();
		assertNotNull(readers);
		assertEquals(1, readers.size());
		assertEquals(romekBombel.getFirstName(), readers.get(0).getFirstName());
		assertEquals(romekBombel.getLastName(), readers.get(0).getLastName());
		assertEquals(romekBombel.getId(), readers.get(0).getId());

		library.add_reader(adrianLuk);
		library.add_reader(przemekRambo);
		readers = library.getReaders();

		assertEquals(3, readers.size());
		assertEquals(przemekRambo.getFirstName(), readers.get(2).getFirstName());
		assertEquals(przemekRambo.getLastName(), readers.get(2).getLastName());
		assertEquals(przemekRambo.getId(), readers.get(2).getId());
	}

	@Test
	public void add_BookTest_MustAddReturnCzytelnicy() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Book> books = null;
		Book hobbitNiezwyklaPodroz = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book hobbitNiezwyklaPodroz2 = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book wladcaPierscieniDruzyna = new Book("Wladca Pierscieni - Druzyna Pierscienia", "Tolkien", "222");
		Book starWarsUczenJedi2 = new Book("Star Wars - Uczen Jedi #2 Mroczny Przeciwnik", "Jude Watson", "333");

		library.add_book(hobbitNiezwyklaPodroz);

		books = library.getBooks();
		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals(hobbitNiezwyklaPodroz.getTitle(), books.get(0).getTitle());
		assertEquals(hobbitNiezwyklaPodroz.getAuthor(), books.get(0).getAuthor());
		assertEquals(hobbitNiezwyklaPodroz.getIsbn(), books.get(0).getIsbn());

		library.add_book(hobbitNiezwyklaPodroz2);
		library.add_book(wladcaPierscieniDruzyna);
		library.add_book(starWarsUczenJedi2);

		books = library.getBooks();

		assertEquals(4, books.size());
		assertEquals(starWarsUczenJedi2.getTitle(), books.get(3).getTitle());
		assertEquals(starWarsUczenJedi2.getAuthor(), books.get(3).getAuthor());
		assertEquals(starWarsUczenJedi2.getIsbn(), books.get(3).getIsbn());
	}
	
	@Test
	public void add_BorrowTest_MustAddReturnCzytelnicy() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Borrow> borrow_list = null;

		Reader romekBombel = new Reader("Romek", "Bombel");
		Reader adrianLuk = new Reader("Adrian", "Luk");
		Reader przemekRambo = new Reader("Przemek", "Rambo");

		library.add_reader(romekBombel);
		library.add_reader(adrianLuk);
		library.add_reader(przemekRambo);

		Book hobbitNiezwyklaPodroz = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book hobbitNiezwyklaPodroz2 = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book wladcaPierscieniDruzyna = new Book("Wladca Pierscieni - Druzyna Pierscienia", "Tolkien", "222");
		Book starWarsUczenJedi2 = new Book("Star Wars - Uczen Jedi #2 Mroczny Przeciwnik", "Jude Watson", "333");

		library.add_book(hobbitNiezwyklaPodroz);
		library.add_book(hobbitNiezwyklaPodroz2);
		library.add_book(wladcaPierscieniDruzyna);
		library.add_book(starWarsUczenJedi2);

		Borrow romekHobbita = new Borrow(hobbitNiezwyklaPodroz, romekBombel);

		library.add_borrow(romekHobbita);

		borrow_list = library.getBorrowList();

		assertNotNull(borrow_list);
		assertEquals(1, borrow_list.size());
		assertEquals(romekHobbita.getReader().getId(), borrow_list.get(0).getReader().getId());
		assertEquals(romekHobbita.getReader().getFirstName(), borrow_list.get(0).getReader().getFirstName());
		assertEquals(romekHobbita.getReader().getLastName(), borrow_list.get(0).getReader().getLastName());
	}

	@Test
	public void remove_ReaderTest_MustRemove() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Reader> readers = null;
		Reader romekBombel = new Reader("Romek", "Bombel");
		Reader adrianLuk = new Reader("Adrian", "Luk");
		Reader adrianLuk2 = new Reader("Adrian", "Luk");
		Reader przemekRambo = new Reader("Przemek", "Rambo");
		library.add_reader(romekBombel);
		library.remove_reader(romekBombel);
		library.add_reader(romekBombel);
		library.remove_reader(romekBombel);
		readers = library.getReaders();
		assertEquals(0, readers.size());

		library.add_reader(adrianLuk);
		library.add_reader(przemekRambo);
		readers = library.getReaders();
		assertEquals(2, readers.size());

		library.add_reader(adrianLuk);
		library.add_reader(adrianLuk2);
		library.remove_reader(adrianLuk2);
		readers = library.getReaders();
		assertEquals(2, readers.size());
	}

	@Test
	public void remove_BookTest() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Book> books = null;
		Book hobbitNiezwyklaPodroz = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book hobbitNiezwyklaPodroz2 = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");

		library.add_book(hobbitNiezwyklaPodroz);
		library.remove_book(hobbitNiezwyklaPodroz);
		books = library.getBooks();
		assertEquals(0, books.size());

		library.remove_book(hobbitNiezwyklaPodroz);
		books = library.getBooks();
		assertEquals(0, books.size());

		library.add_book(hobbitNiezwyklaPodroz);
		library.add_book(hobbitNiezwyklaPodroz2);
		library.remove_book(hobbitNiezwyklaPodroz2);
		books = library.getBooks();
		assertEquals(1, books.size());

		library.remove_book(hobbitNiezwyklaPodroz);
		books = library.getBooks();
		assertEquals(0, books.size());

		library.remove_book(hobbitNiezwyklaPodroz);
		books = library.getBooks();
		assertEquals(0, books.size());
	}

	@Test
	public void remove_BorrowTest_MustRemove() throws SQLException {
		String DB_URL = "jdbc:sqlite:libraryTest.db";
		ConnectionSource connectionSource = new JdbcConnectionSource(DB_URL);
		Library library = new Library(connectionSource);
		TableUtils.createTableIfNotExists(connectionSource, Reader.class);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);
		TableUtils.createTableIfNotExists(connectionSource, Borrow.class);
		TableUtils.clearTable(connectionSource, Reader.class);
		TableUtils.clearTable(connectionSource, Book.class);
		TableUtils.clearTable(connectionSource, Borrow.class);

		ArrayList<Borrow> borrow_list = null;

		Reader romekBombel = new Reader("Romek", "Bombel");
		Reader adrianLuk = new Reader("Adrian", "Luk");
		Reader przemekRambo = new Reader("Przemek", "Rambo");

		library.add_reader(romekBombel);
		library.add_reader(adrianLuk);
		library.add_reader(przemekRambo);

		Book hobbitNiezwyklaPodroz = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book hobbitNiezwyklaPodroz2 = new Book("Hobbit - Niezwykla Podroz", "Tolkien", "111");
		Book wladcaPierscieniDruzyna = new Book("Wladca Pierscieni - Druzyna Pierscienia", "Tolkien", "222");
		Book starWarsUczenJedi2 = new Book("Star Wars - Uczen Jedi #2 Mroczny Przeciwnik", "Jude Watson", "333");

		library.add_book(hobbitNiezwyklaPodroz);
		library.add_book(hobbitNiezwyklaPodroz2);
		library.add_book(wladcaPierscieniDruzyna);
		library.add_book(starWarsUczenJedi2);

		Borrow romekHobbita = new Borrow(hobbitNiezwyklaPodroz, romekBombel);
		Borrow adrianHobbita2 = new Borrow(hobbitNiezwyklaPodroz2, romekBombel);
		Borrow przemekStar = new Borrow(starWarsUczenJedi2, przemekRambo);

		library.add_borrow(romekHobbita);
		library.remove_borrow(romekHobbita);
		borrow_list = library.getBorrowList();
		assertNotNull(borrow_list);
		assertEquals(0, borrow_list.size());

		library.add_borrow(romekHobbita);
		library.add_borrow(adrianHobbita2);

		borrow_list = library.getBorrowList();

		assertNotNull(borrow_list);
		assertEquals(2, borrow_list.size());

		library.remove_borrow(adrianHobbita2);
		library.remove_borrow(romekHobbita);

		borrow_list = library.getBorrowList();
		assertNotNull(borrow_list);
		assertEquals(0, borrow_list.size());

		library.add_borrow(przemekStar);
		library.add_borrow(romekHobbita);
		borrow_list = library.getBorrowList();
		assertEquals(romekHobbita.getReader().getId(), borrow_list.get(1).getReader().getId());
		assertEquals(romekHobbita.getReader().getFirstName(), borrow_list.get(1).getReader().getFirstName());
		assertEquals(romekHobbita.getReader().getLastName(), borrow_list.get(1).getReader().getLastName());
	}
}
