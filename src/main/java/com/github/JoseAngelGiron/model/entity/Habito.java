package com.github.JoseAngelGiron.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "habito")
public class Habito {

    public Habito() {
    }

    public Habito(HabitoId id, Usuario idUsuario, Actividad idActividad, Integer frecuencia, String tipo, LocalDate ultimaFecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
        this.frecuencia = frecuencia;
        this.tipo = tipo;
        this.ultimaFecha = ultimaFecha;
    }

    @EmbeddedId
    private HabitoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @MapsId("idActividad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad idActividad;

    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @Lob
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "ultima_fecha", nullable = false)
    private LocalDate ultimaFecha;

    public HabitoId getId() {
        return id;
    }

    public void setId(HabitoId id) {
        this.id = id;
    }

    public com.github.JoseAngelGiron.model.entity.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(com.github.JoseAngelGiron.model.entity.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(LocalDate ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

}