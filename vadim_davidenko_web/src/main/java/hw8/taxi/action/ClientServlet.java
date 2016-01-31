package hw8.taxi.action;

import hw8.taxi.domain.Client;
import hw8.taxi.exception.ClientException;
import hw8.taxi.service.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by v.davidenko on 29.01.2016.
 *
 *  dashboard.jsp - страница со списком функций
 */

@WebServlet("/clientServlet")
public class ClientServlet extends HttpServlet {

    final static String CLIENT_CREATED_MSG = "New client created successfully";
    final static String NO_CLIENTS_FOUND_MSG = "No clients found";
    final static String CLIENT_REGISTRATION_PAGE = "registeredClient.jsp";
    final static String CLIENT_LIST_PAGE = "clients.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showClientsService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        registrationClientService(req, resp);
    }

    protected void registrationClientService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String name = parameterMap.get("clientName")[0].trim();
        String surname = parameterMap.get("clientSurname")[0].trim();
        String address = parameterMap.get("clientAddress")[0].trim();
        String phone = parameterMap.get("clientPhone")[0].trim();

        ClientServiceImpl clientService = new ClientServiceImpl();
        boolean isCreated = false;
        try {
            synchronized (ClientServlet.class) {
                isCreated = clientService.createClient(name, surname, phone, address);
            }
            if (isCreated) {
                req.setAttribute("clientServlet_msg", CLIENT_CREATED_MSG);
                req.getRequestDispatcher(CLIENT_REGISTRATION_PAGE).forward(req, resp);
            }
        } catch (ClientException e) {
            req.setAttribute("clientServlet_err_msg", e.getMessage());
            req.getRequestDispatcher(CLIENT_REGISTRATION_PAGE).forward(req, resp);
        }
    }

    protected void showClientsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String showBy = parameterMap.get("showBy")[0];

        ClientServiceImpl clientService = new ClientServiceImpl();
        List<Client> clients;
        switch (showBy) {
            case "portion":
                int portionSize = Integer.parseInt(parameterMap.get("portionSize")[0]);
                clients = clientService.showClientsByPortion(portionSize);
                break;
            case "sum":
                int gtSum = Integer.parseInt(parameterMap.get("gtSum")[0]);
                clients = clientService.showClientsGtSum(gtSum);
                break;
            case "month":
                clients = clientService.showClientsLastMonth();
                break;
            default:
                clients = null;
        }
        if (clients != null && !clients.isEmpty()) {
            req.setAttribute("clientList", clients);
            req.getRequestDispatcher(CLIENT_LIST_PAGE).forward(req, resp);
        } else {
            req.setAttribute("clientServlet_err_msg", NO_CLIENTS_FOUND_MSG);
            req.getRequestDispatcher(CLIENT_LIST_PAGE).forward(req, resp);
        }
    }
}
