package Entitys.Dao.iml;

import DB.DbException;
import Entitys.Company;
import Entitys.Dao.DeveloperDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import jdk.jshell.Snippet;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeveloperDaoJDBC implements DeveloperDao {
    //SELECT developer.*,company.name,company.id from developer inner join company on company.id=developer.id_company where developer.id= ?"
    private final Connection conn;

    public DeveloperDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Developer> findByCompany(Company company) {
        Map<Integer, Company> map=new HashMap<>();
        List<Developer>list=new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT developer.*,company.id AS comp_id,company.name AS comp_name,company.cnpj AS comp_cnpj,company.city AS comp_city FROM developer INNER JOIN company ON developer.id_company=company.id where developer.id_company=?")){
            ps.setInt(1,company.getId());

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));

                    if(c==null){
                        c=instantiateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                    Developer d = instanciate(rs,c);
                    list.add(d);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Developer> findByStatus(Developer_Status developer_status) {
        List<Developer> list=new ArrayList<>();
        Map<Integer,Company>map=new HashMap<>();
        try(PreparedStatement ps =conn.prepareStatement("SELECT developer.*,company.id AS comp_id,company.name AS comp_name,company.cnpj AS comp_cnpj,company.city AS comp_city FROM developer INNER JOIN company ON company.id = developer.id_company where  developer.status=?")){
            ps.setString(1, developer_status.name());

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                    Developer d = instanciate(rs,c);
                    list.add(d);
                }
                return  list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Developer> findByWork_Area(Developer developer) {
        List<Developer> list =new ArrayList<>();
        Map<Integer,Company> map=new HashMap<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT developer.*,company.id AS comp_id,company.name AS comp_name,company.cnpj AS comp_cnpj,company.city AS comp_city FROM developer INNER JOIN company ON company.id = developer.id_company where developer.work_area =?")){
            ps.setString(1, developer.getWork_Area());
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));
                    if(c==null){
                        c= instantiateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                    Developer d = instanciate(rs,c);
                    list.add(d);

                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Developer findByEmail(String email) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT developer.*,company.id AS comp_id,company.name AS comp_name,company.cnpj AS comp_cnpj,company.city AS comp_city FROM developer INNER JOIN company ON company.id = developer.id_company where developer.email=?")){
            ps.setString(1,email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Company c = instantiateCompany(rs);
                    Developer d = instanciate(rs,c);
                    return d;
                }else{
                    throw new DbException("This email doesn't exist");
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Developer developer) {
        try(PreparedStatement  stmt  = conn.prepareStatement( "UPDATE developer SET id_company = ?, name = ?, email = ?, city = ?, birth_date = ?, work_area = ?, status = ? WHERE id = ?")){
            stmt.setInt(1, developer.getId_company());
            stmt.setString(2, developer.getName());
            stmt.setString(3, developer.getEmail());
            stmt.setString(4, developer.getCity());
            stmt.setDate(5, java.sql.Date.valueOf(developer.getBirthDate()));
            stmt.setString(6, developer.getWork_Area());
            stmt.setString(7, developer.getStatus().name());
            stmt.setInt(8, developer.getId());
            int row= stmt.executeUpdate();

            if(row==0){
                throw new DbException("No row affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Developer deleteByID(Integer id) {
        Developer developerDeletado = findById(id);

        if (developerDeletado == null) {
            throw new DbException("Developer not found for deletion");
        }
        try(PreparedStatement ps = conn.prepareStatement("DELETE  from developer where developer.id=?")){
            ps.setInt(1,id);

            int rs = ps.executeUpdate();
            if(rs>0){
                return developerDeletado;
            }else{
                throw new DbException("No row affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> list =new ArrayList<>();
        Map<Integer,Company> map=new HashMap<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT developer.*,company.id AS comp_id,company.name AS comp_name,company.cnpj AS comp_cnpj,company.city AS comp_city FROM developer INNER JOIN company ON company.id = developer.id_company")){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                    Developer d = instanciate(rs,c);
                    list.add(d);
                }
            }
            return list;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Developer findById(Integer id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT developer.*, " +
                "company.id AS comp_id, " +
                "company.name AS comp_name, " +
                "company.cnpj AS comp_cnpj, " +
                "company.city AS comp_city " +
                "FROM developer " +
                "INNER JOIN company ON company.id = developer.id_company " +
                "WHERE developer.id = ?")) {
            ps.setInt(1,id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Company c = instantiateCompany(rs);
                    Developer d = instanciate(rs,c);
                    return d;
                }else{
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void save(Developer developer) {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO developer (id_company, name, email, city, birth_date, work_area, status) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){

            if (developer.getId_company() != null) {
                ps.setInt(1, developer.getId_company());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }


            ps.setString(2, developer.getName());
            ps.setString(3, developer.getEmail());
            ps.setString(4, developer.getCity());
            ps.setDate(5, java.sql.Date.valueOf(developer.getBirthDate()));
            ps.setString(6, developer.getWork_Area());
            ps.setString(7, developer.getStatus().name());
            ps.setInt(8, developer.getId());

            int row=ps.executeUpdate();
            if(row>0){
                try(ResultSet rs =ps.getGeneratedKeys()){
                    if(rs.next()){
                        int id=rs.getInt(1);
                        developer.setId(id);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    public Developer instanciate(ResultSet rs, Company c) throws SQLException {
        Developer d = new Developer();
        d.setId(rs.getInt("id"));
        d.setId_company(rs.getInt("id_company"));
        d.setName(rs.getString("name"));
        d.setEmail(rs.getString("email"));
        d.setCity(rs.getString("city"));
        d.setBirthDate(rs.getDate("birth_date").toLocalDate());
        d.setWork_Area(rs.getString("work_area"));
        d.setStatus(Developer_Status.valueOf(rs.getString("status")));
        d.setCompany(c);
        return  d;
    }
    public Company instantiateCompany(ResultSet rs) throws SQLException {
        Company c =new Company();
        c.setId(rs.getInt("comp_id"));
        c.setName(rs.getString("comp_name"));
        c.setCnpj(rs.getString("comp_cnpj"));
        c.setCity(rs.getString("comp_city"));
        return  c;
    }
}
