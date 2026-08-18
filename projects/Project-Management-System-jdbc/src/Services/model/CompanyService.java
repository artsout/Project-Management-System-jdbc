package Services.model;

import Entitys.Company;
import Entitys.Dao.Dao;

import java.util.List;

public interface CompanyService extends Dao<Company> {

    Company findByCnpj(Company company);
}
