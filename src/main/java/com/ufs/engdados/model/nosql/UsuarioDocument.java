package com.ufs.engdados.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuarios")
@Data
@NoArgsConstructor
public class UsuarioDocument {

    @Id
    private String id;
    private String nome;
    private String email;
    private String senha;
}