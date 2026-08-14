package Entitys;

import Entitys.Enums.Developer_Status;

import java.time.LocalDate;
import java.util.Objects;

public class Developer {
    private  Integer id,id_company;
    private  String name,email,city;
    private LocalDate birthDate;
    private String work_Area;
    private Developer_Status status;
    private Company company;

    public Developer() {
    }

    public Developer(Integer id, Integer id_company, String name, String email, String city, LocalDate birthDate, String work_Area, Developer_Status status, Company company) {
        this.id = id;
        this.id_company = id_company;
        this.name = name;
        this.email = email;
        this.city = city;
        this.birthDate = birthDate;
        this.work_Area = work_Area;
        this.status = status;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_company() {
        return id_company;
    }

    public void setId_company(Integer id_company) {
        this.id_company = id_company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getWork_Area() {
        return work_Area;
    }

    public void setWork_Area(String work_Area) {
        this.work_Area = work_Area;
    }

    public Developer_Status getStatus() {
        return status;
    }

    public void setStatus(Developer_Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id) && Objects.equals(id_company, developer.id_company) && Objects.equals(name, developer.name) && Objects.equals(email, developer.email) && Objects.equals(city, developer.city) && Objects.equals(birthDate, developer.birthDate) && Objects.equals(work_Area, developer.work_Area) && status == developer.status && Objects.equals(company, developer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_company, name, email, city, birthDate, work_Area, status, company);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", id_company=" + id_company +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", birthDate=" + birthDate +
                ", work_Area='" + work_Area + '\'' +
                ", status=" + status +
                ", company=" + company +
                '}';
    }
}
