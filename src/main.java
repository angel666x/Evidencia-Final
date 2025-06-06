import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    static List<Medico> listaMedico = new ArrayList<>();
    public static void main(String[] args) {
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
                System.out.println("Alta de medico: ");
                altaMedico();
                break;
            case 4:
                System.out.println("Lista de medicos: ");
                listarMedico();
                break;
        }

    }

    static void altaMedico() {
        Medico medico = new Medico();
        Scanner entrada = new Scanner(System.in);
        System.out.println("Nombre: ");
        medico.nombre = entrada.nextLine();
        System.out.println("Especialidad: ");
        medico.especialidad = entrada.nextLine();
        listaMedico.add(medico);

    }
    static void listarMedico() {
        for (Medico medico : listaMedico) {
            System.out.println("Nombre: "+medico.nombre);
            System.out.println("Especialidad: "+medico.especialidad);
        }
    }
}
