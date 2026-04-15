package presentation;

import com.appointmentsystem.service.*;
import com.appointmentsystem.domain.models.*;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.domain.models.enums.PropertyType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class CLI {
    private Scanner scanner = new Scanner(System.in);
    private PropertyService propertyService = new PropertyService();
    private AppointmentService appointmentService = new AppointmentService();
    
    private VisitorService visitorService = new VisitorService();
    private AdminService adminService = new AdminService();
    private CompanyService companyService = new CompanyService();
    

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
                    System.out.println("\nInvalid choice");
                
            }
        }
    }

    private void loginMenu() {
    	System.out.println("Login as:");
    	System.out.println("1. Admin");
    	System.out.println("2. Company");
    	System.out.println("3. Visitor");

    	int type = scanner.nextInt();

    	System.out.print("Username: ");
    	String username = scanner.next();

    	System.out.print("Password: ");
    	String password = scanner.next();

    	try {
    	    if (type == 1) {
    	        Admin admin = adminService.login(username, password);
    	        adminMenu(admin);
    	    } 
    	    else if (type == 2) {
    	        Company c = companyService.login(username, password);
    	        companyMenu(c);
    	    } 
    	    else {
    	        Visitor v = visitorService.login(username, password);
    	        visitorMenu(v);
    	    }
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
        String name = scanner.next();

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
	            visitorService.signup(v);
	            System.out.println("\nHello " + v.getVisitorName() +". Account created!");
	            visitorMenu(v);
	        }
	        else{
	        	System.out.print("Commercial Register: ");
	            String cr = scanner.next();
	            String id = UUID.randomUUID().toString();
	            Company c = new Company(id, name, username, email, password, cr);
	            companyService.signup(c);
	            System.out.println("\nYour account is pending approval by admin");
	        }	        
        } catch(Exception e){
        	System.out.println(e.getMessage());
        }
    }

    private void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Companies");
            System.out.println("2. Approve Company");
            System.out.println("3. View all appointments");
            System.out.println("4. Logout");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: 
                	System.out.println(companyService.printAllCompanys()); break;
                case 2:
                    System.out.print("Enter company name: ");
                    String Cname = scanner.next();
                    companyService.approveCompany(Cname);
                    break;
                case 3:
                	appointmentService.viewAllAppointments();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid choice");    
                    
            }
        }
    }

    
//////////////////////////////// Client
    private void visitorMenu(Visitor v) {
    	while(true) {
	    	System.out.println("1. View Properties And Book Appointment");
	    	System.out.println("2. My Appointments");
	    	System.out.println("3. Cancel Appointment");
	    	System.out.println("4. Modify Appointment");
	    	System.out.println("5. Logout");
	    	
	    	int choice = scanner.nextInt();
	        switch (choice) {
	        case 1:
	        	visitorService.bookAppointment(v);
	            break;
	        case 2:
	        	visitorService.viewMyAppointments(v);
	            break;
	
	        case 3:
	        	visitorService.cancelAppointment(v);
	            break;
	
	        case 4:
	        	visitorService.modifyAppointment(v);
	            break;
	
	        case 5:
	        	System.out.println("\nLogging out...");
	        	visitorService.logout(v);
	        	return;	
	        default:
                System.out.println("Invalid choice");
	        }
    	}
    }
    
   
      
//////////////////////////////////// Company
    private void companyMenu(Company c) {
        while (true) {
            System.out.println("\n=== Company Menu ===");
            System.out.println("1. Add Property");
            System.out.println("2. View Properties");
            System.out.println("3. Add Time Slot");
            System.out.println("4. View Appointments");
            //System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
	            case 1:
	            	System.out.println("Select property type:");
	            	System.out.println("1. APARTMENT");
	            	System.out.println("2. VILLA");
	            	System.out.println("3. COMMERCIAL");
	            	
	            	int typeNum = scanner.nextInt();
	            	String typeStr = "";
	            	switch (typeNum) {
	            	    case 1:
	            	        typeStr = "APARTMENT";
	            	        break;
	            	    case 2:
	            	        typeStr = "VILLA";
	            	        break;
	            	    case 3:
	            	        typeStr = "COMMERCIAL";
	            	        break;
	            	    default:
	            	        System.out.println("Invalid choice.");
	            	        break;
	            	}
	            	
	                System.out.print("Price in $: ");
	                double price = scanner.nextDouble();
	                
	                System.out.print("Area in m2: ");
	                double area = scanner.nextDouble();
	                
	                System.out.print("Rooms Number: ");
	                int roomsNumber = scanner.nextInt();
	                
	                System.out.print("Address: ");
	                String Address = scanner.next();
	                
	                String id = UUID.randomUUID().toString();
	
	                Property p = new Property(id, c.getId(), PropertyType.valueOf(typeStr), price,area,roomsNumber,Address);
	                propertyService.addProperty(c, p);
	                break;
                case 2:
                	propertyService.viewMyProperties(c);
                    break;
                case 3:
                	System.out.print("Choose property index: ");
                	int index = scanner.nextInt();
                	scanner.nextLine();

                	System.out.print("Enter time: (format: yyyy-MM-dd HH:mm)");
                    String input = scanner.nextLine();

                	companyService.addTimeSlotToProperty(c, index, input);
                    break;
                case 4:
                	appointmentService.viewCompanyAppointments(c);
                    break;
//                case 5:
//                	appointmentService.viewCompanyAppointments(c);
//                	System.out.print("Choose appointment index: ");
//                    int index2 = scanner.nextInt();
//
//                    Appointment selected = appointmentService.getAppointmentById(index2);
//                    
//                    appointmentService.cancelAppointment(selected.getId(), c.getId());
//                    System.out.println("Cancelled!");
//                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    

}