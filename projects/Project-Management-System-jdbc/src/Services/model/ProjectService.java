package Services.model;

import Entitys.Company;
import Entitys.Dao.Dao;
import Entitys.Enums.Project_Status;
import Entitys.Project;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProjectService extends Service<Project> {
       List<Project> findByCompany(Company company);
       List<Project> findByStatus(Project_Status project_status);
       List<Project> findByDate(LocalDate start, LocalDate end);
        List<Project> findByNameContainingIgnoreCase(String name);

}
