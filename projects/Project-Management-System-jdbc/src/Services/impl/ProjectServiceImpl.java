package Services.impl;


import Entitys.Company;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.Factory.DaoFactory;
import Entitys.Dao.ProjectDao;
import Entitys.Enums.Project_Status;
import Entitys.Project;
import Services.Exception.BusinessException;
import Services.model.ProjectService;

import java.time.LocalDate;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private  final ProjectDao projectDao;

    {
        projectDao = DaoFactory.creatProject();
    }

    @Override
    public List<Project> findByCompany(Company company) {
        if (company == null) {
            throw new BusinessException("Company cannot be null");
        }
        List<Project> list =projectDao.findByCompany(company);
        if(list ==null||list.isEmpty()){
            throw  new BusinessException("This list is empty");
        }
         return  list;
    }

    @Override
    public List<Project> findByStatus(Project_Status project_status) {
        List<Project> list =projectDao.findByStatus(project_status);
        if(list ==null||list.isEmpty()){
            throw  new BusinessException("This list is empty");
        }
        return  list;
    }

    @Override
    public List<Project> findByDate(LocalDate start, LocalDate end) {
        if(start.isAfter(end)|| end.isBefore(start)){
            throw  new BusinessException("Not valid dates");
        }
        List<Project> list =projectDao.findByDate(start,end);

        if(list ==null||list.isEmpty()){
            throw  new BusinessException("This list is empty");
        }
        return  list;
    }

    @Override
    public List<Project> findByNameContainingIgnoreCase(String name) {
        return List.of();
    }


    @Override
    public void update(Project project) {
        if(projectDao.findById(project.getId())==null){
            throw  new BusinessException("Do not exist this project");
        }
        projectDao.update(project);
    }



    @Override
    public Project deleteById(Integer id) {
        Project p= projectDao.deleteByID(id);
        if(projectDao.findById(id)==null || p ==null){
            throw  new BusinessException("Do not exist this project");
        }
        projectDao.deleteByID(id);
        return  projectDao.deleteByID(id);
    }

    @Override
    public List<Project> findAll() {
        List<Project> list=projectDao.findAll();
        if(list ==null||list.isEmpty()){
            throw  new BusinessException("This list is empty");
        }

        return list;
    }

    @Override
    public Project findById(Integer id) {
        if(projectDao.findById(id)==null){
            throw  new BusinessException("Do not exist this project");
        }
        return projectDao.findById(id);
    }

    @Override
    public void save(Project project) {
        if (project.getId() != null && projectDao.findById(project.getId()) != null) {
            throw new BusinessException("Project already exists");
        }
         projectDao.save(project);
    }
}
