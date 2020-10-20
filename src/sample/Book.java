package sample;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import java.util.List;
import java.util.Scanner;

public class Book {
  private static Scanner scanner = new Scanner(System.in);
  private String bookID;
  private String nameBook;
  private String authorID;
  private String maNXB;
  private int year;
  private String description;

  public Book() {
  }

  public Book(String nameBook) {
    this.nameBook = nameBook;
  }

  public Book(String bookID, String nameBook, String maNXB, String authorID, int year, String description) {
    this.bookID = bookID;
    this.nameBook = nameBook;
    this.maNXB = maNXB;
    this.authorID = authorID;
    this.year = year;
    this.description = description;
  }

  public static void openDb(String path) {
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), path);
  }

  public void addBook(ObjectContainer db) {
    //String bookID, String nameBook, String author, String authorID, int year, String description
    System.out.println("Nhap id sach: ");
    String bookID = scanner.nextLine();
    System.out.println("Nhap ten sach: ");
    String nameBook = scanner.nextLine();
    System.out.println("Nhap tac gia: ");
    String author = scanner.nextLine();
    System.out.println("Nhap ma tac gia: ");
    String authorID = scanner.nextLine();
    System.out.println("Nhap nam xuat ban: ");
    int year = scanner.nextInt();
    System.out.println("Nhap tom tat: ");
    String description = scanner.nextLine();

    Book book = new Book(bookID, nameBook, author, authorID, year, description);
    db.store(book);
  }

  public static void listResult(List<?> result) {
    for (Object obj : result) {
      System.out.println(obj);
    }
  }

  public static void getAllData(ObjectContainer db) {
//    ObjectSet books = db.queryByExample(Book.class);
    List<Book> books = db.query(Book.class);
    Book.listResult(books);
  }

  public void updateData(ObjectContainer db) {
    System.out.printf("Nhap ten can sua: ");
    String find = scanner.nextLine();

    ObjectSet result = db.queryByExample(new Book(find));
    Book found = (Book) result.next();

    System.out.printf("Nhap muc muon sua: " +
        "\n1. Ten" +
        "\n2. Tac gia" +
        "\n3. Mo ta" +
        "\n4. Nam" +
        "\n> ");
    int select = scanner.nextInt();
    String data = "";

    switch (select) {
      case 1:
        System.out.printf("Nhap lai ten: ");
        scanner.nextLine();
        data = scanner.nextLine();
        found.setNameBook(data);
        break;
      case 2:
        System.out.printf("Nhap lai tac gia: ");
        scanner.nextLine();
        data = scanner.nextLine();
        found.setMaNXB(data);
        break;
      case 3:
        System.out.printf("Nhap lai mo ta: ");
        scanner.nextLine();
        data = scanner.nextLine();
        found.setDescription(data);
        break;
      case 4:
        System.out.printf("Nhap lai nam xuat ban: ");
        scanner.nextLine();
        data = scanner.nextLine();
        found.setYear(Integer.parseInt(data));
        break;
    }
    db.store(found);
  }

  public void deleteData(ObjectContainer db) {
    System.out.printf("Nhap ten sach can xoa: ");
    String find = scanner.nextLine();

    ObjectSet result = db.queryByExample(new Book(find));
    Book found = (Book) result.next();

    db.delete(found);
  }

  public static void findData(ObjectContainer db) {
    List<Book> books = db.query(new Predicate<Book>() {
      String find = scanner.nextLine();

      @Override
      public boolean match(Book book) {
        return book.getNameBook().equals(find) || book.getMaNXB().equals(find) || book.getDescription().equals(find) || book.getYear() == Integer.parseInt(find);
      }
    });
    listResult(books);
  }

  public static void getAllName(ObjectContainer db) {
    List<Book> books = db.query(Book.class);
    for (int i = 0; i < books.size(); i++) {
      System.out.println(books.get(i).getNameBook());
    }
  }

  public static Scanner getScanner() {
    return scanner;
  }

  public static void setScanner(Scanner scanner) {
    Book.scanner = scanner;
  }

  public String getBookID() {
    return bookID;
  }

  public void setBookID(String bookID) {
    this.bookID = bookID;
  }

  public String getNameBook() {
    return nameBook;
  }

  public void setNameBook(String nameBook) {
    this.nameBook = nameBook;
  }

  public String getMaNXB() {
    return maNXB;
  }

  public void setMaNXB(String maNXB) {
    this.maNXB = maNXB;
  }

  public String getAuthorID() {
    return authorID;
  }

  public void setAuthorID(String authorID) {
    this.authorID = authorID;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Ten: " + nameBook + ", Tac gia: " + maNXB + ", Mo ta: " + description + ", Nam xuat ban: " + year;
  }
}
