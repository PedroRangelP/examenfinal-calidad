package com.ccm.examenfinal;

import com.ccm.examenfinal.controller.UsuarioController;
import com.ccm.examenfinal.model.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ExamenfinalIntegrationTests extends ExamenfinalApplicationTests{
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;

    UsuarioController usuarioController;

    @Test
    public void registrarUsuarioTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("A01650939@itesm.mx");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("1234567890");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/registro").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void registrarUsuarioDatosIncTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("1234567890");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/registro").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError()).andExpect(jsonPath("$").value("Datos incompletos"));
    }

    @Test
    public void registrarUsuarioPasswordTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("A01650939@itesm.mx");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("123");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/registro").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$").value("Password no seguro"));
    }

    @Test
    public void loginTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("examenfinal@gmail.com");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("passwordSeguro");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/login").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void loginDatosIncTest() throws Exception {
        Usuario usuario = new Usuario();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/login").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError()).andExpect(jsonPath("$").value("Datos incompletos"));
    }

    @Test
    public void loginIncorrectoTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("examenfinal@gmail.com");
        usuario.setNombre("Pedro Rangel Palacios");
        usuario.setPassword("1234567890");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        mockMvc.perform(post("/v1/login").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$").value("Login incorrecto"));
    }
}
