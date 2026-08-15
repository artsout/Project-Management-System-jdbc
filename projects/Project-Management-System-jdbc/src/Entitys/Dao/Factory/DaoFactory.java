package Entitys.Dao.Factory;

import DB.DB;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.DeveloperDao;
import Entitys.Dao.iml.CompanyDaoJDBC;
import Entitys.Dao.iml.DeveloperDaoJDBC;

public class DaoFactory {
    public  static CompanyDao creatCompanyJDBC(){
        return new CompanyDaoJDBC(DB.getConnection());
    }
    public  static DeveloperDao creatDeveloperJBBC(){
        return  new DeveloperDaoJDBC(DB.getConnection());
    }
}
