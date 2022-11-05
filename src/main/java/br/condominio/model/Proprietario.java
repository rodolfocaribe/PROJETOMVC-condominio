package br.condominio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Proprietario {
    private int id;
    private String nome;
    private String telefone;
    private List<Apartamento> apartamentos;

    public Proprietario(int id, String nome, String telefone) {
    }
}
