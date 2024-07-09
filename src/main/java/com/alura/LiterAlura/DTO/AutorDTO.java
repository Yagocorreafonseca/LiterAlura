package com.alura.LiterAlura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(@JsonAlias("name")String nomeDoAutor,
                         @JsonAlias("birth_year") Integer anoDeNascimento,
                         @JsonAlias("death_year") Integer anoDeFalecimento)  {

}
