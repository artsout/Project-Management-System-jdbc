package Services.impl;

import DB.DbException;
import Entitys.Company;
import Entitys.Dao.DeveloperDao;
import Entitys.Dao.Factory.DaoFactory;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import Services.model.DeveloperService;

import java.util.List;

public class DeveloperServiceIpml implements DeveloperService {
    private  final DeveloperDao developerDao= DaoFactory.creatDeveloperJBBC();

    @Override
    public List<Developer> findByCompany(Company company) {
        if(company==null){
            throw  new DbException("put something company cant be null");
        }

        List<Developer>list=developerDao.findByCompany(company);
        if(list==null || list.isEmpty()){
            throw  new DbException("Dont have any developers in this company");
        }
        return list;
    }

    @Override
    public List<Developer> findByStatus(Developer_Status developer_status) {
        if(developer_status ==null){
            throw  new DbException("put something developer status cant be null");
        }
        List<Developer>list=developerDao.findByStatus(developer_status);
        if(list==null || list.isEmpty()){
            throw  new DbException("Dont have any developers in this company");
        }
        return list;
    }

    @Override
    public List<Developer> findByWork_Area(Developer developer) {
        if(developer==null){
            throw  new DbException("put something work area cant be null");
        }

        List<Developer>list=developerDao.findByWork_Area(developer);
        if(list==null || list.isEmpty()){
            throw  new DbException("Dont have any developers in this company");
        }
        return list;
    }

    @Override
    public Developer findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new DbException("Email cannot be null or empty");
        }

        Developer developer = developerDao.findByEmail(email);

        if (developer == null) {
            throw new DbException("Developer not found");
        }

        return developer;
    }

    @Override
    public void update(Developer developer) {
        if(developer==null){
            throw  new DbException("put something developer cant be null");
        }
        if(developerDao.findByEmail(developer.getEmail())!=null){
            throw  new DbException("Email is unique,aready exist this email");
        }
        developerDao.update(developer);
    }

    @Override
    public Developer deleteById(Integer id) {
        Developer developer = developerDao.findById(id);

        if (developer == null) {
            throw new DbException("This id does not exist");
        }

        developerDao.deleteByID(id);

        return developer;
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> list=developerDao.findAll();
        if(list==null || list.isEmpty()){
            throw  new DbException("Dont have any developers ");
        }
        return list;
    }

    @Override
    public Developer findById(Integer id) {
        if(id==null){
            throw  new DbException("put something id cant be null");
        }
        Developer d = findById(id);

        if(d==null){
            throw  new DbException("This id do not exist");
        }
        return d;
    }

    @Override
    public void save(Developer developer) {
        if(developer==null){
            throw  new DbException("put something developer cant be null");
        }
        if(developer.getId() !=null && developerDao.findById(developer.getId())!=null){
            throw  new DbException("Id is unique,aready exist this id");
        }
        if((developerDao.findByEmail(developer.getEmail()))!=null && !developerDao.findByEmail(developer.getEmail()).getId().equals(developer.getId())){
            throw  new DbException("Email is unique,aready exist this email");
        }
        developerDao.save(developer);
    }
}
