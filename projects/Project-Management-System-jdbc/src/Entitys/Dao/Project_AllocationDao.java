package Entitys.Dao;

import Entitys.Developer;
import Entitys.Project;
import Entitys.Project_Allocation;

import java.time.LocalDate;
import java.util.List;

public interface Project_AllocationDao extends Dao <Project_Allocation>{

    List<Project_Allocation> findByProject(Project project);
    List<Project_Allocation> findByDeveloper(Developer developer);
    List<Project_Allocation> findByHour(LocalDate min,LocalDate max);
}
