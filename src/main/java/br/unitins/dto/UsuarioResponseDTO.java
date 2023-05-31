package br.unitins.dto;

import br.unitins.model.Sexo;
import br.unitins.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

public record UsuarioResponseDTO(
        Long id,
        String cpf,
        String nome,
        String email,
        String login,
        String nomeImagem,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Sexo sexo
) {

    public static UsuarioResponseDTO valueOf(Usuario u) {
        if (u.getPessoaFisica() == null)
            return new UsuarioResponseDTO(u.getId(), null, null, null, u.getLogin(), null, null);

        return new UsuarioResponseDTO(u.getId(),
                u.getPessoaFisica().getCpf(),
                u.getPessoaFisica().getNome(),
                u.getPessoaFisica().getEmail(),
                u.getLogin(),
                u.getNomeImagem(),
                u.getPessoaFisica().getSexo());
    }


}