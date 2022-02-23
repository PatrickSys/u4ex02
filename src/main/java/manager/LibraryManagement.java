package manager;

import entity.CustomEntity;
import entity.LibrosEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import entity.SociosEntity;
import exception.UserNullinputException;
import utils.HibernateUtil;

import static utils.ManagamentUtils.*;


/************************************************************************
 Made by        PatrickSys
 Date           03/02/2022
 Package        manager
 Description:
 ************************************************************************/

public class LibraryManagement {

  private HibernateUtil utils;

  final String MENUCHOICES =
    "Introduce una opción: \n 1.Dar de alta un libro\n 2.Dar de baja un libro\n 3.Modificar un libro\n " +
    "4.Dar de alta un socio \n 5.Dar de baja un socio\n 6.Modificar un socio";

  Map<Integer, Runnable> menuCallbackMap;
  private String menuChoices;
  private ArrayList<String> menuList;

  public void start() throws UserNullinputException {
    this.initializeMenuMap();
    this.utils = new HibernateUtil();

    this.menuList = this.getMenuList();
    this.menuChoices = parseListAsString(this.menuList);

    // do things
    try {
      menu();
    } catch (UserNullinputException e) {
      menu();
    }
  }

  private void initializeMenuMap() throws UserNullinputException {
    this.menuCallbackMap = new HashMap<>();
    this.menuCallbackMap.put(1, () -> this.create(new LibrosEntity()));
    this.menuCallbackMap.put(2, () -> this.baja(new LibrosEntity()));
    this.menuCallbackMap.put(3, () -> this.update(new LibrosEntity()));
    this.menuCallbackMap.put(4, () -> this.create(new SociosEntity()));
    this.menuCallbackMap.put(5, () -> this.baja(new SociosEntity()));
    this.menuCallbackMap.put(6, () -> this.update(new SociosEntity()));

  }


  private void create(CustomEntity<?> entity) {
    try {
      utils.persist(entity.createWithJoption(entity));
    }
    catch (UserNullinputException e) {
      e.printStackTrace();
    }
  }


  private void updateLibro(LibrosEntity entity) {
    try {
      LibrosEntity retrievedEntity = (LibrosEntity) getFromDB(entity);
      LibrosEntity newLibro = (LibrosEntity) entity.createWithJoption(retrievedEntity);
      this.utils.update(newLibro);
    }
    catch (UserNullinputException e) {
      e.printStackTrace();
    }
    catch (EntityNotFoundException e ) {
      showEntityNotFoundException(e.getMessage());
    }
  }

  private void update(CustomEntity<?> entity) {
    try {
      CustomEntity<?> retrievedEntity = getFromDB(entity);
      CustomEntity<?> newEntity =  entity.createWithJoption(retrievedEntity);
      this.utils.update(newEntity);
    }
    catch (UserNullinputException e) {
      e.printStackTrace();
    }
    catch (EntityNotFoundException e ) {
      showEntityNotFoundException(e.getMessage());
    }
  }


  private void updateSocio(CustomEntity<?> socio) {
    try {
      CustomEntity<?> retrievedEntity =  getFromDB(socio);
      CustomEntity<?> newLibro =  socio.createWithJoption(retrievedEntity);
      this.utils.update(newLibro);
    }
    catch (UserNullinputException e) {
      e.printStackTrace();
    }
    catch (EntityNotFoundException e ) {
      showEntityNotFoundException(e.getMessage());
    }
  }



  private void baja(CustomEntity<?> entity)  {
    int entityId = 0;
    try {
      entityId = inputNumber("Id del " + entity.name() + " a dar de baja");
    } catch (UserNullinputException e) {
      e.printStackTrace();
      return;
    }
    entity.setId(entityId);

    try {
      this.utils.delete(entity);
    }
    catch (EntityNotFoundException e ) {
      showEntityNotFoundException(e.getMessage());
    }

  }


  private CustomEntity<?> getFromDB(CustomEntity<?> entity) throws UserNullinputException {
    int id = inputNumber("Id del " + entity.name() + " a actualizar");
    return this.utils.getFromDb(id, entity);
  }

//  private LibrosEntity createLibroWithId() {
//    LibrosEntity libro = createLibro();
//    int id = inputNumber("id del libro a actualizar");
//    libro.setId(id);
//    return libro;
//  }

  private void menu() throws UserNullinputException {
    String choice = inputData(this.MENUCHOICES);


    while(!isNumberValid(choice)){
      choice = inputData("Opción inválida, por favor, escoge una opción entre: " + this.MENUCHOICES);
    }

    int choiceInt = Integer.parseInt(choice);

    this.handleMenuChoice(choiceInt);
  }

  private void handleMenuChoice(int choice) throws UserNullinputException {
    this.menuCallbackMap.get(choice).run();
    this.menu();
  }




  // Returns to the menu in case of wrong input
  private void returnToMenuAfterWrongInput() throws UserNullinputException {
    showWrongChoiceMessage();
    menu();
  }



  private ArrayList<String> getMenuList() {
    return new ArrayList<>() {
      {
        add("1.Dar de alta un libro\n");
        add("2.Dar de baja un libro\n");
        add("3.Modificar un libro\n");
        add("4.Dar de alta un socio\n");
        add("5.Dar de baja un socio\n");
        add("6.Modificar un socio");
      }
    };
  }
}
