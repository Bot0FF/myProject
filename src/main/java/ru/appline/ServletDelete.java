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

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        User tempUser = model.getFromList().get(id); //Временный юзер, чтобы написать кто был удален
        model.getFromList().remove(id);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        pw.print(gson.toJson("Этот пользователь удален из базы: " + "ID=" +
                id + " " + tempUser.getName() + " " + tempUser.getSurname()));
    }
}
