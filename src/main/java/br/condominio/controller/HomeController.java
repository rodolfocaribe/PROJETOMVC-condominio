package br.condominio.controller;


import java.util.List;

import br.condominio.model.Apartamento;
import br.condominio.model.Proprietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
                            res.getInt("id"),
                            res.getString("nome"),
                            res.getString("telefone"));
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
                            res.getInt("id"),
                            res.getInt("numero_da_porta"),
                            res.getInt("qtde_quartos"),
                            res.getString("tipo"));
                    return apartamento;
                });

        modelo.addAttribute("apartamentos", listaDeApartamentos);
        return "apartamentos";
    }

    @GetMapping("novoproprietario")
    public String exibeForm(Model modelo2) {
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
    @GetMapping ("excluir-proprietario")
    public String apagarProprietario(@RequestParam(value = "id", required = true) int id) {
        db.update("delete from proprietario where id=?", id);
        return "redirect:/proprietarios";
    }

    @GetMapping ("editar-proprietario")
    public String exibeFormAlteracaoProprietario(@RequestParam (value = "id", required = true) int id, Model model){
        Proprietario proprietario = db.queryForObject(
                "select * from proprietario where id = ?",
                (rs, rowNum) -> {
                    Proprietario edited = new Proprietario();
                    edited.setId(rs.getInt("id"));
                    edited.setNome(rs.getString("nome"));
                    edited.setTelefone(rs.getString("telefone"));
                    return edited;
                }, id);
        model.addAttribute("proprietarioEditado", proprietario );
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

    @GetMapping ("excluir-proprietarios")
    public String apagarProprietarios(@RequestParam(value = "id", required = true) int id) {
        db.update("delete from proprietario where id=?", id);
        return "redirect:/proprietarios";
    }

    @GetMapping ("editar-apartamento")
    public String exibeFormAlteracaoApartamento(@RequestParam (value = "id", required = true) int id, Model model){
        Apartamento apartamento = db.queryForObject(
                "select * from apartamento where id = ?",
                (rs, rowNum) -> {
                    Apartamento edited = new Apartamento();
                    edited.setId(rs.getInt("id"));
                    edited.setNroPorta(rs.getInt("nroPorta"));
                    edited.setQtdeQuartos(rs.getInt("qtdQuartos"));
                    return edited;
                }, id);
        model.addAttribute("apartamentoeditado", apartamento );
        return "editaapartamento";
    }

    @PostMapping("gravaapartamentoeditado")
    public String gravaApartamentoEditado(Apartamento apartamento) {
        db.update(
                "update apartamento set nome=? where id = ?",
                apartamento.getId(),
                apartamento.getNroPorta(),
                apartamento.getQtdeQuartos(),
                apartamento.getTipo());
        return "redirect:/apartamentos";

    }
}

