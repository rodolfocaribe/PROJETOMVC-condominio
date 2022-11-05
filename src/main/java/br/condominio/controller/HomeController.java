package br.condominio.controller;


import br.condominio.model.Apartamento;
import br.condominio.model.Proprietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    JdbcTemplate db;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/proprietarios")
    public String proprietario(Model model) {
        List<Proprietario> listaDeProprietarios = db.query(
                "select * from proprietario",
                (res, rowNum) -> {
                    Proprietario proprietario = new Proprietario(
                            res.getString("nome"),
                            res.getString("telefone"),
                            res.getInt("id"));
                    return proprietario;
                });
        model.addAttribute("proprietarios", listaDeProprietarios);
        return "proprietarios";
    }

    @GetMapping("/apartamentos")
    public String apartamento(Model modelo) {
        List<Apartamento> listaDeApartamentos = db.query(
                "select * from apartamento",
                (res, rowNum) -> {
                    Apartamento apartamento = new Apartamento(
                            res.getInt("numero_da_porta"),
                            res.getInt("qtde_quartos"),
                            res.getString("tipo"),
                            res.getInt("id"),
                            res.getInt("proprietario"));
                    return apartamento;
                });

        modelo.addAttribute("apartamentos", listaDeApartamentos);
        return "apartamentos";
    }

    @GetMapping("novoproprietario")
    public String exibeFormProprietario(Model modelo2) {
        modelo2.addAttribute("proprietario", new Proprietario());
        return "novoproprietario";
    }

    @PostMapping("novoproprietario")
    public String gravaDados(Proprietario proprietario) {
        db.update("insert into proprietario (nome, telefone) values (?,?)",
                proprietario.getNome(), proprietario.getTelefone());
        return "redirect:/proprietarios";
    }


    @GetMapping("novoapartamento")
    public String exibeFormApartamento(Model modelo4) {
        modelo4.addAttribute("apartamento", new Apartamento());
        return "novoapartamento";
    }

    @PostMapping("novoapartamento")
    public String gravaDadosApartamento(Apartamento apartamento) {
        db.update("insert into apartamento (numero_da_porta, qtde_quartos, tipo) values (?,?,?)",
                apartamento.getNroPorta(), apartamento.getQtdeQuartos(), apartamento.getTipo());
        return "redirect:/apartamentos";
    }

    @GetMapping("excluir-proprietario")
    public String apagarProprietario(@RequestParam(value = "id", required = true) int id) {
        db.update("delete from proprietario where id=?", id);
        return "redirect:/proprietarios";
    }

    @GetMapping("editar-proprietario")
    public String exibeFormAlteracaoProprietario(@RequestParam(value = "id", required = true) int id, Model model) {
        Proprietario proprietario = db.queryForObject(
                "select * from proprietario where id = ?",
                (rs, rowNum) -> {
                    Proprietario edited = new Proprietario();
                    edited.setId(rs.getInt("id"));
                    edited.setNome(rs.getString("nome"));
                    edited.setTelefone(rs.getString("telefone"));
                    return edited;
                }, id);
        model.addAttribute("proprietarioEditado", proprietario);
        return "editaproprietario";
    }

    @PostMapping("gravaproprietarioeditado")
    public String gravaProprietarioEditado(Proprietario proprietario) {
        db.update(
                "update proprietario set nome=?, telefone=? where id = ?",
                proprietario.getNome(),
                proprietario.getTelefone(),
                proprietario.getId());
        return "redirect:/proprietarios";
    }

    @GetMapping("excluir-apartamento")
    public String apagarApartamento(@RequestParam(value = "id", required = true) int id) {
        db.update("delete from apartamento where id=?", id);
        return "redirect:/apartamentos";
    }

    @GetMapping("editar-apartamento")
    public String exibeFormAlteracaoApartamento(@RequestParam(value = "id", required = true) int id, Model model) {
        Apartamento apartamento = db.queryForObject(
                "select * from apartamento where id = ?",
                (rs, rowNum) -> {
                    Apartamento edited = new Apartamento();
                    edited.setNroPorta(rs.getInt("numero_da_porta"));
                    edited.setQtdeQuartos(rs.getInt("qtde_quartos"));
                    edited.setTipo(rs.getString("tipo"));
                    edited.setId(rs.getInt("id"));
                    edited.setProprietario(rs.getInt("proprietario"));
                    return edited;
                }, id);
        model.addAttribute("apartamentoEditado", apartamento);
        return "editaapartamento";
    }

    @PostMapping("gravaapartamentoeditado")
    public String gravaApartamentoEditado(Apartamento apartamento) {
        db.update(
                "update apartamento set numero_da_porta=?, qtde_quartos=?, tipo=? where id = ?",
                apartamento.getNroPorta(),
                apartamento.getQtdeQuartos(),
                apartamento.getTipo(),
                apartamento.getId());
        return "redirect:/apartamentos";

    }
}

