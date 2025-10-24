package com.yourcompany.lnd.spring.ai.rag_example.service.faculty.model;


public record FacultyInfo(int sl_no,
                          String name_of_the_faculty,
                          String designation,
                          String dept,
                          String contact_no,
                          String email_id
) {


    @Override
    public String toString() {
        return "The faculty serial number is " + sl_no +
                ", name of the faculty is " + name_of_the_faculty +
                ", designation is " + designation +
                ", department is " + dept +
                ", contact number is " + contact_no +
                ", email id is " + email_id + ". \n\n";
    }
}
