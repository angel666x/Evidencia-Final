import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    static List<Medico> listaMedico = new ArrayList<>();
    static List<Paciente> listaPaciente = new ArrayList<>();
    static List<Cita> listaCitas = new ArrayList<>();

    public static void main(String[] args) {
        cargarMedicosCSV();
        cargarPacientesCSV();
        cargarCitasCSV();

        Scanner entrada = new Scanner(System.in);
        String usuario;
        String contrasena;
        System.out.println("Bienvenido al sistema de alta de citas");
        System.out.println("-----------Iniciar sesión-----------");
        System.out.println("Usuario:");
        usuario = entrada.nextLine();
        System.out.println("Contraseña:");
        contrasena = entrada.nextLine();
            if(validarUsuario(usuario, contrasena)) {
                System.out.println("Bienvenido: " + usuario);
                while (true) {
                    menu();
                    operaciones();
                }
            }else{
                    System.out.println("Credenciales incorrectas");
            }
    }

    static boolean validarUsuario(String usuario, String contrasena) {
        String usuarioLocal = "Admin";
        String contrasenaLocal = "123456";
        if(usuarioLocal.equals(usuario) && contrasenaLocal.equals(contrasena)) {
            return true;
        }

        return false;
    }

    static void menu(){
        System.out.println("1. Dar de alta a medico");
        System.out.println("2. Dar de alta a paciente");
        System.out.println("3. Crear cita");
        System.out.println("4. Listar medicos");
        System.out.println("5. Listar pacientes");
        System.out.println("6. Listar citas");
    }

    static void operaciones() {
        Scanner entrada = new Scanner(System.in);
        Integer opcion;
        System.out.println("Ingrese una opcion: ");
        opcion = entrada.nextInt();

        switch(opcion) {
            case 1:
                altaMedico();
                break;
            case 2:
                altaPaciente();
                break;
            case 3:
                crearCita();
                break;
            case 4:
                listarMedico();
                break;
            case 5:
                listarPacientes();
                break;
            case 6:
                listarCitas();
                break;
            default:
                System.out.println("Opción no válida");
        }

    }

    static void altaMedico() {
        System.out.println("----Alta de médico----");
        Medico medico = new Medico();
        Scanner entrada = new Scanner(System.in);
        System.out.println("Nombre: ");
        medico.nombre = entrada.nextLine();
        System.out.println("Especialidad: ");
        medico.especialidad = entrada.nextLine();
        listaMedico.add(medico);

    }
    static void listarMedico() {
        System.out.println("---- Lista de médicos ----");
        for (Medico medico : listaMedico) {
            System.out.println("Nombre: "+medico.nombre);
            System.out.println("Especialidad: "+medico.especialidad);
        }
    }
    static void altaPaciente() {
        System.out.println("---- Alta de paciente ----");
        Paciente paciente = new Paciente();
        Scanner entrada = new Scanner(System.in);
        System.out.println("ID del paciente: ");
        paciente.idPaciente = entrada.nextLine();
        System.out.println("Nombre del paciente: ");
        paciente.nombre = entrada.nextLine();
        listaPaciente.add(paciente);
        guardarPacientesCSV();
        System.out.println("Paciente agregado correctamente.");
    }

    static void listarPacientes() {
        System.out.println("---- Lista de pacientes ----");
        for (Paciente paciente : listaPaciente) {
            System.out.println("ID: " + paciente.idPaciente);
            System.out.println("Nombre: " + paciente.nombre);
        }
    }
    static void crearCita() {
        System.out.println("---- Crear nueva cita ----");
        Scanner entrada = new Scanner(System.in);
        Cita cita = new Cita();

        System.out.println("ID de la cita:");
        cita.idCita = entrada.nextLine();

        System.out.println("Fecha y hora de la cita (formato: dd/mm/yyyy hh:mm):");
        cita.fechaHora = entrada.nextLine();

        System.out.println("Motivo de la cita:");
        cita.motivo = entrada.nextLine();

        System.out.println("Seleccione el número del médico:");
        for (int i = 0; i < listaMedico.size(); i++) {
            System.out.println(i + ". " + listaMedico.get(i).nombre + " - " + listaMedico.get(i).especialidad);
        }
        int indiceMedico = entrada.nextInt();
        entrada.nextLine();
        cita.medico = listaMedico.get(indiceMedico);

        System.out.println("Seleccione el número del paciente:");
        for (int i = 0; i < listaPaciente.size(); i++) {
            System.out.println(i + ". " + listaPaciente.get(i).nombre + " (ID: " + listaPaciente.get(i).idPaciente + ")");
        }
        int indicePaciente = entrada.nextInt();
        entrada.nextLine();
        cita.paciente = listaPaciente.get(indicePaciente);

        listaCitas.add(cita);
        guardarCitasCSV();
        System.out.println("Cita creada y guardada correctamente.");
    }
    static void listarCitas() {
        for (Cita cita : listaCitas) {
            System.out.println("ID: " + cita.idCita);
            System.out.println("Fecha/Hora: " + cita.fechaHora);
            System.out.println("Motivo: " + cita.motivo);
            System.out.println("Doctor: " + cita.medico.nombre);
            System.out.println("Paciente: " + cita.paciente.nombre);
            System.out.println("-------------------------------");
        }
    }
    static void guardarMedicosCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("medicos.csv"))) {
            pw.println("idMedico,nombre,especialidad");
            for (Medico medico : listaMedico) {
                pw.println(medico.idMedico + "," + medico.nombre + "," + medico.especialidad);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar médicos: " + e.getMessage());
        }
    }
    static void cargarMedicosCSV() {
        File archivo = new File("medicos.csv");
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo medicos.csv, se iniciará lista vacía");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            listaMedico.clear();
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    Medico medico = new Medico();
                    medico.idMedico = partes[0];
                    medico.nombre = partes[1];
                    medico.especialidad = partes[2];
                    listaMedico.add(medico);
                }
            }
            System.out.println("Médicos cargados correctamente desde medicos.csv");
        } catch (IOException e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }

    static void guardarPacientesCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("pacientes.csv"))) {
            pw.println("idPaciente,nombre");
            for (Paciente paciente : listaPaciente) {
                pw.println(paciente.idPaciente + "," + paciente.nombre);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }
    static void cargarPacientesCSV() {
        File archivo = new File("pacientes.csv");
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo pacientes.csv, se iniciará lista vacía");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            listaPaciente.clear();
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    Paciente paciente = new Paciente();
                    paciente.idPaciente = partes[0];
                    paciente.nombre = partes[1];
                    listaPaciente.add(paciente);
                }
            }
            System.out.println("Pacientes cargados correctamente desde pacientes.csv");
        } catch (IOException e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
    }
    static void guardarCitasCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("citas.csv"))) {
            pw.println("idCita,fechaHora,motivo,idMedico,idPaciente");
            for (Cita cita : listaCitas) {
                pw.println(cita.idCita + "," + cita.fechaHora + "," + cita.motivo + ","
                        + cita.medico.idMedico + "," + cita.paciente.idPaciente);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar citas: " + e.getMessage());
        }
    }
    static void cargarCitasCSV() {
        File archivo = new File("citas.csv");
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo citas.csv, se iniciará lista vacía");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            listaCitas.clear();
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    Cita cita = new Cita();
                    cita.idCita = partes[0];
                    cita.fechaHora = partes[1];
                    cita.motivo = partes[2];

                    String idMedico = partes[3];
                    String idPaciente = partes[4];

                    // Buscar médico por id
                    cita.medico = buscarMedico(idMedico);
                    // Buscar paciente por id
                    cita.paciente = buscarPaciente(idPaciente);

                    if (cita.medico != null && cita.paciente != null) {
                        listaCitas.add(cita);
                    } else {
                        System.out.println("Error: Médico o paciente no encontrado para la cita ID " + cita.idCita);
                    }
                }
            }
            System.out.println("Citas cargadas correctamente desde citas.csv");
        } catch (IOException e) {
            System.out.println("Error al cargar citas: " + e.getMessage());
        }
    }
    static Medico buscarMedico(String idMedico) {
        for (Medico medico : listaMedico) {
            if (medico.idMedico.equals(idMedico)) {
                return medico;
            }
        }
        return null;
    }

    static Paciente buscarPaciente(String idPaciente) {
        for (Paciente paciente : listaPaciente) {
            if (paciente.idPaciente.equals(idPaciente)) {
                return paciente;
            }
        }
        return null;
    }
}




