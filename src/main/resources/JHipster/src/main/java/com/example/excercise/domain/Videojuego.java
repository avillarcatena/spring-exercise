package com.example.excercise.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Videojuego.
 */
@Entity
@Table(name = "videojuego")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Videojuego implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "sequenceGenerator"
  )
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "imagenes")
  private String imagenes;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Videojuego id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public Videojuego nombre(String nombre) {
    this.setNombre(nombre);
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getImagenes() {
    return this.imagenes;
  }

  public Videojuego imagenes(String imagenes) {
    this.setImagenes(imagenes);
    return this;
  }

  public void setImagenes(String imagenes) {
    this.imagenes = imagenes;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Videojuego)) {
      return false;
    }
    return id != null && id.equals(((Videojuego) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Videojuego{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", imagenes='" + getImagenes() + "'" +
            "}";
    }
}
