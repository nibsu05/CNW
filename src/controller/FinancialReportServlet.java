package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.FinancialReportBo;

/**
 * Servlet implementation class FinancialReportServlet
 */
@WebServlet("/FinancialReport")
public class FinancialReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinancialReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        try {
            FinancialReportBo dao = new FinancialReportBo();

            Map<String, BigDecimal> monthlyRevenue = dao.getMonthlyRevenue();
            BigDecimal totalPaid = dao.getTotalPaid();

            request.setAttribute("monthlyRevenue", monthlyRevenue);
            request.setAttribute("totalPaid", totalPaid);

            request.getRequestDispatcher("admin_revenue.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
