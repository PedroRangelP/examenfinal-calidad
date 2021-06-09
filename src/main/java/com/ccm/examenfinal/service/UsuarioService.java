package com.ccm.examenfinal.service;

import com.ccm.examenfinal.model.Usuario;
import com.ccm.examenfinal.util.DatosIncorrectosException;
import com.ccm.examenfinal.util.LoginIncorrectoException;
import com.ccm.examenfinal.util.PasswordNoSeguroException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    public Usuario registraUsuario(Usuario usuario) throws DatosIncorrectosException, PasswordNoSeguroException{
        usuario.setId(1);
        if(usuario.getCorreoElectronico()==null || usuario.getNombre()==null|| usuario.getPassword()==null){
            throw new DatosIncorrectosException();
        }
        if(usuario.getPassword().length()<5){
            throw new PasswordNoSeguroException();
        }
        return usuario;
    }

    public String login(Usuario usuario) throws DatosIncorrectosException, LoginIncorrectoException{
        if(usuario.getCorreoElectronico()==null|| usuario.getPassword()==null){
           throw new DatosIncorrectosException();
        }

        if(usuario.getCorreoElectronico().equalsIgnoreCase("examenfinal@gmail.com")&& usuario.getPassword().equals("passwordSeguro")){
            return "TOKENSEGURO";

        }else{
            throw new LoginIncorrectoException();
        }

    }
}
