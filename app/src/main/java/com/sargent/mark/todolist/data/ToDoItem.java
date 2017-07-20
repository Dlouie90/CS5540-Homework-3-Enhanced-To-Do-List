package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

/* I have updated the ToDoItem class to include checked and category just in case I use this class.
    Getters and Setters were also auto-generated for the two new fields.
 */
public class ToDoItem {
    private String description;
    private String dueDate;
    private boolean checked;
    private String category;

    public ToDoItem(String description, String dueDate, boolean checked, String category) {
        this.description = description;
        this.dueDate = dueDate;
        this.checked = checked;
        this.category = category;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
