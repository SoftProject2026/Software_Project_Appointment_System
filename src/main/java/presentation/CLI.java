package presentation;

import com.appointmentsystem.service.UserService;
import com.appointmentsystem.domain.models.*;

import java.util.Scanner;
import java.util.UUID;

public class CLI {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    
    public CLI() {
    	initializeAdmin();
    }
    
    private void initializeAdmin() {
        if (!userService.usernameExists("admin")) {
            Admin admin = new Admin("1", "Admin", "admin", "admin@mail.com", "1234");
            userService.createUser(admin);
        }
    }
    
    public void start() {
        while (true) {
            System.out.println("\n=== Welcome ===");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: 
                	loginMenu(); break;
                case 2: 
                	signupMenu(); break;
                case 3: 
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice");
                
            }
        }
    }

    private void loginMenu() {
        System.out.print("Username: ");
        String username = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();

        try {
            User user = userService.authenticate(username, password);
            if (user instanceof Admin)
                adminMenu((Admin) user);
            else if (user instanceof Company)
                companyMenu((Company) user);
        	else
                visitorMenu((Visitor) user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void signupMenu() {
        System.out.println("Signup as:");
        System.out.println("1. Visitor");
        System.out.println("2. Company");

        int choice = scanner.nextInt();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.next();

        System.out.print("Email: ");
        String email = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();
        try {
	        if (choice == 1) {
	        	System.out.print("Phone: ");
	            String phone = scanner.next();
	            String id = UUID.randomUUID().toString();
	            Visitor v = new Visitor(id, name, username, email, password, phone);
	            userService.createUser(v);
	        }
	        else{
	        	System.out.print("Commercial Register: ");
	            String cr = scanner.next();
	            String id = UUID.randomUUID().toString();
	            Company c = new Company(id, name, username, email, password, cr);
	            userService.createUser(c);
	        }
	        System.out.println("Account created!");
	        loginMenu();
	        
        } catch(Exception e){
        	System.out.println(e.getMessage());
        }
    }

    private void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Companies");
            System.out.println("2. Approve Company");
            System.out.println("3. Logout");
            // more to be added
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: 
                	viewCompanies(); break;
                case 2: 
                	approveCompany(); break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");    
                    
            }
        }
    }

    private void viewCompanies() {
        for (User user : userService.getAllUsers()) {
            if (user instanceof Company) {
            	Company c = (Company) user;
            	System.out.println("Name: " + c.getCompanyName() +" | Verified: " + c.isVerified());
            }
        }
    }

    private void approveCompany() {
        System.out.print("Enter company username: ");
        String username = scanner.next();

        User user = userService.getUserByUsername(username);

        if (user instanceof Company) {
            Company c = (Company) user;
            if (c.isVerified()) {
                System.out.println("Company already approved!");
                return;
            }
            c.setVerified(true);
            userService.updateUser(c);
            System.out.println("Company approved!");
        }
        else {
            System.out.println("Not a company!");
        }    
    }

    private void visitorMenu(Visitor v) {
        ///////////////////
    }

    private void companyMenu(Company c) {
        //////////////////////////
    }
}