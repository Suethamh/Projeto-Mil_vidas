package iftm.suetham.mil_vidas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ListaDesejo {
    private int cod_wl;
    private Livro livro;
    private Cliente cliente;
    private String data_inclusao;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cod_wl;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListaDesejo other = (ListaDesejo) obj;
        if (cod_wl != other.cod_wl)
            return false;
        return true;
    }
    
}
