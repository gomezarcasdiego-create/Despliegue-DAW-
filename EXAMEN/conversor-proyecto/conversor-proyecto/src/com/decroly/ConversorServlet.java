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
        String modo = req.getParameter("modo");
        if (modo == null) modo = "kg2lbs";

        String contenido;
        try {
            double valor = Double.parseDouble(texto.trim().replace(',', '.'));
            if (valor <= 0) throw new NumberFormatException();

            double kg, lbs;
            String operacion;
            if (modo.equals("lbs2kg")) {
                lbs = valor;
                kg = lbs / FACTOR;                       // DIVISIÓN
                operacion = String.format(ES, "%.2f lbs ÷ 2,20462 = %.2f kg", lbs, kg);
            } else {
                kg = valor;
                lbs = kg * FACTOR;                       // MULTIPLICACIÓN
                operacion = String.format(ES, "%.2f kg × 2,20462 = %.2f lbs", kg, lbs);
            }

            String pronostico;
            String clase;
            if (lbs < 132.2)      { pronostico = "Bajo peso";    clase = "azul"; }
            else if (lbs < 178)   { pronostico = "Peso normal";  clase = "verde"; }
            else if (lbs < 213.7) { pronostico = "Sobrepeso";    clase = "naranja"; }
            else                  { pronostico = "Obesidad";     clase = "rojo"; }

            contenido = "<p class='op'>" + operacion + "</p>"
                    + "<p>Peso en libras: <strong>" + String.format(ES, "%.2f", lbs) + " lbs</strong></p>"
                    + "<p>Pronóstico IMC (altura 180 cm): <span class='badge " + clase + "'>" + pronostico + "</span></p>";
        } catch (Exception e) {
            contenido = "<p class='error'>Introduce un número decimal mayor que 0.</p>";
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1'>"
                    + "<title>Resultado conversión</title><link rel='stylesheet' href='style.css'></head><body>"
                    + "<main class='card'><h1>Resultado</h1><p class='metodo'>Enviado con " + metodo + "</p>"
                    + contenido
                    + "<a class='btn' href='index.html'>← Volver</a></main></body></html>");
        }
    }
}
