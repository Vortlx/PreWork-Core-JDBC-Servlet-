package jdbcproj.controller;


import jdbcproj.dao.toperson.DAOTeachers;
import jdbcproj.data.person.Teacher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestOnTeacher extends HttpServlet {
    private static final long serialVersionUID = 9878761265153L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DAOTeachers connToTeacher = new DAOTeachers();

        String name = req.getParameter("name");
        String familyName = req.getParameter("familyName");

        List<Teacher> list = new ArrayList<Teacher>();
        try{
            if("".equals(name) && "".equals(familyName)){
                list = connToTeacher.getAll();
            }else if(!"".equals(name) && "".equals(familyName)){
                list = connToTeacher.getByName(name);
            } else if("".equals(name) && !"".equals(familyName)){
                list = connToTeacher.getByFamilyName(familyName);
            }else{
                list = connToTeacher.getTeacher(name, familyName);
            }

            req.setAttribute("teachers", list);
        }catch(SQLException e){
        	e.printStackTrace();
        }

        ServletContext servletContext = getServletContext();
        RequestDispatcher disp = servletContext.getRequestDispatcher("/jsp/TeachersList.jsp");
        disp.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        doGet(req, res);
    }

}
