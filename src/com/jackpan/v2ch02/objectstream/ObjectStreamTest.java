package com.jackpan.v2ch02.objectstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author jackpan
 * @version v1.0 2021/8/16 12:39
 */
public class ObjectStreamTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];

        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))) {
            out.writeObject(staff);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"))) {
            Employee[] newStaff = (Employee[]) in.readObject();
            newStaff[1].raiseSalary(10);

            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        }
    }
}
