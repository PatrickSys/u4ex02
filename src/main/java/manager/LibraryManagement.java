package manager;

import entity.CustomEntity;
import entity.LibrosEntity;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.swing.*;

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
    "4.Dar de alta un socio \n 5.Dar de baja un socio\n 6.Modificar un socio\n 7.Consultar un socio\n 8.Consultar un libro" ;

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
    } catch (UserNullinputException | ClassCastException e) {
      menu();
    }
  }

  private void initializeMenuMap()  {
    this.menuCallbackMap = new HashMap<>();
    this.menuCallbackMap.put(1, () -> this.create(new LibrosEntity()));
    this.menuCallbackMap.put(2, () -> this.baja(new LibrosEntity()));
    this.menuCallbackMap.put(3, () -> this.update(new LibrosEntity()));
    this.menuCallbackMap.put(4, () -> this.create(new SociosEntity()));
    this.menuCallbackMap.put(5, () -> this.baja(new SociosEntity()));
    this.menuCallbackMap.put(6, () -> this.update(new SociosEntity()));
    this.menuCallbackMap.put(7, () -> this.consulta(new SociosEntity()));
    this.menuCallbackMap.put(8, () -> this.consulta(new LibrosEntity()));

  }


  private void create(CustomEntity<?> entity) {
    try {
      utils.persist(entity.createWithJoption(entity));
    }
    catch (UserNullinputException e) {
      e.printStackTrace();
    }
  }


  private void update(CustomEntity<?> entity) {
    try {
      CustomEntity<?> retrievedEntity = findById(entity);
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


  private CustomEntity<?> findById(CustomEntity<?> entity) throws UserNullinputException {
    int id = inputNumber("Id del " + entity.name() + " a actualizar");
    return this.utils.findById(id, entity);
  }

  private void consulta(CustomEntity<?> entity) {

    String field = "";
    String fieldValue = "";
    try {
      field = inputData("campo por el que buscar");
      List<String> entityFields = Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

      if(!entityFields.contains(field)) {
        throw new NoSuchFieldException("El campo no existe");
      }

      fieldValue = createForSearch(field);
      CustomEntity<?> retrievedEntity = this.utils.findByField(entity, field, fieldValue);
      if(null == retrievedEntity ) {
        showEntityNotFoundException(entity.name());
        return;
      }
      JOptionPane.showMessageDialog(null, retrievedEntity.toString());

    } catch (NoSuchFieldException e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    } catch (UserNullinputException ignored) {
    }
  }

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
