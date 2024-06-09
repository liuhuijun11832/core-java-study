package org.hoey.generic;


import org.apache.commons.lang3.tuple.Pair;

/**
 * employee
 *
 * @author liuhuijun
 * @since 2024/6/9 16:55
 */
public class Employee {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //public static void printBuddies(Pair<? extends Employee, ? extends Employee> pair){
    //    Employee left = pair.getLeft();
    //    Employee right = pair.getRight();
    //    System.out.println(left + " and " + right + " are buddies.");
    //}


}
