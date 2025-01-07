package com.iw.IW.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PromotorResponse {

    private String status;

    @JsonProperty("data")
    private List<PromotorData> promotores;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PromotorData> getPromotores() {
        return promotores;
    }

    public void setPromotores(List<PromotorData> promotores) {
        this.promotores = promotores;
    }

    public static class PromotorData {
        private String id;
        private String nombre;
        private String cargo;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }
    }
}
