package com.ccm.examenfinal.controller;

import com.ccm.examenfinal.model.Usuario;
import com.ccm.examenfinal.service.UsuarioService;
import com.ccm.examenfinal.util.DatosIncorrectosException;
import com.ccm.examenfinal.util.LoginIncorrectoException;
import com.ccm.examenfinal.util.PasswordNoSeguroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario){
        try {
            usuario=usuarioService.registraUsuario(usuario);
            return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
        } catch (DatosIncorrectosException e) {
            return new ResponseEntity<String>("Datos incompletos",HttpStatus.BAD_REQUEST);
        } catch (PasswordNoSeguroException e) {
            return new ResponseEntity<String>("Password no seguro",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
        try {
            String token=usuarioService.login(usuario);
            return new ResponseEntity<String>(token,HttpStatus.OK);

        } catch (DatosIncorrectosException e) {
            return new ResponseEntity<String>("Datos incompletos",HttpStatus.BAD_REQUEST);
        } catch (LoginIncorrectoException e) {
            return new ResponseEntity<String>("Login incorrecto",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
