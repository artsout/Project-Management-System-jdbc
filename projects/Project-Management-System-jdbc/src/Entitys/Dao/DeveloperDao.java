package Entitys.Dao;

import Entitys.Company;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;

import java.util.List;
import java.util.Map;

public interface DeveloperDao extends Dao<Developer>{

    List<Developer> findByCompany(Company company);
    List<Developer> findByStatus(Developer_Status developer_status);
    List<Developer> findByWork_Area(Developer developer);
    Developer findByEmail(String email);


}
