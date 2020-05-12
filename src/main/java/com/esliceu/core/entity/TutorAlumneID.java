package com.esliceu.core.entity;

import java.io.Serializable;

public class TutorAlumneID implements Serializable {
    private String alumne;
    private String tutor;

    public TutorAlumneID(String alumne, String tutor) {
        this.alumne = alumne;
        this.tutor = tutor;
    }

    public TutorAlumneID() {
    }

    public String getAlumne() {
        return alumne;
    }

    public void setAlumne(String alumneCodi) {
        this.alumne = alumneCodi;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
