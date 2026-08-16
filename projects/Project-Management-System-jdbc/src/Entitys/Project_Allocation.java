package Entitys;

import java.time.LocalDate;
import java.util.Objects;

public class Project_Allocation{
    private Integer id_project,id_developer;
    private LocalDate hours_allocated;
    private  Project project;
    private  Developer developer;

    public Project_Allocation() {
    }

    public Project_Allocation(Integer id_project, Integer id_developer, LocalDate hours_allocated, Project project, Developer developer) {
        this.id_project = id_project;
        this.id_developer = id_developer;
        this.hours_allocated = hours_allocated;
        this.project = project;
        this.developer = developer;
    }

    public Integer getId_project() {
        return id_project;
    }

    public void setId_project(Integer id_project) {
        this.id_project = id_project;
    }

    public Integer getId_developer() {
        return id_developer;
    }

    public void setId_developer(Integer id_developer) {
        this.id_developer = id_developer;
    }

    public LocalDate getHours_allocated() {
        return hours_allocated;
    }

    public void setHours_allocated(LocalDate hours_allocated) {
        this.hours_allocated = hours_allocated;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        Project_Allocation that = (Project_Allocation) o;
        return Objects.equals(id_project, that.id_project) && Objects.equals(id_developer, that.id_developer) && Objects.equals(hours_allocated, that.hours_allocated) && Objects.equals(project, that.project) && Objects.equals(developer, that.developer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_project, id_developer, hours_allocated, project, developer);
    }

    @Override
    public String toString() {
        return "Project_Allocation{" +
                "id_project=" + id_project +
                ", id_developer=" + id_developer +
                ", hours_allocated=" + hours_allocated +
                ", project=" + project +
                ", developer=" + developer +
                '}';
    }
}
