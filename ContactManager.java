import java.util.*;
import java.io.*;

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt";
    private static Map<String, Contact> contactMap = new HashMap<>();

    public static void main(String[] args) {
        loadContacts();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nContact Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    saveContacts();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (contactMap.containsKey(name)) {
            System.out.println("Contact with this name already exists.");
        } else {
            contactMap.put(name, new Contact(name, phone, email));
            System.out.println("Contact added successfully.");
        }
    }

    private static void viewContacts() {
        if (contactMap.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contactMap.values()) {
                System.out.println(contact);
            }
        }
    }

    private static void editContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to edit: ");
        String name = scanner.nextLine();

        if (contactMap.containsKey(name)) {
            Contact contact = contactMap.get(name);
            System.out.print("Enter new phone number: ");
            contact.setPhone(scanner.nextLine());
            System.out.print("Enter new email: ");
            contact.setEmail(scanner.nextLine());
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();

        if (contactMap.remove(name) != null) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    contactMap.put(details[0], new Contact(details[0], details[1], details[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing contacts found.");
        }
    }

    private static void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contactMap.values()) {
                writer.write(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail());
                writer.newLine();
            }
            System.out.println("Contacts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }
}
