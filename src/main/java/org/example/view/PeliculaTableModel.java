package org.example.view;

import org.example.model.Pelicula;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PeliculaTableModel extends AbstractTableModel {
    private final List<Pelicula> data;
    private final String[] cols = {"Título","Año","Director", "Genero"};

    public PeliculaTableModel(List<Pelicula> data){ this.data = data; }

    @Override public int getRowCount(){ return data.size(); }
    @Override public int getColumnCount(){ return cols.length; }
    @Override public String getColumnName(int c){ return cols[c]; }

    /** Devuelve el valor de la celda en la fila {@code r} y columna {@code c}.
     *
     * @param r Fila
     * @param c Columna
     * @return Valor de la celda
     */
     @Override public Object getValueAt(int r,int c){
        Pelicula p = data.get(r);
        return switch(c){
            case 0 -> p.getTitle();
            case 1 -> p.getYear();
            case 2 -> p.getDirector();
            case 3 -> p.getGenre();
            default -> "";
        };
    }

    public Pelicula getAt(int row){ return data.get(row); }
    public void add(Pelicula p){ data.add(p); fireTableRowsInserted(data.size()-1, data.size()-1); }
    public void removeAt(int row){ data.remove(row); fireTableRowsDeleted(row, row); }
}
