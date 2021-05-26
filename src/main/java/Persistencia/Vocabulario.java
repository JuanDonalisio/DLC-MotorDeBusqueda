package Persistencia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
public class Vocabulario {
    @Id
    private String palabra;
    @Column
    private int nr;
    @Column
    private int tf;


    public Vocabulario(String palabra, int nr, int tf) {
        this.palabra = palabra;
        this.nr = nr;
        this.tf = tf;
    }

    public Vocabulario() {

    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "Vocabulario{" +
                "palabra='" + palabra + '\'' +
                ", nr=" + nr +
                ", tf=" + tf +
                '}';
    }
}
