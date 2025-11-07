import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú de Inventario ===");
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Buscar productos con stock bajo");
            System.out.println("3. Actualizar stock de un producto");
            System.out.println("4. Añadir nuevo producto");
            System.out.println("5. Eliminar un producto");
            System.out.println("6. Salir");

            String opcion = GestorInventarioDOM.pedirDato("Elige una opción (1-6): ", "int");

            try {
                switch (opcion) {
                    case "1":
                        GestorInventarioDOM.mostrarInventario();
                        break;
                    case "2":
                        int limiteStock = Integer.parseInt(GestorInventarioDOM.pedirDato(
                                "Introduce el límite de stock: ", "int"
                        ));
                        GestorInventarioDOM.buscarProductosStockBajo(limiteStock);
                        break;
                    case "3":
                        String idActualizar = GestorInventarioDOM.pedirDato(
                                "Introduce la ID del producto a actualizar: ", "String"
                        );
                        int nuevoStock = Integer.parseInt(GestorInventarioDOM.pedirDato(
                                "Introduce el nuevo stock: ", "int"
                        ));
                        GestorInventarioDOM.actualizarStock(idActualizar, nuevoStock);
                        break;
                    case "4":
                        String idNuevo = GestorInventarioDOM.pedirDato("ID del nuevo producto: ", "String");
                        String categoria = GestorInventarioDOM.pedirDato("Categoría: ", "String");
                        String nombre = GestorInventarioDOM.pedirDato("Nombre: ", "String");
                        String marca = GestorInventarioDOM.pedirDato("Marca: ", "String");
                        double precio = Double.parseDouble(GestorInventarioDOM.pedirDato("Precio: ", "double"));
                        int stock = Integer.parseInt(GestorInventarioDOM.pedirDato("Stock: ", "int"));
                        GestorInventarioDOM.anadirProducto(idNuevo, categoria, nombre, marca, precio, stock);
                        break;
                    case "5":
                        String idEliminar = GestorInventarioDOM.pedirDato("Introduce la ID del producto a eliminar: ", "String");
                        GestorInventarioDOM.eliminarProducto(idEliminar);
                        break;
                    case "6":
                        salir = true;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, elige entre 1 y 6.");
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error: " + e.getMessage());
            }
        }
    }
}
