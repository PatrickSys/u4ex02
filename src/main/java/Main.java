import javax.persistence.*;
import manager.LibraryManagement;

public class Main {

  public static void main(String[] args) {
    LibraryManagement management = new LibraryManagement();

    management.start();
  }
}
