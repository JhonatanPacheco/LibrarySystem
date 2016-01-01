package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "books")
public class Book {

	// Fields
	public final static String ID_FIELD_NAME = "id_book";

	@DatabaseField
	private String author;
	@DatabaseField
	private String title;
	@DatabaseField
	private String isbn;
	@DatabaseField(generatedId = true, columnName = ID_FIELD_NAME,allowGeneratedIdInsert = true)
	private int id_book;

	// Property
	public int getId() {
		return id_book;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	protected Book() {
		// ORMLite needs a no-arg constructor
	}

	public Book(String title, String author, String isbn) {
		super();
		this.author = author;
		this.title = title;
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Book [author=" + author + ", title=" + title + ", isbn=" + isbn + ", Id=" + id_book + " ]";
	}
}
