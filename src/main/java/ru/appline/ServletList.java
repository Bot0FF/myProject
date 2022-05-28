package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
    Model model = Model.getInstance();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter pw = response.getWriter();
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if(id == 0) {
//            pw.print("<html>" +
//                    "<h3>Доступные пользователи:</h3><br/>" +
//                    "ID пользователя:" +
//                    "<ul>");
//            for(Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
//                pw.print("<li>" + entry.getKey() + "</li>" +
//                        "<ul>" +
//                        "<li>Имя: " + entry.getValue().getName() + "</li>" +
//                        "<li>Фамилия: " + entry.getValue().getSurname() + "</li>" +
//                        "<li>Зарплата: " + entry.getValue().getSalary() + "</li>" +
//                        "</ul>");
//            }
//            pw.print("</ui>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        } else if(id > 0) {
//            if(id > model.getFromList().size()) {
//                pw.print("<html>" +
//                        "<h3>Такого пользователя нет :(</h3>" +
//                        "</ui>" + "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            } else {
//                pw.print("<html>" +
//                        "<h3>Запрошенный пользователь:</h3>" +
//                        "<br/>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "<br/>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurname() + "<br/>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br/>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            }
//        } else {
//            pw.print("<html>" +
//                    "<h3>ID должен быть больше нуля!</h3>" +
//                    "</ui>" + "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        }
//    }


   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       request.setCharacterEncoding("UTF-8");
       String line = null;
       try {
           BufferedReader reader = request.getReader();
           line = reader.readLine();
       } catch (Exception e) {
           System.out.println("Error");
       }

       JsonObject jobj = gson.fromJson(line, JsonObject.class);

       int id = jobj.get("id").getAsInt();

       response.setContentType("application/json;charset=utf-8");
       PrintWriter pw = response.getWriter();

       if(id == 0) {
           pw.print(gson.toJson(model.getFromList()));
       } else if (id > 0 & id <= model.getFromList().size()) {
           pw.print(gson.toJson(model.getFromList().get(id)));
       } else if (id < 0){
           pw.print(gson.toJson("ID должен быть больше нуля!"));
       } else {
           pw.print(gson.toJson("Такого пользователя нет :("));
       }
   }
}
