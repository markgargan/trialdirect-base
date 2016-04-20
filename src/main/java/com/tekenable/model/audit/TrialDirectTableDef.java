package com.tekenable.model.audit;


import java.util.Objects;

public class TrialDirectTableDef {

    private String columnName;
    private String columnDef;
    private boolean applied;

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
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.columnName);
        hash = 97 * hash + Objects.hashCode(this.columnDef);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrialDirectTableDef other = (TrialDirectTableDef) obj;
        if (!Objects.equals(this.columnName, other.columnName)) {
            return false;
        }
        return Objects.equals(this.columnDef, other.columnDef);
    }

    @Override
    public String toString() {
        return "[" + columnDef + "] is applied: "+applied;
    }
}
