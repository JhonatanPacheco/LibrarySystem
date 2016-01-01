package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "readers")
public class Reader {

	// Fields
	public final static String CARD_ID_FIELD_NAME = "card_id";
	@DatabaseField
	private String firstName;
	@DatabaseField
	private String lastName;
	@DatabaseField(generatedId = true, columnName = CARD_ID_FIELD_NAME,allowGeneratedIdInsert=true)
	private int card_id;

	// Property
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getId() {
		return card_id;
	}

	// Constructors
	public Reader(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	protected Reader() {
		// ORMLite needs a no-arg constructor
	}

	// Methods
	public String toString() {
		return "Reader [firstName=" + firstName + ", lastName=" + lastName + ", card_id=" + card_id + "]";
	}
}
