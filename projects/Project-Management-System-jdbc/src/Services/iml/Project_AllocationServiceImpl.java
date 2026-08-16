package Services.iml;

import Entitys.Company;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.DeveloperDao;
import Entitys.Dao.Factory.DaoFactory;
import Entitys.Dao.ProjectDao;
import Entitys.Dao.Project_AllocationDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import Entitys.Enums.Project_Status;
import Entitys.Project;
import Entitys.Project_Allocation;
import Services.BussinesException;
import Services.model.Project_AllocationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Project_AllocationServiceImpl implements Project_AllocationService {
    private Project_AllocationDao projectAllocationDao= DaoFactory.creatproject_Allocation();
    private DeveloperDao developerDao = DaoFactory.creatDeveloperJBBC();
    private ProjectDao projectDao=DaoFactory.creatProject();
    private CompanyDao companyDao =DaoFactory.creatCompanyJDBC();
    @Override
    public List<Project_Allocation> findByProject(Project project) {
        List<Project_Allocation> list =   projectAllocationDao.findByProject(project);
        if(list==null|| list.isEmpty()){
            throw new BussinesException("Its empty");
        }
        return list ;
    }

    @Override
    public List<Project_Allocation> findByDeveloper(Developer developer) {
        List<Project_Allocation> list =projectAllocationDao.findByDeveloper(developer);
        if(list==null|| list.isEmpty()){
            throw new BussinesException("Its empty");
        }
        return list ;
    }

    @Override
    public List<Project_Allocation> findByPeriod(LocalDate start, LocalDate end) {
        if(start.isAfter(end) || end.isBefore(start)){
            throw  new BussinesException("This datas cannot be used");
        }
        List<Project_Allocation> list= projectAllocationDao.findByHour(start,end);

        if(list==null|| list.isEmpty()){
            throw new BussinesException("Its empty");
        }
        return list ;
    }

    @Override
    public Map<Project_Status, List<Project_Allocation>> groupAllocationsByProjectStatus(Project project) {
       if(projectAllocationDao.findByProject(project)==null){
           throw new BussinesException("This project do not exist");
       }
        List<Project_Allocation> list = projectAllocationDao.findByProject(project);
       if(list==null || list.isEmpty()){
           throw  new BussinesException("No project with this status");
       }
        Map<Project_Status, List<Project_Allocation>> map =new HashMap<>();
        map.put(project.getStatus(),list);

        return map;
    }

    @Override
    public Map<Developer_Status, List<Project_Allocation>> groupAllocationsByDeveloperStatus(Developer developer) {
        Map<Developer_Status, List<Project_Allocation>> map = new HashMap<>();
        List<Project_Allocation>list = projectAllocationDao.findByDeveloper(developer);
        map.put(developer.getStatus(),list);
        if(list==null|| list.isEmpty()){
            throw new BussinesException("this project allocation have 0 developers");
        }
        if(map==null || map.isEmpty()){
            throw new BussinesException("this project allocation have 0 developers with this satatus");
        }
        return map;
    }


    @Override
    public void update(Project_Allocation projectAllocation) {
        if(findById(projectAllocation.getId())==null){
            throw new BussinesException("This project allocation do not exist");
        }
        projectAllocationDao.update(projectAllocation);
    }

    @Override
    public Project_Allocation deleteByID(Integer id) {
        projectAllocationDao.deleteByID(id);
        return  projectAllocationDao.findById(id);
    }

    @Override
    public List<Project_Allocation> findAll() {
        List<Project_Allocation> list= projectAllocationDao.findAll();
        if(list==null|| list.isEmpty()){
            throw new BussinesException("Its empty");
        }
        return list ;
    }

    @Override
    public Project_Allocation findById(Integer id) {
        Project_Allocation pa = projectAllocationDao.findById(id);
            if(pa==null){
                throw  new BussinesException("This id do not exist");
            }
        return pa;
    }

    @Override
    public void save(Project_Allocation projectAllocation) {
        Project proj = projectDao.findById(projectAllocation.getId_project());
     if(proj==null){
         throw  new BussinesException("This Project Allocation do not exist");
     }
        if(developerDao.findById(projectAllocation.getId_developer())==null){
            throw  new BussinesException("This Project Allocation do not exist");
        }

        if (proj.getStatus() == Project_Status.Suspended || proj.getStatus() == Project_Status.Completed) { // Ajuste a escrita de COmpleted se for minúsculo no seu Enum
            throw new BussinesException("You cant allocate to a project complete or suspended");
        }

        List<Project_Allocation> existingAllocations = projectAllocationDao.findByDeveloper(developerDao.findById(projectAllocation.getId_developer()));


        boolean alreadyAllocated = false;
        if (existingAllocations != null) {
            alreadyAllocated = existingAllocations.stream()
                    .anyMatch(p -> p.getProject().getId().equals(projectAllocation.getId_project()));
        }

        if (alreadyAllocated) {
            throw new BussinesException("This developer is already allocated to this project");
        }
            projectAllocationDao.save(projectAllocation);

    }
}
