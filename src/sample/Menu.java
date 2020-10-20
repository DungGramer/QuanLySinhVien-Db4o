package sample;

import com.db4o.ObjectContainer;

import java.util.Scanner;

public class Menu {
  static boolean check = false;
  public Menu(ObjectContainer db) {
    Scanner scanner = new Scanner(System.in);
    Book book = new Book();

    do {
      System.out.println("1. Them");
      System.out.println("2. Sua");
      System.out.println("3. Xoa");
      System.out.println("4. Xem");
      System.out.println("5. Tim kiem");
      System.out.println("6. Thoat");
      int key;
      System.out.print("> ");
      key = scanner.nextInt();

      switch (key) {
        case 1:
          book.addBook(db);
          break;
        case 2:
          book.updateData(db);
          break;
        case 3:
          book.deleteData(db);
          break;
        case 4:
          book.getAllData(db);
          break;
        case 5:
          book.findData(db);
          break;
        case 6:
          db.close();
          check = true;
          break;
        case 7:
          book.getAllName(db);
          break;
      }
    } while (!check);
  }

}
