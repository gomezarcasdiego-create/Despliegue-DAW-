package com.decroly;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalculadoraServlet")
public class CalculadoraServlet extends HttpServlet {

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

        String op = req.getParameter("op");
        String contenido;
        try {
            double a = Double.parseDouble(req.getParameter("a").trim().replace(',', '.'));
            double b = Double.parseDouble(req.getParameter("b").trim().replace(',', '.'));
            double r;
            String simbolo;

            switch (op) {
                case "suma":           r = a + b; simbolo = "+"; break;
                case "resta":          r = a - b; simbolo = "−"; break;
                case "multiplicacion": r = a * b; simbolo = "×"; break;
                case "division":
                    if (b == 0) throw new ArithmeticException("No se puede dividir entre 0");
                    r = a / b; simbolo = "÷"; break;
                default: throw new IllegalArgumentException("Operación no válida");
            }
            contenido = "<p class='op'>" + String.format(ES, "%s %s %s = ", fmt(a), simbolo, fmt(b))
                    + "<strong>" + fmt(r) + "</strong></p>";
        } catch (ArithmeticException | IllegalArgumentException e) {
            contenido = "<p class='error'>" + esc(e.getMessage()) + "</p>";
        } catch (Exception e) {
            contenido = "<p class='error'>Introduce dos números válidos.</p>";
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1'>"
                    + "<title>Resultado calculadora</title><link rel='stylesheet' href='style.css'></head><body>"
                    + "<main class='card'><h1>Resultado</h1><p class='metodo'>Enviado con " + metodo + "</p>"
                    + contenido
                    + "<a class='btn' href='index.html'>← Volver</a></main></body></html>");
        }
    }

    private String fmt(double d) {
        return String.format(ES, "%,.4f", d).replaceAll(",?0+$", "");
    }

    private String esc(String s) {
        return s == null ? "" : s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
