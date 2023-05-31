package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record UsuarioDTO (

    @NotBlank(message = "O campo nome deve ser informado.")
    String cpf,

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,


    @NotBlank(message = "O campo email deve ser informado.")
    String email,

    @NotBlank(message = "O campo login deve ser informado.")
    String login,

    String nomeImagem,

    List<TelefoneDTO> telefones,

    List<EnderecoDTO> enderecos

) {

}



