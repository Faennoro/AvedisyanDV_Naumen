package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class NoteDAO {
    //метод на сохранение заметки
    public void save(NoteEntity note){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //сохранение заметки
            session.save(note);
            transaction.commit();
        }
    }

    //метод на удаление заметки
    public void delete(int id){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //получение заметки из сессии для удаления
            NoteEntity note = session.get(NoteEntity.class, id);
            //если заметка есть - она удаляется
            if (note != null) {
                session.delete(note);
            }
            transaction.commit();
        }
    }

    //метод на получение списка заметок
    //аннотация для игнорирования предупреждений о "session.createQuery has raw type"
    @SuppressWarnings("unchecked")
    public List<NoteEntity> getAll(String filter){
        Transaction transaction = null;
        List<NoteEntity> notesList = null;
        //присвоение фильтру "" для избежания NullPointerException
        if (filter==null) filter="";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //если фильтр пуст - получаются все записи
            if (filter.equals("")) {
                notesList = session.createQuery("FROM NoteEntity").getResultList();
            }
            else {
                //из списка заметок будут отображаться только те заметки, где фильтрующий текст присутствует в заголовке либо тексте заметки
                notesList = session.createCriteria(NoteEntity.class).add(
                        Restrictions.or(
                                //like - для нахождения строки в поле, MatchMode.Anywhere - для нахождения с любой позиции (по-умолчанию только с начала строки)
                                Restrictions.like("caption",filter,MatchMode.ANYWHERE),
                                Restrictions.like("noteText",filter,MatchMode.ANYWHERE)
                        )
                ).list();
            }
            transaction.commit();
        }
        return notesList;
    }
}
