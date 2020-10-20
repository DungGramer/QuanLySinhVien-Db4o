package sample;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class a {
  public static void main(String[] args) {
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "./book.db4o");
    new Menu(db);
  }
}
