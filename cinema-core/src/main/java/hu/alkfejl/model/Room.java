package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Room {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty rows = new SimpleIntegerProperty();
    private IntegerProperty columns = new SimpleIntegerProperty();

    public Room(Integer id, Integer rows, Integer columns) {
        this.id.set(id);
        this.rows.set(rows);
        this.columns.set(columns);
    }

    public Room() {
    }

    public void copyTo(Room r){
        r.id.bindBidirectional(this.idProperty());
        r.rows.bindBidirectional(this.rowsProperty());
        r.columns.bindBidirectional(this.columnsProperty());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", rows=" + rows +
                ", columns=" + columns +
                '}';
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows.set(rows);
    }

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

}


