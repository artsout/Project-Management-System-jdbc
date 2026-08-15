package Entitys.Dao.iml;

import DB.DbException;
import Entitys.Company;
import Entitys.Dao.ProjectDao;
import Entitys.Enums.Project_Status;
import Entitys.Project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDaoJDBC implements ProjectDao {
    private  final Connection conn;

    public ProjectDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public  List<Project> findByCompany(Company company) {
        List<Project> list = new ArrayList<>();
        Map<Integer,Company> map = new HashMap<>();
       try(PreparedStatement ps = conn.prepareStatement("SELECT project.*,company.id as comp_id,company.name as comp_name From project inner join company on project.id_company=company.id where project.id_company=?")){
            ps.setInt(1,company.getId());
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));
                    if(c==null){
                        c=instanciateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                    Project p =new Project();
                    list.add(p);
                }
                return list;
            }
       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
    }

    @Override
    public List<Project> findByStatus(Project_Status project_status) {
        return List.of();
    }

    @Override
    public List<Project> findByDate(LocalDate min,  LocalDate max) {
        List<Project> list = new ArrayList<>();
        Map<Integer,Company> map=new HashMap<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT project.*,company.id as comp_id,company.name as comp_name From project inner join company on project.id_company=company.id where (project.start BETWEEN ? and ? ) or (project.dead_line BETWEEN ? and ? )")){
            ps.setDate(1, Date.valueOf(min));
            ps.setDate(2, Date.valueOf(max));
            ps.setDate(3, Date.valueOf(min));
            ps.setDate(4, Date.valueOf(max));
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Company c = map.get(rs.getInt("comp_id"));
                    if(c==null){
                        c=instanciateCompany(rs);
                        map.put(rs.getInt("comp_id"),c);
                    }
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Project project) {

    }

    @Override
    public Project deleteByID(Integer id) {
        return null;
    }

    @Override
    public List<Project> findAll() {
        return List.of();
    }

    @Override
    public Project findById(Integer id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT project.*,company.id as comp_id,company.name as comp_name From project inner join company on project.id_company=company.id where project.id=?")){
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Company c = instanciateCompany(rs);
                    Project p = instanciateProject(rs,c);
                    return  p ;
                }else {
                    return  null;
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void save(Project project) {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO project (id_company,name,description,start,delivered_Deadline,status,company) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            if (project.getId_company() != null) {

                    ps.setInt(1, project.getId_company());

            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, project.getName());
            ps.setString(3, project.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(project.getStart()));
            ps.setDate(5, java.sql.Date.valueOf(project.getDelivered_Deadline()));
            ps.setString(7, project.getStatus().name());
            ps.setInt(8, project.getId());

           int row = ps.executeUpdate();

           if(row>0){
               try(ResultSet rs = ps.getGeneratedKeys()){
                   if(rs.next()){
                        int id = rs.getInt(1);
                        project.setId(id);
                   }
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  Project instanciateProject(ResultSet rs,Company c) throws SQLException {
        Project p= new Project();
        p.setId(rs.getInt("id"));
        p.setId_company(rs.getInt("id_company"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));

        if (rs.getDate("start") != null) {
            p.setStart(rs.getDate("start").toLocalDate());
        }
        if (rs.getDate("delivered_deadline") != null) {
            p.setDelivered_Deadline(rs.getDate("delivered_deadline").toLocalDate());
        }

        if (rs.getString("status") != null) {
            p.setStatus(Project_Status.valueOf(rs.getString("status")));
        }


        p.setCompany(c);
        return p;
    }
    public Company instanciateCompany(ResultSet rs) throws SQLException {
        Company c =new Company();
        c.setId(rs.getInt("comp_id"));
        c.setName(rs.getString("comp_name"));
        c.setCnpj(rs.getString("comp_cnpj"));
        c.setCity(rs.getString("comp_city"));
        return  c;
    }

}
