package Entitys.Dao;

import Entitys.Company;
import Entitys.Enums.Project_Status;
import Entitys.Project;

import java.time.LocalDate;
import java.util.List;

public interface ProjectDao extends Dao<Project>{
        List<Project> findByCompany(Company company);
        List<Project> findByStatus(Project_Status project_status);
        List<Project> findByDate(LocalDate max,LocalDate min);
}
