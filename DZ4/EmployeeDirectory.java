import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class Employee {
    private int employeeId;
    private String phoneNumber;

    @Override
    public String toString() {
        return "Employee{" + "employeeId=" + employeeId + ", phoneNumber='" + phoneNumber + '\'' + ", name='" + name + '\'' + ", experience=" + experience + '}';
    }

    private String name;
    private int experience;

    public Employee(int employeeId, String phoneNumber, String name, int experience) {
        this.employeeId = employeeId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.experience = experience;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }
}

public class EmployeeDirectory {
    private List<Employee> employees;

    public EmployeeDirectory() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> findEmployeesByExperience(int experience) {
        return employees.stream().filter(emp -> emp.getExperience() == experience).collect(Collectors.toList());
    }


    public List<String> getPhoneNumbersByName(String name) {
        return employees.stream().filter(emp -> emp.getName().equalsIgnoreCase(name)).map(Employee::getPhoneNumber).collect(Collectors.toList());
    }


    public Employee findEmployeeById(int employeeId) {
        return employees.stream().filter(emp -> emp.getEmployeeId() == employeeId).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        EmployeeDirectory directory = new EmployeeDirectory();


        directory.addEmployee(new Employee(1, "123-456-7890", "Ivan", 3));
        directory.addEmployee(new Employee(2, "412-222-3333", "Petty", 2));
        directory.addEmployee(new Employee(3, "888-888-8888", "Misha", 2));
        directory.addEmployee(new Employee(4, "111-666-8901", "Olia", 1));


        List<Employee> experiencedEmployees = directory.findEmployeesByExperience(2);
        System.out.println("Found " + experiencedEmployees.size() + " employees:");
        experiencedEmployees.stream().forEach(System.out::println);
        List<String> phoneNumbers = directory.getPhoneNumbersByName("Olia");
        System.out.println("Phone numbers is: " + phoneNumbers);
        Employee employee = directory.findEmployeeById(3);
        System.out.println("Employee with ID 2: " + (employee != null ? employee.getName() : "The employee was not found"));
    }
}
