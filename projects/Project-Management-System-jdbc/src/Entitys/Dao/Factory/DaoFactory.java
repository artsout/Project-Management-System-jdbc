package Entitys.Dao.Factory;

import DB.DB;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.DeveloperDao;
import Entitys.Dao.ProjectDao;
import Entitys.Dao.Project_AllocationDao;
import Entitys.Dao.iml.CompanyDaoJDBC;
import Entitys.Dao.iml.DeveloperDaoJDBC;
import Entitys.Dao.iml.ProjectDaoJDBC;
import Entitys.Dao.iml.Project_AllocationDaoJDBC;
import Entitys.Project;

public class DaoFactory {
    public  static CompanyDao creatCompanyJDBC(){
        return new CompanyDaoJDBC(DB.getConnection());
    }
    public  static DeveloperDao creatDeveloperJBBC(){
        return  new DeveloperDaoJDBC(DB.getConnection());
    }
    public  static ProjectDao creatProject(){
        return  new ProjectDaoJDBC(DB.getConnection());
    }
    public  static Project_AllocationDao creatproject_Allocation(){
        return  new Project_AllocationDaoJDBC(DB.getConnection());
    }
}
