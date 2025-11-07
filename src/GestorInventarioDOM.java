import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class GestorInventarioDOM {
    private static final Scanner scanner=new Scanner(System.in);

    public static Document cargarXML(String ruta) throws Exception {
        File xmlFile = new File(ruta);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static void mostrarInventario() throws Exception {
        Document document = cargarXML("inventario.xml");
        NodeList listaProductos = document.getElementsByTagName("producto");
        StringBuilder cadena = new StringBuilder("Inventario de La Vereda Tech:\n");

        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto = (Element) listaProductos.item(i);
            cadena.append("Producto Nº").append(i + 1).append(": ");
            cadena.append(obtenerInfoProducto(producto)).append("\n");
        }

        System.out.println(cadena);
    }

    public static void buscarProductosStockBajo(int limiteStock) throws Exception {
        Document document = cargarXML("inventario.xml");
        NodeList listaProductos = document.getElementsByTagName("producto");
        StringBuilder cadena = new StringBuilder("Inventario con stock inferior a " + limiteStock + ":\n");

        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto = (Element) listaProductos.item(i);
            int stock = Integer.parseInt(producto.getElementsByTagName("stock").item(0).getTextContent());

            if (stock < limiteStock) {
                cadena.append(obtenerInfoProducto(producto)).append("\n");
            }
        }

        System.out.println(cadena);
    }

    public static void actualizarStock(String idProducto, int nuevoStock) throws Exception {
        Document document = cargarXML("inventario.xml");

        if (!comprobarExistenciaProducto(document, idProducto)) {
            System.out.println("No se pudo actualizar el stock: el producto no existe.");
            return;
        }

        NodeList listaProductos = document.getElementsByTagName("producto");
        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto = (Element) listaProductos.item(i);
            if (producto.getAttribute("id").equals(idProducto)) {
                producto.getElementsByTagName("stock").item(0).setTextContent(String.valueOf(nuevoStock));
                break;
            }
        }

        guardarXML(document, "inventario_actualizado.xml");
        System.out.println("Stock actualizado correctamente para el producto con id: " + idProducto);
    }

    public static void anadirProducto(String id, String categoria, String nombre, String marca, double precio, int stock) throws Exception {
        Document document = cargarXML("inventario.xml");

        if (comprobarExistenciaProducto(document, id)) {
            System.out.println("Ya existe un producto con esa misma id.");
            return;
        }

        Element nuevoProducto = document.createElement("producto");
        nuevoProducto.setAttribute("id", id);

        Element categoriaNew = document.createElement("categoria");
        categoriaNew.setTextContent(categoria);
        nuevoProducto.appendChild(categoriaNew);

        Element nombreNew = document.createElement("nombre");
        nombreNew.setTextContent(nombre);
        nuevoProducto.appendChild(nombreNew);

        Element marcaNew = document.createElement("marca");
        marcaNew.setTextContent(marca);
        nuevoProducto.appendChild(marcaNew);

        Element precioNew = document.createElement("precio");
        precioNew.setTextContent(String.valueOf(precio));
        nuevoProducto.appendChild(precioNew);

        Element stockNew = document.createElement("stock");
        stockNew.setTextContent(String.valueOf(stock));
        nuevoProducto.appendChild(stockNew);

        document.getDocumentElement().appendChild(nuevoProducto);
        guardarXML(document, "inventario_actualizado.xml");

        System.out.println("Producto añadido correctamente con id: " + id);
    }

    public static void eliminarProducto(String idProducto) throws Exception {
        Document document = cargarXML("inventario.xml");

        if (!comprobarExistenciaProducto(document, idProducto)) {
            System.out.println("No existe un producto con esa id.");
            return;
        }

        Node inventario = document.getElementsByTagName("inventario").item(0);
        NodeList productos = document.getElementsByTagName("producto");

        for (int i = 0; i < productos.getLength(); i++) {
            Element producto = (Element) productos.item(i);
            if (producto.getAttribute("id").equals(idProducto)) {
                inventario.removeChild(producto);
                break;
            }
        }

        guardarXML(document, "inventario_actualizado.xml");
        System.out.println("Producto eliminado correctamente con id: " + idProducto);
    }

    public static void guardarXML(Document document, String rutaSalida) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(rutaSalida));
        transformer.transform(source, result);
    }

    private static boolean comprobarExistenciaProducto(Document document, String idProducto) {
        NodeList listaProductos = document.getElementsByTagName("producto");
        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto = (Element) listaProductos.item(i);
            if (producto.getAttribute("id").equals(idProducto)) {
                return true;
            }
        }
        return false;
    }

    private static String obtenerInfoProducto(Element producto) {
        String id = producto.getAttribute("id");
        String categoria = producto.getElementsByTagName("categoria").item(0).getTextContent();
        String nombre = producto.getElementsByTagName("nombre").item(0).getTextContent();
        String marca = producto.getElementsByTagName("marca").item(0).getTextContent();
        String precio = producto.getElementsByTagName("precio").item(0).getTextContent();
        String stock = producto.getElementsByTagName("stock").item(0).getTextContent();

        return "id: " + id + ", categoria: " + categoria + ", nombre: " + nombre +
                ", marca: " + marca + ", precio: " + precio + ", stock: " + stock;
    }

    public static String pedirDato(String mensaje, String tipo) {
        String valor = null;
        boolean valido = false;

        do {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            switch (tipo) {
                case "String":
                    if (!entrada.isEmpty()) {
                        valor = entrada;
                        valido = true;
                    } else {
                        System.out.println("El valor no puede estar vacío.");
                    }
                    break;
                case "int":
                    try {
                        int intValor = Integer.parseInt(entrada);
                        if (intValor >= 0) {
                            valor = String.valueOf(intValor);
                            valido = true;
                        } else {
                            System.out.println("El número debe ser mayor o igual a 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor introduce un número entero");
                    }
                    break;
                case "double":
                    try {
                        double doubleValor = Double.parseDouble(entrada);
                        if (doubleValor >= 0) {
                            valor = String.valueOf(doubleValor);
                            valido = true;
                        } else {
                            System.out.println("El número debe ser mayor o igual a 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debes introducir un número double válido (número con coma)");
                    }
                    break;
                default:
                    System.out.println("Tipo de dato no soportado.");
                    valido = true;
            }
        } while (!valido);

        return valor;
    }

}
