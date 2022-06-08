package com.example.excercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

/**
 * VideojuegoDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-05-06T18:58:23.364+02:00")

public class VideojuegoDto implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("imagenes")
  private String imagenes = null;

  public VideojuegoDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public VideojuegoDto nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  **/
  @ApiModelProperty(value = "")


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public VideojuegoDto imagenes(String imagenes) {
    this.imagenes = imagenes;
    return this;
  }

  /**
   * Get imagenes
   * @return imagenes
  **/
  @ApiModelProperty(value = "")


  public String getImagenes() {
    return imagenes;
  }

  public void setImagenes(String imagenes) {
    this.imagenes = imagenes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VideojuegoDto videojuegoDto = (VideojuegoDto) o;
    return Objects.equals(this.id, videojuegoDto.id) &&
        Objects.equals(this.nombre, videojuegoDto.nombre) &&
        Objects.equals(this.imagenes, videojuegoDto.imagenes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, imagenes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VideojuegoDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    imagenes: ").append(toIndentedString(imagenes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

