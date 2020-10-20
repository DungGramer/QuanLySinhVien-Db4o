package sample;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
  @FXML
  private Label lblCenter;

  @FXML
  private MenuItem menuOpenFile;
  @FXML
  private MenuItem menuAdd;
  @FXML
  private MenuItem menuClose;
  @FXML
  private MenuItem helpMenu;

  @FXML
  private TableView<Book> bookTableView;
  @FXML
  private TableColumn<Book, String> colNameID;
  @FXML
  private TableColumn<Book, String> colName;
  @FXML
  private TableColumn<Book, String> colAuthorID;
  @FXML
  private TableColumn<Book, String> colnxbID;
  @FXML
  private TableColumn<Book, String> colDescription;
  @FXML
  private TableColumn<Book, Integer> colYear;

  @FXML
  private TextField tfNameID;
  @FXML
  private TextField tfName;
  @FXML
  private TextField tfAuthorID;
  @FXML
  private TextField tfnxbID;
  @FXML
  private TextField tfYear;
  @FXML
  private TextField tfDescription;
  @FXML
  private TextField tfSearch;

  private Desktop desktop = Desktop.getDesktop();
  ObjectContainer db;
  String path;

  @FXML
  private void initialize() {

    menuOpenFile.setOnAction(actionEvent -> {
      path = openFile();
      db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), path);
      createTable();
    });

    menuAdd.setOnAction(actionEvent -> {
      try {
        Stage stage = (Stage) bookTableView.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("addtoDb.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    helpMenu.setOnAction(actionEvent -> {
      lblCenter.setText("Hello");
    });

    menuClose.setOnAction(actionEvent -> {
      db.close();
    });

  }

  private String openFile() {
    //Tạo Filter mở file
    FileChooser.ExtensionFilter db4oFilter = new FileChooser.ExtensionFilter("db4o File", "*.db4o");

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(db4oFilter);

    String path = "";
    fileChooser.setTitle("Open File");
    Window primaryStage = null;
    File file = fileChooser.showOpenDialog(primaryStage);
    if (file != null) {
      path = file.getAbsolutePath();
    }
    return path;
  }

  public void createTable() {
    colNameID.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
    colName.setCellValueFactory(new PropertyValueFactory<Book, String>("nameBook"));
    colAuthorID.setCellValueFactory(new PropertyValueFactory<Book, String>("authorID"));
    colnxbID.setCellValueFactory(new PropertyValueFactory<Book, String>("maNXB"));
    colYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
    colDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));

    bookTableView.setItems(getAllBook(db));
  }

  public void onSave() {
    String nameID = tfNameID.getText();
    String nameBook = tfName.getText();
    String author = tfAuthorID.getText();
    String nxbID = tfnxbID.getText();
    int year = Integer.parseInt(tfYear.getText());
    String description = tfDescription.getText();

    Book book = new Book(nameID, nameBook, author, nxbID, year, description);
    db.store(book);

    createTable();
  }

  public void searching() {
    String value = tfSearch.getText();
    if (value.equals("") || value.equals(" ") || value.length() == 0) {
      bookTableView.setItems(getAllBook(db));
    }
    Query query = db.query();
    query.constrain(Book.class);
    query.descend("nameBook").constrain(value);
    ObjectSet result = query.execute();

    bookTableView.setItems(searchData(db, result));
  }

  private ObservableList<Book> searchData(ObjectContainer db, ObjectSet result) {
    ObservableList<Book> bookList = FXCollections.observableArrayList();
    List<Book> books = result;
    for (int i = 0; i < books.size(); i++) {
      String nameID = books.get(i).getBookID();
      String nameBook = books.get(i).getNameBook();
      String author = books.get(i).getMaNXB();
      String nxbID = books.get(i).getAuthorID();
      int year = books.get(i).getYear();
      String description = books.get(i).getDescription();

      bookList.add(new Book(nameID, nameBook, author, nxbID, year, description));
    }
    return bookList;
  }

  private ObservableList<Book> getAllBook(ObjectContainer db) {
    ObservableList<Book> bookList = FXCollections.observableArrayList();
    List<Book> books = db.query(Book.class);
    for (int i = 0; i < books.size(); i++) {
      String nameID = books.get(i).getBookID();
      String nameBook = books.get(i).getNameBook();
      String author = books.get(i).getMaNXB();
      String nxbID = books.get(i).getAuthorID();
      int year = books.get(i).getYear();
      String description = books.get(i).getDescription();

      bookList.add(new Book(nameID, nameBook, author, nxbID, year, description));
    }
    return bookList;
  }
}
