package jdbcproj.controller.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbcproj.dao.togroup.DAOGroup;
import jdbcproj.data.group.Group;

public class FindGroupByTeacher extends HttpServlet{

    private static final long serialVersionUID = 73111096375035L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	DAOGroup dao = new DAOGroup();
   	
    	try{
    		String groupName = req.getParameter("groupName");
    		Group group = dao.getByName(groupName);
    		
    		req.setAttribute("group", group);
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	
    	ServletContext servletContext = getServletContext();
        RequestDispatcher disp = servletContext.getRequestDispatcher("/jsp/search/GroupsSearchByTeacher.jsp");
        disp.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        doGet(req, res);
    }
}