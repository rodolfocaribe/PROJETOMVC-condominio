package br.condominio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Apartamento {
    private int nroPorta;
    private int qtdeQuartos;
    private String tipo;
    private int id;
    private int proprietario;
}
