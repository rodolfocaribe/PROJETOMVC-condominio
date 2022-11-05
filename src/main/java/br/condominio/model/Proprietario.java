package br.condominio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Proprietario {
    private String nome;
    private String telefone;
    private int id;
//    private List<Apartamento> apartamentosDoProprietario;
}
