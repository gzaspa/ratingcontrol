package edu.chdtu.report.ratingcontrol.entity;

import java.io.Serializable;


public class SubjectGroupPrimaryKey implements Serializable {
    protected int subjectId;
    protected int groupId;

    public SubjectGroupPrimaryKey(){
    }

    public SubjectGroupPrimaryKey(int subjectId, int groupId) {
        this.subjectId = subjectId;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectGroupPrimaryKey that = (SubjectGroupPrimaryKey) o;

        if (subjectId != that.subjectId) return false;
        return groupId == that.groupId;

    }

    @Override
    public int hashCode() {
        int result = subjectId;
        result = 31 * result + groupId;
        return result;
    }
}
