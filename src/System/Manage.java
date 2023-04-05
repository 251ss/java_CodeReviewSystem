package System;

import java.io.Serializable;

public class Manage implements Serializable{
    private String id = null;
    private String password = null;
    private String name = null;
    private int age;
    private String sex;
    public Manage() {
        super();
    }
    public Manage(String id, String password, String name) {
        super();
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public Manage(String id, String password, String name, int age, String sex) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Manage(String id, String password) {
        super();
        this.id = id;
        this.password = password;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Manage other = (Manage) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Manage [id=" + id + ", name=" + name + ", password=" + password
                + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}