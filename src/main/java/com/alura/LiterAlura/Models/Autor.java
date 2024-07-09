package com.alura.LiterAlura.Models;

import com.alura.LiterAlura.DTO.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int dataDeNascimento;
    private int dataDeFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}
    public Autor(AutorDTO data) {
        this.nome = data.nomeDoAutor();
        this.dataDeNascimento = data.anoDeNascimento();
        this.dataDeFalecimento = data.anoDeFalecimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(int dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getDataDeFalecimento() {
        return dataDeFalecimento;
    }

    public void setDataDeFalecimento(int dataDeFalecimento) {
        this.dataDeFalecimento = dataDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }


    @Override
    public String toString() {
        return "____________________________________  DADOS DO AUTOR ________________________________________________________________"+
                "\nNome: " + nome +
                "\nData De Nascimento: " + dataDeNascimento +
                "\nData De Falecimento: " + dataDeFalecimento+
                "\n__________________________________________________________________________________________________________________";
    }

}
