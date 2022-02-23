import javax.persistence.*;

import exception.UserNullinputException;
import manager.LibraryManagement;

public class Main {

  public static void main(String[] args) throws UserNullinputException {
    LibraryManagement management = new LibraryManagement();

    management.start();
  }
}
