package Services.model;

import Entitys.Company;
import Entitys.Dao.Dao;
import Entitys.Dao.DeveloperDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;

import java.util.List;

public interface DeveloperService extends Service<Developer> {
    List<Developer> findByCompany(Company company);
    List<Developer> findByStatus(Developer_Status developer_status);
    List<Developer> findByWork_Area(Developer developer);
    Developer findByEmail(String email);
}
