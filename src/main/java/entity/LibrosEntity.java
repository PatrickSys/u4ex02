package entity;

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
@Table(name = "libro", schema = "u4ex02")
public class LibrosEntity implements CustomEntity<LibrosEntity> {


  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id_libro", nullable = false)
  private int idLibro;

  @Basic
  @Column(name = "titulo", nullable = true, length = 50)
  private String titulo;

  @Basic
  @Column(name = "num_ejemplares", nullable = true)
  private Integer numEjemplares;

  @Basic
  @Column(name = "editorial", nullable = true, length = 50)
  private String editorial;

  @Basic
  @Column(name = "num_paginas", nullable = true)
  private Integer numPaginas;

  @Basic
  @Column(name = "ano_edicion", nullable = true)
  private Integer anoEdicion;

  public int getId() {
    return idLibro;
  }

  public void setId(int idLibro) {
    this.idLibro = idLibro;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Integer getNumEjemplares() {
    return numEjemplares;
  }

  public void setNumEjemplares(Integer numEjemplares) {
    this.numEjemplares = numEjemplares;
  }

  public String getEditorial() {
    return editorial;
  }

  public void setEditorial(String editorial) {
    this.editorial = editorial;
  }

  public Integer getNumPaginas() {
    return numPaginas;
  }

  public void setNumPaginas(Integer numPaginas) {
    this.numPaginas = numPaginas;
  }

  public Integer getAnoEdicion() {
    return anoEdicion;
  }

  public void setAnoEdicion(Integer anoEdicion) {
    this.anoEdicion = anoEdicion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LibrosEntity that = (LibrosEntity) o;
    return (
      idLibro == that.idLibro &&
      Objects.equals(titulo, that.titulo) &&
      Objects.equals(numEjemplares, that.numEjemplares) &&
      Objects.equals(editorial, that.editorial) &&
      Objects.equals(numPaginas, that.numPaginas) &&
      Objects.equals(anoEdicion, that.anoEdicion)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      idLibro,
      titulo,
      numEjemplares,
      editorial,
      numPaginas,
      anoEdicion
    );
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }


  @Override
  public String name() {
    return "Libro";
  }

  public CustomEntity<LibrosEntity> createWithJoption(LibrosEntity libro) {
    String titulo = inputString("titulo del libro: ");
    int numeroEjemplares = inputNumber("numero ejemplares: ");
    String editorial = inputString("editorial: ");
    int numPaginas = inputNumber("numero de páginas: ");
    int anoEdicion = inputNumber("año de edición: ");
    libro.setTitulo(titulo);
    libro.setNumEjemplares(numeroEjemplares);
    libro.setEditorial(editorial);
    libro.setNumPaginas(numPaginas);
    libro.setAnoEdicion(anoEdicion);
    return libro;
  }

}
