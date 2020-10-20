package entity;

import javax.persistence.*;

//сущность заметки с аннотациями hibernate
@Entity
@Table(name="e_notes")
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "caption")
    String caption; //заголовок заметки
    @Column(name = "noteText")
    String noteText; //текст заметки

    public NoteEntity() {
    }

    //конструктор для создания заметки без заголовка
    public NoteEntity(String noteText) {
        this.noteText = noteText;
    }

    //конструктор для создания заметки с заголовком
    public NoteEntity(String caption, String noteText) {
        this.caption = caption;
        this.noteText = noteText;
    }

    //получения заголовка для отображения на странице. Если заголовка нет - отображается текст
    public String getCaption() {
        if (caption.isEmpty()) return noteText; else
            return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getId() {
        return id;
    }

    public String getNoteText() {
        if (caption.isEmpty()) return ""; else
            return noteText;
    }

    @Override
    public String toString() {
        return caption + ": " + noteText;
    }

}
