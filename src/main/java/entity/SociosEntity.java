package entity;


import exception.UserNullinputException;

import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.persistence.*;

import static utils.ManagamentUtils.inputNumber;
import static utils.ManagamentUtils.inputString;

/************************************************************************
 Made by        PatrickSys
 Date           03/02/2022
 Package        entity
 Description:
 ************************************************************************/

@Entity
@Table(name = "socio", schema = "u4ex02")
public class SociosEntity implements CustomEntity<SociosEntity> {



  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id_socio", nullable = false)
  private int idSocio;

  @Basic
  @Column(name = "nombre", nullable = false, length = 50)
  private String nombre;

  @Basic
  @Column(name = "apellidos", nullable = true, length = 75)
  private String apellidos;

  @Basic
  @Column(name = "edad", nullable = true)
  private Integer edad;

  @Basic
  @Column(name = "direccion", nullable = true, length = 100)
  private String direccion;

  @Basic
  @Column(name = "telefono", nullable = true)
  private Integer telefono;


  public int getId() {
    return idSocio;
  }

  public void setId(int idSocio) {
    this.idSocio = idSocio;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public Integer getTelefono() {
    return telefono;
  }

  public void setTelefono(Integer telefono) {
    this.telefono = telefono;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SociosEntity that = (SociosEntity) o;
    return (
      idSocio == that.idSocio &&
      Objects.equals(nombre, that.nombre) &&
      Objects.equals(apellidos, that.apellidos) &&
      Objects.equals(edad, that.edad) &&
      Objects.equals(direccion, that.direccion) &&
      Objects.equals(telefono, that.telefono)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSocio, nombre, apellidos, edad, direccion, telefono);
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }

  @Override
  public String name() {
    return "Socio";
  }

  @Override
  public CustomEntity<SociosEntity> createWithJoption(CustomEntity<?> socio) throws UserNullinputException {

    SociosEntity newSocio = new SociosEntity();
    newSocio.setId(socio.getId());
    String nombre = inputString("nombre del socio: ");
    String apellidos = inputString("apellidos");
    int edad = inputNumber("edad: ");
    String direccion = inputString("direccion: ");
    int telefono = inputNumber("telefono: ");


    newSocio.setNombre(nombre);
    newSocio.setApellidos(apellidos);
    newSocio.setEdad(edad);
    newSocio.setDireccion(direccion);
    newSocio.setTelefono(telefono);

    return newSocio;
  }


}
