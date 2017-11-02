package io.sabri.sqliteexample;

/**
 * Created by Sabri on 11/1/17.
 */

public class TodoItem {

    private int id;
    private String title;
    private int checked;

    public TodoItem(int id, String title, int checked) {
        this.id = id;
        this.title = title;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
