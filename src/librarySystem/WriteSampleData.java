package librarySystem;

import java.sql.SQLException;

import model.Book;
import model.Borrow;
import model.Reader;

public abstract class WriteSampleData {

	public static void writeSampleData(ILibrary library) throws SQLException {
		// -------------------------------------------
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
		Borrow adrainStar = new Borrow(starWarsUczenJedi2, adrianLuk);

		library.add_borrow(romekHobbita);
		library.add_borrow(adrainStar);

	}
}
