package Services.impl;

import Entitys.Company;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.DeveloperDao;
import Entitys.Dao.Factory.DaoFactory;
import Entitys.Dao.ProjectDao;
import Services.Exception.BusinessException;
import Services.model.CompanyService;

import java.util.List;

public class CompanyServiceIpml implements CompanyService {

    private final DeveloperDao developerDao = DaoFactory.creatDeveloperJBBC();
    private final ProjectDao projectDao = DaoFactory.creatProject();
    private final CompanyDao companyDao = DaoFactory.creatCompanyJDBC();


    @Override
    public Company findByCnpj(Company company) {
        if(company==null || companyDao.findById(company.getId())==null){
            throw new BusinessException("Company not found");
        }
        Company foundCompany = companyDao.findByCnpj(company.getCnpj());
        if (foundCompany == null) {
            throw new BusinessException("Company not found with the provided CNPJ");
        }
        return foundCompany;
    }

    @Override
    public void update(Company company) {
        if(company==null || companyDao.findById(company.getId())==null){
            throw  new BusinessException("Company not found");
        }
        companyDao.update(company);
    }

    @Override
    public Company deleteByID(Integer id) {
        if(id==null || companyDao.findById(id)==null){
            throw  new BusinessException("Id cannot be null");
        }
        Company c = companyDao.findById(id);

        if (c == null) {
            throw new BusinessException("Company not found");
        }

        if(projectDao.findByCompany(c)!=null||developerDao.findByCompany(c)!=null){
            throw  new BusinessException("You cant delete a company with developers e projects");
        }
        companyDao.findById(id);
       return companyDao.deleteByID(id);
    }

    @Override
    public List<Company> findAll() {
        List<Company> list = companyDao.findAll();
        if(list ==null||list.isEmpty()){
            throw  new BusinessException("This list is empty");
        }
        return  list;
    }

    @Override
    public Company findById(Integer id) {
        if (id == null) {
            throw new BusinessException("ID cannot be null");
        }
        Company company = companyDao.findById(id);
        if (company == null) {
            throw new BusinessException("Company not found");
        }
        return company;
    }

    @Override
    public void save(Company company) {
        if (company == null) {
            throw new BusinessException("Company cannot be null");
        }

        if (company.getId() != null && companyDao.findById(company.getId()) != null) {
            throw new BusinessException("Company already exists");
        }
        companyDao.save(company);
    }
}
