package Services.model;

import Entitys.Dao.Dao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import Entitys.Enums.Project_Status;
import Entitys.Project;
import Entitys.Project_Allocation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Project_AllocationService extends Dao<Project_Allocation> {

    List<Project_Allocation> findByProject(Project project);

    List<Project_Allocation> findByDeveloper(Developer developer);

    List<Project_Allocation> findByPeriod(LocalDate start, LocalDate end);

    Map<Project_Status, List<Project_Allocation>> groupAllocationsByProjectStatus(Project project);

    Map<Developer_Status, List<Project_Allocation>> groupAllocationsByDeveloperStatus(Developer developer);

}


