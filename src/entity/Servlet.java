package entity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class Servlet extends HttpServlet {

    private NoteDAO noteDAO = new NoteDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try{
            //действие при отправке формы create
            if (action.equals("/create")) {createNote(request,response);}
            else
                //действие при нажатии на ссылку "удалить"
                if (action.equals("/delete")) {deleteNote(request,response);}
                //действие при отправке формы getList
             else if(action.equals("/getList")) {getNotes(request,response);}
             else getNotes(request,response);

        } catch (SQLException ex) {
            throw new ServletException(ex);
    }
    }

    //метод создания заметки с помощью формы на веб-странице
    private void createNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        //проверка на наличия текста заметки
        if (request.getParameter("text").isEmpty()) {
            //отображение на странице текста ошибки
            request.setAttribute("createResult","Ошибка! Текст заметки не должен быть пуст!");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } else
            //проверка на длину заголовка и текста
            if (request.getParameter("caption").length()>20||request.getParameter("text").length()>70)
        {
            //отображение на странице текста ошибки
            request.setAttribute("createResult","Ошибка! Превышена длина текста заметки (70) либо заголовка (20)");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
        else
        {
            //System.out.println(request.getParameter("caption")+" - "+request.getParameter("text"));
            //создание заметки с помощью данных с веб-страницы
            NoteEntity newNote = new NoteEntity(request.getParameter("caption"), request.getParameter("text"));
            noteDAO.save(newNote);
            //отображение на странице текста успешного создания заметки
            request.setAttribute("createResult","Заметка успешно создана!");
            //получение обновленного списка
            getNotes(request,response);
        }
    }

    //метод для удаления заметки с веб-страницы
    private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        noteDAO.delete(id);
        //получение обновленного списка
        getNotes(request,response);
    }

    //метод для получения списка заметок для отображения на веб-странице
    private void getNotes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<NoteEntity> list = noteDAO.getAll(request.getParameter("filter"));
        //список заметок отправится в index.jsp в переменную list
        request.setAttribute("list",list);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
