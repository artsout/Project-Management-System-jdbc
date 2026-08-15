package Entitys.Dao.iml;

import DB.DbException;
import Entitys.Company;
import Entitys.Dao.CompanyDao;
import Entitys.Dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyDaoJDBC implements Dao <Company> , CompanyDao {

    private final Connection conn;

    public CompanyDaoJDBC(Connection conn) {
        this.conn=conn;
    }

    @Override
    public void update(Company company) {
       try(PreparedStatement ps = conn.prepareStatement("UPDATE company set name=?,cnpj=?,city=? where id=?", Statement.RETURN_GENERATED_KEYS)){

           ps.setString(1, company.getName());
           ps.setString(2, company.getCnpj());
           ps.setString(3, company.getCity());
           ps.setInt(4, company.getId());

           int row=ps.executeUpdate();
               if(row==0){
                   throw  new DbException("No row affected");
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public  Company deleteByID(Integer id) {
        try(PreparedStatement ps = conn.prepareStatement("Delete from company where company.id=?")){
                ps.setInt(1,id);
                try (ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        Company c = instanciateCompany(rs);
                        return c;
                    }
                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    @Override
    public List<Company> findAll() {
        List<Company> list=new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * from company")){

            try(ResultSet rs =ps.executeQuery()){
                while (rs.next()){
                    Company c = instanciateCompany(rs);
                    list.add(c);
                }
                return list;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  Company findById(Integer id) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * from company where company.id=?")){
            ps.setInt(1,id);

            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                   Company c= instanciateCompany(rs);
                    return c;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    @Override
    public void save(Company company) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO company (name,cnpj,city) values (?,?,?)",Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, company.getName());
            ps.setString(2, company.getCnpj());
            ps.setString(3, company.getCity());

            int row = ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(row>0){
                    int id =rs.getInt(1);
                    company.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Company instanciateCompany(ResultSet rs) throws SQLException {
        Company c =new Company();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setCnpj(rs.getString("cnpj"));
        c.setCity(rs.getString("city"));
        return c;
    }

    @Override
    public Company findByCnpj(String cnpj) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT  * from company where company.cnpj=?")){

        ps.setString(1,cnpj);

        try(ResultSet rs=ps.executeQuery()){
            if(rs.next()){
                Company c =instanciateCompany(rs);
                return  c;
            }
        }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
}



