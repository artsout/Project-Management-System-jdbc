package Entitys;

import java.util.Objects;

public class Company {
    private Integer id;
    private String name , cnpj ,city;

    public Company() {
    }

    public Company(Integer id, String name, String cnpj, String city) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(cnpj, company.cnpj) && Objects.equals(city, company.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnpj, city);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
