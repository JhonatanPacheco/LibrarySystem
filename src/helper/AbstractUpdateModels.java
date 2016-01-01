package helper;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

import librarySystem.ILibrary;
import model.Book;
import model.NotDistinctBook;
import model.Borrow;
import model.Reader;

public abstract class AbstractUpdateModels {
	
	public static DefaultListModel<NotDistinctBook> updateBooksModel(DefaultListModel<NotDistinctBook> booksModel, ILibrary model) {
		booksModel.clear();
		List<Book> list = Collections.synchronizedList(model.getBooks());
		synchronized (list) {
			Iterator<Book> i = list.iterator();
			while_label: while (i.hasNext()) {
				Book book = (Book) i.next();
				for (int idx = 0; idx < booksModel.size(); idx++) {
					NotDistinctBook notDistinctBook = booksModel.get(idx);
					if (book.getAuthor().equals(notDistinctBook.getAuthor()) && book.getTitle().equals(notDistinctBook.getTitle())
							&& book.getIsbn().equals(notDistinctBook.getIsbn())) {
						notDistinctBook.addOneBook();
						continue while_label;
					}
				}
				NotDistinctBook notDistinctBook = new NotDistinctBook(book.getTitle(), book.getAuthor(), book.getIsbn());
				booksModel.addElement(notDistinctBook);
			}
		}
		List<Borrow> listBorrow = Collections.synchronizedList(model.getBorrowList());
		synchronized (listBorrow) {
			Iterator<Borrow> i = listBorrow.iterator();
			while (i.hasNext()) {
				Borrow borrow = (Borrow) i.next();
				if (borrow == null)
					continue;
				Book book = borrow.getBook();
				if (book == null)
					continue;
				for (int idx = 0; idx < booksModel.size(); idx++) {
					NotDistinctBook notDistinctBook = booksModel.get(idx);
					if (book.getAuthor().equals(notDistinctBook.getAuthor()) && book.getTitle().equals(notDistinctBook.getTitle())
							&& book.getIsbn().equals(notDistinctBook.getIsbn())) {
						notDistinctBook.borrowBook();
					}
				}
			}
		}
		return booksModel;
	}

	public static DefaultListModel<Book> updateBookModel(DefaultListModel<Book> bookModel, ILibrary model) {
		bookModel.clear();
		List<Book> list = Collections.synchronizedList(model.getBooks());
		synchronized (list) {
			Iterator<Book> i = list.iterator();
			while (i.hasNext())
				bookModel.addElement((Book) i.next());
		}
		return bookModel;
	}

	public static DefaultListModel<Reader> updateReaderModel(DefaultListModel<Reader> readerModel, ILibrary model) {
		readerModel.clear();
		List<Reader> list = Collections.synchronizedList(model.getReaders());
		synchronized (list) {
			Iterator<Reader> i = list.iterator();
			while (i.hasNext())
				readerModel.addElement((Reader) i.next());
		}
		return readerModel;
	}

	public static DefaultListModel<Borrow> updateBorrowModel(DefaultListModel<Borrow> borrowModel, ILibrary model) {
		borrowModel.clear();
		List<Borrow> list = Collections.synchronizedList(model.getBorrowList());
		synchronized (list) {
			Iterator<Borrow> i = list.iterator();
			while (i.hasNext())
				borrowModel.addElement((Borrow) i.next());
		}
		return borrowModel;
	}

	public static DefaultListModel<Book> updateBorrowedModel(DefaultListModel<Borrow> borrowModel, DefaultListModel<Book> borrowedModel, int selectedReaderCardId) {
		System.out.println("reader account reader readersList selection changed");
		borrowedModel.clear();
		
		for (int idx = 0; idx < borrowModel.size(); idx++) {
			Borrow borrow = borrowModel.get(idx);
			if (borrow.getReader() != null && borrow.getReader().getId() == selectedReaderCardId)
				borrowedModel.addElement(borrow.getBook());
		}
		return borrowedModel;
	}
}
