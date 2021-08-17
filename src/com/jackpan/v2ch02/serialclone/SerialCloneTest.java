package com.jackpan.v2ch02.serialclone;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author jackpan
 * @version v1.0 2021/8/17 22:26
 */
public class SerialCloneTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Employee harry = new Employee("Harry Hacker", 35000, 1989, 10, 1);

        Employee harry2 = (Employee) harry.clone();
        harry.raiseSalary(10);

        System.out.println(harry);
        System.out.println(harry2);
    }
}

class SerialCloneable implements Cloneable, Serializable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(this);
            }

            try (InputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                ObjectInputStream in = new ObjectInputStream(bin);
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }

    }
}

/**
 * The familiar Employee class, redefined to extend the
 * SerialCloneable class.
 */
class Employee extends SerialCloneable
{
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String n, double s, int year, int month, int day)
    {
        name = n;
        salary = s;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }

    public LocalDate getHireDay()
    {
        return hireDay;
    }

    /**
     Raises the salary of this employee.
     @byPercent the percentage of the raise
     */
    public void raiseSalary(double byPercent)
    {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString()
    {
        return getClass().getName()
            + "[name=" + name
            + ",salary=" + salary
            + ",hireDay=" + hireDay
            + "]";
    }
}
