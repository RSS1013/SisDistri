/**
 * 
 */
/**
 * 
 */
package main; // Paquete correcto

import proyecto_imc.CalculadoraIMC; // Importar la clase CalculadoraIMC
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su peso en kg: ");
        double peso = scanner.nextDouble();

        System.out.print("Ingrese su altura en metros: ");
        double altura = scanner.nextDouble();

        // Llamada al método estático de CalculadoraIMC
        double imc = CalculadoraIMC.calcularIMC(peso, altura);
        System.out.println("Su Índice de Masa Corporal (IMC) es: " + imc);

        scanner.close();
    }
}