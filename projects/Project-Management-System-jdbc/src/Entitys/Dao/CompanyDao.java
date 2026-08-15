package Entitys.Dao;

import Entitys.Company;

public interface CompanyDao extends Dao<Company> {
    Company findByCnpj(String cnpj);
}
