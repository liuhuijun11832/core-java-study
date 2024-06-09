package org.hoey.generic;


import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 泛型测试
 *
 * @author liuhuijun
 * @since 2024/6/9 16:58
 */
public class GenericTest {

    public static void main(String[] args) {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Joy");

        Manager manager = new Manager();
        manager.setId(2L);
        manager.setName("Tom");
        Pair<? extends Employee, ? extends Employee> buddies = Pair.of(employee, manager);
        //Employee.printBuddies(buddies);

        List<? extends Employee> extendList = List.of(employee, manager);
        for (Employee employee1 : extendList) {
            System.out.println(employee1);
        }

        List<? super Employee> superList = new ArrayList<>();
       superList.add(employee);

        //Function<? extends Employee, ? extends Long> function = new Function<Employee, Long>() {
        //    @Override
        //    public Long apply(Employee employee) {
        //        return employee.getId();
        //    }
        //
        //};

        // 编译失败: 入参T为Employee子类，但是不知道具体类型，类型不安全，所以无法作为方法参数写入
        //Long apply = function.apply(employee);
        //Map<Long, String> longStringMap = extendList.stream().collect(Collectors.toMap(function, Employee::getName));
        Function<? super Employee, ? extends Long> function = new Function<Employee, Long>() {
            @Override
            public Long apply(Employee employee) {
                return employee.getId();
            }

        };
        // function的T允许为Employee和它父类，所以传入employee一定是类型安全的
        Long apply = function.apply(employee);
        Map<Long, String> longStringMap = extendList.stream().collect(Collectors.toMap(function, Employee::getName));


    }

}
