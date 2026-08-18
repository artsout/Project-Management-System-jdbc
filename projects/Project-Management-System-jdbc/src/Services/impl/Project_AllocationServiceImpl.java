package Services.impl;

import DB.DbException;
import Entitys.Enums.Project_Status;
import Entitys.Project_Allocation;

import java.util.List;

import Entitys.Dao.DeveloperDao;
import Entitys.Dao.Factory.DaoFactory;
import Entitys.Dao.ProjectDao;
import Entitys.Dao.Project_AllocationDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;

import Entitys.Project;


import Services.Exception.BusinessException;
import Services.model.Project_AllocationService;

import java.time.LocalDate;

import java.util.HashMap;

import java.util.Map;

public class Project_AllocationServiceImpl implements Project_AllocationService{
        private final Project_AllocationDao projectAllocationDao= DaoFactory.creatproject_Allocation();
        private final DeveloperDao developerDao = DaoFactory.creatDeveloperJBBC();
        private final ProjectDao projectDao=DaoFactory.creatProject();

        @Override
        public List<Project_Allocation> findByProject(Project project) {
            List<Project_Allocation> list =   projectAllocationDao.findByProject(project);
            if(list==null|| list.isEmpty()){
                throw new BusinessException("Its empty");
            }
            return list ;
        }

        @Override
        public List<Project_Allocation> findByDeveloper(Developer developer) {
            List<Project_Allocation> list =projectAllocationDao.findByDeveloper(developer);
            if(list==null|| list.isEmpty()){
                throw new BusinessException("Its empty");
            }
            return list ;
        }

        @Override
        public List<Project_Allocation> findByHour(LocalDate start, LocalDate end) {
            if(start.isAfter(end) || end.isBefore(start)){
                throw  new BusinessException("This datas cannot be used");
            }
            List<Project_Allocation> list= projectAllocationDao.findByHour(start,end);

            if(list==null|| list.isEmpty()){
                throw new BusinessException("Its empty");
            }
            return list ;
        }

        @Override
        public Map<Project_Status, List<Project_Allocation>> groupAllocationsByProjectStatus(Project project) {

            List<Project_Allocation> list = projectAllocationDao.findByProject(project);
            if(list==null || list.isEmpty()){
                throw  new BusinessException("No project with this status");
            }
            Map<Project_Status, List<Project_Allocation>> map =new HashMap<>();
            map.put(project.getStatus(),list);

            return map;
        }

        @Override
        public Map<Developer_Status, List<Project_Allocation>> groupAllocationsByDeveloperStatus(Developer developer) {
            if (developer == null) {
                throw new BusinessException("Developer cannot be null");
            }

            List<Project_Allocation>list = projectAllocationDao.findByDeveloper(developer);

            if(list==null|| list.isEmpty()){
                throw new BusinessException("this project allocation have 0 developers");
            }

            Map<Developer_Status, List<Project_Allocation>> map = new HashMap<>();
            map.put(developer.getStatus(),list);

            return map;
        }


        @Override
        public void update(Project_Allocation projectAllocation) {
            if(findById(projectAllocation.getId())==null){
                throw new BusinessException("This project allocation do not exist");
            }
            projectAllocationDao.update(projectAllocation);
        }

        @Override
        public Project_Allocation deleteByID(Integer id) {
            if(id==null){
                throw  new DbException("Id cant be null");

            }
            projectAllocationDao.deleteByID(id);
            return  projectAllocationDao.findById(id);
        }

        @Override
        public List<Project_Allocation> findAll() {
            List<Project_Allocation> list= projectAllocationDao.findAll();
            if(list==null|| list.isEmpty()){
                throw new BusinessException("Its empty");
            }
            return list ;
        }

        @Override
        public Project_Allocation findById(Integer id) {
            Project_Allocation pa = projectAllocationDao.findById(id);
            if(pa==null){
                throw  new BusinessException("This id do not exist");
            }
            return pa;
        }

        @Override
        public void save(Project_Allocation projectAllocation) {
            Project proj = projectDao.findById(projectAllocation.getId_project());
            if(proj==null){
                throw  new BusinessException("This Project Allocation do not exist");
            }
            if(projectAllocation.getId() != null && developerDao.findById(projectAllocation.getId_developer())==null){
                throw  new BusinessException("This Project Allocation do not exist");
            }

            if (proj.getStatus() == Project_Status.Suspended || proj.getStatus() == Project_Status.Completed) { 
                throw new BusinessException("You cant allocate to a project complete or suspended");
            }

            List<Project_Allocation> existingAllocations = projectAllocationDao.findByDeveloper(developerDao.findById(projectAllocation.getId_developer()));


            boolean alreadyAllocated = false;
            if (existingAllocations != null) {
                alreadyAllocated = existingAllocations.stream()
                        .anyMatch(p -> p.getProject().getId().equals(projectAllocation.getId_project()));
            }

            if (alreadyAllocated) {
                throw new BusinessException("This developer is already allocated to this project");
            }
            projectAllocationDao.save(projectAllocation);

        }
    }

