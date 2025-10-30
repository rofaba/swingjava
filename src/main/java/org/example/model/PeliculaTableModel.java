package org.example.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PeliculaTableModel extends AbstractTableModel {

    private final String[] cols = {"ID","Título","Año","Director","Género"};
    private List<Pelicula> data = new ArrayList<>();

    public void setData(List<Pelicula> list) {
        data = (list != null) ? list : new ArrayList<>();
        fireTableDataChanged();
    }

    public Pelicula getAt(int row) {
        return data.get(row);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override public Class<?> getColumnClass(int c) {
        return switch (c) {
            case 0, 2 -> Integer.class; // ID y Año como números
            default -> String.class;
        };
    }

    @Override public boolean isCellEditable(int r, int c) { return false; }

    @Override public Object getValueAt(int r, int c) {
        var p = data.get(r);
        return switch (c) {
            case 0 -> p.getId();
            case 1 -> p.getTitle();
            case 2 -> p.getYear();
            case 3 -> p.getDirector();
            case 4 -> p.getGenre();
            default -> null;
        };
    }
}
