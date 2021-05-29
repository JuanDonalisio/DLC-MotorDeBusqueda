package Persistencia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Posteo")
public class Posteo implements Serializable {
    @Id
    private String palabra;
    @Id
    private String documento;
    @Column
    private int tf;

    public Posteo(String palabra, String documento, int tf) {
        this.palabra = palabra;
        this.documento = documento;
        this.tf = tf;
    }

    public Posteo() {

    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }
}
