import java.io.*;
import java.util.*;

// Doctor class (SRP: Only holds doctor details)
class Doctor {
    int id;
    String name;
    String specialization;

    Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return id + ". " + name + " (" + specialization + ")";
    }
}

// Appointment class (SRP: Only holds appointment details)
class Appointment {
    String patientName;
    Doctor doctor;
    double fee;

    Appointment(String patientName, Doctor doctor, double fee) {
        this.patientName = patientName;
        this.doctor = doctor;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Patient: " + patientName + " | Doctor: " + doctor.name + " | Fee: " + fee;
    }
}

// Thread class to simulate appointment confirmation
class AppointmentThread extends Thread {
    Appointment appointment;

    AppointmentThread(Appointment appointment) {
        this.appointment = appointment;
    }

    public void run() {
        try {
            System.out.println("Processing appointment for " + appointment.patientName + "...");
            Thread.sleep(2000); // simulate delay
            System.out.println("Appointment confirmed with Dr. " + appointment.doctor.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// OCP: Define an abstraction for data persistence
interface DataStore {
    void save(String data);
}

// File-based implementation of DataStore (can be extended later for DB, JSON,
// etc.)
class FileDataStore implements DataStore {
    private String filename;

    FileDataStore(String filename) {
        this.filename = filename;
    }

    public void save(String data) {
        try (FileOutputStream fos = new FileOutputStream(filename, true)) {
            fos.write((data + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Billing service (SRP: only handles billing)
class BillingService {
    DataStore dataStore;

    BillingService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    void generateBill(Appointment appointment) {
        System.out.println("\n--- Billing Receipt ---");
        System.out.println("Patient: " + appointment.patientName);
        System.out.println("Doctor: " + appointment.doctor.name);
        System.out.println("Specialization: " + appointment.doctor.specialization);
        System.out.println("Consultation Fee: Rs. " + appointment.fee);
        System.out.println("Total: Rs. " + appointment.fee);

        dataStore.save("BILL - " + appointment.toString());
    }
}

// Appointment Service (SRP: handles appointments only)
class AppointmentService {
    List<Appointment> appointments = new ArrayList<>();
    DataStore dataStore;

    AppointmentService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    void bookAppointment(String patientName, Doctor doctor) {
        double fee = 500; // fixed consultation fee
        Appointment appointment = new Appointment(patientName, doctor, fee);
        appointments.add(appointment);

        // Simulate booking with thread
        new AppointmentThread(appointment).start();

        // Save to file
        dataStore.save(appointment.toString());
    }

    List<Appointment> getAppointments() {
        return appointments;
    }
}

// Main Hospital Management System
public class HospitalManagementSystem {
    static List<Doctor> doctors = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Adding doctors
        doctors.add(new Doctor(1, "Dr. Sita", "Cardiologist"));
        doctors.add(new Doctor(2, "Dr. Gita", "Dermatologist"));
        doctors.add(new Doctor(3, "Dr. Rita", "Pediatrician"));
        doctors.add(new Doctor(4, "Dr. Raj", "Cardiologist"));
        doctors.add(new Doctor(5, "Dr. Raju", "Dermatologist"));
        doctors.add(new Doctor(6, "Dr. Rani", "Pediatrician"));

        AppointmentService appointmentService = new AppointmentService(new FileDataStore("appointments.txt"));
        BillingService billingService = new BillingService(new FileDataStore("billing.txt"));

        int choice;
        do {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Show Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. Display Appointments");
            System.out.println("4. Generate Billing");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showDoctors();
                case 2 -> {
                    System.out.print("Enter Patient Name: ");
                    String patientName = sc.nextLine();
                    showDoctors();
                    System.out.print("Choose Doctor ID: ");
                    int docId = sc.nextInt();
                    sc.nextLine();
                    Doctor selectedDoctor = doctors.stream()
                            .filter(d -> d.id == docId)
                            .findFirst().orElse(null);
                    if (selectedDoctor == null) {
                        System.out.println("Invalid Doctor ID!");
                    } else {
                        appointmentService.bookAppointment(patientName, selectedDoctor);
                    }
                }
                case 3 -> {
                    System.out.println("\n--- Appointments ---");
                    if (appointmentService.getAppointments().isEmpty()) {
                        System.out.println("No appointments booked.");
                    } else {
                        appointmentService.getAppointments().forEach(System.out::println);
                    }
                }
                case 4 -> {
                    System.out.print("Enter Patient Name for billing: ");
                    String patientName = sc.nextLine();
                    Appointment found = appointmentService.getAppointments().stream()
                            .filter(a -> a.patientName.equalsIgnoreCase(patientName))
                            .findFirst().orElse(null);
                    if (found != null) {
                        billingService.generateBill(found);
                    } else {
                        System.out.println("No appointment found for this patient!");
                    }
                }
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void showDoctors() {
        System.out.println("\n--- Available Doctors ---");
        for (Doctor d : doctors) {
            System.out.println(d);
        }
    }
}
