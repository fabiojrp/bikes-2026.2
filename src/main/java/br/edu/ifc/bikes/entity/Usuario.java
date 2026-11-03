package br.edu.ifc.bikes.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

//===lombok
@Getter
@Setter
@ToString
//===lombok

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    //a constante definida no enum passa a ser uma string no bd
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false, length = 50)
    private Role role = Role.ROLE_CLIENTE;

    @CreatedDate
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    private LocalDateTime dataModificacao;
    @CreatedBy
    private String criadoPor;
    @LastModifiedBy
    private String modificadoPor;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(id, usuario.id);
    }
}