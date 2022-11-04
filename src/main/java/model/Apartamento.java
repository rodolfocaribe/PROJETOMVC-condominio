package model;

public class Apartamento {
    private int nroPorta;
    private int qtdeQuartos;
    private String tipo;

    public Apartamento(int nroPorta, int qtdeQuartos, String tipo) {
        this.nroPorta = nroPorta;
        this.qtdeQuartos = qtdeQuartos;
        this.tipo = tipo;
    }

    public int getNroPorta() {
        return nroPorta;
    }

    public void setNroPorta(int nroPorta) {
        this.nroPorta = nroPorta;
    }

    public int getQtdeQuartos() {
        return qtdeQuartos;
    }

    public void setQtdeQuartos(int qtdeQuartos) {
        this.qtdeQuartos = qtdeQuartos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
