package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "borrow_list")
public class Borrow {

	public static final String BOOK_ID_FIELD_NAME = "id_book";
	public static final String READER_CARD_ID_NAME = "card_id";
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = BOOK_ID_FIELD_NAME, canBeNull=false)
	private Book book;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = READER_CARD_ID_NAME, canBeNull=false)
	private Reader reader;

	public Book getBook() {
		return book;
	}

	public Reader getReader() {
		return reader;
	}
	public int getId() {
		return id;
	}

	protected Borrow() {
	}

	public Borrow(Book book, Reader reader) {
		super();
		this.book = book;
		this.reader = reader;
	}

	@Override
	public String toString() {
		return "Borrow [book=" + book + ", reader=" + reader + "]";
	}

}