package com.jd.o2o.test.scala.javaUseScala;

/**
 * Created by Administrator on 2015/10/16.
 */
public class StudentApp {

    public static void main(String[] args) {

        Student s = StudentFactory.getStudent("tang", "dongyang", "Scala");
        s.teach("Java and Scala");
        System.out.println(s.getFirstName() + ", " + s.getLastName());
    }
}
