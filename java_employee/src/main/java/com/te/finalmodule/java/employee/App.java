package com.te.finalmodule.java.employee;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.te.finalmodule.java.employee.bean.Employee;
import com.te.finalmodule.java.employee.bean.EmployeeLeaveInfo;

public class App {
	static Scanner sc = new Scanner(System.in);

	static void registerEmployee() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence_employee");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		System.out.println("Please enter the employee details :\n");
		System.out.println("Employee Name :");
		String name = sc.next();
		System.out.println("Choose-Type of employee\n -> 1 Employee\n -> 2 Manager");
		int typeOption = sc.nextInt();
		String type = "";
		switch (typeOption) {
		case 1: {
			type = "Employee";
			break;
		}
		case 2: {
			type = "Manager";
			break;
		}
		default: {
			System.out.println("Please enter the proper employee type");
			break;
		}
		}
		System.out.println("Please enter the Employee Email Id:");
		String email = sc.next();
		System.out.println("Please Enter the PassWord As you wish");
		String password = sc.next();
		Employee employee = new Employee();
		employee.setEmployeeName(name);
		employee.setEmployeeType(type);
		employee.setPassword(password);
		employee.setEmail(email);
		transaction.begin();
		entityManager.persist(employee);
		transaction.commit();
		System.out.println("Successfully created the employee :");

	}

	static void login() {
		int loginId;
		String loginPassWord;
		String employeeLoginType;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence_employee");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		System.out.println("Please Enter the Id:");
		loginId = sc.nextInt();
		sc.nextLine();
		System.out.println("Please Enter the PassWord:");
		loginPassWord = sc.nextLine();
		String stringQuery = "from Employee_Info where employeeId=:loginId";
		Query queryEmployeeType = entityManager.createQuery(stringQuery);
		queryEmployeeType.setParameter("loginId", loginId);
		Object employeeLoginList = queryEmployeeType.getSingleResult();
		Employee employee = (Employee) employeeLoginList;
		employeeLoginType = employee.getEmployeeType();
		String typeManager = "Manager";
		String typeEmployee = "Employee";
		if (employeeLoginType.contains(typeManager) && employee.getPassword().equals(loginPassWord)) {
			int managerLoginOption = -1;
			do {
				System.out.println("Please Enter the Below Options to Continue:");
				System.out.println("1:=>Show All Leave Requests:");
				System.out.println("2:=> Approve /Reject Leave Request :");
				managerLoginOption = sc.nextInt();
				switch (managerLoginOption) {
				case 1: {
					String stringQuery2 = "from EmployeeLeaveInfo";
					Query queryEmployeeLeave = entityManager.createQuery(stringQuery2);
					List employeeLeaveList = queryEmployeeLeave.getResultList();
					for (Object object2 : employeeLeaveList) {
						entityManager.refresh(object2);
					//	System.out.println(object2);
					}
					for (Object object2 : employeeLeaveList) {
						System.out.println(object2);
					}
					break;
				}
				case 2: {
					String stringQuery2 = "from EmployeeLeaveInfo";
					Query queryEmployeeLeave = entityManager.createQuery(stringQuery2);
					List employeeLeaveList = queryEmployeeLeave.getResultList();
					for (Object object2 : employeeLeaveList) {
						System.out.println(object2);
					}
					System.out.println("Please enter the below options to continue :");
					System.out.println("Please Enter the employee Id ");
					int employeeUpdateId = sc.nextInt();
					String stringQuery3 = "from EmployeeLeaveInfo where employeeId=:employeeUpdateId";
					Query queryLeaveUpdate = entityManager.createQuery(stringQuery3);
					queryLeaveUpdate.setParameter("employeeUpdateId", employeeUpdateId);
					List employeeLeaveUpdateList = queryLeaveUpdate.getResultList();
					for (Object employeeObjectLeave : employeeLeaveUpdateList) {
						EmployeeLeaveInfo employeeLeaveupdates = (EmployeeLeaveInfo) employeeObjectLeave;
						System.out.println(employeeLeaveupdates);	
					}
					System.out.println("Please enter the Sno: To Aprove or Reject");
					int leaveAproveRejectOption=sc.nextInt();
					String stringSno="from EmployeeLeaveInfo where sNO=:sNO";
					Query querySno=entityManager.createQuery(stringSno);
					querySno.setParameter("sNO", leaveAproveRejectOption);
					Object employeeLeaveChange = querySno.getSingleResult();
					EmployeeLeaveInfo employeeLeaveupdates=(EmployeeLeaveInfo) employeeLeaveChange;
					int employeeLeaveStatusId = employeeLeaveupdates.getEmployeeID();
					System.out.println("please select either one options from below :");
					System.out.println("1:=>for Approved");
					System.out.println("2:=> for Reject");
					int updateStatus = sc.nextInt();
					if (updateStatus == 1) {
						String Approved = "Approved";
						String updateStatusString = "update EmployeeLeaveInfo set leaveStatus=:Approved where sNO=:sNO";
						Query queryUpdateStatus = entityManager.createQuery(updateStatusString);
						queryUpdateStatus.setParameter("sNO",leaveAproveRejectOption);
						queryUpdateStatus.setParameter("Approved", Approved);
						transaction.begin();
						queryUpdateStatus.executeUpdate();
						transaction.commit();
						System.out.println("SuccessFully Updated Thanks !!!!");
					} else if (updateStatus == 2) {
						String Rejected = "Rejected";
						String updateStatusString = "update EmployeeLeaveInfo set leaveStatus=:Approved where sNO=:sNO";
						Query queryUpdateStatus = entityManager.createQuery(updateStatusString);
						queryUpdateStatus.setParameter("sNO",leaveAproveRejectOption);
						queryUpdateStatus.setParameter("Approved", Rejected);
						transaction.begin();
						queryUpdateStatus.executeUpdate();
						transaction.commit();
						System.out.println("SuccessFully Updated Thanks !!!!");
					} else {
						System.out.println("Please enter the proper options !!!!!");
					}
					break;
				}
				default:
					System.out.println("please enter the proper options please :");
					managerLoginOption = 0;
				}
			} while (managerLoginOption != 0);
		} else if (employeeLoginType.contains(typeEmployee) && employee.getPassword().equals(loginPassWord)) {
			int employeeLoginOption = -1;
			do {
				int employeeLoggedId = employee.getEmployeeID();
				System.out.println("please enter the below options to continue :");
				System.out.println("1:=> Show all leave status :");
				System.out.println("2:=> request for a leave:");
				employeeLoginOption = sc.nextInt();
				switch (employeeLoginOption) {
				case 1: {
					String stringEmployeeQuery = "from EmployeeLeaveInfo where employeeId=:employeeLoggedId";
					Query queryEmployee = entityManager.createQuery(stringEmployeeQuery);
					queryEmployee.setParameter("employeeLoggedId", employeeLoggedId);
					List employeeLeaves = queryEmployee.getResultList();
					for (Object employeeLoggedObject : employeeLeaves) {
						System.out.println(employeeLoggedObject);
					}
					break;
				}
				case 2: {
					System.out.println("please enter the below details to continue :");
					System.out.println("please enter the leave date in dd/mm/yyyy fromat");
					EmployeeLeaveInfo leaveInfo = new EmployeeLeaveInfo();
					String employeeLeaveDate = sc.next();

					leaveInfo.setLeaveDate(employeeLeaveDate);
					leaveInfo.setEmployeeID(employeeLoggedId);
					leaveInfo.setLeaveStatus("Pending");
					transaction.begin();
					entityManager.persist(leaveInfo);
					transaction.commit();
					System.out.println("Leave applied successfully");

					break;
				}
				default: {
					System.out.println("please enter the proper options");
					employeeLoginOption=0;
				}
				}
			} while (employeeLoginOption != 0);
		} else {
			System.out.println("Sorry credentials doesnt match try again !!!!!");
		}

	}

	public static void main(String[] args) {
		int choice = -1;
		do {

			System.out.println("Please enter the below options to Comtinue:");
			System.out.println("1: => To register Employee");
			System.out.println("2: => To Login");

			choice = sc.nextInt();
			switch (choice) {
			case 1: {
				registerEmployee();
				break;
			}
			case 2: {
				login();
				break;
			}
			default: {
				choice = 0;
			}
			}
		} while (choice != 0);
	}
}
