package Entitys.Dao;

import DB.DB;
import Entitys.Company;
import Entitys.Dao.iml.CompanyDaoJDBC;

public class DaoFactory {
    public  static CompanyDao creatCompany(){
        return new CompanyDaoJDBC(DB.getConnection());
    }
}
