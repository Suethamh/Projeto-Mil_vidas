package iftm.suetham.mil_vidas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
    
    private String usuario;
    private String senha;
}
