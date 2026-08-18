package Services.model;

import Entitys.Dao.Dao;
import Entitys.Dao.Project_AllocationDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import Entitys.Enums.Project_Status;
import Entitys.Project;
import Entitys.Project_Allocation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Project_AllocationService extends Project_AllocationDao {

    Map<Project_Status, List<Project_Allocation>> groupAllocationsByProjectStatus(Project project);

    Map<Developer_Status, List<Project_Allocation>> groupAllocationsByDeveloperStatus(Developer developer);

}


