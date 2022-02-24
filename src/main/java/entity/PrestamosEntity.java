package entity;

import exception.UserNullinputException;

import java.lang.annotation.Annotation;
import java.sql.Date;
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
@Table(name = "prestamo", schema = "u4ex02", catalog = "")
public class PrestamosEntity implements CustomEntity<PrestamosEntity> {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id_prestamo", nullable = false)
  private int idPrestamo;

  @Basic
  @Column(name = "id_libro", nullable = false)
  private int idLibro;

  @Basic
  @Column(name = "id_socio", nullable = false)
  private int idSocio;

  @Basic
  @Column(name = "inicio_prestamo", nullable = false)
  private Date inicioPrestamo;

  @Basic
  @Column(name = "fin_prestamo", nullable = false)
  private Date finPrestamo;

  public int getId() {
    return idPrestamo;
  }

  public void setId(int idPrestamo) {
    this.idPrestamo = idPrestamo;
  }

  public int getIdLibro() {
    return idLibro;
  }

  public void setIdLibro(int idLibro) {
    this.idLibro = idLibro;
  }

  public int getIdSocio() {
    return idSocio;
  }

  public void setIdSocio(int idSocio) {
    this.idSocio = idSocio;
  }

  public Date getInicioPrestamo() {
    return inicioPrestamo;
  }

  public void setInicioPrestamo(Date inicioPrestamo) {
    this.inicioPrestamo = inicioPrestamo;
  }

  public Date getFinPrestamo() {
    return finPrestamo;
  }

  public void setFinPrestamo(Date finPrestamo) {
    this.finPrestamo = finPrestamo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PrestamosEntity that = (PrestamosEntity) o;
    return (
      idPrestamo == that.idPrestamo &&
      idLibro == that.idLibro &&
      idSocio == that.idSocio &&
      Objects.equals(inicioPrestamo, that.inicioPrestamo) &&
      Objects.equals(finPrestamo, that.finPrestamo)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      idPrestamo,
      idLibro,
      idSocio,
      inicioPrestamo,
      finPrestamo
    );
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }


  @Override
  public String name() {
    return "Prestamo";
  }


  @Override
  public String findBy() {
    return null;
  }


  @Override
  public CustomEntity<PrestamosEntity> createWithJoption(CustomEntity<?> prestamo) throws UserNullinputException {
    PrestamosEntity newPrestamo = new PrestamosEntity();
    newPrestamo.setId(prestamo.getId());

    int idLibro = inputNumber("id del libro: ");
    int idSocio = inputNumber("id del socio: ");
    int numeroEjemplares = inputNumber("numero ejemplares: ");
    String editorial = inputString("editorial: ");
    int numPaginas = inputNumber("numero de páginas: ");
    int anoEdicion = inputNumber("año de edición: ");
    newPrestamo.setIdLibro(idLibro);
    newPrestamo.setIdSocio(idSocio);
//    newLibro.setEditorial(editorial);
//    newLibro.setNumPaginas(numPaginas);
//    newLibro.setAnoEdicion(anoEdicion);
//    return newLibro;
  }


}
