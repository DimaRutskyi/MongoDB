package hw8.taxi.action;

import hw8.taxi.domain.Client;
import hw8.taxi.domain.Order;
import hw8.taxi.exception.OrderException;
import hw8.taxi.service.ClientServiceImpl;
import hw8.taxi.service.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by v.davidenko on 29.01.2016.
 *
 */

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {

    private OrderServiceImpl orderService = new OrderServiceImpl();
    private ClientServiceImpl clientService = new ClientServiceImpl();
    private int portionStartPos;

    final static String ORDER_CREATED_MSG = "New order created successfully";
    final static String ORDER_UPDATED_MSG = "Order updated successfully";
    final static String NO_ORDERS_FOUND_MSG = "No orders found";
    final static String ORDER_MANAGEMENT_PAGE = "order.jsp";
    final static String ORDER_LIST_PAGE = "orders.jsp";
    private final static int SHOW_PORTION_SIZE = 5;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        showOrdersService(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        orderActionService(req, resp);
    }

    public void orderActionService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        if (!parameterMap.containsKey("selectAction")){
            registrationOrderService(req, resp);
            return;
        }
        String action = parameterMap.get("selectAction")[0];
        String orderId = "";

        if (action.equals("edit")) {
            orderId = parameterMap.get("id")[0].trim();
            Order order = orderService.getOrderMap().get(Long.valueOf(orderId));
            if (order == null) {
                req.setAttribute("orderServlet_err_msg", NO_ORDERS_FOUND_MSG);
                req.getRequestDispatcher(ORDER_MANAGEMENT_PAGE).forward(req, resp);
                return;
            }
            req.setAttribute("clientId", String.valueOf(order.getClientId()));
            req.setAttribute("amount", order.getAmount());
            req.setAttribute("addressFrom", order.getAddressFrom());
            req.setAttribute("addressTo", order.getAddressTo());

        } else if (action.equals("new")){
            orderId = String.valueOf(sequenceNumber(orderService.getOrderMap().keySet()));
        }
        Set<Map.Entry<Long, Client>> entries = clientService.getClientMap().entrySet();
        List<Client> clientList = new LinkedList<Client>();
        for (Map.Entry entry : entries) {
            clientList.add((Client) entry.getValue());
        }
        req.setAttribute("clientList", clientList);
        req.setAttribute("formAction", action);
        req.setAttribute("orderId", orderId);

        req.getRequestDispatcher(ORDER_MANAGEMENT_PAGE).forward(req, resp);
    }

    public void registrationOrderService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        String action = parameterMap.get("formAction")[0];
        Long id = Long.valueOf(parameterMap.get("orderId")[0]);
        Long clientId = Long.valueOf(parameterMap.get("client")[0]);
        String amount = parameterMap.get("amount")[0].trim();
        String addressFrom = parameterMap.get("addressFrom")[0].trim();
        String addressTo = parameterMap.get("addressTo")[0].trim();

        Client client = clientService.getClientMap().get(clientId);
        boolean isSuccess = false;
        try {
            if (action.equals("new")) {
                synchronized (ClientServlet.class) {
                    isSuccess = orderService.createOrder(id, client, amount, addressFrom, addressTo);
                }
                if (isSuccess) {
                    req.setAttribute("orderServlet_msg", ORDER_CREATED_MSG);
                    req.getRequestDispatcher(ORDER_MANAGEMENT_PAGE).forward(req, resp);
                }
            } else if (action.equals("edit")) {
                synchronized (ClientServlet.class) {
                    orderService.editOrder(id, client, amount, addressFrom, addressTo);
                }
                req.setAttribute("orderServlet_msg", ORDER_UPDATED_MSG);
                req.getRequestDispatcher(ORDER_MANAGEMENT_PAGE).forward(req, resp);
            }
        } catch (OrderException e) {
            req.setAttribute("orderServlet_err_msg", e.getMessage());
            req.getRequestDispatcher(ORDER_MANAGEMENT_PAGE).forward(req, resp);
        }
    }

    public void showOrdersService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String showBy = parameterMap.get("showBy")[0];
        List<Order> orders = null;
        String title = "";
        long fromSum = 0;
        long toSum = 0;

        switch (showBy) {
            case "portion":
                orders = orderService.showOrdersByPortion();
                int portionSize = (SHOW_PORTION_SIZE > orders.size()) ? orders.size() : SHOW_PORTION_SIZE;
                title = "Orders " + String.valueOf(portionStartPos + 1) + " - " +
                        String.valueOf(portionStartPos + portionSize);
                break;
            case "sum":
                portionStartPos = 0;
                fromSum = Integer.parseInt(parameterMap.get("fromSum")[0]);
                toSum = Integer.parseInt(parameterMap.get("toSum")[0]);
                orders = orderService.showOrders(fromSum, toSum);
                title = "Orders on sum from " + String.valueOf(fromSum) + " to " + String.valueOf(toSum);
                break;
        }
        req.setAttribute("showBy", showBy);
        req.setAttribute("fromSum", fromSum);
        req.setAttribute("toSum", toSum);

        if (orders != null && !orders.isEmpty()) {
            req.setAttribute("orderList", orders);
            req.setAttribute("orderListTitle", title);
        } else {
            req.setAttribute("orderServlet_err_msg", NO_ORDERS_FOUND_MSG);
        }
        req.getRequestDispatcher(ORDER_LIST_PAGE).forward(req, resp);
    }

    public Long sequenceNumber(Set<Long> numbers) {
        Long max = 0L;
        for (Long number : numbers) {
            if (max.compareTo(number) < 0) max = number;
        }
        return ++max;
    }
}