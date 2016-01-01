package model;

public class NotDistinctBook extends Book{
	int _numberOfBooks=1;
	int _numberOfBorrowed=0;
	boolean _available = true;

	public int get_numberOfBooks() {
		return _numberOfBooks;
	}
	public int getNumberOfRented() {
		return _numberOfBorrowed;
	}
	public boolean is_available(){
		if(_numberOfBorrowed>=_numberOfBooks)
			_available = false;
		else
			_available = true;
		return _available;
	}
	
	public void borrowBook(){
		this._numberOfBorrowed++;
		is_available();
	}
	public void addOneBook(){
		this._numberOfBooks++;
		is_available();
	}
	
	public NotDistinctBook(String title, String author, String isbn){
		super(title, author, isbn);
	}
	@Override
	public String toString() {
		return super.getAuthor() + ", " + super.getTitle() + ", isbn=" + super.getIsbn() + ", total=" + _numberOfBooks
				+ ", borrowed=" + _numberOfBorrowed;
	}
}