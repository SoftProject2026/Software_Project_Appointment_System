package presentation;

import com.appointmentsystem.service.*;
import com.appointmentsystem.domain.models.*;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.domain.models.enums.PropertyType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CLI {
    private Scanner scanner = new Scanner(System.in);
    private PropertyService propertyService = new PropertyService();
    private AppointmentService appointmentService = new AppointmentService();
    private VisitorService visitorService = new VisitorService();
    private AdminService adminService = new AdminService();
    private CompanyService companyService = new CompanyService();
    
    
    
    public CLI() {
    	initializeAdmin();
    }
    
    private void initializeAdmin() {
    	// ?
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
	        }
	        else{
	        	System.out.print("Commercial Register: ");
	            String cr = scanner.next();
	            String id = UUID.randomUUID().toString();
	            Company c = new Company(id, name, username, email, password, cr);
	            companyService.signup(c);
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
    
//////////////////////////////// Client
    private void visitorMenu(Visitor v) {
    	while(true) {
	    	System.out.println("1. View Properties");
	    	System.out.println("2. Book Appointment");
	    	System.out.println("3. My Appointments");
	    	System.out.println("4. Cancel Appointment");
	    	System.out.println("5. Modify Appointment");
	    	System.out.println("6. Logout");
	    	
	    	int choice = scanner.nextInt();
	
	        switch (choice) {
	//        case 1:
	//        	viewProperties(v);
	//            break;
	        case 2:
	            bookAppointment(v);
	            break;
	
	        case 3:
	            viewMyAppointments(v);
	            break;
	
	        case 4:
	            cancelAppointment(v);
	            break;
	
	        case 5:
	            modifyAppointment(v);
	            break;
	
	        case 6:
	            return;
	        }
    	}
    }
    
    private  List<Appointment> viewMyAppointments(Visitor visitor) {
        List<Appointment> apps = appointmentService.getAppointmentsByVisitor(visitor.getId());
        if (apps.isEmpty()) {
            System.out.println("No appointments");
            return apps;
        }
        for (int i = 0; i < apps.size(); i++) {
            System.out.println(i + ". " + apps.get(i));
        }

        return apps;
    }
    
    
    private void bookAppointment(Visitor visitor) {
        List<Property> properties = propertyService.getAllProperties();
        if (properties.isEmpty()) {
            System.out.println("No properties available");
            return;
        }
        System.out.println("Available Properties:");
        for (int i = 0; i < properties.size(); i++) {
            System.out.println(i + ". " + properties.get(i));
        }

        System.out.print("Choose property index: ");
        int pIndex = scanner.nextInt();

        Property selectedProperty = properties.get(pIndex);

        List<TimeSlot> slots = selectedProperty.getAvailableSlots();

        if (slots.isEmpty()) {
            System.out.println("No available slots");
            return;
        }
        System.out.println("Available Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println(i + ". " + slots.get(i));
        }

        System.out.print("Choose slot index: ");
        int sIndex = scanner.nextInt();

        TimeSlot selectedSlot = slots.get(sIndex);

        AppointmentType type = AppointmentType.IN_PERSON; //needs work

        Appointment appointment = appointmentService.bookAppointment(
                selectedProperty.getId(),
                visitor.getId(),
                selectedSlot,
                type
        );
        System.out.println("Appointment booked successfully!");
        System.out.println(appointment);
    }
    
    private void cancelAppointment(Visitor visitor) {
    	List<Appointment> apps = viewMyAppointments(visitor);
    	if (apps.isEmpty()) {
    	    System.out.println("Nothing to do");
    	    return;
    	}
    	
        System.out.print("Choose appointment index to cancel: ");
        int index = scanner.nextInt();

        Appointment selected = apps.get(index);

        try {
            appointmentService.cancelAppointment(selected.getId());
            System.out.println("Cancelled successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void modifyAppointment(Visitor visitor) {
    	List<Appointment> apps = viewMyAppointments(visitor);
    	if (apps.isEmpty()) {
    	    System.out.println("Nothing to do");
    	    return;
    	}

        System.out.print("Choose appointment index: ");
        int index = scanner.nextInt();

        Appointment selected = apps.get(index);

        Property property = propertyService.getPropertyById(selected.getPropertyId());

        List<TimeSlot> slots = property.getAvailableSlots();

        if (slots.isEmpty()) {
            System.out.println("No available slots");
            return;
        }

        System.out.println("Available Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println(i + ". " + slots.get(i));
        }

        System.out.print("Choose new slot: ");
        int sIndex = scanner.nextInt();

        TimeSlot newSlot = slots.get(sIndex);

        try {
            appointmentService.modifyAppointment(selected.getId(), newSlot);
            System.out.println("Modified successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println("5. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProperty(c);
                    break;
                case 2:
                    viewCompanyProperties(c);
                    break;
                case 3:
                    addTimeSlotToProperty(c);
                    break;
                case 4:
                    viewCompanyAppointments(c);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    
    
    private void addProperty(Company c) {
        System.out.print("Type (1=APARTMENT, 2=VILLA): ");
        int typeChoice = scanner.nextInt();

        PropertyType type = (typeChoice == 1) 
            ? PropertyType.APARTMENT 
            : PropertyType.VILLA;

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        String id = UUID.randomUUID().toString();

        Property p = new Property(id, c.getId(), type, price);

        propertyService.createProperty(p);

        System.out.println("Property added!");
    }
    
    private List<Property> viewCompanyProperties(Company c) {

        List<Property> properties = propertyService.getPropertiesByCompany(c.getId());

        if (properties.isEmpty()) {
            System.out.println("No properties");
            return properties;
        }

        for (int i = 0; i < properties.size(); i++) {
            System.out.println(i + ". " + properties.get(i));
        }

        return properties;
    }
    
    private void addTimeSlotToProperty(Company c) {
        List<Property> properties = viewCompanyProperties(c);
        if (properties.isEmpty()) return;

        System.out.print("Choose property index: ");
        int index = scanner.nextInt();

        Property selected = properties.get(index);

        System.out.print("Start hour (e.g. 14): ");
        int hour = scanner.nextInt();

        LocalDateTime start = LocalDateTime.now()
                .plusDays(1)
                .withHour(hour)
                .withMinute(0);

        TimeSlot slot = TimeSlot.createSlotWithDuration(start, 30);

        selected.addTimeSlot(slot);

        System.out.println("Time slot added!");
    }
    
    private void viewCompanyAppointments(Company c) {
        List<Appointment> all = appointmentService.getAllAppointments();

        boolean found = false;

        for (Appointment a : all) {
            Property p = propertyService.getPropertyById(a.getPropertyId());

            if (p != null && p.getCompanyId().equals(c.getId())) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments");
        }
    }
    
}