package Entitys;

import Entitys.Enums.Project_Status;

import java.time.LocalDate;
import java.util.Objects;

public class Project {
    private  Integer id , id_company;
    private String name ,description;
    private LocalDate start ,delivered_Deadline;
    private Project_Status status;
    private Company company;
    public Project() {
    }

    public Project(Integer id, Integer id_company, String name, String description, LocalDate start, LocalDate delivered_Deadline, Project_Status status, Company company) {
        this.id = id;
        this.id_company = id_company;
        this.name = name;
        this.description = description;
        this.start = start;
        this.delivered_Deadline = delivered_Deadline;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getDelivered_Deadline() {
        return delivered_Deadline;
    }

    public void setDelivered_Deadline(LocalDate delivered_Deadline) {
        this.delivered_Deadline = delivered_Deadline;
    }

    public Project_Status getStatus() {
        return status;
    }

    public void setStatus(Project_Status status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(id_company, project.id_company) && Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(start, project.start) && Objects.equals(delivered_Deadline, project.delivered_Deadline) && status == project.status && Objects.equals(company, project.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_company, name, description, start, delivered_Deadline, status, company);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", id_company=" + id_company +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", delivered_Deadline=" + delivered_Deadline +
                ", status=" + status +
                ", company=" + company +
                '}';
    }
}
