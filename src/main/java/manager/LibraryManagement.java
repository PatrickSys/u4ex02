package manager;

import entity.CustomEntity;
import entity.LibrosEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.OptimisticLockException;

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

  public void start() {
    this.initializeMenuMap();
    this.utils = new HibernateUtil();

    this.menuList = this.getMenuList();
    this.menuChoices = parseListAsString(this.menuList);

    // do things
    menu();
  }

  private void initializeMenuMap() {
    this.menuCallbackMap = new HashMap<>();
    this.menuCallbackMap.put(1, () -> utils.persist(create(new LibrosEntity())));
    this.menuCallbackMap.put(2, () -> this.baja(new LibrosEntity()));
    this.menuCallbackMap.put(3, () -> this.update(new LibrosEntity()));
  }

  private CustomEntity<?> create(CustomEntity<?> entity) {
    return entity.createWithJoption(entity);
  }



  private void update(CustomEntity<LibrosEntity> entity) {
    CustomEntity<?> retrievedEntity = getFromDB(entity);
    this.utils.update(retrievedEntity);
  }

  private void alta(CustomEntity<?> entity) {

  }

  private void baja(CustomEntity<?> entity) {
    int entityId = inputNumber("Id del " + entity.name() + " a dar de baja");
    entity.setId(entityId);
    try {
      this.utils.delete(entity);
    }
    catch (OptimisticLockException e) {
      showEntityNotFoundException(entity.name());
      return;
    }
      showSuccessfullyEntityDeleted(entity.name());
  }


  private CustomEntity<?> getFromDB(CustomEntity<LibrosEntity> entity) {
    int id = inputNumber("Id del " + entity.name() + " a actualizar");
    return this.utils.getFromDb(id, entity);
  }

//  private LibrosEntity createLibroWithId() {
//    LibrosEntity libro = createLibro();
//    int id = inputNumber("id del libro a actualizar");
//    libro.setId(id);
//    return libro;
//  }

  private void menu() {
    String choice = inputData(this.MENUCHOICES);

    while(!isNumberValid(choice)){
      choice = inputData("Opción inválida, por favor, escoge una opción entre: " + this.MENUCHOICES);
    }

    int choiceInt = Integer.parseInt(choice);

    this.handleMenuChoice(choiceInt);
  }

  private void handleMenuChoice(int choice) {
    this.menuCallbackMap.get(choice).run();
    this.menu();
  }




  // Returns to the menu in case of wrong input
  private void returnToMenuAfterWrongInput() {
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
