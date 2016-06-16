package com.tekenable.model.audit;


public class TrialDirectTableDef {

    private String columnName;
    private String columnDef;
    private boolean applied;

    public TrialDirectTableDef() {
    }

    public TrialDirectTableDef(String colName, String colDef) {
        this.columnName = colName;
        this.columnDef = colDef;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrialDirectTableDef)) return false;

        TrialDirectTableDef that = (TrialDirectTableDef) o;

        if (applied != that.applied) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        return !(columnDef != null ? !columnDef.equals(that.columnDef) : that.columnDef != null);

    }

    @Override
    public int hashCode() {
        int result = columnName != null ? columnName.hashCode() : 0;
        result = 31 * result + (columnDef != null ? columnDef.hashCode() : 0);
        result = 31 * result + (applied ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + columnDef + "] is applied: "+applied;
    }
}
