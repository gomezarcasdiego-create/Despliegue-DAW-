package com.decroly;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ConversorServlet")
public class ConversorServlet extends HttpServlet {

    private static final double FACTOR = 2.20462;
    private static final Locale ES = Locale.forLanguageTag("es-ES");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        procesar(req, resp, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        procesar(req, resp, "POST");
    }

    private void procesar(HttpServletRequest req, HttpServletResponse resp, String metodo) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String texto = req.getParameter("valor");
        if (texto == null) texto = "no haya texto";

        
        private void resultado(HttpServletRequest req, HttpServletResponse resp, String metodo) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
            for{
                texto.lenght() = resultado;
                echo "hay" + resultado + "caracteres";
            }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1'>"
                    + "<title>Resultado conversión</title><link rel='stylesheet' href='style.css'></head><body>"
                    + "<main class='card'><h1>Resultado</h1><p class='metodo'>Enviado con " + resultado + "</p>"
                    + "<a class='btn' href='index.html'>← Volver</a></main></body></html>");
        }
        }
}
