package org.example.model;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"name","type"})
public class PontoColeta {
    private String name;
    private String type;

    public PontoColeta() {
    }

    public PontoColeta(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PontoColeta{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
