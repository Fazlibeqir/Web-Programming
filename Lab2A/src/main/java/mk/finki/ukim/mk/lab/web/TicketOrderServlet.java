//package mk.finki.ukim.mk.lab.web;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import mk.finki.ukim.mk.lab.service.TicketOrderService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.web.IWebExchange;
//import org.thymeleaf.web.servlet.JakartaServletWebApplication;
//
//import java.io.IOException;
//
//@WebServlet(urlPatterns = "/ticketOrder")
//public class TicketOrderServlet extends HttpServlet {
//    private final SpringTemplateEngine springTemplateEngine;
//    private final TicketOrderService ticketOrderService;
//
//    public TicketOrderServlet(SpringTemplateEngine springTemplateEngine, TicketOrderService ticketOrderService) {
//        this.springTemplateEngine = springTemplateEngine;
//        this.ticketOrderService = ticketOrderService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        IWebExchange webExchange = JakartaServletWebApplication
//                .buildApplication(getServletContext())
//                .buildExchange(req, resp);
//        WebContext context = new WebContext(webExchange);
//
//        context.setVariable("name", req.getParameter("title"));
//        context.setVariable("num", req.getParameter("tickets"));
//
//        springTemplateEngine
//                .process("orderConfirmation.html",
//                        context,
//                        resp.getWriter());
//    }
//}
