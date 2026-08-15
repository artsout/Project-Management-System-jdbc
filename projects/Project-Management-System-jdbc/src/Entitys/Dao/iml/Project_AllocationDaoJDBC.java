package Entitys.Dao.iml;

import DB.DbException;
import Entitys.Company;
import Entitys.Dao.Project_AllocationDao;
import Entitys.Developer;
import Entitys.Enums.Developer_Status;
import Entitys.Enums.Project_Status;
import Entitys.Project;
import Entitys.Project_Allocation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project_AllocationDaoJDBC implements Project_AllocationDao {

    private final Connection conn;

    public Project_AllocationDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Project_Allocation> findByProject(Project project) {
        List<Project_Allocation>list = new ArrayList<>();
        Map<Integer,Company>map1=new HashMap<>();
        Map<Integer,Project>map2=new HashMap<>();
        Map<Integer,Developer>map3=new HashMap<>();
       try(PreparedStatement ps = conn.prepareStatement("SELECT project_allocation.*,developer.id as dev_id ,project.id as pro_id,project.name as pro_name,company.id as com_id FROM project_allocation inner join project on project_allocation.id_project=project.id inner join company on project.id_company=company.id where project.id=?")){
            ps.setInt(1,project.getId());

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Company c = map1.get(rs.getInt("com_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map1.put(rs.getInt("com_id"),c);
                    }
                    Project p = map2.get(rs.getInt("pro_id"));
                    if(p==null){
                        p=instanciateProject(rs,c);
                        map2.put(rs.getInt("pro_id"),p);
                    }
                    Developer d = map3.get(rs.getInt("dev_id"));
                    if(d==null){
                        d=instanciateDeveloper(rs,c);
                        map3.put(rs.getInt("dev_id"),d);
                    }
                    Project_Allocation pa = instanciateProjectAllocation(rs,p,d);
                    list.add(pa);

                }
                return list;
            }
       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
    }

    @Override
    public List<Project_Allocation> findByDeveloper(Developer developer) {
        List<Project_Allocation>list = new ArrayList<>();
        Map<Integer,Company>map1=new HashMap<>();
        Map<Integer,Project>map2=new HashMap<>();
        Map<Integer,Developer>map3=new HashMap<>();
        String sql = "SELECT project_allocation.*, " +
                "developer.id as dev_id, developer.name as dev_name, " +
                "project.id as pro_id, project.name as pro_name, " +
                "company.id as com_id, company.name as comp_name " +
                "FROM project_allocation " +
                "INNER JOIN project ON project_allocation.id_project = project.id " +
                "INNER JOIN developer ON project_allocation.id_developer = developer.id " + // ADICIONADO
                "INNER JOIN company ON developer.id_company = company.id " +
                "WHERE developer.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,developer.getId());

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Company c = map1.get(rs.getInt("com_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map1.put(rs.getInt("com_id"),c);
                    }
                    Project p = map2.get(rs.getInt("pro_id"));
                    if(p==null){
                        p=instanciateProject(rs,c);
                        map2.put(rs.getInt("pro_id"),p);
                    }
                    Developer d = map3.get(rs.getInt("dev_id"));
                    if(d==null){
                        d=instanciateDeveloper(rs,c);
                        map3.put(rs.getInt("dev_id"),d);
                    }
                    Project_Allocation pa = instanciateProjectAllocation(rs,p,d);
                    list.add(pa);

                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Project_Allocation> findByHour(LocalDate min,LocalDate max) {
        List<Project_Allocation> list = new ArrayList<>();
        Map<Integer,Company>map1=new HashMap<>();
        Map<Integer,Project>map2=new HashMap<>();
        Map<Integer,Developer>map3=new HashMap<>();

        String sql = "SELECT project_allocation.*, " +
                "developer.id as dev_id, developer.name as dev_name, " +
                "project.id as pro_id, project.name as pro_name, " +
                "company.id as com_id, company.name as comp_name " +
                "FROM project_allocation " +
                "INNER JOIN project ON project_allocation.id_project = project.id " +
                "INNER JOIN developer ON project_allocation.id_developer = developer.id " +
                "INNER JOIN company ON developer.id_company = company.id " +
                "WHERE project_allocation.hours_allocated BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(min));
            ps.setDate(2, Date.valueOf(max));

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Company c = map1.get(rs.getInt("com_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map1.put(rs.getInt("com_id"),c);
                    }
                    Project p = map2.get(rs.getInt("pro_id"));
                    if(p==null){
                        p=instanciateProject(rs,c);
                        map2.put(rs.getInt("pro_id"),p);
                    }
                    Developer d = map3.get(rs.getInt("dev_id"));
                    if(d==null){
                        d=instanciateDeveloper(rs,c);
                        map3.put(rs.getInt("dev_id"),d);
                    }
                    Project_Allocation pa = instanciateProjectAllocation(rs,p,d);
                    list.add(pa);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Project_Allocation projectAllocation) {

    }

    @Override
    public Project_Allocation deleteByID(Integer id) {
        return null;
    }

    @Override
    public List<Project_Allocation> findAll(){
        List<Project_Allocation>list = new ArrayList<>();
        Map<Integer,Company>map1=new HashMap<>();
        Map<Integer,Project>map2=new HashMap<>();
        Map<Integer,Developer>map3=new HashMap<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT project_allocation.*,developer.id as dev_id,developer.name as dev_name,project.id as pro_id,project.name as pro_name,company.id as com_id FROM project_allocation inner join developer on project_allocation.id_developer=developer.id inner join project on project_allocation.id_project=project.id inner join company on company.id=project.id_company")){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Company c = map1.get(rs.getInt("com_id"));
                    if(c==null){
                        c=instantiateCompany(rs);
                        map1.put(rs.getInt("com_id"),c);
                    }
                    Project p = map2.get(rs.getInt("pro_id"));
                    if(p==null){
                        p=instanciateProject(rs,c);
                        map2.put(rs.getInt("pro_id"),p);
                    }
                    Developer d = map3.get(rs.getInt("dev_id"));
                    if(d==null){
                        d=instanciateDeveloper(rs,c);
                        map3.put(rs.getInt("dev_id"),d);
                    }
                    Project_Allocation pa = instanciateProjectAllocation(rs,p,d);
                    list.add(pa);

                }
                return list;
            }
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Project_Allocation findById(Integer id) {
        String sql = "SELECT * FROM project_allocation WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Project_Allocation pa = new Project_Allocation();
                    pa.setId_project(rs.getInt("id_project"));
                    pa.setId_developer(rs.getInt("id_developer"));

                    if (rs.getDate("hours_allocated") != null) {
                        pa.setHours_allocated(rs.getDate("hours_allocated").toLocalDate());
                    }
                    return pa;
                }else{
                    return  null;
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Project_Allocation projectAllocation) {

    }

    public Project_Allocation instanciateProjectAllocation(ResultSet rs, Project p, Developer d) throws SQLException {
        Project_Allocation pa = new Project_Allocation();

        pa.setId_project(rs.getInt("id_project"));
        pa.setId_developer(rs.getInt("id_developer"));

        // Convertendo a data de alocação de forma segura contra valores nulos
        if (rs.getDate("hours_allocated") != null) {
            pa.setHours_allocated(rs.getDate("hours_allocated").toLocalDate());
        }

        // Faz o vínculo das duas entidades associadas
        pa.setProject(p);
        pa.setDeveloper(d);

        return pa;
    }

    public Project instanciateProject(ResultSet rs, Company c) throws SQLException {
            Project p= new Project();
            p.setId(rs.getInt("pro_id"));
            p.setId_company(rs.getInt("id_company"));
            p.setName(rs.getString("pro_name"));
            p.setDescription(rs.getString("pro_description"));

            if (rs.getDate("pro_start") != null) {
                p.setStart(rs.getDate("pro_start").toLocalDate());
            }
            if (rs.getDate("delivered_deadline") != null) {
                p.setDelivered_Deadline(rs.getDate("pro_delivered_deadline").toLocalDate());
            }

            if (rs.getString("pro_status") != null) {
                p.setStatus(Project_Status.valueOf(rs.getString("pro_status")));
            }
            p.setCompany(c);
            return p;
    }
    public Developer instanciateDeveloper(ResultSet rs, Company c) throws SQLException {
        Developer d = new Developer();
        d.setId(rs.getInt("dev_id"));
        d.setId_company(rs.getInt("id_company"));
        d.setName(rs.getString("dev_name"));
        d.setEmail(rs.getString("dev_email"));
        d.setCity(rs.getString("dev_city"));
        d.setBirthDate(rs.getDate("dev_birth_date").toLocalDate());
        d.setWork_Area(rs.getString("dev_work_area"));
        d.setStatus(Developer_Status.valueOf(rs.getString("dev_status")));
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
