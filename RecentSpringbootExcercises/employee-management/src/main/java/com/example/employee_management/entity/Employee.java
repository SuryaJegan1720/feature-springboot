package com.example.employee_management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
    @Entity
    @Table(name = "employees")
    public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String gender;
        private String department;
        private int age;
        private double salary;
        private int yearOfJoining;

        public Employee() {
        }

        public Employee(String name, String gender,
                        String department, int age,
                        double salary, int yearOfJoining) {
            this.name = name;
            this.gender = gender;
            this.department = department;
            this.age = age;
            this.salary = salary;
            this.yearOfJoining = yearOfJoining;
        }

       /* public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public int getYearOfJoining() {
            return yearOfJoining;
        }

        public void setYearOfJoining(int yearOfJoining) {
            this.yearOfJoining = yearOfJoining;
        }*/

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    ", department='" + department + '\'' +
                    ", yearOfJoining=" + yearOfJoining +
                    ", salary=" + salary +
                    '}';
        }
    }

