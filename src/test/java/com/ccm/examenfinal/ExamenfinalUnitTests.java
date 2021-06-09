package com.ccm.examenfinal;

import com.ccm.examenfinal.model.Usuario;
import com.ccm.examenfinal.service.UsuarioService;
import com.ccm.examenfinal.util.DatosIncorrectosException;
import com.ccm.examenfinal.util.LoginIncorrectoException;
import com.ccm.examenfinal.util.PasswordNoSeguroException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamenfinalUnitTests extends ExamenfinalApplicationTests {
    @Autowired
    UsuarioService usuarioService;

    Usuario usuario = new Usuario();

    @Test
    public void registraUsuarioTest() throws DatosIncorrectosException, PasswordNoSeguroException {
        usuario.setCorreoElectronico("A01650939@itesm.mx");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("1234567890");

        assertEquals(usuario, usuarioService.registraUsuario(usuario));
    }

    @Test
    public void loginTest() throws DatosIncorrectosException, LoginIncorrectoException {
        usuario.setCorreoElectronico("examenfinal@gmail.com");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("passwordSeguro");

        assertEquals("TOKENSEGURO", usuarioService.login(usuario));
    }
}
