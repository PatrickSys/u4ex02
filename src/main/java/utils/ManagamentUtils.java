package utils;

import exception.UserNullinputException;

import javax.persistence.EntityNotFoundException;
import javax.swing.*;
import java.util.ArrayList;


/************************************************************************
 Made by        PatrickSys
 Date           22/02/2022
 Package        utils
 Description:
 ************************************************************************/


public class ManagamentUtils {

    public static void showWrongChoiceMessage() {
        showMessage("Por favor elige una opción entre 1 y 6");
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static String inputData(String message) throws UserNullinputException {
        String data =  JOptionPane.showInputDialog(message);
        if(null == data) {
            throw new UserNullinputException();
        }
        return data;
    }

    public static String inputNonBlankData(String message) throws UserNullinputException {
        String data = inputData(message);
        if(null == data) {
            throw new UserNullinputException();
        }
        if (data.isBlank()) {
            String errorMessage =  "No puedes dejar un campo en blanco\n";
            message = message.replaceAll(errorMessage, "");
            return inputNonBlankData("No puedes dejar un campo en blanco\n" + message );
        }
        return data;
    }

    public static  String parseListAsString(ArrayList<String> listToParse) {
        return listToParse.toString().replaceAll("\\[", "").replaceAll("\\]", "");
    }
    // Checks if input data is not a number
    public static boolean notNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
    public static int inputNumber(String message) throws UserNullinputException {
        String input = inputNonBlankData(message);
        if (!isNumberValid(input)) {
            return inputNumber("Please enter a valid number");
        }
        return Integer.parseInt(input);
    }

    public static String inputString(String message) throws UserNullinputException {
        String input = inputNonBlankData(message);
        if (notNumber(input)) {
            return input;
        }
        return inputString("Por favor introduce un nombre");
    }

    public static boolean isNumberValid(String number) {
        return !notNumber(number);
    }



    public static void showEntityNotFoundException(String entityName) {
        JOptionPane.showMessageDialog(null, entityName +
                " no encontrado en la base de datos");
    }
    public static void showSuccessfullyEntityDeleted(String entityName) {
        JOptionPane.showMessageDialog(null,entityName + " borrado de la base de datos");
    }
}
